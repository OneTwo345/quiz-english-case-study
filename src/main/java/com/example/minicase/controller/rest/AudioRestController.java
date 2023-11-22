package com.example.minicase.controller.rest;

import com.example.minicase.model.dto.FlaskAudioReq;
import com.example.minicase.model.dto.FlaskAudioRes;
import com.google.gson.Gson;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/test")
public class AudioRestController {
    @PostMapping("")
    public ResponseEntity<?> downloadAudioFile(@RequestBody FlaskAudioReq flaskAudioReq) {
        String flaskUrl = "http://localhost:5000/audios";
//        FlaskAudioReq flaskAudioReq = new FlaskAudioReq("I love you", "Anh yeu em");
        Gson gson = new Gson();

//        FlaskAudioReq flaskAudioReq1 = gson.fromJson("aaaa", FlaskAudioReq.class);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(gson.toJson(flaskAudioReq), headers);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(flaskUrl, requestEntity, String.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                String responseBody = responseEntity.getBody();
                FlaskAudioRes flaskAudioRes = gson.fromJson(responseBody, FlaskAudioRes.class);
                System.out.println("Response from Flask API: " + flaskAudioRes);
            } else {
                System.err.println("Error calling Flask API. Status code: " + responseEntity.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error calling Flask API: " + e.getMessage());
        }
        return ResponseEntity.ok().build();
    }
}
