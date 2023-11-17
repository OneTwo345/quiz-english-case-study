package com.example.minicase.repository;


import com.example.minicase.model.Question;
import com.example.minicase.model.QuestionWord;
import org.springframework.data.jpa.repository.JpaRepository;


public interface QuestionWordRepository extends JpaRepository<QuestionWord,Long> {

}
