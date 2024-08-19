package com.bankingsolutions.payment.payment_routing.dao;

import com.bankingsolutions.payment.payment_routing.model.Branch;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BranchDAOTest {

    @Mock
    private Session session;

    @Mock
    private Transaction transaction;

    private BranchDAO branchDAO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(session.beginTransaction()).thenReturn(transaction);

        branchDAO = new BranchDAO(session);
    }

    @Test
    public void testGetAllBranches() {
        Branch branchA = new Branch("A", 5);
        Branch branchB = new Branch("B", 50);

        List<Branch> branchList = Arrays.asList(branchA, branchB);

        when(session.createQuery("from Branch", Branch.class).list()).thenReturn(branchList);

        Map<String, Branch> result = branchDAO.getAllBranches();

        Map<String, Branch> expected = new HashMap<>();
        expected.put("A", branchA);
        expected.put("B", branchB);

        assertEquals(expected, result);

        verify(transaction).commit();
    }
}
