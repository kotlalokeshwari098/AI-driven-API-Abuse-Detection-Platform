package com.javaspring.backend.service;

import com.javaspring.backend.DTO.RegisterRequest;
import com.javaspring.backend.model.User;
import com.javaspring.backend.repository.UserRepository;
import com.javaspring.backend.security.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public MyUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByUsername(username);
        return new UserPrincipal(user);
    }

    public void createUser(RegisterRequest request) {
        User user=new User();
        user.setUsername(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());

        userRepository.save(user);
    }


}
