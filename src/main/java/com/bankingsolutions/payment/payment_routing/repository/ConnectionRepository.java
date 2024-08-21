package com.bankingsolutions.payment.payment_routing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bankingsolutions.payment.payment_routing.model.Connection;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Integer> {
}
