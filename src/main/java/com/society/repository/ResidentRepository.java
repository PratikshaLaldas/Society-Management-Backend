package com.society.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.society.entity.Resident;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, Long> {
    // Custom query methods can be defined here if needed
	
	Resident findByEmail(String email);
	
	Optional<Resident> findOptionalByEmail(String email);

	List<Resident> findByWingNo(Integer wingNo);
	
}