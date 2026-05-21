package com.soham.aiinterview.controller;

import com.soham.aiinterview.dto.LoginRequest;
import com.soham.aiinterview.entity.User;
import com.soham.aiinterview.jwt.JwtUtil;
import com.soham.aiinterview.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }
    @GetMapping("/profile")
    public String profile(@RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);

        String email = jwtUtil.extractEmail(token);

        return "Welcome " + email;

    }
}