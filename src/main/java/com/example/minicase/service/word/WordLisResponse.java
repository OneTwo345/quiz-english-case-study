package com.example.minicase.service.word;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WordLisResponse {
    private Long id;

    private String name;
}
