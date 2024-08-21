package com.bankingsolutions.payment.payment_routing.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Connection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int connectionId;

    @Column(nullable = false)
    private int fromBranchId;

    @Column(nullable = false)
    private int toBranchId;

    @Column(nullable = false)
    private int cost;

    
    public Connection() {}

	public Connection(int connectionId, int fromBranchId, int toBranchId, int cost) {
		super();
		this.connectionId = connectionId;
		this.fromBranchId = fromBranchId;
		this.toBranchId = toBranchId;
		this.cost = cost;
	}

	public Connection(int fromBranchId, int toBranchId, int cost) {
		super();
		this.fromBranchId = fromBranchId;
		this.toBranchId = toBranchId;
		this.cost = cost;
	}

	// Getters and setters
    public int getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(int connectionId) {
        this.connectionId = connectionId;
    }

    public int getFromBranchId() {
        return fromBranchId;
    }

    public void setFromBranchId(int fromBranchId) {
        this.fromBranchId = fromBranchId;
    }

    public int getToBranchId() {
        return toBranchId;
    }

    public void setToBranchId(int toBranchId) {
        this.toBranchId = toBranchId;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

	@Override
	public String toString() {
		return "Connection [fromBranchId=" + fromBranchId + ", toBranchId=" + toBranchId + ", cost=" + cost + "]";
	}

	
    
    
}
