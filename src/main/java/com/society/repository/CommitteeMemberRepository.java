package com.society.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.society.entity.CommitteeMember;

@Repository
public interface CommitteeMemberRepository extends JpaRepository<CommitteeMember, Long> {
    // Custom query methods can be defined here if needed
	CommitteeMember findByEmail(String email);
}
