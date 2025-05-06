package br.com.gestaovagas.gestao_vagas.modules.candidate.useCases;

import br.com.gestaovagas.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteCandidateUseCase {

    @Autowired
    private CandidateRepository repository;

    public void execute(UUID id){
        this.repository.deleteById(id);
    }
}
