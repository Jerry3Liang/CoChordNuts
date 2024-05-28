package com.ispan.recordshop.cochordnuts.repository;

import com.ispan.recordshop.cochordnuts.dto.CustomerCaseRequest;
import com.ispan.recordshop.cochordnuts.model.CustomerCase;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerCaseRepository extends JpaRepository<CustomerCase, Integer> {

    @Query("from CustomerCase c where c.customerCaseMember.memberNo = :memberNo")
    List<CustomerCase> findByMemberNo(Integer memberNo);
}
