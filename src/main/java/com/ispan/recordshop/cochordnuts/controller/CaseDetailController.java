package com.ispan.recordshop.cochordnuts.controller;

import com.ispan.recordshop.cochordnuts.dto.*;
import com.ispan.recordshop.cochordnuts.service.CaseDetailService;
import com.ispan.recordshop.cochordnuts.service.CustomerCaseService;
import com.ispan.recordshop.cochordnuts.util.Page;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/rest")
public class CaseDetailController {

    @Autowired
    private CaseDetailService caseDetailService;

    @Autowired
    private CustomerCaseService caseService;

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
    public ResponseEntity<CaseDetailRequest> createAnswerContent(@RequestBody CaseDetailRequest detailRequest){
        Integer caseDetailId = caseDetailService.answerContent(detailRequest);
        CaseDetailRequest caseDetailRequest = caseDetailService.getCaseDetailById(caseDetailId);
        CustomerCaseRequest customerCaseRequest = caseService.getCaseById(caseDetailRequest.getCustomerCaseNo());
        customerCaseRequest.setStatus(1);

        caseService.updateCaseStatus(customerCaseRequest.getCustomerCaseNo(), customerCaseRequest);

        return ResponseEntity.created(URI.create("http://localhost:8080/rest/findContent/" + caseDetailId)).body(caseDetailRequest);
    }

    @GetMapping("/findContent/{pk}")
    public ResponseEntity<?> findAnswerById(@PathVariable(name = "pk") Integer id){
        CaseDetailDto caseDetailDto = caseDetailService.findCaseDetailById(id);
        if(caseDetailDto != null){
            return ResponseEntity.ok(caseDetailDto);
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

    @GetMapping("/findMemberAnswer/{memberNo}")
    public ResponseEntity<?> findMemberAnswerByMemberNo(@PathVariable(name = "memberNo") Integer memberNo){
        List<MemberAnswerDto> memberAnswerDto = caseDetailService.findMemberAnswerByMemberNo(memberNo);
        for(MemberAnswerDto member : memberAnswerDto){
            System.out.println(member.getMemberName());
        }
        if(memberAnswerDto != null){
            return ResponseEntity.ok(memberAnswerDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/Answer/{pk}")
    public ResponseEntity<?> updateAnswerContent(@PathVariable(name = "pk") Integer id, @RequestBody CaseDetailRequest caseDetailRequest){
        CaseDetailRequest detailRequest = caseDetailService.getCaseDetailById(id);

        if(detailRequest == null){
            return ResponseEntity.notFound().build();
        }

        caseDetailService.updateContent(id, caseDetailRequest);

        CaseDetailRequest updateCaseDetailRequest = caseDetailService.getCaseDetailById(id);

        return ResponseEntity.ok().body(updateCaseDetailRequest);
    }

    @DeleteMapping("/caseAnswerDelete/{pk}")
    public ResponseEntity<Void> remove(@PathVariable(name = "pk") Integer caseDetailNo){
        if(caseDetailNo != null && caseDetailNo != 0){
            boolean exists = caseDetailService.existById(caseDetailNo);
            if(exists){
                if(caseDetailService.deleteCaseAnswerByCaseDetailNo(caseDetailNo)){
                    return ResponseEntity.noContent().build();
                }
            }
        }

        return ResponseEntity.notFound().build();
    }

}
