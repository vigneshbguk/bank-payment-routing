package com.bankingsolutions.payment.payment_routing.repository;

import org.springframework.stereotype.Repository;
import com.bankingsolutions.payment.payment_routing.entity.Branch;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;

@Repository
public class BranchDAO {

    @PersistenceContext
    private EntityManager entityManager;

    // Fetch all branches
    public List<Branch> findAllBranches() {
        String queryStr = "SELECT b FROM Branch b";
        TypedQuery<Branch> query = entityManager.createQuery(queryStr, Branch.class);
        return query.getResultList();
    }
}
