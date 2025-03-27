package br.com.nttdata.skillbuilder.adapter.out.persistence;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.nttdata.skillbuilder.adapter.out.persistence.entity.AtividadeConcluidaEntity;
import br.com.nttdata.skillbuilder.adapter.out.persistence.repository.AtividadeConcluidaJpaRepository;
import br.com.nttdata.skillbuilder.application.port.out.AtividadeConcluidaRepository;

@Component
public class AtividadeConcluidaRepositoryAdapter implements AtividadeConcluidaRepository {

    private final AtividadeConcluidaJpaRepository atividadeConcluidaJpaRepository;

    public AtividadeConcluidaRepositoryAdapter(AtividadeConcluidaJpaRepository atividadeConcluidaJpaRepository) {
        this.atividadeConcluidaJpaRepository = atividadeConcluidaJpaRepository;
    }

    @Override
    @Transactional
    public void registrarConclusao(UUID usuarioId, UUID cursoId, UUID atividadeId) {
        AtividadeConcluidaEntity entity = new AtividadeConcluidaEntity(usuarioId, cursoId, atividadeId);
        atividadeConcluidaJpaRepository.save(entity);
    }

    @Override
    public List<UUID> buscarAtividadesConcluidasPorUsuarioECurso(UUID usuarioId, UUID cursoId) {
        List<String> atividadesIds = atividadeConcluidaJpaRepository.findAtividadesIdsByUsuarioIdAndCursoId(
                usuarioId.toString(), cursoId.toString());
        return atividadesIds.stream()
                .map(UUID::fromString)
                .collect(Collectors.toList());
    }

    @Override
    public boolean verificarConclusao(UUID usuarioId, UUID atividadeId) {
        return atividadeConcluidaJpaRepository.findByUsuarioIdAndAtividadeId(
                usuarioId.toString(), atividadeId.toString()).isPresent();
    }

    @Override
    public int contarConcluidasPorUsuarioECurso(UUID usuarioId, UUID cursoId) {
        return atividadeConcluidaJpaRepository.countByUsuarioIdAndCursoId(
                usuarioId.toString(), cursoId.toString());
    }
}
