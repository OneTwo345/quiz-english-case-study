package com.example.minicase.model;

import com.example.minicase.service.customer.CustomerListResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customer_questions")
public class CustomerQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;


    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    public CustomerQuestion(Customer customer,Question question) {
        this.customer = customer;
        this.question = question;
    }
}
