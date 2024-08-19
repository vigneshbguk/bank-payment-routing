package com.bankingsolutions.payment.payment_routing.model;

import java.util.List;

public class PaymentRoute {
    private final List<String> branches;
    private final int totalCost;

    public PaymentRoute(List<String> branches, int totalCost) {
        this.branches = branches;
        this.totalCost = totalCost;
    }

    public List<String> getBranches() {
        return branches;
    }

    public int getTotalCost() {
        return totalCost;
    }
}
