package br.com.gestaovagas.gestao_vagas.modules.candidate.controllers;

import java.util.List;
import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import br.com.gestaovagas.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.gestaovagas.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.gestaovagas.gestao_vagas.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.gestaovagas.gestao_vagas.modules.candidate.useCases.ListAllJobsByFilterUseCase;
import br.com.gestaovagas.gestao_vagas.modules.candidate.useCases.ProfileCandidateUseCase;
import br.com.gestaovagas.gestao_vagas.modules.company.entities.JobEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

@Tag(name = "Candidato", description = "Operações de cadastro, perfil e busca de vagas para candidatos")
@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @Autowired
    private ProfileCandidateUseCase profileCandidateUseCase;

    @Autowired
    private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

    @Operation(summary = "Criar novo candidato", description = """
            Registra um novo candidato com os seguintes campos:
            • name (2–100 caracteres)
            • username (sem espaços)
            • email (formato válido)
            • password (10–100 caracteres)
            • description (opcional)
            • curriculum (URL opcional)
            Retorna o objeto `CandidateEntity` completo, já com `id` e `createdAt`.
            """)
    @PostMapping("/register")
    public ResponseEntity<Object> create(
            @Valid @RequestBody CandidateEntity candidateEntity) {
        try {
            var result = createCandidateUseCase.execute(candidateEntity);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Obter perfil do candidato autenticado", description = """
            Retorna todos os dados do candidato logado:
            • id, name, username, email
            • description
            """)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Perfil do candidato retornado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProfileCandidateResponseDTO.class), examples = @ExampleObject(name = "PerfilCandidatoExemplo", value = """
                    {
                      "id": "ad5eb131-adf6-4ef1-ac47-accbaa3dd474",
                      "name": "Jake Snow",
                      "username": "jake_snow",
                      "email": "jakecompany@gmail.com",
                      "description": "Sou techlead Back-End e busco oportunidades."
                    }
                    """))),
            @ApiResponse(responseCode = "400", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @SecurityRequirement(name = "jwt_auth")
    @PreAuthorize("hasRole('CANDIDATE')")
    @GetMapping("/me")
    public ResponseEntity<Object> getProfile(HttpServletRequest request) {
        var idCandidate = request.getAttribute("candidate_id");
        try {
            var profile = profileCandidateUseCase.execute(
                    UUID.fromString(idCandidate.toString()));
            return ResponseEntity.ok(profile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Listagem de vagas disponível para o candidato", description = "Retorna uma lista de vagas cujo título ou descrição contenham o termo informado em `filter`.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de vagas retornada com sucesso", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = JobEntity.class)))),
            @ApiResponse(responseCode = "400", description = "Parâmetro `filter` inválido"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @PreAuthorize("hasRole('CANDIDATE')")
    @GetMapping("/job")
    @SecurityRequirement(name = "jwt_auth")
    public List<JobEntity> findJobByFilter(@RequestParam String filter) {
        return this.listAllJobsByFilterUseCase.execute(filter);
    }
}
