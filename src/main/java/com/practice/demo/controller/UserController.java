package com.practice.demo.controller;

import com.practice.demo.entitiy.User;
import com.practice.demo.request.CreateRequest;
import com.practice.demo.request.LoginRequest;
import com.practice.demo.response.AuthResponse;
import com.practice.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody CreateRequest request) {
        User user = userService.createUser(request);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginRequest request) {
        AuthResponse authResponse = userService.loginUser(request);
        return ResponseEntity.ok(authResponse);
    }
}
