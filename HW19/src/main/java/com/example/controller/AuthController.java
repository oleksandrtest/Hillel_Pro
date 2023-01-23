package com.example.controller;

import com.example.exeption.UserExistException;
import com.example.model.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class AuthController {

    @Autowired
    private UserService userService;


    @GetMapping("/login")
    public String loginForm() {
        return "security/login";
    }

    @GetMapping("/registration")
    public String registrationForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "security/registration";
    }

    @PostMapping("/registration")
    public String registration(User user) throws UserExistException {
        userService.saveUser(user);
        return "redirect:security/registration?success";
    }

    @GetMapping("denied")
    public String accessDenied() {
        return "security/access-denied";
    }
}
