package com.bankingsolutions.payment.payment_routing.model;

public class BranchCostPair {
    private final Branch branch;
    private final int cost;

    public BranchCostPair(Branch branch, int cost) {
    	this.branch = branch;
        this.cost = cost;
    }

    public Branch getBranch() {
        return branch;
    }

    public int getCost() {
        return cost;
    }
}