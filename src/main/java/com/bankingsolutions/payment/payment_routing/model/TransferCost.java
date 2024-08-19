package com.bankingsolutions.payment.payment_routing.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TransferCost")
public class TransferCost {
    @Id
    private String id;
    @ManyToOne
    private Branch fromBranch;
    @ManyToOne
    private Branch toBranch;
    private int cost;

    public TransferCost() {
    }

    public TransferCost(Branch fromBranch, Branch toBranch, int cost) {
        this.fromBranch = fromBranch;
        this.toBranch = toBranch;
        this.cost = cost;
    }

    public Branch getFromBranch() {
        return fromBranch;
    }

    public void setFromBranch(Branch fromBranch) {
        this.fromBranch = fromBranch;
    }

    public Branch getToBranch() {
        return toBranch;
    }

    public void setToBranch(Branch toBranch) {
        this.toBranch = toBranch;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
