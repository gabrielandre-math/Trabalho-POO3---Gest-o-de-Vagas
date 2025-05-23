package br.com.gestaovagas.gestao_vagas.modules.candidate.controllers;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import br.com.gestaovagas.gestao_vagas.modules.candidate.useCases.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import br.com.gestaovagas.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.gestaovagas.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import br.com.gestaovagas.gestao_vagas.modules.candidate.entities.CandidateEntity;
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

    @Autowired
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @Autowired
    private UpdateCandidateUseCase updateCandidateUseCase;

    @Autowired
    private DeleteCandidateUseCase deleteCandidateUseCase;

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
    public ResponseEntity<ProfileCandidateResponseDTO> createCandidate(
            @Valid @RequestBody CandidateEntity candidateEntity) {
            var result = createCandidateUseCase.execute(candidateEntity);
            return ResponseEntity.created(URI.create("/candidate/" + result.id())).body(result);
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
    public ResponseEntity<ProfileCandidateResponseDTO> getProfile(HttpServletRequest request) {
        var idCandidate = request.getAttribute("candidate_id");
        var profile = profileCandidateUseCase.execute(UUID.fromString(idCandidate.toString()));
        return ResponseEntity.ok(profile);
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

    @Operation(summary = "Candidatar-se a uma vaga", description = "Permite que o candidato autenticado aplique-se a uma vaga informando o UUID da Vaga.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Candidatura realizada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApplyJobEntity.class), examples = @ExampleObject(name = "CandidaturaExemplo", value = """
                    {
                      "id": "ae12f3c4-5b6a-7d8e-9f01-23456789abcd",
                      "candidateId": "123e4567-e89b-12d3-a456-426614174000",
                      "jobId": "987f6543-21dc-ba09-8765-4321fedcba98"
                    }
                    """))),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos ou recurso não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @PostMapping("/job/apply")
    @PreAuthorize("hasRole('CANDIDATE')")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<ApplyJobEntity> applyJob(HttpServletRequest request, @RequestBody UUID idJob) {
        var idCandidate = request.getAttribute("candidate_id");
        var result = this.applyJobCandidateUseCase.execute(UUID.fromString(idCandidate.toString()), idJob);
        return ResponseEntity.ok().body(result);
    }

    @Operation(summary = "Atualizar perfil do candidato autenticado", description = """
            Atualiza os dados do candidato logado:
            • name, username, email, password
            • description
            """)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Perfil do candidato atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProfileCandidateResponseDTO.class), examples = @ExampleObject(name = "PerfilCandidatoExemplo", value = """
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

    @PutMapping("/update")
    @PreAuthorize("hasRole('CANDIDATE')")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<ProfileCandidateResponseDTO> updateCandidate(@Valid @RequestBody CandidateEntity candidate) {
        return ResponseEntity.ok(this.updateCandidateUseCase.execute(candidate));
    }

    @Operation(summary = "Deletar um candidato", description = "Excluí um candidato informando seu UUID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Candidato deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('CANDIDATE')")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<String> deleteCandidate(@PathVariable UUID id) {
        deleteCandidateUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
