package com.callsprediction.demo.service;

import com.callsprediction.demo.model.JobResult;
import com.callsprediction.demo.repository.JobResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

@Service
public class JobResultService {

    @Autowired
    private JobResultRepository jobResultRepository;

    public List<Map<String, Object>> getResultsByJobId(@PathVariable Long id) {

        Optional <List<JobResult>> resultsOptional = Optional.ofNullable(jobResultRepository.findByJobId(id));
        List<Map<String, Object>> response = new ArrayList<>();
        resultsOptional.ifPresent(resultsList -> {
            for (JobResult result : resultsList) {
                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("id", result.getId());
                resultMap.put("filename", result.getFilename());
                resultMap.put("fileType", result.getFileType());
                resultMap.put("file", Base64.getEncoder().encodeToString(result.getFile()));
                response.add(resultMap);
            }
        });
        return response;
    }

}
