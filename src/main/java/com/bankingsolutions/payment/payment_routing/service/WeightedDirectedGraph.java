package com.bankingsolutions.payment.payment_routing.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.StringJoiner;
import org.springframework.stereotype.Component;
import com.bankingsolutions.payment.payment_routing.entity.Node;
import com.bankingsolutions.payment.payment_routing.entity.Edge;

@Component
public class WeightedDirectedGraph {

    public String calculateLowestCostPathUsingDijkstra(String originBranch, String destinationBranch, Map<String, Integer> branchCosts, List<Node> allowedTransactions) {
        List<Edge> graph = createGraph(branchCosts, allowedTransactions);
        Map<String, List<Edge>> adjacencyList = buildAdjacencyList(graph);
        String result = dijkstra(adjacencyList, originBranch, destinationBranch);
        return result;
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
            adjacencyList.computeIfAbsent(edge.getSource(), k -> new ArrayList<>()).add(edge);
        }
        return adjacencyList;
    }

    public static String dijkstra(Map<String, List<Edge>> adjacencyList, String start, String end) {
        
    	PriorityQueue<NodeCost> queue = new PriorityQueue<>(Comparator.comparingInt(nc -> nc.cost));
        queue.add(new NodeCost(start, 0));

        Map<String, Integer> minCost = new HashMap<>();
        minCost.put(start, 0);

        Map<String, String> previousNode = new HashMap<>();

        while (!queue.isEmpty()) {
            NodeCost current = queue.poll();
            String currentNode = current.node;

            if (currentNode.equals(end)) {
                List<String> path = reconstructPath(previousNode, start, end);
                
                StringJoiner resultCSV = new StringJoiner(",");
                
                // Add each string from the list to the StringJoiner
                for (String str : path) {
                	resultCSV.add(str);
                }
                return resultCSV +" (Total Cost = "+current.cost+")";            
            }

            if (current.cost > minCost.get(currentNode)) {
                continue;
            }

            if (adjacencyList.containsKey(currentNode)) {
                for (Edge edge : adjacencyList.get(currentNode)) {
                    int newCost = current.cost + edge.getWeight();
                    if (newCost < minCost.getOrDefault(edge.getDestination(), Integer.MAX_VALUE)) {
                        minCost.put(edge.getDestination(), newCost);
                        previousNode.put(edge.getDestination(), currentNode);
                        queue.add(new NodeCost(edge.getDestination(), newCost));
                    }
                }
            }
        }
        
        return "No Path Available";
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
