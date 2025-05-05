package br.com.gestaovagas.gestao_vagas.modules.candidates.useCases;

import br.com.gestaovagas.gestao_vagas.exceptions.UserNotFoundException;
import br.com.gestaovagas.gestao_vagas.exceptions.JobNotFoundException;
import br.com.gestaovagas.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import br.com.gestaovagas.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.gestaovagas.gestao_vagas.modules.candidate.repositories.ApplyJobRepository;
import br.com.gestaovagas.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import br.com.gestaovagas.gestao_vagas.modules.company.entities.JobEntity;
import br.com.gestaovagas.gestao_vagas.modules.company.repositories.JobRepository;
import br.com.gestaovagas.gestao_vagas.modules.candidate.useCases.ApplyJobCandidateUseCase;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateUseCaseTest {

    @InjectMocks
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private ApplyJobRepository applyJobRepository;

    @Test
    @DisplayName("Shoulb not be able to apply job with candidate not found")
    public void shouldNotBeAbleToApplyJobWithCandidateNotFound() {
        try {
            applyJobCandidateUseCase.execute(null, null);
        } catch (Exception e) {
            Assertions.assertThat(e).isInstanceOf(UserNotFoundException.class);
        }
    }

    @Test
    public void shouldNotBeAbleToApplyJobWithJobNotFound() {

        var idCandidate = UUID.randomUUID();
        var candidate = new CandidateEntity();
        candidate.setId(idCandidate);

        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(candidate));

        try {
            applyJobCandidateUseCase.execute(idCandidate, null);
        } catch (Exception e) {
            Assertions.assertThat(e).isInstanceOf(JobNotFoundException.class);
        }
    }

    @Test
    public void shouldBeAbleToCreateNewApplyJob() {
        var idCandidate = UUID.randomUUID();
        var idJob = UUID.randomUUID();

        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(new CandidateEntity()));
        when(jobRepository.findById(idJob)).thenReturn(Optional.of(new JobEntity()));

        ArgumentCaptor<ApplyJobEntity> applyJobCaptor = ArgumentCaptor.forClass(ApplyJobEntity.class);
        when(applyJobRepository.save(applyJobCaptor.capture())).thenReturn(createApplyJobWithId());

        var result = applyJobCandidateUseCase.execute(idCandidate, idJob);

        ApplyJobEntity capturedEntity = applyJobCaptor.getValue();
        Assertions.assertThat(capturedEntity.getCandidateId()).isEqualTo(idCandidate);
        Assertions.assertThat(capturedEntity.getJobId()).isEqualTo(idJob);

        Assertions.assertThat(result).hasFieldOrProperty("id");
    }

    private ApplyJobEntity createApplyJobWithId() {
        var entity = new ApplyJobEntity();
        entity.setId(UUID.randomUUID());
        return entity;
    }
}
