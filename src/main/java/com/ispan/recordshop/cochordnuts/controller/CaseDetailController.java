package com.ispan.recordshop.cochordnuts.controller;

import com.ispan.recordshop.cochordnuts.dto.CaseDetailDto;
import com.ispan.recordshop.cochordnuts.dto.CustomerCaseParams;
import com.ispan.recordshop.cochordnuts.model.CaseDetail;
import com.ispan.recordshop.cochordnuts.service.CaseDetailService;
import com.ispan.recordshop.cochordnuts.util.Page;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
            @RequestParam(defaultValue = "asc") String sort,

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
    public ResponseEntity<?> createAnswerContent(@RequestBody CaseDetailDto detail){
        if(detail != null && detail.getCaseDetailNo() != null && detail.getCaseDetailNo() != 0){
            boolean exists = caseDetailService.existById(detail.getCaseDetailNo());
            if(!exists){
                CaseDetail caseDetail = caseDetailService.answerContent(detail);
                if(caseDetail != null){
                    String uri = "http://localhost:8080/rest/findContent/" + detail.getCaseDetailNo();
                    return ResponseEntity.created(URI.create(uri))
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(caseDetail);
                }
            }
        }

        return ResponseEntity.noContent().build();
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