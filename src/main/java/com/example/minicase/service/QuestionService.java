package com.example.minicase.service;

import com.example.minicase.repository.QuestionRepository;
import com.example.minicase.service.question.QuestionListResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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
                        .content(question.getContent())
                        .words(question.getQuestionWords()
                                .stream().map(c -> c.getWord().getName())
                                .collect(Collectors.joining(", ")))
                        .build())
                .collect(Collectors.toList());
    }






}