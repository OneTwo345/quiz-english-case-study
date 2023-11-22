package com.example.minicase.controller.rest;

import com.example.minicase.service.customer.CustomerDetailResponse;
import com.example.minicase.service.customer.CustomerSaveReq;
import com.example.minicase.service.question.QuestionDetailResponse;
import com.example.minicase.service.question.QuestionListResponse;
import com.example.minicase.service.QuestionService;
import com.example.minicase.service.question.QuestionSaveReq;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@AllArgsConstructor
public class QuestionRestController {
    private final QuestionService questionService;

    @GetMapping
    public ResponseEntity<List<QuestionListResponse>> getAllQuestion() {
        List<QuestionListResponse> questions = questionService.getQuestions();
        return ResponseEntity.ok(questions);
    }

    @PostMapping
    public void create(@RequestBody QuestionSaveReq request) {
        questionService.create(request);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateQuestion(@RequestBody @Valid QuestionSaveReq request, @PathVariable Long id) {
        questionService.update(request, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionDetailResponse> findById(@PathVariable Long id) {
        return new ResponseEntity<>(questionService.findById(id), HttpStatus.OK);
    }


}
