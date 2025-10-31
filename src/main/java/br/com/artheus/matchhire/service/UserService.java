package br.com.artheus.matchhire.service;

import br.com.artheus.matchhire.domain.model.User;
import br.com.artheus.matchhire.domain.model.enums.UserRole;
import br.com.artheus.matchhire.domain.repository.jpa.UserRepository;
import br.com.artheus.matchhire.dto.RegisterDTO;
import br.com.artheus.matchhire.dto.UserDTO;
import br.com.artheus.matchhire.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service responsible for user management operations.
 * Now register returns the generated publicId so caller can respond with Location or body.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * Registers a new user with default USER role.
     * Returns the generated publicId.
     * Throws ResponseStatusException(HttpStatus.CONFLICT) if user already exists.
     */
    public String register(RegisterDTO dto) {
        userRepository.findByLogin(dto.login())
                .ifPresent(u -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
                });

        User newUser = userMapper.toEntity(dto);
        newUser.setPassword(passwordEncoder.encode(dto.password()));
        newUser.setRole(UserRole.USER);
        newUser.setPublicId(UUID.randomUUID().toString());

        userRepository.save(newUser);

        return newUser.getPublicId();
    }

    /**
     * Returns a list of all users as DTOs.
     * Passwords are never exposed.
     */
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a single user by publicId and maps to DTO.
     */
    public UserDTO getUserByPublicId(String publicId) {
        User user = userRepository.findByPublicId(publicId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return toDTO(user);
    }

    /**
     * Maps a User entity to UserDTO.
     */
    private UserDTO toDTO(User user) {
        return new UserDTO(user.getPublicId(), user.getLogin(), user.getRole());
    }
}
