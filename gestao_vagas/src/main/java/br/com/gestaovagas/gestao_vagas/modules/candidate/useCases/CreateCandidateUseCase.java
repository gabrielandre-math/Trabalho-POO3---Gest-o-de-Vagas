package br.com.gestaovagas.gestao_vagas.modules.candidate.useCases;

import br.com.gestaovagas.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.gestaovagas.gestao_vagas.exceptions.UserFoundException;
import br.com.gestaovagas.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.gestaovagas.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import jakarta.validation.Valid;

@Service
public class CreateCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ProfileCandidateResponseDTO execute(@Valid CandidateEntity candidateEntity) {
        this.candidateRepository
                .findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
                .ifPresent(user -> {
                    throw new UserFoundException();
                });
        var password = passwordEncoder.encode(candidateEntity.getPassword());
        candidateEntity.setPassword(password);

        var candidateSaved = candidateRepository.save(candidateEntity);

        return new ProfileCandidateResponseDTO(candidateSaved);
    }
}
