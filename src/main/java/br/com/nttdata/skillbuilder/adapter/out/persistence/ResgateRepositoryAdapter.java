package br.com.nttdata.skillbuilder.adapter.out.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.nttdata.skillbuilder.adapter.out.persistence.entity.ResgateEntity;
import br.com.nttdata.skillbuilder.adapter.out.persistence.repository.ResgateJpaRepository;
import br.com.nttdata.skillbuilder.application.port.out.ResgateRepository;
import br.com.nttdata.skillbuilder.domain.model.Resgate;

@Component
public class ResgateRepositoryAdapter implements ResgateRepository {

    private final ResgateJpaRepository resgateJpaRepository;

    public ResgateRepositoryAdapter(ResgateJpaRepository resgateJpaRepository) {
        this.resgateJpaRepository = resgateJpaRepository;
    }

    @Override
    public Resgate salvar(Resgate resgate) {
        ResgateEntity entity = new ResgateEntity(resgate);
        ResgateEntity savedEntity = resgateJpaRepository.save(entity);
        return savedEntity.toDomainModel();
    }

    @Override
    public Optional<Resgate> buscarPorId(UUID id) {
        return resgateJpaRepository.findById(id.toString())
                .map(ResgateEntity::toDomainModel);
    }

    @Override
    public List<Resgate> buscarPorUsuarioId(UUID usuarioId) {
        return resgateJpaRepository.findByUsuarioId(usuarioId.toString()).stream()
                .map(ResgateEntity::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Resgate> buscarPorRecompensaId(UUID recompensaId) {
        return resgateJpaRepository.findByRecompensaId(recompensaId.toString()).stream()
                .map(ResgateEntity::toDomainModel)
                .collect(Collectors.toList());
    }
}
