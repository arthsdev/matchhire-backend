package br.com.artheus.matchhire.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * DTO for creating or updating a Job.
 */
public record JobRequestDTO(

        @NotBlank(message = "Job title cannot be blank")
        @Size(min = 10, max = 100)
        String title,

        @NotBlank(message = "Job description cannot be blank")
        @Size(min = 100, max = 2000)
        String description,

        String requirements,

        @NotNull(message = "Company ID cannot be null")
        UUID companyId
) {}
