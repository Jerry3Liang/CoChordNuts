package com.ispan.recordshop.cochordnuts.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ispan.recordshop.cochordnuts.dao.MemberDao;
import com.ispan.recordshop.cochordnuts.model.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer>, MemberDao {

    @Query("select count(*) from Member where email = :email")
    public long countByEmail(String email);

    @Query("select count(*) from Member where phone = :phone")
    public long countByPhone(String phone);

    Optional<Member> findByEmailAndPhone(String email, String phone);

    Optional<Member> findByEmail(String email);

}
