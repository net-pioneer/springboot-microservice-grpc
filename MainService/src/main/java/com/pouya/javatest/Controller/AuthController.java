package com.pouya.javatest.Controller;

import com.pouya.javatest.FormRequests.LoginRequest;
import com.pouya.javatest.Models.PersonalAccessToken;
import com.pouya.javatest.Models.Repository.ApiTokenRepository;
import com.pouya.javatest.Models.Repository.UserRepository;
import com.pouya.javatest.Models.Service.Interfaces.TokenService;
import com.pouya.javatest.Models.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService _tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = (User) authentication.getPrincipal();
        PersonalAccessToken _token = _tokenService.createOrUpdateToken(user);

        return ResponseEntity.ok(Map.of(
                "message", "Login successful",
                "token", _token.getToken(),
                "user", Map.of("id", user.getId(), "email", user.getEmail())
        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7).trim();
            _tokenService.delete(token);
        }
        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }

}