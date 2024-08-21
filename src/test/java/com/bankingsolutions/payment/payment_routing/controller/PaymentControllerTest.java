package com.bankingsolutions.payment.payment_routing.controller;

import com.bankingsolutions.payment.payment_routing.entity.PaymentRequest;
import com.bankingsolutions.payment.payment_routing.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class PaymentControllerTest {

    @InjectMocks
    private PaymentController paymentController;

    @Mock
    private PaymentService paymentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPaymentRoute_ValidRequest() {
        // Arrange
        PaymentRequest request = new PaymentRequest("A", "D");
        String expectedRoute = "A,B,D";
        when(paymentService.processPayment("A", "D")).thenReturn(expectedRoute);

        // Act
        ResponseEntity<String> response = paymentController.getPaymentRoute(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedRoute, response.getBody());
    }

    @Test
    public void testGetPaymentRoute_NoRouteFound() {
        // Arrange
        PaymentRequest request = new PaymentRequest("A", "D");
        when(paymentService.processPayment("A", "D")).thenReturn(null);

        // Act
        ResponseEntity<String> response = paymentController.getPaymentRoute(request);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No route found between the specified branches. Try another way", response.getBody());
    }

    @Test
    public void testGetPaymentRoute_InvalidRequestSourceBranchEmpty() {
        // Arrange
        PaymentRequest request = new PaymentRequest("", "D");

        // Act
        ResponseEntity<String> response = paymentController.getPaymentRoute(request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Source and destination branches must be valid.", response.getBody());
    }

    @Test
    public void testGetPaymentRoute_InvalidRequestDestinationBranchEmpty() {
        // Arrange
        PaymentRequest request = new PaymentRequest("A", "");

        // Act
        ResponseEntity<String> response = paymentController.getPaymentRoute(request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Source and destination branches must be valid.", response.getBody());
    }

    @Test
    public void testGetPaymentRoute_InvalidRequestBothBranchesEmpty() {
        // Arrange
        PaymentRequest request = new PaymentRequest("", "");

        // Act
        ResponseEntity<String> response = paymentController.getPaymentRoute(request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Source and destination branches must be valid.", response.getBody());
    }
}
