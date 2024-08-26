package com.practice.demo.service;

import com.practice.demo.entitiy.User;
import com.practice.demo.request.CreateRequest;
import com.practice.demo.request.LoginRequest;
import com.practice.demo.response.AuthResponse;

public interface UserService {
    public User createUser(CreateRequest request);
    public AuthResponse loginUser(LoginRequest request);
}
