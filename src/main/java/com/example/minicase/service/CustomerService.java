package com.example.minicase.service;

import com.example.minicase.model.Customer;
import com.example.minicase.model.CustomerQuestion;
import com.example.minicase.model.Question;
import com.example.minicase.repository.CustomerQuestionRepository;
import com.example.minicase.repository.CustomerRepository;
import com.example.minicase.repository.QuestionRepository;
import com.example.minicase.service.customer.CustomerDetailResponse;
import com.example.minicase.service.customer.CustomerListResponse;
import com.example.minicase.service.customer.CustomerSaveReq;
import com.example.minicase.service.question.QuestionListResponse;
import com.example.minicase.util.AppUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor


public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerQuestionRepository customerQuestionRepository;

    public Set<CustomerListResponse> getCustomers() {
        return customerRepository.findAll().stream().map(customer -> CustomerListResponse.builder()
                        .id(customer.getId())
                        .name(customer.getName())
                        .heart(customer.getHeart())
                        .score(customer.getScore())
                        .questions(customer.getCustomerQuestions()
                                .stream().map(c -> c.getQuestion().getEnglish())
                                .collect(Collectors.joining(", ")))
                        .build())
                .collect(Collectors.toSet());
    }

    public void create (CustomerSaveReq request){
        var customer = AppUtil.mapper.map(request, Customer.class);
        customerRepository.save(customer);
    }

    @Transactional
    public void update (CustomerSaveReq req, Long id){
        var customerDb = customerRepository.findById(id).orElse(new Customer());
        AppUtil.mapper.map(req,customerDb);
        customerQuestionRepository.deleteAll(customerDb.getCustomerQuestions());
        var customerQuestion = new ArrayList<CustomerQuestion>();
        for (String idQuestion : req.getQuestionIds()) {
            Question question = new Question(Long.valueOf(idQuestion));
            customerQuestion.add(new CustomerQuestion(customerDb, question));
        }
        customerQuestionRepository.saveAll(customerQuestion);
        customerRepository.save(customerDb);
    }

    public CustomerDetailResponse findById(Long id){
        var customer = customerRepository.findById(id).orElse(new Customer());
        var result = AppUtil.mapper.map(customer, CustomerDetailResponse.class);
        result.setQuestionIds(customer
                .getCustomerQuestions()
                .stream().map(customerQuestion -> customerQuestion.getQuestion().getId())
                .collect(Collectors.toList()));
        return result;
    }
}
