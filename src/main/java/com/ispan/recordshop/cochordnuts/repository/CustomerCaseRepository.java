package com.ispan.recordshop.cochordnuts.repository;

import com.ispan.recordshop.cochordnuts.model.CustomerCase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerCaseRepository extends JpaRepository<CustomerCase, Integer> {
}
