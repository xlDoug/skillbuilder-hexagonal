package br.com.nttdata.skillbuilder.adapter.out.persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.nttdata.skillbuilder.adapter.out.persistence.entity.AtividadeEntity;
import br.com.nttdata.skillbuilder.adapter.out.persistence.repository.AtividadeJpaRepository;
import br.com.nttdata.skillbuilder.application.port.out.AtividadeRepository;
import br.com.nttdata.skillbuilder.domain.model.Atividade;

@Component
public class AtividadeRepositoryAdapter implements AtividadeRepository {

    private final AtividadeJpaRepository atividadeJpaRepository;

    public AtividadeRepositoryAdapter(AtividadeJpaRepository atividadeJpaRepository) {
        this.atividadeJpaRepository = atividadeJpaRepository;
    }

    @Override
    public Atividade salvar(Atividade atividade) {
        AtividadeEntity entity = new AtividadeEntity(atividade);
        AtividadeEntity savedEntity = atividadeJpaRepository.save(entity);
        return savedEntity.toDomainModel();
    }

    @Override
    public Optional<Atividade> buscarPorId(UUID id) {
        return atividadeJpaRepository.findById(id.toString())
                .map(AtividadeEntity::toDomainModel);
    }

    @Override
    public List<Atividade> buscarPorCursoId(UUID cursoId) {
        return atividadeJpaRepository.findByCursoId(cursoId.toString()).stream()
                .map(AtividadeEntity::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Atividade> buscarAtividadesDisponiveis(UUID cursoId) {
        return atividadeJpaRepository.findByCursoIdAndDataDisponibilidadeLessThanEqual(
                cursoId.toString(), LocalDate.now()).stream()
                .map(AtividadeEntity::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public void deletar(UUID id) {
        atividadeJpaRepository.deleteById(id.toString());
    }
}
