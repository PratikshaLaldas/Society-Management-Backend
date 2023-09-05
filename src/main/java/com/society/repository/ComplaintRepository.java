package com.society.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.society.entity.Complaint;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    // Custom query methods can be defined here if needed
	List<Complaint> findByResident_rId(Long rId);
}
