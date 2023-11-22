package com.example.minicase.service.customer;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CustomerDetailResponse {
    private Long id;

    private String name;

    private Integer heart;

    private Integer score;

    private List<Long> questionIds;

}
