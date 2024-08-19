package com.bankingsolutions.payment.payment_routing.service;

import com.bankingsolutions.payment.payment_routing.dao.BranchDAO;
import com.bankingsolutions.payment.payment_routing.dao.TransferCostDAO;
import com.bankingsolutions.payment.payment_routing.model.Branch;
import com.bankingsolutions.payment.payment_routing.model.BranchCostPair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PaymentServiceImplTest {

    @Mock
    private BranchDAO branchDAO;

    @Mock
    private TransferCostDAO transferCostDAO;

    private PaymentServiceImpl paymentService;

    private Map<String, Branch> branches;
    private Map<String, List<BranchCostPair>> graph;

    @SuppressWarnings("deprecation")
	@BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        branches = new HashMap<>();
        branches.put("A", new Branch("A", 5));
        branches.put("B", new Branch("B", 50));
        branches.put("C", new Branch("C", 10));
        branches.put("D", new Branch("D", 10));

        graph = new HashMap<>();
        graph.put("A", Arrays.asList(new BranchCostPair(branches.get("B"), 50)));
        graph.put("B", Arrays.asList(new BranchCostPair(branches.get("D"), 10)));
        graph.put("C", Arrays.asList(new BranchCostPair(branches.get("B"), 10)));
        graph.put("D", new ArrayList<>());

        when(branchDAO.getAllBranches()).thenReturn(branches);
        when(transferCostDAO.getAllTransferCosts(branches)).thenReturn(graph);

        paymentService = new PaymentServiceImpl(branchDAO, transferCostDAO);
    }

    @Test
    public void testProcessPayment() {
        String result = paymentService.processPayment("A", "D");
        assertEquals("A,B,D", result);
    }

    @Test
    public void testNoAvailableRoute() {
        String result = paymentService.processPayment("A", "C");
        assertEquals(null, result);
    }
}
