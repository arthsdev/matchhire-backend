package br.com.artheus.matchhire.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CompanyRequestDTO(

        String publicId,

        @NotBlank(message = "Company name cannot be blank")
        @Size(min = 3, max = 100)
        String name,

        String industry,

        @Size(max = 1000)
        String description,

        boolean active
) {}
