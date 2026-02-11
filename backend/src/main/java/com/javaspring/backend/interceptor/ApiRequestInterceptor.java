package com.javaspring.backend.interceptor;


import com.javaspring.backend.filters.AuthenticationFilter;
import com.javaspring.backend.model.ApiRequestLog;
import com.javaspring.backend.service.ApiRequestLogService;
import com.javaspring.backend.utils.ApiRequestLogBuilder;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Instant;


@Component
public class ApiRequestInterceptor implements HandlerInterceptor {

    public ApiRequestLogService apiRequestLogService;

    public ApiRequestLogBuilder apiRequestLogBuilder;

    public ApiRequestInterceptor(ApiRequestLogService apiRequestLogService, ApiRequestLogBuilder apiRequestLogBuilder) {
        this.apiRequestLogService = apiRequestLogService;
        this.apiRequestLogBuilder = apiRequestLogBuilder;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            System.out.println("preHandle Before sending request to controller");
            System.out.println("Method Type: " + request.getMethod());
            System.out.println("Request URI: " + request.getRequestURI());
            System.out.println("Query params" + request.getQueryString());
            System.out.println("Content-Type: " + request.getContentType());
            System.out.println("Client IP: "+ request.getRemoteAddr());

            //per request memory
            if(request.getAttribute("startTime") == null) {
                request.setAttribute("startTime", Instant.now());
            }
            request.setAttribute("requestURI", request.getRequestURI());


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        System.out.println("afterCompletion Before sending request to controller"+request.getDispatcherType());

    }
}
