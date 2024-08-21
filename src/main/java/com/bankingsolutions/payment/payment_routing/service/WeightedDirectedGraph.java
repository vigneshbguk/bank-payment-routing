package com.bankingsolutions.payment.payment_routing.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import org.springframework.stereotype.Component;
import com.bankingsolutions.payment.payment_routing.entity.Node;
import com.bankingsolutions.payment.payment_routing.entity.Edge;
import com.bankingsolutions.payment.payment_routing.entity.PathWithCost;

@Component
public class WeightedDirectedGraph {

    public String calculateLowestCostPathUsingDijkstra(String originBranch, String destinationBranch, Map<String, Integer> branchCosts, List<Node> allowedTransactions) {
        List<Edge> graph = createGraph(branchCosts, allowedTransactions);
        Map<String, List<Edge>> adjacencyList = buildAdjacencyList(graph);
        PathWithCost result = dijkstra(adjacencyList, originBranch, destinationBranch);
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
            adjacencyList.computeIfAbsent(edge.getSource(), k -> new ArrayList<>()).add(edge);
        }
        return adjacencyList;
    }

    public static PathWithCost dijkstra(Map<String, List<Edge>> adjacencyList, String start, String end) {
        
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
                return new PathWithCost(path, current.cost);               //////////// remove toString()
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
        
        //System.out.println("--------------------------------\n\n" + adjacencyList + "\n\n"+"*********************");
        
        return new PathWithCost(Arrays.asList(), 999);
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
