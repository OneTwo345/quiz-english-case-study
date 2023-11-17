package com.example.minicase.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/quiz")
public class QuestionController {
    @GetMapping
    public String showListPage(Model model) {

        return "duolingo";
    }

    @GetMapping("/start")
    public String showStartPage(Model model) {

        return "start";
    }

    @GetMapping("/guess")
    public String showGuessPage(Model model) {

        return "guess";
    }


}
