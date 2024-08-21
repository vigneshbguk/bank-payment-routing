package com.bankingsolutions.payment.payment_routing.controller;

import com.bankingsolutions.payment.payment_routing.entity.PaymentRequest;
import com.bankingsolutions.payment.payment_routing.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

@WebMvcTest(PaymentController.class)
public class PaymentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetPaymentRoute_ValidRequest() throws Exception {
        // Arrange
        PaymentRequest request = new PaymentRequest("A", "D");
        String expectedRoute = "A,B,D";
        when(paymentService.processPayment("A", "D")).thenReturn(expectedRoute);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/payments/route")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedRoute));
    }

    @Test
    public void testGetPaymentRoute_NoRouteFound() throws Exception {
        // Arrange
        PaymentRequest request = new PaymentRequest("A", "D");
        when(paymentService.processPayment("A", "D")).thenReturn(null);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/payments/route")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("No route found between the specified branches. Try another way"));
    }

    @Test
    public void testGetPaymentRoute_InvalidRequestSourceBranchEmpty() throws Exception {
        // Arrange
        PaymentRequest request = new PaymentRequest("", "D");

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/payments/route")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Source and destination branches must be valid."));
    }

    @Test
    public void testGetPaymentRoute_InvalidRequestDestinationBranchEmpty() throws Exception {
        // Arrange
        PaymentRequest request = new PaymentRequest("A", "");

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/payments/route")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Source and destination branches must be valid."));
    }

    @Test
    public void testGetPaymentRoute_InvalidRequestBothBranchesEmpty() throws Exception {
        // Arrange
        PaymentRequest request = new PaymentRequest("", "");

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/payments/route")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Source and destination branches must be valid."));
    }
}

