package br.com.nttdata.skillbuilder.adapter.out.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.nttdata.skillbuilder.adapter.out.persistence.entity.RecompensaEntity;
import br.com.nttdata.skillbuilder.adapter.out.persistence.repository.RecompensaJpaRepository;
import br.com.nttdata.skillbuilder.application.port.out.RecompensaRepository;
import br.com.nttdata.skillbuilder.domain.model.Recompensa;

@Component
public class RecompensaRepositoryAdapter implements RecompensaRepository {

    private final RecompensaJpaRepository recompensaJpaRepository;

    public RecompensaRepositoryAdapter(RecompensaJpaRepository recompensaJpaRepository) {
        this.recompensaJpaRepository = recompensaJpaRepository;
    }

    @Override
    public Recompensa salvar(Recompensa recompensa) {
        RecompensaEntity entity = new RecompensaEntity(recompensa);
        RecompensaEntity savedEntity = recompensaJpaRepository.save(entity);
        return savedEntity.toDomainModel();
    }

    @Override
    public Optional<Recompensa> buscarPorId(UUID id) {
        return recompensaJpaRepository.findById(id.toString())
                .map(RecompensaEntity::toDomainModel);
    }

    @Override
    public List<Recompensa> buscarTodas() {
        return recompensaJpaRepository.findAll().stream()
                .map(RecompensaEntity::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Recompensa> buscarDisponiveis() {
        return recompensaJpaRepository.findDisponiveis().stream()
                .map(RecompensaEntity::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void atualizarQuantidadeDisponivel(UUID id, int novaQuantidade) {
        recompensaJpaRepository.findById(id.toString())
                .ifPresent(entity -> {
                    entity.setQuantidadeDisponivel(novaQuantidade);
                    recompensaJpaRepository.save(entity);
                });
    }

    @Override
    public void deletar(UUID id) {
        recompensaJpaRepository.deleteById(id.toString());
    }
}
