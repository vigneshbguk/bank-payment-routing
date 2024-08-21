package com.bankingsolutions.payment.payment_routing.controller;

import com.bankingsolutions.payment.payment_routing.entity.PaymentRequest;
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
    public ResponseEntity<String> getPaymentRoute(@RequestBody PaymentRequest paymentRequest) {
            
        // Input validation (e.g., check if branch names are not empty)
    	if (paymentRequest.getSourceBranch() == null || paymentRequest.getSourceBranch().trim().isEmpty() ||
                paymentRequest.getDestinationBranch() == null || paymentRequest.getDestinationBranch().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Source and destination branches must be provided.");
            }

        
     // Process payment
        String route = paymentService.processPayment(paymentRequest.getSourceBranch(), paymentRequest.getDestinationBranch());

        if (route != null) {
            return ResponseEntity.ok(route);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No route found between the specified branches. Try another way");
        }
    }
    
    
}
