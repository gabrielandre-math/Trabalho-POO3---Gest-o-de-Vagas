package br.com.gestaovagas.gestao_vagas.modules.candidate.useCases;

import br.com.gestaovagas.gestao_vagas.exceptions.UserNotFoundException;
import br.com.gestaovagas.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.gestaovagas.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.gestaovagas.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateCandidateUseCase {
    @Autowired
    private CandidateRepository repository;

    public ProfileCandidateResponseDTO execute(@Valid CandidateEntity candidate) {

        var isCandidate = this.repository.existsById(candidate.getId());

        if (!isCandidate) throw new UserNotFoundException();

        this.repository.save(candidate);

        return new ProfileCandidateResponseDTO(candidate);
    }
}
