package br.com.artheus.matchhire.service;

import br.com.artheus.matchhire.domain.model.Application;
import br.com.artheus.matchhire.domain.model.Candidate;
import br.com.artheus.matchhire.domain.model.Job;
import br.com.artheus.matchhire.domain.model.enums.Status;
import br.com.artheus.matchhire.domain.repository.ApplicationRepository;
import br.com.artheus.matchhire.domain.repository.CandidateRepository;
import br.com.artheus.matchhire.domain.repository.JobRepository;
import br.com.artheus.matchhire.dto.ApplicationRequestDTO;
import br.com.artheus.matchhire.dto.ApplicationResponseDTO;
import br.com.artheus.matchhire.exception.BusinessException;
import br.com.artheus.matchhire.exception.NotFoundException;
import br.com.artheus.matchhire.mapper.ApplicationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final CandidateRepository candidateRepository;
    private final JobRepository jobRepository;
    private final ApplicationMapper applicationMapper;

    /**
     * Creates a new application for a candidate to a specific job.
     */
    public ApplicationResponseDTO createApplication(ApplicationRequestDTO dto) {
        Candidate candidate = candidateRepository.findById(dto.getCandidateId())
                .orElseThrow(() -> new NotFoundException("Candidate not found"));

        if (!candidate.isActive()) {
            throw new BusinessException("Candidate is inactive");
        }

        Job job = jobRepository.findById(dto.getJobId())
                .orElseThrow(() -> new NotFoundException("Job not found"));

        if (!job.isActive()) {
            throw new BusinessException("Job is inactive");
        }

        Application application = applicationMapper.toEntity(dto);
        application.setCandidate(candidate);
        application.setJob(job);

        Application savedApplication = applicationRepository.save(application);
        return applicationMapper.toResponseDTO(savedApplication);
    }

    /**
     * Retrieves an application by its public UUID.
     */
    public ApplicationResponseDTO getApplicationByPublicId(UUID publicId) {
        Application application = applicationRepository.findByPublicId(publicId)
                .orElseThrow(() -> new NotFoundException("Application not found"));

        if (!application.isActive()) {
            throw new BusinessException("Application is inactive");
        }

        return applicationMapper.toResponseDTO(application);
    }

    /**
     * Lists all active applications for a given candidate.
     */
    public List<ApplicationResponseDTO> getApplicationsByCandidate(UUID candidatePublicId) {
        Candidate candidate = candidateRepository.findByPublicId(candidatePublicId)
                .orElseThrow(() -> new NotFoundException("Candidate not found"));

        List<Application> applications = applicationRepository.findByCandidateAndActiveTrue(candidate);
        return applications.stream()
                .map(applicationMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Updates the status of an existing application.
     */
    public ApplicationResponseDTO updateApplicationStatus(UUID publicId, Status newStatus) {
        Application application = applicationRepository.findByPublicId(publicId)
                .orElseThrow(() -> new NotFoundException("Application not found"));

        if (!application.isActive()) {
            throw new BusinessException("Cannot update inactive application");
        }

        application.setStatus(newStatus);
        Application updated = applicationRepository.save(application);
        return applicationMapper.toResponseDTO(updated);
    }

    /**
     * Performs a soft delete (deactivation) instead of physical removal.
     */
    public void deactivateApplication(UUID publicId) {
        Application application = applicationRepository.findByPublicId(publicId)
                .orElseThrow(() -> new NotFoundException("Application not found"));

        if (!application.isActive()) {
            throw new BusinessException("Application already inactive");
        }

        application.setActive(false);
        applicationRepository.save(application);
    }
}
