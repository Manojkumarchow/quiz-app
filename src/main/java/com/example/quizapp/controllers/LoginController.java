package com.example.quizapp.controllers;

import com.example.quizapp.model.LoginRequest;
import com.example.quizapp.session.SessionValidator;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "${frontend.url}")
@SuppressWarnings("unused")
public class LoginController {
    @Autowired
    private SessionValidator sessionValidator;
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        // Validate the login credentials
        return sessionValidator.createSession(loginRequest, session);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestParam("username") String username) {
        return sessionValidator.logout(username);
    }
}

