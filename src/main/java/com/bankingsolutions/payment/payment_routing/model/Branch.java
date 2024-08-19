package com.bankingsolutions.payment.payment_routing.model;

/**
 * POJO class
 * Bank branch in the system.
 * Each branch has a name and a cost associated with transferring funds from that branch.
 */
public class Branch {
    
    // Name of the branch
    private String name;
    
    // Cost of transferring funds from this branch.
    private int transferCost;

    // Constructor to initialize values
    public Branch(String name, int transferCost) {
        this.name = name;
        this.transferCost = transferCost;
    }

    
// Getters and Setters for the fields    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTransferCost() {
        return transferCost;
    }

    public void setTransferCost(int transferCost) {
        this.transferCost = transferCost;
    }

//Returns a string representation of the Branch object.
 
    @Override
    public String toString() {
        return "Branch{" +
                "name='" + name + '\'' +
                ", transferCost=" + transferCost +
                '}';
    }
}
