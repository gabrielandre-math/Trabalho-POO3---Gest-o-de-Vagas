package br.com.gestaovagas.gestao_vagas.modules.company.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "job")
@Table(name = "jobs")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "A descrição da vaga é obrigatória.")
    @Length(min = 10, max = 1000, message = "A descrição deve ter entre 10 e 1000 caracteres.")
    private String description;

    @NotBlank(message = "Os benefícios da vaga são obrigatórios.")
    @Length(min = 5, max = 500, message = "Os benefícios devem ter entre 5 e 500 caracteres.")
    private String benefits;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false, referencedColumnName = "id", insertable = false, updatable = false)
    private CompanyEntity company;

    @Column(name = "company_id", nullable = false)
    private UUID companyId;

    @Column(nullable = false, columnDefinition = "varchar(255) default 'JUNIOR'")
    @NotBlank(message = "O level é necessário.")
    private String level;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
