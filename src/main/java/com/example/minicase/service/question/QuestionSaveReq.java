package com.example.minicase.service.question;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class QuestionSaveReq {

    private String vietnamese;

    private String english;

    private String content;

    private String qType;

    private String category;

    private List<String> wordIds;
}
