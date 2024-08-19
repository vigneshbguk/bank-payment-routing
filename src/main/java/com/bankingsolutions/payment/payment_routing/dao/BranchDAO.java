package com.bankingsolutions.payment.payment_routing.dao;

import com.bankingsolutions.payment.payment_routing.model.Branch;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BranchDAO {
    private final Session session;

    public BranchDAO(Session session) {
        this.session = session;
    }

    public Map<String, Branch> getAllBranches() {
        Map<String, Branch> branches = new HashMap<>();
        Transaction transaction = session.beginTransaction();

        try {
            List<Branch> branchList = session.createQuery("from Branch", Branch.class).list();
            for (Branch branch : branchList) {
                branches.put(branch.getId(), branch);
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }

        return branches;
    }
}
