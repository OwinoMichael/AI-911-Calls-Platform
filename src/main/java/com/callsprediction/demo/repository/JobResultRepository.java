package com.callsprediction.demo.repository;

import com.callsprediction.demo.model.JobResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobResultRepository extends JpaRepository<JobResult, Long> {
    List<JobResult> findByJobId(Long jobId);
}
