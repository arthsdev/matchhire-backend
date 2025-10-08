package br.com.artheus.matchhire.service;

import br.com.artheus.matchhire.domain.model.Company;
import br.com.artheus.matchhire.domain.model.Job;
import br.com.artheus.matchhire.domain.repository.CompanyRepository;
import br.com.artheus.matchhire.domain.repository.JobRepository;
import br.com.artheus.matchhire.dto.JobRequestDTO;
import br.com.artheus.matchhire.dto.JobResponseDTO;
import br.com.artheus.matchhire.exception.BusinessException;
import br.com.artheus.matchhire.exception.NotFoundException;
import br.com.artheus.matchhire.mapper.JobMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;
    private final JobMapper jobMapper;

    /**
     * Create a new job for a company.
     */
    public JobResponseDTO createJob(JobRequestDTO dto) {
        Company company = companyRepository.findByIdAndActiveTrue(dto.companyId())
                .orElseThrow(() -> new BusinessException("Company not found or inactive"));

        Job job = jobMapper.toEntity(dto);
        job.setCompany(company);

        Job saved = jobRepository.save(job);
        return jobMapper.toResponseDTO(saved);
    }

    /**
     * Retrieve a job by publicId.
     */
    public JobResponseDTO getJobByPublicId(UUID publicId) {
        Job job = jobRepository.findByPublicId(publicId)
                .orElseThrow(() -> new NotFoundException("Job not found"));

        if (!job.isActive()) {
            throw new BusinessException("Job is inactive");
        }

        return jobMapper.toResponseDTO(job);
    }

    /**
     * List all active jobs.
     */
    public List<JobResponseDTO> getAllJobs() {
        return jobRepository.findByActiveTrue().stream()
                .map(jobMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing job.
     */
    public JobResponseDTO updateJob(UUID publicId, JobRequestDTO dto) {
        Job job = jobRepository.findByPublicId(publicId)
                .orElseThrow(() -> new NotFoundException("Job not found"));

        if (!job.isActive()) {
            throw new BusinessException("Cannot update inactive job");
        }

        // Optional: check if the company is still active
        if (dto.companyId() != null && !job.getCompany().getId().equals(dto.companyId())) {
            Company company = companyRepository.findByIdAndActiveTrue(dto.companyId())
                    .orElseThrow(() -> new BusinessException("Company not found or inactive"));
            job.setCompany(company);
        }

        job.setTitle(dto.title());
        job.setDescription(dto.description());
        job.setRequirements(dto.requirements());

        Job updated = jobRepository.save(job);
        return jobMapper.toResponseDTO(updated);
    }

    /**
     * Soft delete a job.
     */
    public void deactivateJob(UUID publicId) {
        Job job = jobRepository.findByPublicId(publicId)
                .orElseThrow(() -> new NotFoundException("Job not found"));

        if (!job.isActive()) {
            throw new BusinessException("Job already inactive");
        }

        job.setActive(false);
        jobRepository.save(job);
    }
}
