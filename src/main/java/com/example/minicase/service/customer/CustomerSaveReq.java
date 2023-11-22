package com.example.minicase.service.customer;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CustomerSaveReq {
    private String name;

    private String heart;

    private String score;

    private List<String> questionIds;
}
