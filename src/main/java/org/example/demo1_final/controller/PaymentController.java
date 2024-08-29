package org.example.demo1_final.controller;

import org.example.demo1_final.model.PaymentRequest;
import org.example.demo1_final.model.PaymentResponse;
import org.example.demo1_final.service.PaymentValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/process")
public class PaymentController {
    @Autowired
    private PaymentValidationService paymentValidationService;

    @PostMapping("/service")
    public PaymentResponse processRequest(@RequestBody PaymentRequest request) {
        return paymentValidationService.processRequest(request);
    }
}

