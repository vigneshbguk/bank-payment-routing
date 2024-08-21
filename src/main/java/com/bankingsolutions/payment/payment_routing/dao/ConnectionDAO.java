package com.bankingsolutions.payment.payment_routing.dao;

import com.bankingsolutions.payment.payment_routing.model.Connection;
import com.bankingsolutions.payment.payment_routing.model.Branch;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class ConnectionDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public ConnectionDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public void saveConnection(Connection connection) {
        getSession().merge(connection); // Using merge to save or update
    }

    public Connection getConnectionById(Long id) {
        return getSession().find(Connection.class, id); // Using find instead of get
    }

    public void deleteConnection(Long id) {
        Connection connection = getConnectionById(id);
        if (connection != null) {
            getSession().remove(connection); // Using remove instead of delete
        }
    }

    public List<Connection> getAllConnections() {
        return getSession().createQuery("from Connection", Connection.class).getResultList(); // Typed query
    }

    public List<Connection> getConnectionsBySourceBranch(Branch sourceBranch) {
        return getSession().createQuery("from Connection where sourceBranch = :sourceBranch", Connection.class)
                           .setParameter("sourceBranch", sourceBranch)
                           .getResultList(); // Typed query
    }

    public List<Connection> getConnectionsByDestinationBranch(Branch destinationBranch) {
        return getSession().createQuery("from Connection where destinationBranch = :destinationBranch", Connection.class)
                           .setParameter("destinationBranch", destinationBranch)
                           .getResultList(); // Typed query
    }
}
