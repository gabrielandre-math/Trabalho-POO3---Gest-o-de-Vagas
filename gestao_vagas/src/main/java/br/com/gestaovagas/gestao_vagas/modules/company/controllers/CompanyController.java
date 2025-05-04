package br.com.gestaovagas.gestao_vagas.modules.company.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.gestaovagas.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.gestaovagas.gestao_vagas.modules.company.useCases.CreateCompanyUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;

@Tag(name = "Empresa", description = "Endpoints para cadastro e gerenciamento de empresas")
@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CreateCompanyUseCase createCompanyUseCase;

    @Operation(summary = "Registrar nova empresa", description = """
            Recebe os dados de uma nova empresa e persiste no sistema.
            Campos obrigatórios em CompanyEntity:
            • name (String)
            • username (String, sem espaços)
            • email (String, formato válido)
            • password (String, 10–100 caracteres)
            • (demais campos conforme CompanyEntity)
            Retorna o objeto `CompanyEntity` criado, com `id` e `createdAt`.
            """)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empresa registrada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CompanyEntity.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição")
    })
    @PostMapping("/register")
    public ResponseEntity<CompanyEntity> create(
            @Valid @RequestBody CompanyEntity companyEntity) {
        try {
            var created = createCompanyUseCase.execute(companyEntity);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(null);
        }
    }
}
