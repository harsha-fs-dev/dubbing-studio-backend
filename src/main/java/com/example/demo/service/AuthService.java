package com.example.demo.service;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.RegisterRequest;
import com.example.demo.dto.response.AuthResponse;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // ─────────────────────────────────────────
    // REGISTER
    // ─────────────────────────────────────────
    public AuthResponse register(RegisterRequest request) {

        // Step 1: Check if email is already registered
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email is already registered: " + request.getEmail());
        }

        // Step 2: Hash the plain-text password using BCrypt before saving
        String hashedPassword = passwordEncoder.encode(request.getPassword());

        // Step 3: Build the User entity from the request data
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(hashedPassword)
                .role(request.getRole())
                .build();

        // Step 4: Save the user to the database
        User savedUser = userRepository.save(user);

        // Step 5: Generate a JWT token for the newly registered user
        String token = jwtUtil.generateToken(savedUser.getEmail());

        // Step 6: Build and return the response with user details and token
        return AuthResponse.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .token(token)
                .tokenType("Bearer")
                .build();
    }

    // ─────────────────────────────────────────
    // LOGIN
    // ─────────────────────────────────────────
    public AuthResponse login(LoginRequest request) {

        // Step 1: Find the user by email, throw exception if not found
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        // Step 2: Compare the provided password with the hashed password in DB
        boolean isPasswordMatch = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!isPasswordMatch) {
            throw new RuntimeException("Invalid email or password");
        }

        // Step 3: Generate a JWT token for the authenticated user
        String token = jwtUtil.generateToken(user.getEmail());

        // Step 4: Build and return the response with user details and token
        return AuthResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .token(token)
                .tokenType("Bearer")
                .build();
    }
}