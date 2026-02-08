package com.javaspring.backend.interceptor;


import com.javaspring.backend.model.ApiRequestLog;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ApiRequestInterceptor implements HandlerInterceptor {

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
            request.setAttribute("StartTime", System.currentTimeMillis());
            request.setAttribute("requestURI", request.getRequestURI());


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ApiRequestLog responsemodel=new ApiRequestLog();
        System.out.println("afterCompletion after sending request to controller"+responsemodel+"❤️❤️❤️❤️");
    }
}
