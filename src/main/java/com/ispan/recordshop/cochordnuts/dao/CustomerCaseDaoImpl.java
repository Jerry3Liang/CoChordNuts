package com.ispan.recordshop.cochordnuts.dao;

import com.ispan.recordshop.cochordnuts.dto.CustomerCaseDto;
import com.ispan.recordshop.cochordnuts.dto.CustomerCaseParams;
import com.ispan.recordshop.cochordnuts.rowmapper.CustomerCaseRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CustomerCaseDaoImpl implements CustomerCaseDao{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Override
    public List<CustomerCaseDto> getCases(CustomerCaseParams customerCaseParams) {
        String sql = "SELECT cc.case_no, cc.member_name, cc.subject, cd.message_time, ee.name, cc.status FROM customer_case cc " +
                "LEFT JOIN case_detail cd ON cc.case_no = cd.case_no " +
                "LEFT JOIN employee ee ON cd.employee_no = ee.employee_no WHERE 1 = 1";

        Map<String, Object> map = new HashMap<>();

        //查詢條件
        sql = addFilteringSql(sql, map, customerCaseParams);

        //排序
        sql = sql + " ORDER BY " +customerCaseParams.getOrderby() + " " + customerCaseParams.getSort();

        //分頁
        sql = sql + " OFFSET :offset ROWS FETCH NEXT :fetch ROWS ONLY";
        map.put("fetch", customerCaseParams.getFetch());
        map.put("offset", customerCaseParams.getOffset());

        List<CustomerCaseDto> caseList = namedParameterJdbcTemplate.query(sql, map, new CustomerCaseRowMapper());

        return caseList;
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

    private String addFilteringSql(String sql, Map<String, Object> map, CustomerCaseParams customerCaseParams){

        if(customerCaseParams.getSearch() != null){
            sql = sql + " AND ee.name LIKE :search";
            map.put("search", "%" + customerCaseParams.getSearch() + "%");
        }

        return sql;
    }

}
