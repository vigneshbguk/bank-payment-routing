package com.bankingsolutions.payment.payment_routing.dao;


import org.springframework.stereotype.Repository;

import com.bankingsolutions.payment.payment_routing.model.Branch;
import com.bankingsolutions.payment.payment_routing.model.Connection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Repository
public class BranchDAO {

    @PersistenceContext
    private EntityManager entityManager;

    // Fetch branch by ID
    public Branch findBranchById(Integer branchId) {
        return entityManager.find(Branch.class, branchId);
    }

    // Fetch all branches
    public List<Branch> findAllBranches() {
        String queryStr = "SELECT b FROM Branch b";
        TypedQuery<Branch> query = entityManager.createQuery(queryStr, Branch.class);
        return query.getResultList();
    }

    // Fetch all connections
    public List<Connection> findAllConnections() {
        String queryStr = "SELECT c FROM Connection c";
        TypedQuery<Connection> query = entityManager.createQuery(queryStr, Connection.class);
        return query.getResultList();
    }

    // Fetch branch names by IDs
    public Map<Integer, String> findBranchNamesByIds(List<Integer> branchIds) {
        String queryStr = "SELECT b FROM Branch b WHERE b.id IN :ids";
        TypedQuery<Branch> query = entityManager.createQuery(queryStr, Branch.class);
        query.setParameter("ids", branchIds);
        List<Branch> branches = query.getResultList();

        Map<Integer, String> branchNames = new HashMap<>();
        for (Branch branch : branches) {
            branchNames.put(branch.getId(), branch.getBranchName());
        }
        return branchNames;
    }
}
