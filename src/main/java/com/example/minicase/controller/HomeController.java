package com.example.minicase.controller;
import com.example.minicase.model.User;
import com.example.minicase.repository.UserRepository;
import com.example.minicase.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.Optional;
@Controller
@AllArgsConstructor
@RequestMapping(value="/quiz/home")
public class HomeController {
    private UserService userService;
    private final ModelAndView modelAndView = new ModelAndView();
    public ModelAndView Login(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView modelAndView = new ModelAndView();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails userDetails) {
            String username = userDetails.getUsername();
            // Tìm người dùng theo username
            Optional<User> user = userService.findByNameIgnoreCaseOrEmailIgnoreCaseOrPhone(username);
            if (user.isPresent()) {
                modelAndView.addObject("loggedIn", true);
                modelAndView.addObject("user", user.get());
            } else {
                modelAndView.addObject("loggedIn", false);
            }
        } else {
            modelAndView.addObject("loggedIn", false);
        }
        return modelAndView;
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login?logout";
    }
}