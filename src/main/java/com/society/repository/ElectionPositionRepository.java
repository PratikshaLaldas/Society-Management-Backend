package com.society.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.society.entity.ElectionPosition;

@Repository
public interface ElectionPositionRepository extends JpaRepository<ElectionPosition, Long> {
	ElectionPosition findByPosition(String position);
	@Transactional
	void deleteByPosition(String position);
}