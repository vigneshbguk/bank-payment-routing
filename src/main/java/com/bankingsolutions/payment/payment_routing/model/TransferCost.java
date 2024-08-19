package com.bankingsolutions.payment.payment_routing.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class TransferCost {

    @Id
    private Long id;

    @Column(name = "from_branch")
    private Branch sourceBranch;

    @Column(name = "to_branch")
    private Branch destinationBranch;

    @Column(name = "cost")
    private int cost;
    
    
    

    public TransferCost(Branch sourceBranch, Branch destinationBranch, int cost) {
		super();
		this.sourceBranch = sourceBranch;
		this.destinationBranch = destinationBranch;
		this.cost = cost;
	}

	// Getters and Setters
    public Branch getSourceBranch() {
        return sourceBranch;
    }

    public Branch getDestinationBranch() {
        return destinationBranch;
    }

    public int getCost() {
        return cost;
    }
}
