package com.practice.demo.service;

import com.practice.demo.config.JwtProvider;
import com.practice.demo.entitiy.CustomeUserDetails;
import com.practice.demo.entitiy.User;
import com.practice.demo.repository.UserRepository;
import com.practice.demo.request.CreateRequest;
import com.practice.demo.request.LoginRequest;
import com.practice.demo.response.AuthResponse;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public UserServiceImplementation(UserRepository userRepository, PasswordEncoder passwordEncoder,JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public User createUser(CreateRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public AuthResponse loginUser(LoginRequest request) {
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
//            );
//
//            String token = jwtProvider.generateToken(authentication);
//            return new AuthResponse(token);
//        }
//        catch (AuthenticationException e) {
//            throw new RuntimeException("Invalid email or password", e);
//        }

        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if(passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                CustomeUserDetails customeUserDetails = new CustomeUserDetails(user);
                String token = jwtProvider.generateToken(customeUserDetails);
                return new AuthResponse(token);
            }
        }
        throw new UsernameNotFoundException("Invalid email or password");
    }
}
