package com.javaspring.backend.controller;


import com.javaspring.backend.DTO.AuthResponse;
import com.javaspring.backend.DTO.LoginRequest;
import com.javaspring.backend.DTO.RegisterRequest;
import com.javaspring.backend.service.JwtService;
import com.javaspring.backend.service.MyUserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    public MyUserDetailsService  myUserDetailsService;

    public JwtService jwtService;

    public AuthController(MyUserDetailsService myUserDetailsService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.myUserDetailsService = myUserDetailsService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest user) {
        System.out.println("register"+user);
        myUserDetailsService.createUser(user);
        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        UserDetails user =  myUserDetailsService.loadUserByUsername(request.getEmail());

        String jwt = jwtService.generateToken(user);

        return ResponseEntity.ok(new AuthResponse(jwt));
    }

}
