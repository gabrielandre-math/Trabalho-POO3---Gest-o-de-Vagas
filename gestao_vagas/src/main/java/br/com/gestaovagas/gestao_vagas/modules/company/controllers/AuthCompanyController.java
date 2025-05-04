package br.com.gestaovagas.gestao_vagas.modules.company.controllers;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.gestaovagas.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.gestaovagas.gestao_vagas.modules.company.dto.AuthCompanyResponseDTO;
import br.com.gestaovagas.gestao_vagas.modules.company.useCases.AuthCompanyUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;

@Tag(name = "Empresa", description = "Autenticação de empresas")
@RestController
@RequestMapping("/company")
public class AuthCompanyController {

    @Autowired
    private AuthCompanyUseCase authCompanyUseCase;

    @Operation(summary = "Autenticar empresa", description = "Recebe username e password e retorna um objeto com o JWT e seu timestamp de expiração.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autenticação bem-sucedida", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthCompanyResponseDTO.class), examples = @ExampleObject(name = "AuthResponseExemplo", value = """
                    {
                      "accesToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnZXN0YW92YWdhcyIsImV4cCI6MTc0NjM1MzczMSwic3ViIjoiYjg2ZDJkNzktNDViZS00YjU2LTliZDctODA1ODQyMjMyZjFjIiwicm9sZXMiOlsiQ09NUEFOWSJdfQ.B1Kfgg24EzY4WgU6D7P1o-vthlZ1NGaAxTCAwov-7qg",
                      "expiresIn": 1746353731171
                    }
                    """))),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    @PostMapping("/auth")
    public ResponseEntity<AuthCompanyResponseDTO> create(
            @RequestBody AuthCompanyDTO authCompanyDTO) {
        try {
            AuthCompanyResponseDTO result = authCompanyUseCase.execute(authCompanyDTO);
            return ResponseEntity.ok(result);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
