package com.example.minicase.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerHeartScoreDTO {

    private Integer heart;

    private Integer score;

    private Long id;

    private String name;
}
