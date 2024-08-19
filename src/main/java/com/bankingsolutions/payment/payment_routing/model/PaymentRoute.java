package com.bankingsolutions.payment.payment_routing.model;


// Represents a payment route between two branches along with the cost associated with the transfer. (POJO class)

public class PaymentRoute {

    // The origin branch from where the payment starts.
    private Branch originBranch;

    // The destination branch where the payment ends. 
    private Branch destinationBranch;

    // The cost incurred for transferring funds along this route.
    private int cost;

    
    public PaymentRoute(Branch originBranch, Branch destinationBranch, int cost) {
        this.originBranch = originBranch;
        this.destinationBranch = destinationBranch;
        this.cost = cost;
    }

    // Getters and Setters for the fields
    
    public Branch getOriginBranch() {
        return originBranch;
    }

    public void setOriginBranch(Branch originBranch) {
        this.originBranch = originBranch;
    }

    public Branch getDestinationBranch() {
        return destinationBranch;
    }

    public void setDestinationBranch(Branch destinationBranch) {
        this.destinationBranch = destinationBranch;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    
    // Overriding toString method to get string formatted object
    @Override
    public String toString() {
        return "PaymentRoute{" +
                "originBranch=" + originBranch +
                ", destinationBranch=" + destinationBranch +
                ", cost=" + cost +
                '}';
    }

    /**
     * Compares this PaymentRoute to another object for equality. Return true if the other object is a PaymentRoute with the same origin, destination, and cost, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaymentRoute that = (PaymentRoute) o;

        if (cost != that.cost) return false;
        if (!originBranch.equals(that.originBranch)) return false;
        return destinationBranch.equals(that.destinationBranch);
    }

    /**
     * Computes the hash code for this PaymentRoute object. Return the hash code based on the origin branch, destination branch, and cost
     */
    @Override
    public int hashCode() {
        int result = originBranch.hashCode();
        result = 31 * result + destinationBranch.hashCode();
        result = 31 * result + cost;
        return result;
    }
}
