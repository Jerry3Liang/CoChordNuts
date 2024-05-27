package com.ispan.recordshop.cochordnuts.service.impl;

import com.ispan.recordshop.cochordnuts.dao.CaseDetailDao;
import com.ispan.recordshop.cochordnuts.dto.CaseDetailDto;
import com.ispan.recordshop.cochordnuts.dto.CaseDetailRequest;
import com.ispan.recordshop.cochordnuts.dto.CustomerCaseParams;
import com.ispan.recordshop.cochordnuts.model.CaseDetail;
import com.ispan.recordshop.cochordnuts.model.CustomerCase;
import com.ispan.recordshop.cochordnuts.repository.CaseDetailRepository;
import com.ispan.recordshop.cochordnuts.service.CaseDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class CaseDetailServiceImpl implements CaseDetailService {

    @Autowired
    private CaseDetailRepository caseDetailRepository;

    @Autowired
    private CaseDetailDao caseDetailDao;

    @Override
    public List<CaseDetailDto> getAnswers(CustomerCaseParams customerCaseParams) {
        return caseDetailDao.getAnswers(customerCaseParams);
    }

    @Override
    public Integer countAnswer(CustomerCaseParams customerCaseParams) {
        return caseDetailDao.countAnswer(customerCaseParams);
    }

    /**
     * 新增回覆內容
     * @param caseRequest: 畫面接收的輸入內容
     * @return CaseDetail 物件所儲存的資料
     */
    @Override
    public Integer answerContent(CaseDetailRequest caseRequest) {
        return caseDetailDao.answerContent(caseRequest);
    }


    @Transactional
    @Override
    public void updateContent(Integer caseDetailNo, CaseDetailRequest caseDetailRequest) {
        caseDetailDao.updateContent(caseDetailNo, caseDetailRequest);
    }


    @Override
    public CaseDetailRequest getCaseDetailById(Integer caseDetailNo) {
        return caseDetailDao.getCaseDetailById(caseDetailNo);
    }

    @Override
    public CaseDetailDto findCaseDetailById(Integer caseDetailNo) {
        return caseDetailDao.findCaseDetailById(caseDetailNo);
    }

    @Override
    public CaseDetail findById(Integer id) {
        if(id != null){
            Optional<CaseDetail> optional = caseDetailRepository.findById(id);
            if(optional.isPresent()){
                return optional.get();
            }
        }

        return null;
    }

    @Override
    public List<CaseDetailDto> findByCaseNo(Integer caseNo) {
        if(caseNo != null){
            return caseDetailDao.findByCaseNo(caseNo);
        }

        return null;
    }

    @Override
    public boolean deleteCaseAnswerByCaseDetailNo(Integer caseDetailNo) {
        if(caseDetailNo != null){
            Optional<CaseDetail> optional = caseDetailRepository.findById(caseDetailNo);
            if(optional.isPresent()){
                caseDetailRepository.deleteById(caseDetailNo);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean existById(Integer id) {
        if(id != null){
            return caseDetailRepository.existsById(id);
        }

        return false;
    }
}
