package br.com.gestaovagas.gestao_vagas.modules.company.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "company")
@Table(name = "companies")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(description = "Identificador único da empresa", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @NotBlank(message = "O campo 'username' é obrigatório.")
    @Pattern(regexp = "^[^\\s]+$", message = "O campo 'username' não deve conter espaços.")
    @Schema(description = "Nome de usuário da empresa (sem espaços)", example = "minhaEmpresa123", requiredMode = RequiredMode.REQUIRED)
    private String username;

    @NotBlank(message = "Você deve enviar algum e-mail.")
    @Email(message = "O campo 'email' deve conter um endereço válido.")
    @Schema(description = "E-mail de contato da empresa", example = "contato@minhaempresa.com", requiredMode = RequiredMode.REQUIRED)
    private String email;

    @NotBlank(message = "A senha é necessária.")
    @Length(min = 10, max = 100, message = "A 'senha' deve ter no mínimo 10 e menos de 100 caracteres.")
    @Schema(description = "Senha de acesso (10–100 caracteres)", example = "SenhaForte!2025", requiredMode = RequiredMode.REQUIRED)
    private String password;

    @Pattern(regexp = "^(https?|ftp)://.*$", message = "O site deve conter uma URL válida (ex: http://...)")
    @Schema(description = "URL do site da empresa", example = "https://www.minhaempresa.com")
    private String website;

    @NotBlank(message = "O campo 'name' é obrigatório.")
    @Length(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres.")
    @Schema(description = "Razão social ou nome fantasia da empresa", example = "Minha Empresa LTDA", requiredMode = RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Descrição breve da empresa", example = "Somos uma startup de tecnologia focada em inovação.")
    private String description;

    @CreationTimestamp
    @Schema(description = "Data e hora de criação do registro", example = "2025-05-04T14:20:30", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;
}
