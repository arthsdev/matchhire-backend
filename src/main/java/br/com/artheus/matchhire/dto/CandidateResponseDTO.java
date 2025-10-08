package br.com.artheus.matchhire.dto;

import java.util.UUID;

public record CandidateResponseDTO(
        UUID publicId,

        String name,

        String email,

        String skills,

        Integer experience,

        boolean active
) {
}
