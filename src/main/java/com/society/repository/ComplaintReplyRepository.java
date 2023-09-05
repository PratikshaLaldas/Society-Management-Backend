package com.society.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.society.entity.ComplaintReply;

@Repository
public interface ComplaintReplyRepository extends JpaRepository<ComplaintReply, Long> {
    // Custom query methods can be defined here if needed
	List<ComplaintReply> findByResident_rId(Long rId);

	boolean existsByComplaint_Cid(Long cid);
}
