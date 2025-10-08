package br.com.artheus.matchhire.mapper;

import br.com.artheus.matchhire.domain.model.Application;
import br.com.artheus.matchhire.dto.ApplicationRequestDTO;
import br.com.artheus.matchhire.dto.ApplicationResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {

    Application toEntity(ApplicationRequestDTO dto);

    @Mappings({
            @Mapping(source = "candidate.id", target = "candidateId"),
            @Mapping(source = "candidate.name", target = "candidateName"),
            @Mapping(source = "job.id", target = "jobId"),
            @Mapping(source = "job.title", target = "jobTitle")
    })
    ApplicationResponseDTO toResponseDTO(Application application);
}
