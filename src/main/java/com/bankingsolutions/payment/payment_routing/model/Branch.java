package com.bankingsolutions.payment.payment_routing.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Branch")
public class Branch {
    @Id
    private String id;
    private int processingCost;

    public Branch() {
    }

    public Branch(String id, int processingCost) {
        this.id = id;
        this.processingCost = processingCost;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getProcessingCost() {
        return processingCost;
    }

    public void setProcessingCost(int processingCost) {
        this.processingCost = processingCost;
    }
}
