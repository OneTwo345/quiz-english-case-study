package com.example.minicase.service;

import com.example.minicase.repository.CustomerRepository;
import com.example.minicase.repository.QuestionRepository;
import com.example.minicase.service.customer.CustomerListResponse;
import com.example.minicase.service.question.QuestionListResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor


public class CustomerService {
    private final CustomerRepository customerRepository;

    public List<CustomerListResponse> getCustomers() {
        return customerRepository.findAll().stream().map(customer -> CustomerListResponse.builder()
                        .id(customer.getId())
                        .name(customer.getName())
                        .heart(customer.getHeart())
                        .score(customer.getScore())
                        .questions(customer.getCustomerQuestions()
                                .stream().map(c -> c.getQuestion().getEnglish())
                                .collect(Collectors.joining(", ")))
                        .build())
                .collect(Collectors.toList());
    }
}
