package com.bankingsolutions.payment.payment_routing.service;

import com.bankingsolutions.payment.payment_routing.dao.BranchDAO;
import com.bankingsolutions.payment.payment_routing.dao.TransferCostDAO;
import com.bankingsolutions.payment.payment_routing.model.Branch;
import com.bankingsolutions.payment.payment_routing.model.BranchCostPair;
import com.bankingsolutions.payment.payment_routing.model.PaymentRoute;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final Map<String, Branch> branches;
    private final Map<String, List<BranchCostPair>> graph;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    @Autowired
    public PaymentServiceImpl(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        BranchDAO branchDAO = new BranchDAO(session);
        TransferCostDAO transferCostDAO = new TransferCostDAO(session);

        this.branches = branchDAO.getAllBranches();
        this.graph = transferCostDAO.getAllTransferCosts(branches);

        session.close();
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
