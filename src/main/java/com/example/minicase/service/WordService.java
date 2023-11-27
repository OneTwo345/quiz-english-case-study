package com.example.minicase.service;

import com.example.minicase.model.Question;
import com.example.minicase.model.Word;
import com.example.minicase.repository.WordRepository;
import com.example.minicase.service.word.WordLisResponse;
import com.example.minicase.service.word.WordSaveRequest;
import com.example.minicase.util.AppUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WordService {
    private final WordRepository wordRepository;

    public List<WordLisResponse> getWords() {
        return wordRepository.findAll().stream().map(word -> WordLisResponse.builder()
                        .id(word.getId())
                        .name(word.getName())
                        .build())
                .collect(Collectors.toList());
    }

    public void create(WordSaveRequest request) {
        String name = request.getName();
        Word newWord = new Word();
        newWord.setName(name);
        wordRepository.save(newWord);
    }

}