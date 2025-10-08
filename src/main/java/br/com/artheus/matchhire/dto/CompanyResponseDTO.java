package br.com.artheus.matchhire.dto;

public record CompanyResponseDTO(

        String publicId,

        String name,

        String industry,

        String description,

        boolean active
) {}
