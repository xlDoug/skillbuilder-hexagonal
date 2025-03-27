package br.com.nttdata.skillbuilder.adapter.out.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.nttdata.skillbuilder.adapter.out.persistence.entity.ProgressoEntity;
import br.com.nttdata.skillbuilder.adapter.out.persistence.repository.ProgressoJpaRepository;
import br.com.nttdata.skillbuilder.application.port.out.ProgressoRepository;
import br.com.nttdata.skillbuilder.domain.model.Progresso;
import br.com.nttdata.skillbuilder.domain.model.StatusProgresso;

@Component
public class ProgressoRepositoryAdapter implements ProgressoRepository {

    private final ProgressoJpaRepository progressoJpaRepository;

    public ProgressoRepositoryAdapter(ProgressoJpaRepository progressoJpaRepository) {
        this.progressoJpaRepository = progressoJpaRepository;
    }

    @Override
    public Progresso salvar(Progresso progresso) {
        ProgressoEntity entity = new ProgressoEntity(progresso);
        ProgressoEntity savedEntity = progressoJpaRepository.save(entity);
        return savedEntity.toDomainModel();
    }

    @Override
    public Optional<Progresso> buscarPorId(UUID id) {
        return progressoJpaRepository.findById(id.toString())
                .map(ProgressoEntity::toDomainModel);
    }

    @Override
    public Optional<Progresso> buscarPorUsuarioECurso(UUID usuarioId, UUID cursoId) {
        return progressoJpaRepository.findByUsuarioIdAndCursoId(
                usuarioId.toString(), cursoId.toString())
                .map(ProgressoEntity::toDomainModel);
    }

    @Override
    public List<Progresso> buscarPorUsuarioId(UUID usuarioId) {
        return progressoJpaRepository.findByUsuarioId(usuarioId.toString()).stream()
                .map(ProgressoEntity::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Progresso> buscarPorCursoId(UUID cursoId) {
        return progressoJpaRepository.findByCursoId(cursoId.toString()).stream()
                .map(ProgressoEntity::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Progresso> buscarPorStatus(StatusProgresso status) {
        return progressoJpaRepository.findByStatus(status).stream()
                .map(ProgressoEntity::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public int contarConcluidosPorCurso(UUID cursoId) {
        return progressoJpaRepository.countConcluidosByCursoId(cursoId.toString());
    }
}
