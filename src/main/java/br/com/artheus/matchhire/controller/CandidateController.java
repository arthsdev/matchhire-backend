package br.com.artheus.matchhire.controller;

import br.com.artheus.matchhire.dto.CandidateRequestDTO;
import br.com.artheus.matchhire.dto.CandidateResponseDTO;
import br.com.artheus.matchhire.service.CandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/candidates")
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateService candidateService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CandidateResponseDTO createCandidate(@Valid @RequestBody CandidateRequestDTO dto) {
        return candidateService.createCandidate(dto);
    }

    @GetMapping("/{publicId}")
    public CandidateResponseDTO getCandidate(@PathVariable UUID publicId) {
        return candidateService.getCandidateByPublicId(publicId);
    }

    @GetMapping
    public List<CandidateResponseDTO> getAllCandidates() {
        return candidateService.getAllCandidates();
    }

    @PutMapping("/{publicId}")
    public CandidateResponseDTO updateCandidate(
            @PathVariable UUID publicId,
            @Valid @RequestBody CandidateRequestDTO dto) {
        return candidateService.updateCandidate(publicId, dto);
    }

    @DeleteMapping("/{publicId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivateCandidate(@PathVariable UUID publicId) {
        candidateService.deactivateCandidate(publicId);
    }
}
