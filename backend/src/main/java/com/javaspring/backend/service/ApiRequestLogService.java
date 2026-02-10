package com.javaspring.backend.service;


import com.javaspring.backend.model.ApiRequestLog;
import com.javaspring.backend.repository.ApiRequestLogRepository;
import org.springframework.stereotype.Service;

@Service
public class ApiRequestLogService {

    private ApiRequestLogRepository apiRequestLogRepository;

    public ApiRequestLogService(ApiRequestLogRepository apiRequestLogRepository) {
        this.apiRequestLogRepository=apiRequestLogRepository;
    }


    public void saveLogs(ApiRequestLog log) {
        apiRequestLogRepository.save(log);
    }
}
