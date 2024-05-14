package com.ispan.recordshop.cochordnuts.service.impl;

import com.ispan.recordshop.cochordnuts.dao.CaseDetailDao;
import com.ispan.recordshop.cochordnuts.dto.CaseDetailDto;
import com.ispan.recordshop.cochordnuts.dto.CustomerCaseParams;
import com.ispan.recordshop.cochordnuts.model.CaseDetail;
import com.ispan.recordshop.cochordnuts.repository.CaseDetailRepository;
import com.ispan.recordshop.cochordnuts.service.CaseDetailService;
import com.ispan.recordshop.cochordnuts.util.DatetimeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
     * @param content: 畫面接收的輸入內容
     * @return CaseDetail 物件所儲存的資料
     */
    @Override
    public CaseDetail answerContent(CaseDetailDto content) {
        if(content != null && content.getCaseDetailNo() != null){
            Optional<CaseDetail> optional = caseDetailRepository.findById(content.getCaseDetailNo());
            if(optional.isEmpty()){
                CaseDetail detail = optional.get();
                detail.setMessage(content.getMessage());
                Date temp = DatetimeConverter.parse(content.getMessageTime(), "yyyy-MM-dd HH:mm:ss");
                detail.setMessageTime(temp);

                return caseDetailRepository.save(detail);
            }
        }

        return null;
    }

    /**
     * 修改回覆內容
     * @param updateContent: 畫面接收的修改內容
     * @return CaseDetail 物件所修改的資料
     */
    @Transactional
    @Override
    public CaseDetail updateContent(CaseDetailDto updateContent) {

        Optional<CaseDetail> optional = caseDetailRepository.findById(updateContent.getCaseDetailNo());

        if(optional.isPresent()){
            CaseDetail originContent = optional.get();
            originContent.setMessage(updateContent.getMessage());
            Date temp = DatetimeConverter.parse(updateContent.getMessageTime(), "yyyy-MM-dd HH:mm:ss");
            originContent.setMessageTime(temp);
            caseDetailRepository.save(originContent);
            return originContent;
        }

        return null;
    }

    @Override
    public boolean existById(Integer id) {
        if(id != null){
            return caseDetailRepository.existsById(id);
        }

        return false;
    }
}
