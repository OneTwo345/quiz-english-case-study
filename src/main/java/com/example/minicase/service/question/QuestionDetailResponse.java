package com.example.minicase.service.question;

import com.example.minicase.model.enums.ECategory;
import com.example.minicase.model.enums.EType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class QuestionDetailResponse {
    private String vietnamese;

    private String english;

    private String content;

    private EType type;

    private ECategory category;

    private List<String> wordIds;
}
