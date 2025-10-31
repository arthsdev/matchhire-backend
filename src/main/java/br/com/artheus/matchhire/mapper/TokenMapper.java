package br.com.artheus.matchhire.mapper;

import br.com.artheus.matchhire.dto.AuthTokens;
import br.com.artheus.matchhire.dto.LoginResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper responsible for mapping token-related responses.
 */
@Mapper(componentModel = "spring")
public interface TokenMapper {

    TokenMapper INSTANCE = Mappers.getMapper(TokenMapper.class);

    default LoginResponseDTO toLoginResponseDTO(AuthTokens tokens) {
        return new LoginResponseDTO(tokens.accessToken(), tokens.refreshToken());
    }
}
