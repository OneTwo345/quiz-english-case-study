package com.example.minicase.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

//@RestController
//public class AudioRestController {
//    @GetMapping
//    public ResponseEntity<?> downloadAudioFile() {
//        String flaskUrl = "http://localhost:5001/api/audio";
//        String requestData = "{\n" +
//                "    \"id\":  \"2223\",\n" +
//                "    \"text\": \"Bạn có thích bóng đá không\",\n" +
//                "    \"language\": \"vi\"\n" +
//                "}";
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<String> requestEntity = new HttpEntity<>(requestData, headers);
//        RestTemplate restTemplate = new RestTemplate();
//        try {
//            ResponseEntity<String> responseEntity = restTemplate.postForEntity(flaskUrl, requestEntity, String.class);
//            if (responseEntity.getStatusCode().is2xxSuccessful()) {
//                String responseBody = responseEntity.getBody();
//                System.out.println("Response from Flask API: " + responseBody);
//            } else {
//                System.err.println("Error calling Flask API. Status code: " + responseEntity.getStatusCode());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.err.println("Error calling Flask API: " + e.getMessage());
//        }
//        return ResponseEntity.ok().build();
//    }
//}
