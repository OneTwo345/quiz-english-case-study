package com.example.minicase.repository;


import com.example.minicase.model.Customer;
import com.example.minicase.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer,Long> {

}
