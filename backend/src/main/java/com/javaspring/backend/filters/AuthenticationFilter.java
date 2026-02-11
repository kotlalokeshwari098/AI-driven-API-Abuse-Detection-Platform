package com.javaspring.backend.filters;

import com.javaspring.backend.model.ApiRequestLog;
import com.javaspring.backend.service.ApiRequestLogService;
import com.javaspring.backend.utils.ApiRequestLogBuilder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;


@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    ApiRequestLogService apiRequestLogService;

    public ApiRequestLogBuilder apiRequestLogBuilder;


    public AuthenticationFilter(ApiRequestLogService apiRequestLogService, ApiRequestLogBuilder apiRequestLogBuilder) {
        this.apiRequestLogService=apiRequestLogService;
        this.apiRequestLogBuilder=apiRequestLogBuilder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getAttribute("startTime") == null) {
            request.setAttribute("startTime", Instant.now());
        }


        try{
          System.out.println("AuthenticationFilter before doFilterInternal");
          filterChain.doFilter(request,response);

        }
        catch(Exception e){
            e.printStackTrace();
        }
       finally{

           System.out.println("AuthenticationFilter after doFilterInternal<UNK>");
            ApiRequestLog log = null;
            try {
                    log = apiRequestLogBuilder.build(request, response, null);
            } catch (Exception e) {
                    throw new RuntimeException(e);
            }
            if (log != null) {
                    apiRequestLogService.saveLogs(log);
            }
        }
    }
}
