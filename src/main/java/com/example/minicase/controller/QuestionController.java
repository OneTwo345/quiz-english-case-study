package com.example.minicase.controller;

import com.example.minicase.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
@RequestMapping("/quiz")
public class QuestionController {
    private final UserService userService;
    @GetMapping
    public ModelAndView showQuizPage() {
        ModelAndView view = new ModelAndView("quiz");
        view.addObject("customer", userService.getCurrentCustomer().get());
        return view;
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
    public ModelAndView showHomePage() {
        ModelAndView view =  new ModelAndView("home");
        view.addObject("customer", userService.getCurrentCustomer().get());
        return view;
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
    @GetMapping("/playlist")
    public String showPlaylistPage(Model model) {

        return "playlist";
    }


}
