package br.com.gestaovagas.gestao_vagas.modules.candidate.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCandidateResponseDTO {

    @Schema(description = "Identificador único do candidato", example = "f47ac10b-58cc-4372-a567-0e02b2c3d479")
    private UUID id;

    @Schema(description = "Nome completo do candidato", example = "Gabriel André")
    private String name;

    @Schema(description = "Nome de usuário do candidato", example = "gabriel123")
    private String username;

    @Schema(description = "Endereço de e-mail do candidato", example = "gabriel@example.com")
    private String email;

    @Schema(description = "Breve descrição do perfil do candidato", example = "Desenvolvedor Java com experiência em APIs REST.")
    private String description;
}
