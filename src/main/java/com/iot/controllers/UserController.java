package com.iot.controllers;

import com.iot.domain.entity.User;
import com.iot.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/smartPlantie")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public String welcomePage() {
        return "welcomePage";
    }

    @GetMapping("/login")
    public String logIn() {
        return "logIn";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        try {
            userService.authenticate(username, password);
            return "redirect:/smartPlantie/stats";
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return "logIn";
        }
    }

    @GetMapping("/signup")
    public String signUp() {
        return "signUp";
    }

    @PostMapping("/signup")
    public String signUp(@RequestParam String username, @RequestParam String password, Model model) {
        try {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            userService.save(user, model);
            return "redirect:/smartPlantie/login?registered=true";
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return "signUp";
        }
    }

    @GetMapping("/plantType")
    public String plantType() {
        return "plantType";
    }

    @GetMapping("/stats")
    public String stats() {
        return "stats";
    }


}
