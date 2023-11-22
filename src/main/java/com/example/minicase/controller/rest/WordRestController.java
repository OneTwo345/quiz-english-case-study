package com.example.minicase.controller.rest;

import com.example.minicase.service.QuestionService;
import com.example.minicase.service.WordService;
import com.example.minicase.service.question.QuestionListResponse;
import com.example.minicase.service.word.WordLisResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
