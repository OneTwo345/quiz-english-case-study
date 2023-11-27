package com.example.minicase.service;

import com.example.minicase.model.*;
import com.example.minicase.repository.QuestionRepository;
import com.example.minicase.repository.QuestionWordRepository;
import com.example.minicase.service.customer.CustomerDetailResponse;
import com.example.minicase.service.customer.CustomerSaveReq;
import com.example.minicase.service.question.QuestionDetailResponse;
import com.example.minicase.service.question.QuestionListResponse;
import com.example.minicase.service.question.QuestionSaveReq;
import com.example.minicase.util.AppUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionWordRepository questionWordRepository;



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

    public void create (QuestionSaveReq request){
        var question = AppUtil.mapper.map(request, Question.class);
        questionRepository.save(question);

        Question question1 = question;
        questionWordRepository.saveAll(request
                .getWordIds()
                .stream()
                .map(id -> new QuestionWord(question1, new Word(Long.valueOf(id))))
                .collect(Collectors.toList()));
    }

    @Transactional
    public void update (QuestionSaveReq req, Long id){
        var questionDb = questionRepository.findById(id).orElse(new Question());
        AppUtil.mapper.map(req,questionDb);
        questionWordRepository.deleteAll(questionDb.getQuestionWords());
        var questionWord = new ArrayList<QuestionWord>();
        for (String idWord : req.getWordIds()) {
            Word word = new Word(Long.valueOf(idWord));
            questionWord.add(new QuestionWord(questionDb, word));
        }
        questionWordRepository.saveAll(questionWord);
        questionRepository.save(questionDb);
    }

    public QuestionDetailResponse findById(Long id){
        var question = questionRepository.findById(id).orElse(new Question());
        var result = AppUtil.mapper.map(question, QuestionDetailResponse.class);
        result.setWordIds(question
                .getQuestionWords()
                .stream().map(questionWord -> questionWord.getWord().getId().toString())
                .collect(Collectors.toList()));
        return result;
    }

}