package br.com.nttdata.skillbuilder.adapter.out.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.nttdata.skillbuilder.adapter.out.persistence.entity.PontuacaoEntity;
import br.com.nttdata.skillbuilder.adapter.out.persistence.repository.PontuacaoJpaRepository;
import br.com.nttdata.skillbuilder.application.port.out.PontuacaoRepository;
import br.com.nttdata.skillbuilder.domain.model.OrigemPontuacao;
import br.com.nttdata.skillbuilder.domain.model.Pontuacao;

@Component
public class PontuacaoRepositoryAdapter implements PontuacaoRepository {

    private final PontuacaoJpaRepository pontuacaoJpaRepository;

    public PontuacaoRepositoryAdapter(PontuacaoJpaRepository pontuacaoJpaRepository) {
        this.pontuacaoJpaRepository = pontuacaoJpaRepository;
    }

    @Override
    public Pontuacao salvar(Pontuacao pontuacao) {
        PontuacaoEntity entity = new PontuacaoEntity(pontuacao);
        PontuacaoEntity savedEntity = pontuacaoJpaRepository.save(entity);
        return savedEntity.toDomainModel();
    }

    @Override
    public Optional<Pontuacao> buscarPorId(UUID id) {
        return pontuacaoJpaRepository.findById(id.toString())
                .map(PontuacaoEntity::toDomainModel);
    }

    @Override
    public List<Pontuacao> buscarPorUsuarioId(UUID usuarioId) {
        return pontuacaoJpaRepository.findByUsuarioId(usuarioId.toString()).stream()
                .map(PontuacaoEntity::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Pontuacao> buscarPorOrigem(OrigemPontuacao origem) {
        return pontuacaoJpaRepository.findByOrigemPontuacao(origem).stream()
                .map(PontuacaoEntity::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public int calcularTotalPontosPorUsuario(UUID usuarioId) {
        Integer totalPontos = pontuacaoJpaRepository.sumPontosByUsuarioId(usuarioId.toString());
        return totalPontos != null ? totalPontos : 0;
    }
}
