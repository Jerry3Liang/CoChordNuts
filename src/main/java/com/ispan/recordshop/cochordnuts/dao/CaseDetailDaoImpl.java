package com.ispan.recordshop.cochordnuts.dao;

import com.ispan.recordshop.cochordnuts.dto.CaseDetailDto;
import com.ispan.recordshop.cochordnuts.dto.CaseDetailRequest;
import com.ispan.recordshop.cochordnuts.dto.CustomerCaseParams;
import com.ispan.recordshop.cochordnuts.rowmapper.CaseDetailRowMapper;
import com.ispan.recordshop.cochordnuts.rowmapper.ShowCaseDetailRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CaseDetailDaoImpl implements CaseDetailDao{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<CaseDetailDto> getAnswers(CustomerCaseParams customerCaseParams) {
        String sql = "SELECT cd.case_detail_no, cd.case_no, cd.[message], cd.message_time, ee.emp_name FROM case_detail cd\n" +
                     "LEFT JOIN employee ee ON cd.employee_no = ee.employee_no WHERE 1 = 1";

        Map<String, Object> map = new HashMap<>();

        //排序
        sql = sql + " ORDER BY " +customerCaseParams.getOrderby() + " " + customerCaseParams.getSort();

        //分頁
        sql = sql + " OFFSET :offset ROWS FETCH NEXT :fetch ROWS ONLY";
        map.put("fetch", customerCaseParams.getFetch());
        map.put("offset", customerCaseParams.getOffset());

        return namedParameterJdbcTemplate.query(sql, map, new ShowCaseDetailRowMapper());
    }

    @Override
    public Integer countAnswer(CustomerCaseParams customerCaseParams) {
        String sql = "SELECT count(*) FROM case_detail WHERE 1=1";

        Map<String, Object> map = new HashMap<>();

        //查詢條件
        //sql = addFilteringSql(sql, map, customerCaseParams);

        return namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
    }

    @Override
    public Integer answerContent(CaseDetailRequest caseDetailRequest) {
        String sql = "INSERT INTO case_detail ([message], message_time, case_no, employee_no) " +
                     "VALUES (:answerMessage, :lastMessageDate, :customerCaseNo, :employeeNo)";

        Map<String, Object> map = new HashMap<>();
        map.put("answerMessage", caseDetailRequest.getAnswerMessage());
        Date now = new Date();
        map.put("lastMessageDate", now);
        map.put("customerCaseNo", caseDetailRequest.getCustomerCaseNo());
        map.put("employeeNo", caseDetailRequest.getEmployeeNo());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    public void updateContent(Integer caseDetailNo, CaseDetailRequest caseDetailRequest) {
        String sql = "UPDATE case_detail SET message = :answerMessage WHERE case_detail_no = :caseDetailNo";
        Map<String, Object> map = new HashMap<>();
        map.put("caseDetailNo", caseDetailNo);
        map.put("answerMessage", caseDetailRequest.getAnswerMessage());

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public List<CaseDetailDto> findByCaseNo(Integer caseNo) {
        String sql = "SELECT cd.case_detail_no, cd.case_no, cd.[message], cd.message_time, ee.emp_name FROM case_detail cd " +
                     "LEFT JOIN employee ee ON cd.employee_no = ee.employee_no " +
                     "WHERE cd.case_no = :caseNo " +
                     "ORDER BY cd.message_time ASC";

        Map<String, Object> map = new HashMap<>();
        map.put("caseNo", caseNo);

        return namedParameterJdbcTemplate.query(sql, map, new ShowCaseDetailRowMapper());
    }

    @Override
    public CaseDetailRequest getCaseDetailById(Integer caseDetailNo) {
        String sql = "SELECT case_detail_no, [message], message_time, case_no, employee_no FROM case_detail " +
                     " WHERE case_detail_no = :caseDetailNo";

        Map<String, Object> map = new HashMap<>();
        map.put("caseDetailNo", caseDetailNo);

        List<CaseDetailRequest> caseDetailRequestList = namedParameterJdbcTemplate.query(sql, map, new CaseDetailRowMapper());

        if(!caseDetailRequestList.isEmpty()){
            return caseDetailRequestList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public CaseDetailDto findCaseDetailById(Integer caseDetailNo) {
        String sql = "SELECT cd.case_detail_no, cd.case_no, cd.[message], cd.message_time, ee.emp_name FROM case_detail cd " +
                     "LEFT JOIN employee ee ON cd.employee_no = ee.employee_no " +
                     "WHERE cd.case_detail_no = :caseDetailNo";

        Map<String, Object> map = new HashMap<>();
        map.put("caseDetailNo", caseDetailNo);

        List<CaseDetailDto> caseDetailDtoList = namedParameterJdbcTemplate.query(sql, map, new ShowCaseDetailRowMapper());

        if(!caseDetailDtoList.isEmpty()){
            return caseDetailDtoList.get(0);
        } else {
            return null;
        }
    }
}
