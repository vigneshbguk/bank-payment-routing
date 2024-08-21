package com.bankingsolutions.payment.payment_routing.test_service;


import com.bankingsolutions.payment.payment_routing.entity.Branch;
import com.bankingsolutions.payment.payment_routing.entity.Connection;
import com.bankingsolutions.payment.payment_routing.entity.Node;
import com.bankingsolutions.payment.payment_routing.repository.BranchDAO;
import com.bankingsolutions.payment.payment_routing.repository.ConnectionDAO;
import com.bankingsolutions.payment.payment_routing.service.PaymentServiceImplementation;
import com.bankingsolutions.payment.payment_routing.service.WeightedDirectedGraph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentServiceImplementationTest {

    @Mock
    private BranchDAO branchDAO;

    @Mock
    private ConnectionDAO connectionDAO;

    @InjectMocks
    private PaymentServiceImplementation paymentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcessPaymentSuccess() {
        // Arrange: Set up mock data
        Branch branch1 = new Branch(1, "Branch A", 10);
        Branch branch2 = new Branch(2, "Branch B", 20);
        Connection connection = new Connection(1, 2, 5);

        when(branchDAO.findAllBranches()).thenReturn(Arrays.asList(branch1, branch2));
        when(connectionDAO.findAllConnections()).thenReturn(Collections.singletonList(connection));

        // Mock the behavior of WeightedDirectedGraph's method
        WeightedDirectedGraph graphMock = org.mockito.Mockito.mock(WeightedDirectedGraph.class);
        when(graphMock.calculateLowestCostPathUsingDijkstra("Branch A", "Branch B", Map.of(
                "Branch A", 10,
                "Branch B", 20), Arrays.asList(
                new Node("Branch A", "Branch B"))))
                .thenReturn("Optimal path with cost 10");

        // Act: Call the method under test
        String result = paymentService.processPayment("Branch A", "Branch B");

        // Assert: Verify the results
        assertEquals("Branch A,Branch B (Total Cost = 10)", result);
    }

    @Test
    public void testProcessPaymentNoConnections() {
        // Arrange: Set up mock data with no connections
        Branch branch1 = new Branch(1, "Branch A", 10);
        Branch branch2 = new Branch(2, "Branch B", 20);

        when(branchDAO.findAllBranches()).thenReturn(Arrays.asList(branch1, branch2));
        when(connectionDAO.findAllConnections()).thenReturn(Collections.emptyList());

        // Act: Call the method under test
        String result = paymentService.processPayment("Branch A", "Branch B");

        // Assert: Verify the result when no connections are available
        assertEquals("No Path Available", result); // Adjust expected result based on actual implementation
    }

    // Additional tests for edge cases and different scenarios can be added here
}
