package com.example.minicase.controller;

import com.example.minicase.model.Question;
import com.example.minicase.service.QuestionListResponse;
import com.example.minicase.service.QuestionService;
import lombok.AllArgsConstructor;
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

//    @GetMapping("/{id}")
//    public ResponseEntity<QuestionListResponse> getQuestionById(@PathVariable Long id) {
//        QuestionListResponse question = questionService.findById(id);
//        return ResponseEntity.ok(question);
//    }

//    @PostMapping
//    public void create(@RequestBody Question request){
//        questionService.addQuestion(request);
//    }
//
//    @PutMapping("/{id}")
//    public Question update(@PathVariable Long id, @RequestBody Question questionRequest) {
//        return questionService.updateQuestion(questionRequest,id );
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable Long id) {
//        questionService.deleteQuestionById(id);
//        return ResponseEntity.noContent().build();
//    }
//    @GetMapping("/{id}")
//    public Question getQuestionById(@PathVariable("id") Long id) {
//        return questionService.findById(id);
//    }

}
