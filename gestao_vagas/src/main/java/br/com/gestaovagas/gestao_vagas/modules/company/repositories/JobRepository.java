package br.com.gestaovagas.gestao_vagas.modules.company.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.gestaovagas.gestao_vagas.modules.company.entities.JobEntity;

public interface JobRepository extends JpaRepository<JobEntity, UUID> {

    @Query("""
                SELECT j
                  FROM job j
                 WHERE LOWER(j.description) LIKE LOWER(CONCAT('%', :f, '%'))
                    OR LOWER(j.benefits) LIKE LOWER(CONCAT('%', :f, '%'))
            """)
    List<JobEntity> findByDescriptionContainingIgnoreCase(@Param("f") String filter);
}
