package com.example.minicase.controller.rest;


import com.example.minicase.service.CustomerService;
import com.example.minicase.service.QuestionService;
import com.example.minicase.service.customer.CustomerDetailResponse;
import com.example.minicase.service.customer.CustomerListResponse;
import com.example.minicase.service.customer.CustomerSaveReq;
import com.example.minicase.service.question.QuestionListResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/customers")
public class CustomerRestController {
    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<Set<CustomerListResponse>> getCustomer() {
        Set<CustomerListResponse> customers = customerService.getCustomers();
        return ResponseEntity.ok(customers);
    }

    @PostMapping
    public void create(@RequestBody CustomerSaveReq request) {
        customerService.create(request);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateCustomer(@RequestBody @Valid CustomerSaveReq request, @PathVariable Long id) {
        customerService.update(request, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDetailResponse> findById(@PathVariable Long id) {
        return new ResponseEntity<>(customerService.findById(id), HttpStatus.OK);
    }
}
