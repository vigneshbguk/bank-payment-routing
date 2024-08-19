package com.bankingsolutions.payment.payment_routing.dao;

import com.bankingsolutions.payment.payment_routing.model.Branch;
import com.bankingsolutions.payment.payment_routing.model.BranchCostPair;
import com.bankingsolutions.payment.payment_routing.model.TransferCost;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TransferCostDAOTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Transaction transaction;

    private TransferCostDAO transferCostDAO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);

        transferCostDAO = new TransferCostDAO(sessionFactory);
    }

    @Test
    public void testGetAllTransferCosts() {
        Branch branchA = new Branch("A", 5);
        Branch branchB = new Branch("B", 50);
        Branch branchD = new Branch("D", 10);

        TransferCost transferCost = new TransferCost(branchA, branchB, 50);
        List<TransferCost> transferCosts = Arrays.asList(transferCost);

        when(session.createQuery("from TransferCost", TransferCost.class).list()).thenReturn(transferCosts);

        Map<String, Branch> branches = new HashMap<>();
        branches.put("A", branchA);
        branches.put("B", branchB);
        branches.put("D", branchD);

        Map<String, List<BranchCostPair>> result = transferCostDAO.getAllTransferCosts(branches);

        Map<String, List<BranchCostPair>> expected = new HashMap<>();
        expected.put("A", Collections.singletonList(new BranchCostPair(branchB, 50)));

        assertEquals(expected, result);

        verify(transaction).commit();
    }
}
