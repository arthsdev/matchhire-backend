package br.com.artheus.matchhire.dto;

import br.com.artheus.matchhire.domain.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO for returning application data to the client.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationResponseDTO {

    private UUID id;             // Application ID
    private UUID candidateId;    // Candidate ID
    private String candidateName;
    private UUID jobId;          // Job ID
    private String jobTitle;
    private Status status;       // Application status
    private Boolean active;      // Soft delete flag
    private Double score;        // Optional match score
}
