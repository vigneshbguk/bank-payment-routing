package com.bankingsolutions.payment.payment_routing.dao;

import com.bankingsolutions.payment.payment_routing.model.Branch;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class BranchDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public BranchDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public Map<String, Branch> getAllBranches() {
        List<Branch> branches = getSession().createQuery("from Branch", Branch.class).getResultList();
        return branches.stream().collect(Collectors.toMap(Branch::getId, branch -> branch));
    }

    public Branch getBranchById(String id) {
        return getSession().get(Branch.class, id);
    }

    @SuppressWarnings("deprecation")
	public void saveBranch(Branch branch) {
        getSession().saveOrUpdate(branch);
    }
}
