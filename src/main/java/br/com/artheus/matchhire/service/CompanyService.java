package br.com.artheus.matchhire.service;

import br.com.artheus.matchhire.domain.model.Company;
import br.com.artheus.matchhire.domain.repository.CompanyRepository;
import br.com.artheus.matchhire.dto.CompanyRequestDTO;
import br.com.artheus.matchhire.dto.CompanyResponseDTO;
import br.com.artheus.matchhire.exception.BusinessException;
import br.com.artheus.matchhire.exception.NotFoundException;
import br.com.artheus.matchhire.mapper.CompanyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    /**
     * Creates a new company.
     * Business rule: Company name must be unique.
     */
    public CompanyResponseDTO createCompany(CompanyRequestDTO dto) {
        companyRepository.findByName(dto.name())
                .ifPresent(c -> { throw new BusinessException("Company name already exists"); });

        Company company = companyMapper.toEntity(dto);
        Company saved = companyRepository.save(company);
        return companyMapper.toResponseDTO(saved);
    }

    /**
     * Retrieves a company by publicId.
     */
    public CompanyResponseDTO getCompanyByPublicId(UUID publicId) {
        Company company = companyRepository.findByIdAndActiveTrue(publicId)
                .orElseThrow(() -> new NotFoundException("Company not found or inactive"));

        return companyMapper.toResponseDTO(company);
    }

    /**
     * Lists all active companies.
     */
    public List<CompanyResponseDTO> getAllCompanies() {
        List<Company> companies = companyRepository.findAll()
                .stream()
                .filter(Company::isActive)
                .collect(Collectors.toList());

        return companies.stream()
                .map(companyMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Updates company information.
     */
    public CompanyResponseDTO updateCompany(UUID publicId, CompanyRequestDTO dto) {
        Company company = companyRepository.findByIdAndActiveTrue(publicId)
                .orElseThrow(() -> new NotFoundException("Company not found or inactive"));

        if (!company.getName().equals(dto.name())) {
            companyRepository.findByName(dto.name())
                    .ifPresent(c -> { throw new BusinessException("Company name already exists"); });
        }

        company.setName(dto.name());
        company.setIndustry(dto.industry());
        company.setDescription(dto.description());

        Company updated = companyRepository.save(company);
        return companyMapper.toResponseDTO(updated);
    }

    /**
     * Performs a soft delete of a company.
     */
    public void deactivateCompany(UUID publicId) {
        Company company = companyRepository.findByIdAndActiveTrue(publicId)
                .orElseThrow(() -> new NotFoundException("Company not found or already inactive"));

        company.setActive(false);
        companyRepository.save(company);
    }
}
