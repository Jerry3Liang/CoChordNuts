package com.ispan.recordshop.cochordnuts.controller;

import com.ispan.recordshop.cochordnuts.dto.CaseDetailDto;
import com.ispan.recordshop.cochordnuts.dto.CaseDetailRequest;
import com.ispan.recordshop.cochordnuts.dto.CustomerCaseParams;
import com.ispan.recordshop.cochordnuts.model.CaseDetail;
import com.ispan.recordshop.cochordnuts.service.CaseDetailService;
import com.ispan.recordshop.cochordnuts.util.Page;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class CaseDetailController {

    @Autowired
    private CaseDetailService caseDetailService;

    @GetMapping("/allAnswer")
    public ResponseEntity<Page<CaseDetailDto>> findAllCase(
            //查詢條件 Filtering
            @RequestParam(required = false) String search,

            //排序 Sorting
            @RequestParam(defaultValue = "message_time") String orderby,
            @RequestParam(defaultValue = "desc") String sort,

            //分頁 Pagination
            @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer fetch,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset
    ){
        CustomerCaseParams customerCaseParams = new CustomerCaseParams();
        customerCaseParams.setSearch(search);
        customerCaseParams.setOrderby(orderby);
        customerCaseParams.setSort(sort);
        customerCaseParams.setFetch(fetch);
        customerCaseParams.setOffset(offset);

        //取得 case list
        List<CaseDetailDto> answerList = caseDetailService.getAnswers(customerCaseParams);

        //取得 case 總數
        Integer total = caseDetailService.countAnswer(customerCaseParams);

        //分頁
        Page<CaseDetailDto> page = new Page<>();
        page.setFetch(fetch);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResults(answerList);

        return ResponseEntity.ok().body(page);
    }

    @PostMapping("/Answer")
    public ResponseEntity<CaseDetail> createAnswerContent(@RequestBody CaseDetailRequest detailRequest){
        Integer caseDetailId = caseDetailService.answerContent(detailRequest);
        CaseDetail caseDetail = caseDetailService.findById(caseDetailId);

        return ResponseEntity.created(URI.create("http://localhost:8080/rest/findContent/" + caseDetailId)).body(caseDetail);
    }

    @GetMapping("/findContent/{pk}")
    public ResponseEntity<?> findAnswerById(@PathVariable(name = "pk") Integer id){
        CaseDetail caseDetail = caseDetailService.findById(id);
        if(caseDetail != null){
            return ResponseEntity.ok(caseDetail);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/findCaseContent/{caseNo}")
    public ResponseEntity<?> findAnswerByCaseNo(@PathVariable(name = "caseNo") Integer caseNo){
        List<CaseDetailDto> caseDetails = caseDetailService.findByCaseNo(caseNo);
        if(caseDetails != null){
            return ResponseEntity.ok(caseDetails);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/Answer/{pk}")
    public ResponseEntity<?> updateAnswerContent(@PathVariable(name = "pk") Integer id, @RequestBody CaseDetailDto caseDetail){
        if(caseDetail != null && caseDetail.getCaseDetailNo() != null && caseDetail.getCaseDetailNo() != 0){
            boolean exists = caseDetailService.existById(caseDetail.getCaseDetailNo());
            if(exists){
                CaseDetail detail = caseDetailService.updateContent(caseDetail);
                if(detail != null){
                    return ResponseEntity.ok(detail);
                }
            }
        }

        return ResponseEntity.notFound().build();
    }
}
