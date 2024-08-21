package com.bankingsolutions.payment.payment_routing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankingsolutions.payment.payment_routing.dao.BranchDAO;
import com.bankingsolutions.payment.payment_routing.model.Branch;
import com.bankingsolutions.payment.payment_routing.model.Connection;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private BranchDAO branchDAO;

    @Override
    public String processPayment(String originBranch, String destinationBranch) {
        // Fetch branch entities by name
        Integer startBranchId = getBranchIdByName(originBranch);
        Integer endBranchId = getBranchIdByName(destinationBranch);

        if (startBranchId == null || endBranchId == null || startBranchId == endBranchId) {
            return "Invalid Request"; // Branch not found
        }

        // Fetch all connections
        List<Connection> connections = branchDAO.findAllConnections();

        // Fetch branch names for the involved branch IDs
        Set<Integer> branchIds = connections.stream()
                .flatMap(conn -> Stream.of(conn.getFromBranchId(), conn.getToBranchId()))
                .collect(Collectors.toSet());
        Map<Integer, String> branchNames = branchDAO.findBranchNamesByIds(new ArrayList<>(branchIds));

        // Implement Dijkstra's Algorithm
        return dijkstraAlgorithm(startBranchId, endBranchId, connections, branchNames);
    }

    private Integer getBranchIdByName(String branchName) {
        List<Branch> branches = branchDAO.findAllBranches();
        for (Branch branch : branches) {
            if (branch.getBranchName().equalsIgnoreCase(branchName)) {
                return branch.getId();
            }
        }
        return null;
    }

    private String dijkstraAlgorithm(int startBranchId, int endBranchId, List<Connection> connections, Map<Integer, String> branchNames) {
        // Initialize data structures
        Map<Integer, Integer> distances = new HashMap<>();
        Map<Integer, Integer> previous = new HashMap<>();
        Set<Integer> visited = new HashSet<>();
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Node::getDistance));

        // Initialize distances
        for (Connection conn : connections) {
            distances.put(conn.getFromBranchId(), Integer.MAX_VALUE);
            distances.put(conn.getToBranchId(), Integer.MAX_VALUE);
        }

        distances.put(startBranchId, 0);
        priorityQueue.add(new Node(startBranchId, 0));

        while (!priorityQueue.isEmpty()) {
            Node currentNode = priorityQueue.poll();
            int currentBranchId = currentNode.getBranchId();

            if (currentBranchId == endBranchId) {
                return reconstructPath(previous, endBranchId, branchNames);
            }

            if (visited.contains(currentBranchId)) {
                continue;
            }
            visited.add(currentBranchId);

            for (Connection conn : connections) {
                if (conn.getFromBranchId() == currentBranchId) {
                    int neighborBranchId = conn.getToBranchId();
                    int newDist = distances.get(currentBranchId) + conn.getCost();

                    if (newDist < distances.get(neighborBranchId)) {
                        distances.put(neighborBranchId, newDist);
                        previous.put(neighborBranchId, currentBranchId);
                        priorityQueue.add(new Node(neighborBranchId, newDist));
                    }
                }
            }
        }

        return null; // No path found
    }

    private String reconstructPath(Map<Integer, Integer> previous, int endBranchId, Map<Integer, String> branchNames) {
        List<String> path = new ArrayList<>();
        Integer step = endBranchId;

        while (step != null) {
            path.add(branchNames.get(step));
            step = previous.get(step);
        }

        Collections.reverse(path);
        return String.join(",", path);
    }

    static class Node {
        private final int branchId;
        private final int distance;

        public Node(int branchId, int distance) {
            this.branchId = branchId;
            this.distance = distance;
        }

        public int getBranchId() {
            return branchId;
        }

        public int getDistance() {
            return distance;
        }
    }
}
