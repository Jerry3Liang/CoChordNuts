package com.ispan.recordshop.cochordnuts.dao;

import com.ispan.recordshop.cochordnuts.dto.CaseDetailDto;
import com.ispan.recordshop.cochordnuts.dto.CaseDetailRequest;
import com.ispan.recordshop.cochordnuts.dto.CustomerCaseParams;
import com.ispan.recordshop.cochordnuts.dto.MemberAnswerDto;
import com.ispan.recordshop.cochordnuts.rowmapper.CaseDetailRowMapper;
import com.ispan.recordshop.cochordnuts.rowmapper.MemberAnswerRowMapper;
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
        String sql = "SELECT cd.caseDetailNo, cd.caseNo, cd.[message], cd.messageTime, ee.empName, m.name FROM case_detail cd " +
                     "LEFT JOIN employee ee ON cd.employeeNo = ee.employeeNo " +
                     "LEFT JOIN member m ON cd.member_no = m.memberNo " +
                     "WHERE 1 = 1";

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
        String sql = "INSERT INTO case_detail ([message], messageTime, caseNo, employeeNo, member_no) " +
                     "VALUES (:answerMessage, :lastMessageDate, :customerCaseNo, :employeeNo, :memberNo)";

        Map<String, Object> map = new HashMap<>();
        map.put("answerMessage", caseDetailRequest.getAnswerMessage());
        Date now = new Date();
        map.put("lastMessageDate", now);
        map.put("customerCaseNo", caseDetailRequest.getCustomerCaseNo());
        map.put("employeeNo", caseDetailRequest.getEmployeeNo());
        map.put("memberNo", caseDetailRequest.getMemberNo());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    public void updateContent(Integer caseDetailNo, CaseDetailRequest caseDetailRequest) {
        String sql = "UPDATE case_detail SET message = :answerMessage WHERE caseDetailNo = :caseDetailNo";
        Map<String, Object> map = new HashMap<>();
        map.put("caseDetailNo", caseDetailNo);
        map.put("answerMessage", caseDetailRequest.getAnswerMessage());

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public List<CaseDetailDto> findByCaseNo(Integer caseNo) {
        String sql = "SELECT cd.caseDetailNo, cd.caseNo, cd.[message], cd.messageTime, ee.empName, m.name FROM case_detail cd " +
                     "LEFT JOIN employee ee ON cd.employeeNo = ee.employeeNo " +
                     "LEFT JOIN member m ON cd.member_no = m.memberNo " +
                     "WHERE cd.caseNo = :caseNo " +
                     "ORDER BY cd.messageTime ASC";

        Map<String, Object> map = new HashMap<>();
        map.put("caseNo", caseNo);

        return namedParameterJdbcTemplate.query(sql, map, new ShowCaseDetailRowMapper());
    }

    @Override
    public CaseDetailRequest getCaseDetailById(Integer caseDetailNo) {
        String sql = "SELECT caseDetailNo, [message], messageTime, caseNo, employeeNo, member_no FROM case_detail " +
                     " WHERE caseDetailNo = :caseDetailNo";

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
        String sql = "SELECT cd.caseDetailNo, cd.caseNo, cd.[message], cd.messageTime, ee.empName, m.name FROM case_detail cd " +
                     "LEFT JOIN employee ee ON cd.employeeNo = ee.employeeNo " +
                     "LEFT JOIN member m ON cd.member_no = m.memberNo " +
                     "WHERE cd.caseDetailNo = :caseDetailNo";

        Map<String, Object> map = new HashMap<>();
        map.put("caseDetailNo", caseDetailNo);

        List<CaseDetailDto> caseDetailDtoList = namedParameterJdbcTemplate.query(sql, map, new ShowCaseDetailRowMapper());

        if(!caseDetailDtoList.isEmpty()){
            return caseDetailDtoList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<MemberAnswerDto> findMemberAnswerByMemberNo(Integer memberNo) {
        String sql = "SELECT cd.[message], cd.messageTime, m.name FROM case_detail cd " +
                     "LEFT JOIN member m ON cd.member_no = m.memberNo " +
                     "WHERE m.memberNo = :memberNo";

        Map<String, Object> map = new HashMap<>();
        map.put("memberNo", memberNo);

        return namedParameterJdbcTemplate.query(sql, map, new MemberAnswerRowMapper());

    }
}
