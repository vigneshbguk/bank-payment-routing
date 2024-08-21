package com.bankingsolutions.payment.payment_routing.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bankingsolutions.payment.payment_routing.repository.BranchDAO;
import com.bankingsolutions.payment.payment_routing.entity.Connection;
import com.bankingsolutions.payment.payment_routing.entity.Node;
import com.bankingsolutions.payment.payment_routing.repository.ConnectionDAO;
import com.bankingsolutions.payment.payment_routing.entity.Branch;

@Service
public class PaymentServiceImplementation implements PaymentService {

    private final BranchDAO branchDAO;
    private final ConnectionDAO connectionDAO;
    private final ReentrantLock lock = new ReentrantLock();
    
    @Autowired
    public PaymentServiceImplementation(BranchDAO branchDAO, ConnectionDAO connectionDAO) {
        this.branchDAO = branchDAO;
        this.connectionDAO = connectionDAO;
    }
    
    
    public static boolean inputValidation(String input) {
    	// Return true if the input is null or empty
        if (input == null || input.trim().isEmpty()) {
            return true;
        }
        String regex = "^[a-zA-Z0-9]+$";
        // Return true if input matches the regex, false otherwise
        return !input.matches(regex);
    }
    
    @Override
    public String processPayment(String originBranch, String destinationBranch) {
    	
        String result = new String("Unknown Exception");
    	lock.lock();
	    try {	
	    	originBranch = originBranch.toUpperCase();
	    	destinationBranch = destinationBranch.toUpperCase();
	    	
	    	if(inputValidation(originBranch) && inputValidation(destinationBranch)){
	    		return "Source and destination branches must be valid.";
	    	}
	    	
	    	// Fetch all connections
	        List<Connection> rawConnections = connectionDAO.findAllConnections();
	        List<Branch> rawBranches = branchDAO.findAllBranches();
	        
	        
	        // Refine the extracted data and Implement Dijkstra's Algorithm
	        result = calculateLowestCostPath(originBranch, destinationBranch, rawBranches, rawConnections);
	        
	    }catch(Exception E){}
	    
	    finally {
	    	lock.unlock();
	    }
	    return result;
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
