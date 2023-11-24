package com.example.minicase.service.picture;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PictureSaveRequest {
    private Long id;

    private String translation;

    private String content;
}
