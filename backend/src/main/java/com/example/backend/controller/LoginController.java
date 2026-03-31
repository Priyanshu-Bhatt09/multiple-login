package com.example.backend.controller;

import com.example.backend.dto.JwtResponseDTO;
import com.example.backend.dto.MessageResponseDTO;
import com.example.backend.dto.SignInRequestDTO;
import com.example.backend.dto.SignUpRequestDTO;
import com.example.backend.security.JwtService;
import com.example.backend.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/proctor/login")
@CrossOrigin(origins = "http://localhost:3000/")
public class LoginController {
    private final LoginService service;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginController(LoginService service, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.service = service;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }
    @PostMapping("/signup")
    public ResponseEntity<MessageResponseDTO> signup(@RequestBody SignUpRequestDTO request) {
        boolean success = service.signUp(
                request.getName(),
                request.getEmail(),
                request.getPassword()

        );
        if(!success) {
            return ResponseEntity.badRequest().body(new MessageResponseDTO("User already exist"));
        }
        return ResponseEntity.ok(new MessageResponseDTO("Signup successful"));
    }
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody SignInRequestDTO request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            String jwtToken = jwtService.generateToken(request.getEmail());
            return ResponseEntity.ok(new JwtResponseDTO(jwtToken, "Login successful"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponseDTO("Invalid Credentials"));
        }
    }

//    @GetMapping("/test") //this is only for testing
//    public String hola() {
//        return "Heloo";
//    }
}
