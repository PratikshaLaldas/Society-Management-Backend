package com.society.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.society.entity.Account;
import com.society.entity.Resident;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	List<Account> findByResident_rId(Long rId);
}
