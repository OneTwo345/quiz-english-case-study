package com.example.minicase.service.customer;

import com.example.minicase.service.question.QuestionListResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CustomerListResponse {
    private Long id;

    private String name;

    private Integer heart;

    private Integer score;

    private String questions;
}
