package br.com.gestaovagas.gestao_vagas.modules.candidate.dto;

import java.util.UUID;

import br.com.gestaovagas.gestao_vagas.modules.candidate.entities.CandidateEntity;
import io.swagger.v3.oas.annotations.media.Schema;

public record ProfileCandidateResponseDTO (

    @Schema(description = "Identificador único do candidato", example = "f47ac10b-58cc-4372-a567-0e02b2c3d479")
    UUID id,

    @Schema(description = "Nome completo do candidato", example = "Gabriel André")
    String name,

    @Schema(description = "Nome de usuário do candidato", example = "gabriel123")
    String username,

    @Schema(description = "Endereço de e-mail do candidato", example = "gabriel@example.com")
    String email,

    @Schema(description = "Breve descrição do perfil do candidato", example = "Desenvolvedor Java com experiência em APIs REST.")
    String description
) {
    public ProfileCandidateResponseDTO(CandidateEntity candidate) {
        this(
                candidate.getId(),
                candidate.getName(),
                candidate.getUsername(),
                candidate.getEmail(),
                candidate.getDescription()
        );
    }
}
