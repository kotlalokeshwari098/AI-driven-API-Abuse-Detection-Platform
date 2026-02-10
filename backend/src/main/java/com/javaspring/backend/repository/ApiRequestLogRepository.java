package com.javaspring.backend.repository;

import com.javaspring.backend.model.ApiRequestLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ApiRequestLogRepository extends
        JpaRepository<ApiRequestLog,Long> {

}
