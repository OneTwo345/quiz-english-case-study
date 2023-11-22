package com.example.minicase.controller.rest;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/quiz")
public class QuestionController {
    @GetMapping
    public String showListPage(Model model) {

        return "quiz";
    }

    @GetMapping("/start")
    public String showStartPage(Model model) {

        return "start";
    }

    @GetMapping("/guess")
    public String showGuessPage(Model model) {

        return "guess";
    }

    @GetMapping("/daily")
    public String showDailyPage(Model model) {

        return "daily";
    }
    @GetMapping("/listen")
    public String showListenPage(Model model) {

        return "listen";
    }
    @GetMapping("/home")
    public String showHomePage(Model model , HttpSession session) {

        Long id = (Long) session.getAttribute("idUser");
        model.addAttribute("idUser",id);

        return "home";
    }
    @GetMapping("/streak")
    public String showStreakPage(Model model) {

        return "streak";
    }
    @GetMapping("/drunk")
    public String showDrunkPage(Model model) {

        return "drunk";
    }
    @GetMapping("/miss")
    public String showMissPage(Model model) {

        return "miss";
    }


}
