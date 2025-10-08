package br.com.artheus.matchhire.controller;

import br.com.artheus.matchhire.dto.JobRequestDTO;
import br.com.artheus.matchhire.dto.JobResponseDTO;
import br.com.artheus.matchhire.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    /**
     * Create a new job.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JobResponseDTO createJob(@Valid @RequestBody JobRequestDTO dto) {
        return jobService.createJob(dto);
    }

    /**
     * Retrieve a job by publicId.
     */
    @GetMapping("/{publicId}")
    public JobResponseDTO getJob(@PathVariable UUID publicId) {
        return jobService.getJobByPublicId(publicId);
    }

    /**
     * List all active jobs.
     */
    @GetMapping
    public List<JobResponseDTO> getAllJobs() {
        return jobService.getAllJobs();
    }

    /**
     * Update an existing job by publicId.
     */
    @PutMapping("/{publicId}")
    public JobResponseDTO updateJob(
            @PathVariable UUID publicId,
            @Valid @RequestBody JobRequestDTO dto) {
        return jobService.updateJob(publicId, dto);
    }

    /**
     * Soft delete a job (deactivate).
     */
    @DeleteMapping("/{publicId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivateJob(@PathVariable UUID publicId) {
        jobService.deactivateJob(publicId);
    }
}
