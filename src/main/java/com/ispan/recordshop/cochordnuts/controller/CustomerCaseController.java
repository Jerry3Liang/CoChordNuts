package com.ispan.recordshop.cochordnuts.controller;

import com.ispan.recordshop.cochordnuts.dto.*;
import com.ispan.recordshop.cochordnuts.service.CustomerCaseService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import com.ispan.recordshop.cochordnuts.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/rest")
public class CustomerCaseController {

    @Autowired
    private CustomerCaseService customerCaseService;

    @GetMapping("/allCase")
    public ResponseEntity<Page<CustomerCaseDto>> findAllCase(
            //查詢條件 Filtering
            @RequestParam(required = false) String search,

            //排序 Sorting
            @RequestParam(defaultValue = "messageTime") String orderby,
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
        List<CustomerCaseDto> caseList = customerCaseService.getCases(customerCaseParams);

        //取得 case 總數
        Integer total = customerCaseService.countCase(customerCaseParams);

        //分頁
        Page<CustomerCaseDto> page = new Page<>();
        page.setFetch(fetch);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResults(caseList);

        //加上 response header
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Access-Control-Allow-Origin", "*");

        return ResponseEntity.ok().body(page);
    }

    @PostMapping("/createCustomerCase")
    public ResponseEntity<CustomerCaseRequest> createCustomerCase(@RequestBody CustomerCaseRequest customerCaseRequest){
        Integer caseNo = customerCaseService.createCase(customerCaseRequest);
        CustomerCaseRequest customerCase = customerCaseService.getCaseById(caseNo);

        return ResponseEntity.created(URI.create("http://localhost:8080/rest/findCase/" + caseNo)).body(customerCase);
    }

    @GetMapping("/findCase/{pk}")
    public ResponseEntity<?> findCaseById(@PathVariable(name = "pk") Integer id){
        CustomerCaseRequest customerCase = customerCaseService.getCaseById(id);
        if(customerCase != null){
            return ResponseEntity.ok(customerCase);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/findMemberCase/{memberNo}")
    public ResponseEntity<?> findAnswerByCaseNo(@PathVariable(name = "memberNo") Integer memberNo){
        List<MemberAnswerCaseDto> memberAnswerCaseDtoList = customerCaseService.getCaseByMemberNo(memberNo);
        if(memberAnswerCaseDtoList != null){
            return ResponseEntity.ok(memberAnswerCaseDtoList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/customerCase/{pk}")
    public ResponseEntity<CustomerCaseRequest> updateCaseStatus(@PathVariable(name = "pk") Integer customerCaseNo, @RequestBody CustomerCaseRequest customerCaseRequest){
        CustomerCaseRequest caseRequest = customerCaseService.getCaseById(customerCaseNo);

        if(caseRequest == null){
            return ResponseEntity.notFound().build();
        }

        customerCaseService.updateCaseStatus(customerCaseNo, customerCaseRequest);

        CustomerCaseRequest updateCustomerCaseRequest = customerCaseService.getCaseById(customerCaseNo);

        return ResponseEntity.ok().body(updateCustomerCaseRequest);


    }

    @DeleteMapping("/customerCaseDelete/{pk}")
    public ResponseEntity<Void> remove(@PathVariable(name = "pk") Integer id){
        if(id != null && id != 0){
            boolean exists = customerCaseService.existById(id);
            if(exists){
                if(customerCaseService.deleteCustomerCaseByCaseNo(id)){
                    return ResponseEntity.noContent().build();
                }
            }
        }

        return ResponseEntity.notFound().build();
    }
}
