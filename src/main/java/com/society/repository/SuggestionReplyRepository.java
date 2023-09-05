package com.society.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.society.entity.SuggestionReply;

@Repository
public interface SuggestionReplyRepository extends JpaRepository<SuggestionReply, Long> {

	boolean existsBySuggestion_sid(Long sid);
    // Custom query methods can be defined here if needed

	List<SuggestionReply> findByResident_rId(Long rId);

}