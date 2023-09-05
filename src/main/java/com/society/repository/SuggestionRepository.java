package com.society.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.society.entity.Complaint;
import com.society.entity.Suggestion;

@Repository
public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {
    // Custom query methods can be defined here if needed
	List<Suggestion> findByResident_rId(Long rId);
}