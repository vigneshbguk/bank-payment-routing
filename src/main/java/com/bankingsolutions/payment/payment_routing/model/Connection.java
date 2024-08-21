package com.bankingsolutions.payment.payment_routing.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Connection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer connectionId;

    @Column(nullable = false)
    private Integer fromBranchId;

    @Column(nullable = false)
    private Integer toBranchId;

    @Column(nullable = false)
    private Integer cost;

    
    
    
    public Connection() {
		
	}

	public Connection(Integer connectionId, Integer fromBranchId, Integer toBranchId, Integer cost) {
		super();
		this.connectionId = connectionId;
		this.fromBranchId = fromBranchId;
		this.toBranchId = toBranchId;
		this.cost = cost;
	}

	public Connection(Integer fromBranchId, Integer toBranchId, Integer cost) {
		super();
		this.fromBranchId = fromBranchId;
		this.toBranchId = toBranchId;
		this.cost = cost;
	}

	// Getters and setters
    public Integer getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(Integer connectionId) {
        this.connectionId = connectionId;
    }

    public Integer getFromBranchId() {
        return fromBranchId;
    }

    public void setFromBranchId(Integer fromBranchId) {
        this.fromBranchId = fromBranchId;
    }

    public Integer getToBranchId() {
        return toBranchId;
    }

    public void setToBranchId(Integer toBranchId) {
        this.toBranchId = toBranchId;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

	@Override
	public String toString() {
		return "Connection [fromBranchId=" + fromBranchId + ", toBranchId=" + toBranchId + ", cost=" + cost + "]";
	}

	
    
    
}
