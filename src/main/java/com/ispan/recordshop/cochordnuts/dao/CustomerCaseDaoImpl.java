package com.ispan.recordshop.cochordnuts.dao;

import com.ispan.recordshop.cochordnuts.dto.CustomerCaseDto;
import com.ispan.recordshop.cochordnuts.dto.CustomerCaseParams;
import com.ispan.recordshop.cochordnuts.dto.CustomerCaseRequest;
import com.ispan.recordshop.cochordnuts.rowmapper.CustomerCaseRowMapper;
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
        String sql = "SELECT MIN(cc.case_no) case_no, m.name, cc.subject, MAX(cd.message_time) message_time, MIN(ee.emp_name) emp_name, cc.status FROM customer_case cc " +
                "LEFT JOIN member m ON cc.member_no = m.member_no " +
                "LEFT JOIN case_detail cd ON cc.case_no = cd.case_no " +
                "LEFT JOIN employee ee ON cd.employee_no = ee.employee_no " +
                "GROUP BY cc.case_no, cc.subject, cc.status, m.name";

        Map<String, Object> map = new HashMap<>();

        //查詢條件
        sql = addFilteringSql(sql, map, customerCaseParams);

        //排序
        sql = sql + " ORDER BY " +customerCaseParams.getOrderby() + " " + customerCaseParams.getSort();
//
//        //分頁
//        sql = sql + " OFFSET :offset ROWS FETCH NEXT :fetch ROWS ONLY";
//        map.put("fetch", customerCaseParams.getFetch());
//        map.put("offset", customerCaseParams.getOffset());

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
        String sql = "INSERT INTO customer_case (subject, status, member_no, order_no) " +
                     "VALUES (:subject, :status, :memberNo, :orderNo)";

        Map<String, Object> map = new HashMap<>();
        map.put("subject", customerCaseRequest.getSubject());
        map.put("status", customerCaseRequest.getStatus());
        map.put("memberNo", customerCaseRequest.getMemberNo());
        map.put("orderNo", customerCaseRequest.getOrderNo());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    public CustomerCaseRequest getCaseById(Integer customerCaseNo) {
        String sql = "SELECT case_no, subject, status, order_no, member_no FROM customer_case " +
                     "WHERE case_no = :customerCaseNo";

        Map<String, Object> map = new HashMap<>();
        map.put("customerCaseNo", customerCaseNo);

        List<CustomerCaseRequest> caseRequestList = namedParameterJdbcTemplate.query(sql, map, new CustomerCaseRowMapper());

        if(!caseRequestList.isEmpty()){
            return caseRequestList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    @Override
    public void updateCaseStatus(Integer customerCaseNo, CustomerCaseRequest customerCaseRequest) {
        String sql = "UPDATE customer_case SET status = :status WHERE case_no = :caseNo";

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
