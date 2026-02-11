package com.javaspring.backend.controller;


import com.javaspring.backend.DTO.RegisterRequest;
import com.javaspring.backend.model.User;
import com.javaspring.backend.service.JwtService;
import com.javaspring.backend.service.MyUserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    public MyUserDetailsService  myUserDetailsService;

    public JwtService jwtService;

    public AuthController(MyUserDetailsService myUserDetailsService, JwtService jwtService) {
        this.myUserDetailsService = myUserDetailsService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest user) {
        System.out.println("register"+user);
        myUserDetailsService.createUser(user);
        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        UserDetails userdetails=myUserDetailsService.loadUserByUsername(user.getUsername());
        if(userdetails==null){
            return ResponseEntity.badRequest().build();
        }
        if(!(userdetails.getPassword().equals(user.getPassword()))){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("Login successful");
    }

}
