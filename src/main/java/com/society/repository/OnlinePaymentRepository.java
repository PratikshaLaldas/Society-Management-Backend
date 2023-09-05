package com.society.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.society.entity.OnlinePayment;

@Repository
public interface OnlinePaymentRepository extends JpaRepository<OnlinePayment, Long> {
    // Custom query methods can be defined here if needed
}
