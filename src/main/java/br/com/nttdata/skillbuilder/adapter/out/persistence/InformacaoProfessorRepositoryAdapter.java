package br.com.nttdata.skillbuilder.adapter.out.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import br.com.nttdata.skillbuilder.adapter.out.persistence.entity.InformacaoProfessorEntity;
import br.com.nttdata.skillbuilder.adapter.out.persistence.repository.InformacaoProfessorJpaRepository;
import br.com.nttdata.skillbuilder.application.port.out.InformacaoProfessorRepository;
import br.com.nttdata.skillbuilder.domain.model.InformacaoProfessor;

@Component
public class InformacaoProfessorRepositoryAdapter implements InformacaoProfessorRepository {

    private final InformacaoProfessorJpaRepository informacaoProfessorJpaRepository;

    public InformacaoProfessorRepositoryAdapter(InformacaoProfessorJpaRepository informacaoProfessorJpaRepository) {
        this.informacaoProfessorJpaRepository = informacaoProfessorJpaRepository;
    }

    @Override
    public InformacaoProfessor salvar(InformacaoProfessor informacaoProfessor) {
        InformacaoProfessorEntity entity = new InformacaoProfessorEntity(informacaoProfessor);
        InformacaoProfessorEntity savedEntity = informacaoProfessorJpaRepository.save(entity);
        return savedEntity.toDomainModel();
    }

    @Override
    public Optional<InformacaoProfessor> buscarPorUsuarioId(UUID usuarioId) {
        return informacaoProfessorJpaRepository.findById(usuarioId.toString())
                .map(InformacaoProfessorEntity::toDomainModel);
    }

    @Override
    public void atualizar(InformacaoProfessor informacaoProfessor) {
        // Como estamos usando findById e save, a implementação é igual ao salvar
        salvar(informacaoProfessor);
    }
}
