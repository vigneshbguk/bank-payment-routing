package shortest_path_algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class WeightedDirectedGraph {

	public String calculateLeastCostPath(Map<String, Integer> branchCosts, List<Node> allowedTransactions) {
		
		List<Edge> graph = createGraph(branchCosts, allowedTransactions);
		Map<String, List<Edge>> adjacencyList = buildAdjacencyList(graph);
		
		String start = "A";
		String end = "D";
		PathWithCost result = dijkstra(adjacencyList, start, end);
		
		//System.out.println("Shortest path from " + start + " to " + end + ": " + result);
		return result.toString();
	}
    
	
	public static List<Edge> createGraph(Map<String, Integer> branchCosts, List<Node> allowedTransactions) {

    	
    	List<Edge> graph = new ArrayList<>();
        for (Node entry : allowedTransactions) {
            String source = entry.getSource();
            String destination = entry.getDestination();
            int cost = branchCosts.get(source);
            graph.add(new Edge(source, destination, cost));
        }
        return graph;
    }

    public static Map<String, List<Edge>> buildAdjacencyList(List<Edge> edges) {
        Map<String, List<Edge>> adjacencyList = new HashMap<>();
        for (Edge edge : edges) {
            adjacencyList.computeIfAbsent(edge.source, k -> new ArrayList<>()).add(edge);
        }
        return adjacencyList;
    }

    public static PathWithCost dijkstra(Map<String, List<Edge>> adjacencyList, String start, String end) {
        // Priority queue to select the node with the smallest distance
        PriorityQueue<NodeCost> queue = new PriorityQueue<>(Comparator.comparingInt(nc -> nc.cost));
        queue.add(new NodeCost(start, 0));

        // Map to store the minimum cost to reach each node
        Map<String, Integer> minCost = new HashMap<>();
        minCost.put(start, 0);

        // Map to track the path
        Map<String, String> previousNode = new HashMap<>();

        while (!queue.isEmpty()) {
            NodeCost current = queue.poll();
            String currentNode = current.node;

            // If the destination is reached, reconstruct the path
            if (currentNode.equals(end)) {
                List<String> path = reconstructPath(previousNode, start, end);
                return new PathWithCost(path, current.cost);
            }

            // Skip processing if we've found a cheaper path already
            if (current.cost > minCost.get(currentNode)) {
                continue;
            }

            // Explore neighbors
            if (adjacencyList.containsKey(currentNode)) {
                for (Edge edge : adjacencyList.get(currentNode)) {
                    int newCost = current.cost + edge.weight;
                    if (newCost < minCost.getOrDefault(edge.destination, Integer.MAX_VALUE)) {
                        minCost.put(edge.destination, newCost);
                        previousNode.put(edge.destination, currentNode);
                        queue.add(new NodeCost(edge.destination, newCost));
                    }
                }
            }
        }

        // If no path is found
        return new PathWithCost(Collections.emptyList(), -1);
    }

    private static List<String> reconstructPath(Map<String, String> previousNode, String start, String end) {
        List<String> path = new ArrayList<>();
        for (String at = end; at != null; at = previousNode.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }
    
}

class NodeCost {
    String node;
    int cost;

    NodeCost(String node, int cost) {
        this.node = node;
        this.cost = cost;
    }
}