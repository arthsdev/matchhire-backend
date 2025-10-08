package br.com.artheus.matchhire.dto;

import java.util.UUID;

/**
 * DTO for returning Job information.
 */
public record JobResponseDTO(

        UUID publicId,

        String title,

        String description,

        String requirements,

        UUID companyId,

        String companyName,

        boolean active
) {}
