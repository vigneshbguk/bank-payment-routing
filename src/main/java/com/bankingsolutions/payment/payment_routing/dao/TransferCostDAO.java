package com.bankingsolutions.payment.payment_routing.dao;

import com.bankingsolutions.payment.payment_routing.model.Branch;
import com.bankingsolutions.payment.payment_routing.model.BranchCostPair;
import com.bankingsolutions.payment.payment_routing.model.TransferCost;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TransferCostDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public TransferCostDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    public Map<String, List<BranchCostPair>> getAllTransferCosts(Map<String, Branch> branches) {
        // Fetch all TransferCost entities from the database
        List<TransferCost> transferCosts = getSession().createQuery("from TransferCost", TransferCost.class).getResultList();

        // Create a map to store the transfer costs
        Map<String, List<BranchCostPair>> transferCostMap = new HashMap<>();

        // Iterate through the list of TransferCost entities
        for (TransferCost transferCost : transferCosts) {
            // Get source branch ID
            String sourceBranchId = transferCost.getSourceBranch().getId();
            
            // Create BranchCostPair object
            BranchCostPair costPair = new BranchCostPair(
                transferCost.getDestinationBranch(), // Destination branch
                transferCost.getCost() // Cost
            );

            // Add to the map
            transferCostMap.computeIfAbsent(sourceBranchId, k -> new ArrayList<>()).add(costPair);
        }

        return transferCostMap;
    }
}
