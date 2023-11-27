package com.example.minicase.controller;

import com.example.minicase.service.WordService;
import com.example.minicase.service.user.UserService;
import com.example.minicase.service.word.WordLisResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping()
public class AdminController {
    private final WordService wordService;
    private final ModelAndView modelAndView = new ModelAndView();
    @GetMapping("/dashboard")
    public ModelAndView home() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return new ModelAndView("/dashboard");
    }

    @GetMapping("/question")
    public ModelAndView question() {
        ModelAndView view = new ModelAndView("question");

        List<WordLisResponse> words = wordService.getWords(); // Danh sách các từ

        view.addObject("words", words); // Thêm danh sách từ vào ModelAndView

        return view;
    }


    @GetMapping("/game")
    public ModelAndView combo() {
        ModelAndView view = new ModelAndView("/game");
//        view.addObject("products", productService.findAll());
        return view;
    }
}
