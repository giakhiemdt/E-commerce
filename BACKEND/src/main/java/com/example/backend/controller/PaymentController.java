package com.example.backend.controller;

import com.example.backend.model.request.frontend.order.OrderListRequest;
import com.example.backend.model.response.StatusResponse;
import com.example.backend.model.response.payment.DashboardResponse;
import com.example.backend.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4000")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/dashboard/{year}")
    @PreAuthorize("hasAnyAuthority('SELLER', 'ADMIN')")
    public ResponseEntity<DashboardResponse> getRevenuesWithMonth(@PathVariable(required = false) Integer year) {
        return ResponseEntity.ok(paymentService.getDashboard(year));
    }


}
