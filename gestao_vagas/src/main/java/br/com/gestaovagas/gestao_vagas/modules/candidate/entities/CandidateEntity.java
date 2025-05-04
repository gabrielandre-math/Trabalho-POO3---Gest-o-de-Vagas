package br.com.gestaovagas.gestao_vagas.modules.candidate.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.Data;

@Data
@Entity(name = "candidate")
@Table(name = "candidates")
public class CandidateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(description = "Identificador único do candidato", example = "d9b2d63d-a233-4123-847a-7e5af1234567", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @NotBlank(message = "O campo 'name' é obrigatório.")
    @Length(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres.")
    @Schema(description = "Nome completo do candidato", example = "João da Silva", requiredMode = RequiredMode.REQUIRED)
    private String name;

    @NotBlank(message = "Não é possível enviar o username em branco.")
    @Pattern(regexp = "^[^\\s]+$", message = "O campo 'username' não deve conter espaços.")
    @Schema(description = "Nome de usuário (sem espaços)", example = "joaosilva", requiredMode = RequiredMode.REQUIRED)
    private String username;

    @NotBlank(message = "Você deve enviar algum e-mail.")
    @Email(message = "O campo 'email' deve conter um endereço válido.")
    @Schema(description = "Email do candidato", example = "joao@email.com", requiredMode = RequiredMode.REQUIRED)
    private String email;

    @NotBlank(message = "A senha é necessária.")
    @Length(min = 10, max = 100, message = "A 'senha' deve ter no mínimo 10 e menos de 100 caracteres.")
    @Schema(description = "Senha do candidato", example = "SenhaForte@123", requiredMode = RequiredMode.REQUIRED)
    private String password;

    @Schema(description = "Descrição adicional do perfil do candidato", example = "Desenvolvedor Backend apaixonado por Java.")
    private String description;

    @Schema(description = "URL do currículo do candidato", example = "https://meucurriculo.com/joao")
    private String curriculum;

    @CreationTimestamp
    @Schema(description = "Data de criação do registro", example = "2025-05-04T13:45:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;
}
