package com.bankingsolutions.payment.payment_routing.service;

public interface PaymentService {

    /**
     * Processes a payment returning the cheapest sequence of branches as a comma-separated String.
   Implementations are expected
     * to be thread safe.
     * @param originBranch the starting branch
     * @param destinationBranch the destination branch
     * @return the cheapest sequence for the payment as a CSV (e.g., "A,B,D") or null if no sequence is available
     */
    String processPayment(String originBranch, String destinationBranch);
}
