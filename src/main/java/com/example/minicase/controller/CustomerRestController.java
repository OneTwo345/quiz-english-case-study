package com.example.minicase.controller;


import com.example.minicase.service.CustomerService;
import com.example.minicase.service.QuestionService;
import com.example.minicase.service.customer.CustomerListResponse;
import com.example.minicase.service.question.QuestionListResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/customers")
public class CustomerRestController {
    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerListResponse>> getCustomer() {
        List<CustomerListResponse> customers = customerService.getCustomers();
        return ResponseEntity.ok(customers);
    }
}
