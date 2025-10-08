package br.com.artheus.matchhire.service;

import br.com.artheus.matchhire.domain.model.Candidate;
import br.com.artheus.matchhire.domain.repository.CandidateRepository;
import br.com.artheus.matchhire.dto.CandidateRequestDTO;
import br.com.artheus.matchhire.dto.CandidateResponseDTO;
import br.com.artheus.matchhire.exception.BusinessException;
import br.com.artheus.matchhire.exception.NotFoundException;
import br.com.artheus.matchhire.mapper.CandidateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CandidateService {

    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;

    /**
     * Creates a new candidate.
     */
    public CandidateResponseDTO createCandidate(CandidateRequestDTO dto) {
        // Business rule: email must be unique
        candidateRepository.findByEmail(dto.email())
                .ifPresent(c -> { throw new BusinessException("Email already in use"); });

        Candidate candidate = candidateMapper.toEntity(dto);
        Candidate saved = candidateRepository.save(candidate);
        return candidateMapper.toResponseDTO(saved);
    }

    /**
     * Retrieves a candidate by publicId.
     */
    public CandidateResponseDTO getCandidateByPublicId(UUID publicId) {
        Candidate candidate = candidateRepository.findByPublicId(publicId)
                .orElseThrow(() -> new NotFoundException("Candidate not found"));

        if (!candidate.isActive()) {
            throw new BusinessException("Candidate is inactive");
        }

        return candidateMapper.toResponseDTO(candidate);
    }

    /**
     * Lists all active candidates.
     */
    public List<CandidateResponseDTO> getAllCandidates() {
        List<Candidate> candidates = candidateRepository.findByActiveTrue();
        return candidates.stream()
                .map(candidateMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Updates candidate information.
     */
    public CandidateResponseDTO updateCandidate(UUID publicId, CandidateRequestDTO dto) {
        Candidate candidate = candidateRepository.findByPublicId(publicId)
                .orElseThrow(() -> new NotFoundException("Candidate not found"));

        if (!candidate.isActive()) {
            throw new BusinessException("Cannot update inactive candidate");
        }

        // Optional business rules: cannot update email to one that already exists
        if (!candidate.getEmail().equals(dto.email())) {
            candidateRepository.findByEmail(dto.email())
                    .ifPresent(c -> { throw new BusinessException("Email already in use"); });
        }

        candidate.setName(dto.name());
        candidate.setEmail(dto.email());
        candidate.setSkills(dto.skills());
        candidate.setExperience(dto.experience());

        Candidate updated = candidateRepository.save(candidate);
        return candidateMapper.toResponseDTO(updated);
    }

    /**
     * Performs a soft delete of a candidate.
     */
    public void deactivateCandidate(UUID publicId) {
        Candidate candidate = candidateRepository.findByPublicId(publicId)
                .orElseThrow(() -> new NotFoundException("Candidate not found"));

        if (!candidate.isActive()) {
            throw new BusinessException("Candidate already inactive");
        }

        candidate.setActive(false);
        candidateRepository.save(candidate);
    }
}
