package br.com.artheus.matchhire.controller;

import br.com.artheus.matchhire.dto.CompanyRequestDTO;
import br.com.artheus.matchhire.dto.CompanyResponseDTO;
import br.com.artheus.matchhire.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    /**
     * Creates a new company.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyResponseDTO createCompany(@Valid @RequestBody CompanyRequestDTO dto) {
        return companyService.createCompany(dto);
    }

    /**
     * Retrieves a company by publicId.
     */
    @GetMapping("/{publicId}")
    public CompanyResponseDTO getCompany(@PathVariable UUID publicId) {
        return companyService.getCompanyByPublicId(publicId);
    }

    /**
     * Lists all active companies.
     */
    @GetMapping
    public List<CompanyResponseDTO> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    /**
     * Updates company information.
     */
    @PutMapping("/{publicId}")
    public CompanyResponseDTO updateCompany(
            @PathVariable UUID publicId,
            @Valid @RequestBody CompanyRequestDTO dto) {
        return companyService.updateCompany(publicId, dto);
    }

    /**
     * Performs a soft delete of a company.
     */
    @DeleteMapping("/{publicId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivateCompany(@PathVariable UUID publicId) {
        companyService.deactivateCompany(publicId);
    }
}
