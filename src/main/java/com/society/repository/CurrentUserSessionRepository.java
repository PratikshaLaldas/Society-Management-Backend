package com.society.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.society.entity.CurrentUserSession;

@Repository
public interface CurrentUserSessionRepository extends JpaRepository<CurrentUserSession, Integer> {

	public CurrentUserSession findByEmail(String email);

	public CurrentUserSession findByPrivateKey(String key);
	
	Optional<CurrentUserSession> findByRole(String role);
}
