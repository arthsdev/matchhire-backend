package br.com.artheus.matchhire.mapper;

import br.com.artheus.matchhire.domain.model.Application;
import br.com.artheus.matchhire.dto.ApplicationRequestDTO;
import br.com.artheus.matchhire.dto.ApplicationResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true), // Ignored because it's initialized with a default value
            @Mapping(target = "publicId", ignore = true),
            @Mapping(target = "candidate", ignore = true),// Ignored because it's set manually in the service layer using the candidateId from the DTO
            @Mapping(target = "job", ignore = true),  //Ignored because it's set manually in the service layer using the jobId from the DTO
            @Mapping(target = "status", ignore = true),
            @Mapping(target = "active", ignore = true),
            @Mapping(target = "score", ignore = true)
    })
    Application toEntity(ApplicationRequestDTO dto);

    @Mappings({
            @Mapping(source = "candidate.id", target = "candidateId"),
            @Mapping(source = "candidate.name", target = "candidateName"),
            @Mapping(source = "job.id", target = "jobId"),
            @Mapping(source = "job.title", target = "jobTitle")
    })
    ApplicationResponseDTO toResponseDTO(Application application);
}

