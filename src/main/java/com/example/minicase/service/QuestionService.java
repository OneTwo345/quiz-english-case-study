package com.example.minicase.service;

import com.example.minicase.model.Question;
import com.example.minicase.model.QuestionWord;
import com.example.minicase.model.Word;
import com.example.minicase.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;



    public List<QuestionListResponse> getQuestions() {
        return questionRepository.findAll().stream().map(question -> QuestionListResponse.builder()
                        .id(question.getId())
                        .category(question.getCategory())
                        .english(question.getEnglish())
                        .vietnamese(question.getVietnamese())
                        .type(String.valueOf(question.getType()))
                        .words(question.getQuestionWords()
                                .stream().map(c -> c.getWord().getName())
                                .collect(Collectors.joining(", ")))
                        .build())
                .collect(Collectors.toList());
    }






}