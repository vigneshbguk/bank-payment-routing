package com.bankingsolutions.payment.payment_routing.dao;

import com.bankingsolutions.payment.payment_routing.model.Branch;
import com.bankingsolutions.payment.payment_routing.model.BranchCostPair;
import com.bankingsolutions.payment.payment_routing.model.TransferCost;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransferCostDAO {
    private final Session session;

    public TransferCostDAO(Session session) {
        this.session = session;
    }

    public Map<String, List<BranchCostPair>> getAllTransferCosts(Map<String, Branch> branches) {
        Map<String, List<BranchCostPair>> graph = new HashMap<>();
        Transaction transaction = session.beginTransaction();

        try {
            List<TransferCost> transferCosts = session.createQuery("from TransferCost", TransferCost.class).list();
            for (TransferCost transferCost : transferCosts) {
                String fromBranchId = transferCost.getFromBranch().getId();
                String toBranchId = transferCost.getToBranch().getId();
                int cost = transferCost.getCost();

                BranchCostPair costPair = new BranchCostPair(branches.get(toBranchId), cost);
                graph.computeIfAbsent(fromBranchId, k -> new ArrayList<>()).add(costPair);
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }

        return graph;
    }
}
