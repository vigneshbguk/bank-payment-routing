package com.bankingsolutions.payment.payment_routing.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bankingsolutions.payment.payment_routing.dao.BranchDAO;
import com.bankingsolutions.payment.payment_routing.dao.ConnectionDAO;
import com.bankingsolutions.payment.payment_routing.entity.Connection;
import com.bankingsolutions.payment.payment_routing.entity.Node;
import com.bankingsolutions.payment.payment_routing.entity.Branch;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final BranchDAO branchDAO;
    private final ConnectionDAO connectionDAO;
    
    @Autowired
    public PaymentServiceImpl(BranchDAO branchDAO, ConnectionDAO connectionDAO) {
        this.branchDAO = branchDAO;
        this.connectionDAO = connectionDAO;
    }

    @Override
    public String processPayment(String originBranch, String destinationBranch) {
        // Fetch all connections
        List<Connection> rawConnections = connectionDAO.findAllConnections();
        List<Branch> rawBranches = branchDAO.findAllBranches();

//        System.out.println("rawConnections"+rawConnections);
//        System.out.println("rawBranches"+rawBranches);
    
        
        // Refine the extracted data and Implement Dijkstra's Algorithm
        return calculateLowestCostPath(originBranch, destinationBranch, rawBranches, rawConnections);
    }
    
    public String calculateLowestCostPath(String originBranch, String destinationBranch, List<Branch> rawBranch, List<Connection> rawConnection) {
        // Creating a map from branch id to branch name
        Map<Integer, String> branchMap = rawBranch.stream()
                .collect(Collectors.toMap(Branch::getId, Branch::getBranchName));
        List<Node> transactionAllowedBranches = mapConnectionsToNodes(branchMap, rawConnection);
        
        // Extracting only branch names and costs
        Map<String, Integer> branchWithCost = rawBranch.stream()
                .collect(Collectors.toMap(Branch::getBranchName, Branch::getTransferCost));
        
        
        
        WeightedDirectedGraph weightedDirectedGraph = new WeightedDirectedGraph();
        
        
        
        String displayResult = weightedDirectedGraph.calculateLowestCostPathUsingDijkstra(originBranch, destinationBranch, branchWithCost, transactionAllowedBranches);
        
        
        return displayResult;
    }
    
    public static List<Node> mapConnectionsToNodes(Map<Integer, String> branchMap, List<Connection> connections) {
        return connections.stream()
                .map(connection -> new Node(branchMap.get(connection.getFromBranchId()), branchMap.get(connection.getToBranchId())))
                .collect(Collectors.toList());
    }
    
}
