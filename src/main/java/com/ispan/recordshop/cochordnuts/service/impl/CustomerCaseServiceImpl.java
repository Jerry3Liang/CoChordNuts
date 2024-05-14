package com.ispan.recordshop.cochordnuts.service.impl;

import com.ispan.recordshop.cochordnuts.dao.CustomerCaseDao;
import com.ispan.recordshop.cochordnuts.dto.CustomerCaseDto;
import com.ispan.recordshop.cochordnuts.dto.CustomerCaseParams;
import com.ispan.recordshop.cochordnuts.model.CustomerCase;
import com.ispan.recordshop.cochordnuts.repository.CustomerCaseRepository;
import com.ispan.recordshop.cochordnuts.service.CustomerCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomerCaseServiceImpl implements CustomerCaseService {

    @Autowired
    private CustomerCaseRepository caseRepository;

    @Autowired
    private CustomerCaseDao customerCaseDao;

    /**
     * 查詢所有訂單的客服回覆
     * @return 所有訂單客服回覆清單
     */
    @Override
    public List<CustomerCaseDto> getCases(CustomerCaseParams customerCaseParams) {
        return customerCaseDao.getCases(customerCaseParams);
    }

    @Override
    public Integer countCase(CustomerCaseParams customerCaseParams) {
        return customerCaseDao.countCase(customerCaseParams);
    }

    /**
     * 根據案件編號刪除回覆案件
     * @param caseNo: 案件編號
     */
    @Override
    public boolean deleteCustomerCaseByCaseNo(Integer caseNo) {
        if(caseNo != null){
            Optional<CustomerCase> optional = caseRepository.findById(caseNo);
            if(optional.isPresent()){
                caseRepository.deleteById(caseNo);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean existById(Integer id) {
        if(id != null){
            return caseRepository.existsById(id);
        }

        return false;
    }
}
