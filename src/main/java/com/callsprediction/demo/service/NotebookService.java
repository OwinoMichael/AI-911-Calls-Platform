package com.callsprediction.demo.service;

import com.callsprediction.demo.model.Job;
import com.callsprediction.demo.model.JobResult;
import com.callsprediction.demo.repository.JobRepository;
import com.callsprediction.demo.repository.JobResultRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class NotebookService {

    private final JobRepository jobRepository;
    private final JobResultRepository jobResultRepository;

    @Value("${notebook.output.images:src/output/%s/images/}")
    private String imagesPath;

    @Value("${notebook.output.results:src/output/%s/results/}")
    private String resultsPath;

    @Value("${notebook.output.results:src/output/%s/}")
    private String baseDirPath;

    public NotebookService(JobRepository jobRepository, JobResultRepository jobResultRepository) {
        this.jobRepository = jobRepository;
        this.jobResultRepository = jobResultRepository;
    }

    @Async
    public void runNotebookAsync(Job job) {
        try {
            job.setStatus("RUNNING");
            jobRepository.save(job);

            String notebookPath = "src/python/911_data_notebook.ipynb";
            String outputNotebookPath = "src/python/911_data_executed_notebook.ipynb";
            long jobId = job.getId();

            boolean success = runPythonNotebook(notebookPath, outputNotebookPath, jobId);
            if (!success) {
                job.setStatus("FAILED");
                jobRepository.save(job);
                return;
            }

            File baseDir = new File(String.format(baseDirPath, jobId));
            if (!baseDir.exists()) {
                System.err.println("Base directory does not exist: " + baseDirPath);
            }
            // Upload images/
            File imagesDir = new File(baseDir, "images");
            if (imagesDir.exists()) {
                for (File file : imagesDir.listFiles()) {
                    if (file.isFile()) {
                        saveResult(job, file, "image/png");
                    }
                }
            }

            // Upload results/
            File resultsDir = new File(baseDir, "results");
            if (resultsDir.exists()) {
                for (File file : resultsDir.listFiles()) {
                    if (file.isFile()) {
                        saveResult(job, file, "application/json");
                    }
                }
            }

            job.setStatus("COMPLETED");
            jobRepository.save(job);

        } catch (Exception e) {
            job.setStatus("FAILED");
            jobRepository.save(job);
            e.printStackTrace();
        }
    }

    private boolean runPythonNotebook(String notebookPath, String outputNotebookPath, Long jobId) {
        String pythonCommand = "python3 src/python/run_notebook.py" + " " + notebookPath + " " + outputNotebookPath + " " +  jobId.toString();

        try {
            Process process = Runtime.getRuntime().exec(pythonCommand);

            // stdout
            try (BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                in.lines().forEach(System.out::println);
            }

            // stderr
            try (BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                err.lines().forEach(System.err::println);
            }

            int exitCode = process.waitFor();
            return exitCode == 0;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }



    private void saveResult(Job job, File file, String fileType) {
        try {
            if (file.length() == 0) {
                System.err.println("Skipping empty file: " + file.getName());
                return;
            }

            JobResult result = new JobResult();
            result.setJob(job);
            result.setFilename(file.getName());
            result.setFileType(fileType);
            result.setFile(convertFileToByteArray(file));
            jobResultRepository.save(result);
        } catch (IOException e) {
            System.err.println("Error saving file: " + file.getName());
            e.printStackTrace();
        }
    }

    private byte[] convertFileToByteArray(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] fileBytes = new byte[(int) file.length()];
            fis.read(fileBytes);
            return fileBytes;
        }
    }
}
