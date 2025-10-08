package br.com.artheus.matchhire.controller;

import br.com.artheus.matchhire.dto.ApplicationRequestDTO;
import br.com.artheus.matchhire.dto.ApplicationResponseDTO;
import br.com.artheus.matchhire.domain.model.enums.Status;
import br.com.artheus.matchhire.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller responsible for managing job applications.
 * Provides endpoints for creating, retrieving, updating, and deactivating applications.
 */
@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    /**
     * Creates a new job application.
     *
     * @param request validated application data (DTO)
     * @return the created application with generated publicId
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApplicationResponseDTO createApplication(@Valid @RequestBody ApplicationRequestDTO request) {
        return applicationService.createApplication(request);
    }

    /**
     * Retrieves a specific application by its public UUID.
     *
     * @param publicId the application's unique public identifier
     * @return the found application
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{publicId}")
    public ApplicationResponseDTO getApplicationById(@PathVariable UUID publicId) {
        return applicationService.getApplicationByPublicId(publicId);
    }

    /**
     * Lists all active applications for a given candidate.
     *
     * @param candidatePublicId the candidate's public UUID
     * @return list of active applications
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/candidate/{candidatePublicId}")
    public List<ApplicationResponseDTO> getApplicationsByCandidate(@PathVariable UUID candidatePublicId) {
        return applicationService.getApplicationsByCandidate(candidatePublicId);
    }

    /**
     * Updates the status of an existing application.
     *
     * @param publicId the application's public UUID
     * @param status   the new status to assign
     * @return the updated application
     */
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{publicId}/status")
    public ApplicationResponseDTO updateStatus(@PathVariable UUID publicId,
                                               @RequestParam Status status) {
        return applicationService.updateApplicationStatus(publicId, status);
    }

    /**
     * Deactivates an application (soft delete).
     *
     * @param publicId the application's public UUID
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{publicId}")
    public void deactivateApplication(@PathVariable UUID publicId) {
        applicationService.deactivateApplication(publicId);
    }
}
