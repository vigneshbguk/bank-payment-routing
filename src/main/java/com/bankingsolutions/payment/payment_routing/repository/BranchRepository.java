package com.bankingsolutions.payment.payment_routing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bankingsolutions.payment.payment_routing.model.Branch;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Integer> {
    Branch findByBranchName(String branchName);
}
