package com.bankingsolutions.payment.payment_routing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bankingsolutions.payment.payment_routing.model.Branch;

public interface BranchRepository extends JpaRepository<Branch, String> {
}
