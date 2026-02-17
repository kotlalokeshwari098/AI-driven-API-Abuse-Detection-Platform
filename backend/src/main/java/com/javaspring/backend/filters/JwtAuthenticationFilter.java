package com.javaspring.backend.filters;

import com.javaspring.backend.service.JwtService;
import com.javaspring.backend.service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final MyUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, MyUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");
        final String jwt;
        final String email;

        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authorizationHeader.substring(7);
        email=jwtService.extractUsername(jwt);

        if(email !=null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userdetails=userDetailsService.loadUserByUsername(email);

            if(jwtService.isTokenValid(jwt,userdetails)) {
                UsernamePasswordAuthenticationToken  authentication =
                        new UsernamePasswordAuthenticationToken(userdetails, null, userdetails.getAuthorities());
                authentication.setDetails(new
                        WebAuthenticationDetailsSource()
                        .buildDetails(request));

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
