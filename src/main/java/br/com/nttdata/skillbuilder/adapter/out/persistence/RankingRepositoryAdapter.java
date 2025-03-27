package br.com.nttdata.skillbuilder.adapter.out.persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.nttdata.skillbuilder.adapter.out.persistence.entity.RankingEntity;
import br.com.nttdata.skillbuilder.adapter.out.persistence.repository.PontuacaoJpaRepository;
import br.com.nttdata.skillbuilder.adapter.out.persistence.repository.RankingJpaRepository;
import br.com.nttdata.skillbuilder.application.port.out.RankingRepository;
import br.com.nttdata.skillbuilder.domain.model.Ranking;

@Component
public class RankingRepositoryAdapter implements RankingRepository {

    private final RankingJpaRepository rankingJpaRepository;
    private final PontuacaoJpaRepository pontuacaoJpaRepository;

    public RankingRepositoryAdapter(RankingJpaRepository rankingJpaRepository, 
                              PontuacaoJpaRepository pontuacaoJpaRepository) {
        this.rankingJpaRepository = rankingJpaRepository;
        this.pontuacaoJpaRepository = pontuacaoJpaRepository;
    }

    @Override
    @Transactional
    public void recalcularRanking() {
        // Esta implementação é simplificada. Em um sistema real,
        // você usaria algo como Batch Processing ou ajustes no banco de dados.
        List<RankingEntity> allRankings = rankingJpaRepository.findAll();

        // Ordena pelo total de pontos (descendente)
        allRankings.sort((a, b) -> Integer.compare(b.getTotalPontos(), a.getTotalPontos()));

        // Atualiza as posições
        for (int i = 0; i < allRankings.size(); i++) {
            RankingEntity ranking = allRankings.get(i);
            ranking.setPosicaoGlobal(i + 1);
            rankingJpaRepository.save(ranking);
        }
    }

    @Override
    public Ranking salvar(Ranking ranking) {
        RankingEntity entity = new RankingEntity(ranking);
        RankingEntity savedEntity = rankingJpaRepository.save(entity);
        return savedEntity.toDomainModel();
    }

    @Override
    public Optional<Ranking> buscarPorUsuarioId(UUID usuarioId) {
        return rankingJpaRepository.findById(usuarioId.toString())
                .map(RankingEntity::toDomainModel);
    }

    @Override
    public List<Ranking> buscarTopRanking(int limite) {
        return rankingJpaRepository.findTopRanking(PageRequest.of(0, limite)).stream()
                .map(RankingEntity::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public int buscarPosicaoUsuario(UUID usuarioId) {
        return rankingJpaRepository.findPosicaoByUsuarioId(usuarioId.toString());
    }

    @Override
    @Transactional
    public void atualizarPontuacao(UUID usuarioId, int novosPontos) {
        String usuarioIdStr = usuarioId.toString();
        Optional<RankingEntity> rankingOpt = rankingJpaRepository.findById(usuarioIdStr);
        
        if (rankingOpt.isPresent()) {
            RankingEntity entity = rankingOpt.get();
            entity.setTotalPontos(entity.getTotalPontos() + novosPontos);
            entity.setDataAtualizacao(LocalDate.now());
            rankingJpaRepository.save(entity);
        } else {
            Integer totalPontos = pontuacaoJpaRepository.sumPontosByUsuarioId(usuarioIdStr);
            
            if (totalPontos == null) {
                totalPontos = novosPontos;
            }
            
            // Última posição por padrão, será recalculada depois
            int posicao = (int) rankingJpaRepository.count() + 1;
            
            RankingEntity novoRanking = new RankingEntity();
            novoRanking.setUsuarioId(usuarioIdStr);
            novoRanking.setPosicaoGlobal(posicao);
            novoRanking.setTotalPontos(totalPontos);
            novoRanking.setDataAtualizacao(LocalDate.now());
            
            rankingJpaRepository.save(novoRanking);
        }
    }

}
