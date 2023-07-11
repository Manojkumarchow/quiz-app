package com.example.quizapp.session;

import com.example.quizapp.model.LoginRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionValidator {

    private static final Map<String, HttpSession> usernameToSessionMap = new ConcurrentHashMap<>();

    public ResponseEntity<String> createSession(LoginRequest loginRequest, HttpSession session) {
        return Optional.ofNullable(loginRequest).filter(this::isValidLogin).map(request -> {
            session.setAttribute("username", request.getUsername());
            addSession(loginRequest.getUsername(), session);
            return new ResponseEntity<>(session.getId(), HttpStatus.OK);
        }).orElse(new ResponseEntity<>("Invalid login", HttpStatus.UNAUTHORIZED));
    }

    private boolean isValidLogin(LoginRequest loginRequest) {
        // Add your login validation logic here
        // fetch from db and validate the user details --> or store all the user_details in the cache (redis)
        // if not present in the redis, fetch from db and write-back to cache
        // Keep the user_details in the redis server once the application/server gets started.

        return "manoj".equals(loginRequest.getUsername()) && "manoj".equals(loginRequest.getPassword());
    }

    // This method should be called after successful login to add the session to the map
    public static void addSession(String username, HttpSession session) {
        usernameToSessionMap.put(username, session);
    }

    public ResponseEntity<String> logout(String username) {
        return Optional.ofNullable(usernameToSessionMap.get(username)).map(session -> {
            session.invalidate(); // Invalidate the session
            usernameToSessionMap.remove(username);
            return new ResponseEntity<>("Logout successful", HttpStatus.OK);
        }).orElse(new ResponseEntity<>("User session not found", HttpStatus.BAD_REQUEST));
    }


}
