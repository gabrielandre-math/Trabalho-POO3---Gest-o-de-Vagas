package br.com.gestaovagas.gestao_vagas.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthCompanyDTO {

    @Schema(example = "empresa123", description = "Nome de usuário da empresa", requiredMode = RequiredMode.REQUIRED)
    private String username;

    @Schema(example = "SenhaForte@123", description = "Senha da empresa", requiredMode = RequiredMode.REQUIRED)
    private String password;
}
