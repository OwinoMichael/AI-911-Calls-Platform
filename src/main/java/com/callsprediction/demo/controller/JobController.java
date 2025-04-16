package com.callsprediction.demo.controller;

import com.callsprediction.demo.dto.JobSummaryDTO;
import com.callsprediction.demo.model.Job;
import com.callsprediction.demo.repository.JobRepository;
import com.callsprediction.demo.service.JobResultService;
import com.callsprediction.demo.service.NotebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private final JobRepository jobRepository;

    @Autowired
    private final NotebookService notebookService;

    @Autowired
    private JobResultService jobResultService;

    public JobController(JobRepository jobRepository, NotebookService notebookService, JobResultService jobResultService) {
        this.jobRepository = jobRepository;
        this.notebookService = notebookService;
        this.jobResultService = jobResultService;
    }

    @PostMapping("/create")
    public Job createJob() {
        Job job = new Job("PENDING");
        job = jobRepository.save(job);
        return job;
    }

    @PostMapping("/{jobId}/run")
    public Job runNotebookForJob(@PathVariable Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        if (!"PENDING".equals(job.getStatus())) {
            throw new RuntimeException("Job already started or completed.");
        }
        notebookService.runNotebookAsync(job);
        return job;
    }

    @GetMapping("/{id}/results")
    public ResponseEntity<List<Map<String, Object>>> getAllResults(@PathVariable Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        if (!"COMPLETED".equals(job.getStatus())) {
            throw new RuntimeException("Job is not completed, can't get results.");
        }
        List<Map<String, Object>> results = jobResultService.getResultsByJobId(id);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/{jobId}")
    public Job getJob(@PathVariable Long jobId) {
        return jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
    }

    @GetMapping
    public List<JobSummaryDTO> listJobs(@RequestParam(required = false) String status) {
        List<Job> jobs;

        if (status != null && !status.isEmpty()) {
            jobs = jobRepository.findByStatus(status.toUpperCase());
        } else {
            jobs = jobRepository.findAll();
        }

        return jobs.stream()
                .map(job -> new JobSummaryDTO(
                        job.getId(),
                        job.getCreatedAt(),
                        job.getUpdatedAt(),
                        job.getStatus()
                ))
                .collect(Collectors.toList());
    }
}
