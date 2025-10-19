package br.com.artheus.matchhire.mapper;

import br.com.artheus.matchhire.domain.model.User;
import br.com.artheus.matchhire.dto.RegisterDTO;
import br.com.artheus.matchhire.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Maps between User entity and UserDTO/UserCreateDTO.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * Maps a User entity to UserDTO.
     */
    UserDTO toDTO(User user);

    /**
     * Maps a UserCreateDTO to User entity.
     * The password should be encoded separately before saving.
     */
    @Mapping(target = "publicId", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    User toEntity(RegisterDTO dto);
}
