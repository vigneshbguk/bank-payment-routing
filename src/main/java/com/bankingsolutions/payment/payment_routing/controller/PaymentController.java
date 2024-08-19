package com.bankingsolutions.payment.payment_routing.controller;

import com.bankingsolutions.payment.payment_routing.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/route")
    public String getPaymentRoute(
            @RequestParam String originBranch,
            @RequestParam String destinationBranch) {
        return paymentService.processPayment(originBranch, destinationBranch);
    }
}
