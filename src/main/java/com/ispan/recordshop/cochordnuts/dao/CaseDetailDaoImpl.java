package com.ispan.recordshop.cochordnuts.dao;

import com.ispan.recordshop.cochordnuts.dto.CaseDetailDto;
import com.ispan.recordshop.cochordnuts.dto.CustomerCaseParams;
import com.ispan.recordshop.cochordnuts.rowmapper.CaseDetailRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

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

        List<CaseDetailDto> anwserList = namedParameterJdbcTemplate.query(sql, map, new CaseDetailRowMapper());

        return anwserList;
    }

    @Override
    public Integer countAnswer(CustomerCaseParams customerCaseParams) {
        String sql = "SELECT count(*) FROM case_detail WHERE 1=1";

        Map<String, Object> map = new HashMap<>();

        //查詢條件
        //sql = addFilteringSql(sql, map, customerCaseParams);

        Integer total = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);

        return total;
    }
}
