package com.example.minicase.service;

import com.example.minicase.model.Picture;
import com.example.minicase.model.Question;
import com.example.minicase.model.QuestionWord;
import com.example.minicase.model.Word;
import com.example.minicase.repository.PictureRepository;
import com.example.minicase.service.picture.PictureDetailResponse;
import com.example.minicase.service.picture.PictureListResponse;
import com.example.minicase.service.picture.PictureSaveRequest;
import com.example.minicase.service.question.QuestionDetailResponse;
import com.example.minicase.service.question.QuestionListResponse;
import com.example.minicase.service.question.QuestionSaveReq;
import com.example.minicase.util.AppUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class PictureService {
    private final PictureRepository pictureRepository;

    public List<PictureListResponse> getPictures() {
        return pictureRepository.findAll().stream().map(picture -> PictureListResponse.builder()
                        .id(picture.getId())
                        .translation(picture.getTranslation())
                        .content(picture.getContent())
                        .name(picture.getName())
                        .build())
                .collect(Collectors.toList());
    }

    public void create(PictureSaveRequest request) {
        var picture = AppUtil.mapper.map(request, Picture.class);
        pictureRepository.save(picture);
    }

    public PictureDetailResponse findById(Long id) {
        var picture = pictureRepository.findById(id).orElse(new Picture());
        return AppUtil.mapper.map(picture, PictureDetailResponse.class);
    }

    public void update(PictureSaveRequest request, Long id) {
        var pictureDb = pictureRepository.findById(id).orElse(new Picture());
        AppUtil.mapper.map(request, pictureDb);
        pictureRepository.save(pictureDb);
    }


}
