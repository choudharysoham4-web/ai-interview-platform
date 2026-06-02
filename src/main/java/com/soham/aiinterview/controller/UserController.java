package com.soham.aiinterview.controller;

import com.soham.aiinterview.dto.AuthResponse;
import com.soham.aiinterview.dto.LoginRequest;
import com.soham.aiinterview.dto.RegisterRequest;
import com.soham.aiinterview.entity.User;
import com.soham.aiinterview.jwt.JwtService;
import com.soham.aiinterview.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {

        Optional<User> existingUser =
                userRepository.findByEmail(request.getEmail());

        if (existingUser.isPresent()) {
            return new AuthResponse(null, "User already exists");
        }

        User user = new User(
                request.getName(),
                request.getEmail(),
                request.getPassword()
        );

        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(token, "Registration successful");
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {

        Optional<User> userOptional =
                userRepository.findByEmail(request.getEmail());

        if (userOptional.isEmpty()) {
            return new AuthResponse(null, "User not found");
        }

        User user = userOptional.get();

        if (!user.getPassword().equals(request.getPassword())) {
            return new AuthResponse(null, "Invalid password");
        }

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(token, "Login successful");
    }
}