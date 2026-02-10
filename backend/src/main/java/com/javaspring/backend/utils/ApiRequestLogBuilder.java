package com.javaspring.backend.utils;


import com.javaspring.backend.model.ApiRequestLog;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
public class ApiRequestLogBuilder {

    public ApiRequestLog build(HttpServletRequest request, HttpServletResponse response, Exception ex) throws Exception {

        ApiRequestLog log = new ApiRequestLog();
        Instant startTime = (Instant) request.getAttribute("startTime");

        if (startTime == null) {
            return null;
        }

        Instant endTime = Instant.now();

        long durationMs = Duration.between(startTime, endTime).toMillis();

        log.setMethod(request.getMethod());
        log.setEndpoint(request.getRequestURI());
        log.setClientIp(request.getRemoteAddr());
        log.setStatusCode(response.getStatus());
        log.setStartTime(startTime);
        log.setEndTime(endTime);
        log.setDurationMs(durationMs);

        if (ex != null) {
            log.setErrorMessage(ex.getMessage());
        }


        System.out.println("afterCompletion after sending request to controller" + log.toString());
        return log;
    }
}
