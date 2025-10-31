package br.com.artheus.matchhire.mapper;

import br.com.artheus.matchhire.domain.model.User;
import br.com.artheus.matchhire.dto.AuthRequestDTO;
import br.com.artheus.matchhire.dto.AuthResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper responsible for converting authentication-related DTOs and User entities.
 */

@Mapper(componentModel = "spring")
public interface AuthMapper {

    @Mapping(target = "login", source = "login")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publicId", ignore = true)
    @Mapping(target = "role", ignore = true)
    User toEntity(AuthRequestDTO dto);

    @Mapping(target = "accessToken", ignore = true)
    AuthResponseDTO toDTO(User user);
}
