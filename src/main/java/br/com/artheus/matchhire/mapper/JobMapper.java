package br.com.artheus.matchhire.mapper;

import br.com.artheus.matchhire.domain.model.Job;
import br.com.artheus.matchhire.dto.JobRequestDTO;
import br.com.artheus.matchhire.dto.JobResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Mapper for converting between Job entities and DTOs.
 */
@Mapper(componentModel = "spring")
public interface JobMapper {

    Job toEntity(JobRequestDTO dto);

    @Mappings({
            @Mapping(source = "company.id", target = "companyId"),
            @Mapping(source = "company.name", target = "companyName")
    })
    JobResponseDTO toResponseDTO(Job job);
}
