package com.bankingsolutions.payment.payment_routing.service;

import com.bankingsolutions.payment.payment_routing.dao.BranchDAO;
import com.bankingsolutions.payment.payment_routing.dao.TransferCostDAO;
import com.bankingsolutions.payment.payment_routing.model.Branch;
import com.bankingsolutions.payment.payment_routing.model.BranchCostPair;
import com.bankingsolutions.payment.payment_routing.model.PaymentRoute;

//import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final BranchDAO branchDAO;
    private final TransferCostDAO transferCostDAO;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private Map<String, Branch> branches;
    private Map<String, List<BranchCostPair>> graph;

    @Autowired
    public PaymentServiceImpl(BranchDAO branchDAO, TransferCostDAO transferCostDAO) {
        this.branchDAO = branchDAO;
        this.transferCostDAO = transferCostDAO;
        loadGraph();
    }

    private void loadGraph() {
        this.branches = branchDAO.getAllBranches();
        this.graph = transferCostDAO.getAllTransferCosts(branches);
    }

    @Override
    public String processPayment(String originBranch, String destinationBranch) {
        lock.readLock().lock();
        try {
            return findCheapestRoute(originBranch, destinationBranch);
        } finally {
            lock.readLock().unlock();
        }
    }

    private String findCheapestRoute(String start, String end) {
        PriorityQueue<PaymentRoute> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(PaymentRoute::getTotalCost));
        Map<String, Integer> minCostMap = new HashMap<>();
        Map<String, List<String>> paths = new HashMap<>();

        priorityQueue.add(new PaymentRoute(Collections.singletonList(start), 0));
        minCostMap.put(start, 0);

        while (!priorityQueue.isEmpty()) {
            PaymentRoute current = priorityQueue.poll();
            String currentBranch = current.getBranches().get(current.getBranches().size() - 1);

            if (currentBranch.equals(end)) {
                return String.join(",", current.getBranches());
            }

            for (BranchCostPair neighbor : graph.getOrDefault(currentBranch, Collections.emptyList())) {
                String nextBranch = neighbor.getBranch().getId();
                int newCost = current.getTotalCost() + neighbor.getCost();

                if (newCost < minCostMap.getOrDefault(nextBranch, Integer.MAX_VALUE)) {
                    minCostMap.put(nextBranch, newCost);
                    List<String> newPath = new ArrayList<>(current.getBranches());
                    newPath.add(nextBranch);
                    priorityQueue.add(new PaymentRoute(newPath, newCost));
                    paths.put(nextBranch, newPath);
                }
            }
        }

        return null; // No path found
    }
}
