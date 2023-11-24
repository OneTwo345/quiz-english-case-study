package com.example.minicase.controller.rest;

import com.example.minicase.service.PictureService;
import com.example.minicase.service.picture.PictureDetailResponse;
import com.example.minicase.service.picture.PictureListResponse;
import com.example.minicase.service.picture.PictureSaveRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pictures")
@AllArgsConstructor
public class PictureRestController {
    private final PictureService pictureService;
    @GetMapping
    public ResponseEntity<List<PictureListResponse>> getPictures() {
        List<PictureListResponse> pictures = pictureService.getPictures();
        return ResponseEntity.ok(pictures);
    }

    @PostMapping
    public void create(@RequestBody PictureSaveRequest request) {
        pictureService.create(request);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updatePicture(@RequestBody @Valid PictureSaveRequest request, @PathVariable Long id) {
        pictureService.update(request, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PictureDetailResponse> findById(@PathVariable Long id) {
        return new ResponseEntity<>(pictureService.findById(id), HttpStatus.OK);
    }
}
