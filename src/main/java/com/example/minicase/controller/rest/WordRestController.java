package com.example.minicase.controller.rest;

import com.example.minicase.service.QuestionService;
import com.example.minicase.service.WordService;
import com.example.minicase.service.question.QuestionListResponse;
import com.example.minicase.service.question.QuestionSaveReq;
import com.example.minicase.service.word.WordLisResponse;
import com.example.minicase.service.word.WordSaveRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/words")
@AllArgsConstructor
public class WordRestController {
    private final WordService wordService;

    @GetMapping
    public ResponseEntity<List<WordLisResponse>> getAllWords() {
        List<WordLisResponse> words = wordService.getWords();
        return ResponseEntity.ok(words);
    }

    @PostMapping
    public void create(@RequestBody WordSaveRequest request) {
        wordService.create(request);
    }
}
