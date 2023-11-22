package com.example.minicase.repository;


import com.example.minicase.model.CustomerQuestion;
import com.example.minicase.model.QuestionWord;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerQuestionRepository extends JpaRepository<CustomerQuestion,Long> {

}
