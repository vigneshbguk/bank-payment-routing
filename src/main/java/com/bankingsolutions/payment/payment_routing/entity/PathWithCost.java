package com.bankingsolutions.payment.payment_routing.entity;

import java.util.List;

public class PathWithCost {
    private List<String> path;
    private int cost;

    public PathWithCost(List<String> path, int cost) {
        this.path = path;
        this.cost = cost;
    }

    public List<String> getPath() {
		return path;
	}

	public void setPath(List<String> path) {
		this.path = path;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	@Override
    public String toString() {
        return "Path: " + path + ", Total Cost: " + cost;
    }
}