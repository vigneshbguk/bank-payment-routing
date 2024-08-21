package com.bankingsolutions.payment.payment_routing.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import com.bankingsolutions.payment.payment_routing.entity.Connection;
import java.util.List;

@Repository
public class ConnectionDAO {

	@PersistenceContext
    private EntityManager entityManager;

    
    // Fetch all connections
    public List<Connection> findAllConnections() {
        String queryStr = "SELECT c FROM Connection c";
        TypedQuery<Connection> query = entityManager.createQuery(queryStr, Connection.class);
        return query.getResultList();
    }
}
