package com.example.minicase.service;

import com.example.minicase.model.Word;
import com.example.minicase.model.enums.ECategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class QuestionListResponse {
    private Long id;

    private String vietnamese;

    private String english;

    private String type;

    private ECategory category;

    private String words;


}
