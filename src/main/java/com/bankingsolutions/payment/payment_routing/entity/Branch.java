package com.bankingsolutions.payment.payment_routing.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String branchName;

    @Column(nullable = false)
    private int transferCost;


	public Branch() {
		super();
	}

	public Branch(String branchName, Integer transferCost) {
		super();
		this.branchName = branchName;
		this.transferCost = transferCost;
	}

	public Branch(int id, String branchName, int transferCost) {
		super();
		this.id = id;
		this.branchName = branchName;
		this.transferCost = transferCost;
	}

	// Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Integer getTransferCost() {
        return transferCost;
    }

    public void setTransferCost(Integer transferCost) {
        this.transferCost = transferCost;
    }
    @Override
    public String toString() {
    	return "Branch [branchName=" + branchName + ", transferCost=" + transferCost + "]";
    }
}
