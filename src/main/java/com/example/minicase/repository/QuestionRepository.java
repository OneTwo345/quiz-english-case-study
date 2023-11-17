package com.example.minicase.repository;


import com.example.minicase.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


public interface QuestionRepository extends JpaRepository<Question,Long> {

}
