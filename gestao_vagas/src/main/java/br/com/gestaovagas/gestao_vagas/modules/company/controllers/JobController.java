package br.com.gestaovagas.gestao_vagas.modules.company.controllers;

import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import br.com.gestaovagas.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.gestaovagas.gestao_vagas.modules.company.entities.JobEntity;
import br.com.gestaovagas.gestao_vagas.modules.company.useCases.CreateJobUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@Tag(name = "Vaga", description = "Endpoints para cadastro de vagas pelas empresas")
@SecurityRequirement(name = "jwt_auth")
@RestController
@RequestMapping("/company/job")
public class JobController {

        @Autowired
        private CreateJobUseCase createJobUseCase;

        @Operation(summary = "Criar nova vaga", description = """
                        Cria uma nova vaga associada à empresa autenticada.
                        """)
        @ApiResponses({
                        @ApiResponse(responseCode = "200", content = {
                                        @Content(schema = @Schema(implementation = JobEntity.class))
                        })
        })

        @PostMapping("/new-job")
        @PreAuthorize("hasRole('COMPANY')")
        @SecurityRequirement(name = "jwt_auth")
        public ResponseEntity<Object> create(
                        @Valid @RequestBody CreateJobDTO createJobDTO,
                        HttpServletRequest request) {
                var companyId = UUID.fromString(request.getAttribute("company_id").toString());
                try {
                        var jobEntity = JobEntity.builder()
                                        .benefits(createJobDTO.getBenefits())
                                        .companyId(companyId)
                                        .description(createJobDTO.getDescription())
                                        .level(createJobDTO.getLevel())
                                        .build();

                        var result = createJobUseCase.execute(jobEntity);
                        return ResponseEntity.ok().body(result);
                } catch (Exception e) {
                        return ResponseEntity.badRequest().body(e.getMessage());
                }

        }
}
