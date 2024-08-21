package com.bankingsolutions.payment.payment_routing.entity;

public class PaymentRequest {
    private String sourceBranch;
    private String destinationBranch;

    // Getters and setters
    public String getSourceBranch() {
        return sourceBranch;
    }

    public void setSourceBranch(String sourceBranch) {
        this.sourceBranch = sourceBranch;
    }

    public String getDestinationBranch() {
        return destinationBranch;
    }

    public void setDestinationBranch(String destinationBranch) {
        this.destinationBranch = destinationBranch;
    }
}
