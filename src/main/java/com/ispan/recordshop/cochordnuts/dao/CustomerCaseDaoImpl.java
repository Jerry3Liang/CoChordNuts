package com.ispan.recordshop.cochordnuts.dao;

import com.ispan.recordshop.cochordnuts.dto.CustomerCaseDto;
import com.ispan.recordshop.cochordnuts.dto.CustomerCaseParams;
import com.ispan.recordshop.cochordnuts.dto.CustomerCaseRequest;
import com.ispan.recordshop.cochordnuts.dto.MemberAnswerCaseDto;
import com.ispan.recordshop.cochordnuts.rowmapper.CustomerCaseRowMapper;
import com.ispan.recordshop.cochordnuts.rowmapper.MemberCaseAnswerRowMapper;
import com.ispan.recordshop.cochordnuts.rowmapper.ShowCustomerCaseRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CustomerCaseDaoImpl implements CustomerCaseDao{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Override
    public List<CustomerCaseDto> getCases(CustomerCaseParams customerCaseParams) {
        String sql = "" +
                "WITH LatestCaseDetail AS ( " +
                "    SELECT cd.caseNo, " +
                "           cd.employeeNo, " +
                "           cd.messageTime, " +
                "           ROW_NUMBER() OVER (PARTITION BY cd.caseNo ORDER BY cd.messageTime DESC) AS rn " +
                "    FROM case_detail cd " +
                "    WHERE cd.employeeNo IS NOT NULL " +
                ") " +
                "SELECT MIN(cc.caseNo) AS caseNo, " +
                "       m.name, " +
                "       cc.subject, " +
                "       MAX(lcd.messageTime) AS messageTime, " +
                "       MIN(ee.empName) AS empName, " +
                "       cc.status " +
                "FROM customer_case cc " +
                "LEFT JOIN member m ON cc.memberNo = m.memberNo " +
                "LEFT JOIN LatestCaseDetail lcd ON cc.caseNo = lcd.caseNo AND lcd.rn = 1 " +
                "LEFT JOIN employee ee ON lcd.employeeNo = ee.employeeNo " +
                "GROUP BY cc.caseNo, cc.subject, cc.status, m.name " +
                "ORDER BY MAX(lcd.messageTime) DESC";

        Map<String, Object> map = new HashMap<>();

        //查詢條件
        sql = addFilteringSql(sql, map, customerCaseParams);

        //排序
//        sql = sql + " ORDER BY " +customerCaseParams.getOrderby() + " " + customerCaseParams.getSort();

        //分頁
        sql = sql + " OFFSET :offset ROWS FETCH NEXT :fetch ROWS ONLY";
        map.put("fetch", customerCaseParams.getFetch());
        map.put("offset", customerCaseParams.getOffset());

        return namedParameterJdbcTemplate.query(sql, map, new ShowCustomerCaseRowMapper());
    }

    @Override
    public List<CustomerCaseDto> getCaseByEmployeeNo(CustomerCaseParams customerCaseParams, Integer employeeNo) {
        String sql = "SELECT MIN(cc.caseNo) caseNo, m.name, cc.subject, MAX(cd.messageTime) messageTime, MIN(ee.empName) empName, cc.status FROM customer_case cc " +
                "LEFT JOIN member m ON cc.memberNo = m.memberNo " +
                "LEFT JOIN case_detail cd ON cc.caseNo = cd.caseNo " +
                "LEFT JOIN employee ee ON cd.employeeNo = ee.employeeNo " +
                "WHERE ee.employeeNo = :employeeNo " +
                "GROUP BY cc.caseNo, cc.subject, cc.status, m.name";

        Map<String, Object> map = new HashMap<>();
        map.put("employeeNo", employeeNo);

        //查詢條件
        sql = addFilteringSql(sql, map, customerCaseParams);

        //排序
        sql = sql + " ORDER BY " +customerCaseParams.getOrderby() + " " + customerCaseParams.getSort();

        //分頁
        sql = sql + " OFFSET :offset ROWS FETCH NEXT :fetch ROWS ONLY";
        map.put("fetch", customerCaseParams.getFetch());
        map.put("offset", customerCaseParams.getOffset());

        return namedParameterJdbcTemplate.query(sql, map, new ShowCustomerCaseRowMapper());
    }

    @Override
    public Integer countCase(CustomerCaseParams customerCaseParams) {
        String sql = "SELECT count(*) FROM customer_case WHERE 1=1";

        Map<String, Object> map = new HashMap<>();

        //查詢條件
        sql = addFilteringSql(sql, map, customerCaseParams);

        Integer total = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);

        return total;
    }

    @Override
    public Integer createCase(CustomerCaseRequest customerCaseRequest) {
        String sql = "INSERT INTO customer_case (subject, status, memberNo, orderNo) " +
                     "VALUES (:subject, :status, :memberNo, :orderNo)";

        Map<String, Object> map = new HashMap<>();
        map.put("subject", customerCaseRequest.getSubject());
        map.put("status", 0);
        map.put("memberNo", customerCaseRequest.getMemberNo());
        map.put("orderNo", customerCaseRequest.getOrderNo());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    public CustomerCaseRequest getCaseById(Integer customerCaseNo) {
        String sql = "SELECT caseNo, subject, status, orderNo, memberNo FROM customer_case " +
                     "WHERE caseNo = :customerCaseNo";

        Map<String, Object> map = new HashMap<>();
        map.put("customerCaseNo", customerCaseNo);

        List<CustomerCaseRequest> caseRequestList = namedParameterJdbcTemplate.query(sql, map, new CustomerCaseRowMapper());

        if(!caseRequestList.isEmpty()){
            return caseRequestList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<MemberAnswerCaseDto> getCaseByMemberNo(Integer memberNo) {
        String sql = "SELECT MIN(cc.caseNo) caseNo, m.name, cc.subject, MAX(cd.messageTime) messageTime, MIN(ee.empName) empName, cc.status FROM customer_case cc " +
                     "LEFT JOIN member m ON cc.memberNo = m.memberNo " +
                     "LEFT JOIN case_detail cd ON cc.caseNo = cd.caseNo " +
                     "LEFT JOIN employee ee ON cd.employeeNo = ee.employeeNo " +
                     "WHERE cc.memberNo = :memberNo " +
                     "GROUP BY cc.caseNo, cc.subject, cc.status, m.name";

        Map<String, Object> map = new HashMap<>();
        map.put("memberNo", memberNo);

        return namedParameterJdbcTemplate.query(sql, map, new MemberCaseAnswerRowMapper());
    }

    @Override
    public boolean caseExitByOrderNo(Integer orderNo) {
        String sql = "SELECT * FROM customer_case " +
                     "WHERE orderNo = :orderNo";
        Map<String, Object> map = new HashMap<>();
        map.put("orderNo", orderNo);

        List<CustomerCaseRequest> caseRequest = namedParameterJdbcTemplate.query(sql, map, new CustomerCaseRowMapper());

        if(caseRequest.isEmpty()){
            return false;
        }

        return true;
    }

    @Transactional
    @Override
    public void updateCaseStatus(Integer customerCaseNo, CustomerCaseRequest customerCaseRequest) {
        String sql = "UPDATE customer_case SET status = :status WHERE caseNo = :caseNo";

        Map<String, Object> map = new HashMap<>();
        map.put("caseNo", customerCaseNo);
        map.put("status", customerCaseRequest.getStatus());

        namedParameterJdbcTemplate.update(sql, map);
    }

    private String addFilteringSql(String sql, Map<String, Object> map, CustomerCaseParams customerCaseParams){

        if(customerCaseParams.getSearch() != null){
            sql = sql + " AND ee.name LIKE :search";
            map.put("search", "%" + customerCaseParams.getSearch() + "%");
        }

        return sql;
    }

}
