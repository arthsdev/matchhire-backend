package br.com.artheus.matchhire.mapper;

import br.com.artheus.matchhire.domain.model.Company;
import br.com.artheus.matchhire.dto.CompanyRequestDTO;
import br.com.artheus.matchhire.dto.CompanyResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    /**
     * Maps a CompanyRequestDTO to a Company entity.
     */
    Company toEntity(CompanyRequestDTO dto);

    /**
     * Maps a Company entity to a CompanyResponseDTO.
     */
    @Mapping(source = "publicId", target = "publicId")
    CompanyResponseDTO toResponseDTO(Company company);
}
