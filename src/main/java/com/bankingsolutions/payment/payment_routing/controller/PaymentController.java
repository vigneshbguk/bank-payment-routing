package com.bankingsolutions.payment.payment_routing.controller;

import com.bankingsolutions.payment.payment_routing.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/route")
    public ResponseEntity<String> getPaymentRoute(
            @RequestParam String sourceBranch,
            @RequestParam String destinationBranch) {
        
        // Input validation (e.g., check if branch names are not empty)
        if (sourceBranch == null || sourceBranch.trim().isEmpty() ||
            destinationBranch == null || destinationBranch.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Source and destination branches must be provided.");
        }

        // Process payment
        String route = paymentService.processPayment(sourceBranch, destinationBranch);

        if (route != null) {
            return ResponseEntity.ok(route);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No route found between the specified branches. Try another way");
        }
    }
}
