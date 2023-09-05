package com.society.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.society.entity.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
	@Query("SELECT v.candidate, COUNT(v) FROM Vote v WHERE v.position = :position GROUP BY v.candidate")
    List<Object[]> findByPositionAndGroupByCandidateName(@Param("position") String position);
    Vote findByrIdAndPosition(Long rId, String position);
}