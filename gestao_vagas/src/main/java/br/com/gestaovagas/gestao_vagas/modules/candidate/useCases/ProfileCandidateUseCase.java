package br.com.gestaovagas.gestao_vagas.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import br.com.gestaovagas.gestao_vagas.exceptions.UserNotFoundException;
import br.com.gestaovagas.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.gestaovagas.gestao_vagas.modules.candidate.repositories.CandidateRepository;

@Service
public class ProfileCandidateUseCase {
    @Autowired
    private CandidateRepository candidateRepository;

    public ProfileCandidateResponseDTO execute(UUID idCandidate) {
        var candidate = this.candidateRepository.findById(idCandidate)
                .orElseThrow(UserNotFoundException::new);

        return new ProfileCandidateResponseDTO(candidate);
    }
}
