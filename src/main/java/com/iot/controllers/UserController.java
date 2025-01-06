package com.iot.controllers;

import com.iot.domain.entity.User;
import com.iot.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/smartPlantie")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public String welcomePage(){
        return "welcomePage";
    }

    @PostMapping("/login")
    public void login(@RequestBody User user) {
        try{
            userService.getById(user.getId());
            userService.authenticate(user.getUsername(), user.getPassword());
        }catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
