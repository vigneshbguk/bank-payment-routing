package com.bankingsolutions.payment.payment_routing.test_service;

import com.bankingsolutions.payment.payment_routing.entity.Edge;
import com.bankingsolutions.payment.payment_routing.entity.Node;
import com.bankingsolutions.payment.payment_routing.service.WeightedDirectedGraph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeightedDirectedGraphTest {

    private WeightedDirectedGraph weightedDirectedGraph;

    @BeforeEach
    public void setUp() {
        weightedDirectedGraph = new WeightedDirectedGraph();
    }

    @Test
    public void testCalculateLowestCostPathUsingDijkstra() {
        // Arrange: Set up mock data
        Map<String, Integer> branchCosts = new HashMap<>();
        branchCosts.put("A", 10);
        branchCosts.put("B", 20);
        branchCosts.put("C", 30);

        List<Node> allowedTransactions = Arrays.asList(
                new Node("A", "B"),
                new Node("B", "C")
        );

        // Act: Call the method under test
        String result = weightedDirectedGraph.calculateLowestCostPathUsingDijkstra("A", "C", branchCosts, allowedTransactions);

        // Assert: Verify the results
        assertEquals("A,B,C (Total Cost = 30)", result);
    }

    @Test
    public void testCreateGraph() {
        // Arrange: Set up mock data
        Map<String, Integer> branchCosts = new HashMap<>();
        branchCosts.put("A", 10);
        branchCosts.put("B", 20);

        List<Node> allowedTransactions = Arrays.asList(
                new Node("A", "B")
        );

        // Act: Call the method under test
        List<Edge> graph = WeightedDirectedGraph.createGraph(branchCosts, allowedTransactions);

        // Assert: Verify the results
        assertEquals(1, graph.size());
        assertEquals("A", graph.get(0).getSource());
        assertEquals("B", graph.get(0).getDestination());
        assertEquals(10, graph.get(0).getWeight());
    }

    @Test
    public void testBuildAdjacencyList() {
        // Arrange: Set up mock data
        List<Edge> edges = Arrays.asList(
                new Edge("A", "B", 10),
                new Edge("A", "C", 20),
                new Edge("B", "C", 5)
        );

        // Act: Call the method under test
        Map<String, List<Edge>> adjacencyList = WeightedDirectedGraph.buildAdjacencyList(edges);

        // Assert: Verify the results
        assertEquals(2, adjacencyList.get("A").size());
        assertEquals(1, adjacencyList.get("B").size());
        assertEquals("B", adjacencyList.get("A").get(0).getDestination());
        assertEquals("C", adjacencyList.get("A").get(1).getDestination());
    }

    @Test
    public void testDijkstra() {
        // Arrange: Set up mock data
        Map<String, List<Edge>> adjacencyList = new HashMap<>();
        adjacencyList.put("A", Arrays.asList(new Edge("A", "B", 10), new Edge("A", "C", 20)));
        adjacencyList.put("B", Arrays.asList(new Edge("B", "C", 5)));

        // Act: Call the method under test
        String result = WeightedDirectedGraph.dijkstra(adjacencyList, "A", "C");

        // Assert: Verify the results
        assertEquals("A,B,C (Total Cost = 15)", result);
    }

    @Test
    public void testDijkstraNoPath() {
        // Arrange: Set up mock data with no path
        Map<String, List<Edge>> adjacencyList = new HashMap<>();
        adjacencyList.put("A", Arrays.asList(new Edge("A", "B", 10)));
        adjacencyList.put("B", Arrays.asList());

        // Act: Call the method under test
        String result = WeightedDirectedGraph.dijkstra(adjacencyList, "A", "C");

        // Assert: Verify the results when no path is available
        assertEquals("No Path Available", result);
    }
}
