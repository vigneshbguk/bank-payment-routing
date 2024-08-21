package shortest_path_algorithm;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.bankingsolutions.payment.payment_routing.model.Branch;
import com.bankingsolutions.payment.payment_routing.model.Connection;

// Converts Result Set which was retrieved from database to apply Dijkstra Algorithm

public class BranchMapping {

    public static void branchMapping(ResultSet branch, ResultSet connections) {
        // Sample branch data
    	
    	List<Branch> rawBranch = new ArrayList<>();
    	List<Connection> rawConnection = new ArrayList<>();

    	try {
    		while (branch.next()) {
    			int id = branch.getInt("id");
                String branchName = branch.getString("branch_name");
                int transferCost = branch.getInt("transfer_cost");

                // Put the branch name and cost into the Branch List
                rawBranch.add(new Branch(id, branchName, transferCost));
                
            }
    	}catch(Exception e) {}
    	
    	
    	try {
    		while (connections.next()) {
    			int id = connections.getInt("id");
                int source = connections.getInt("from_branch_id");
                int destination = connections.getInt("to_branch_id");

                // Put the branch name and cost into the ConectionList
                rawConnection.add(new Connection(id, source, destination));
            }
    	}catch(Exception e) {}
    	
    
        // Creating a map from branch id to branch name
    	// Mapping connections to branch names
        Map<Integer, String> branchMap = rawBranch.stream()
                .collect(Collectors.toMap(Branch::getId, Branch::getBranchName));
        List<Node> transactionAllowedBranches = mapConnectionsToNodes(branchMap, rawConnection);
        
        
        //Extracting only branch names and costs
        Map<String, Integer> branchWithCost = rawBranch.stream()
        		.collect(Collectors.toMap(Branch::getBranchName, Branch::getTransferCost));
        
        WeightedDirectedGraph weightedDirectedGraph = new WeightedDirectedGraph();
        String displayResult = weightedDirectedGraph.calculateLeastCostPath(branchWithCost, transactionAllowedBranches);
        
        
        //// have to return this displayResult
    }
    
    
    public static List<Node> mapConnectionsToNodes(Map<Integer, String> branchMap, List<Connection> connections) {
    	return connections.stream()
    			.map(connection -> new Node(branchMap.get(connection.getFromBranchId()), branchMap.get(connection.getToBranchId())))
    			.collect(Collectors.toList());
    }
}
