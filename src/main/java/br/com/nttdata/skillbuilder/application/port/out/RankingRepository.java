package br.com.nttdata.skillbuilder.application.port.out;

import br.com.nttdata.skillbuilder.domain.model.Ranking;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RankingRepository {
    Ranking salvar(Ranking ranking);
    Optional<Ranking> buscarPorUsuarioId(UUID usuarioId);
    List<Ranking> buscarTopRanking(int limite);
    int buscarPosicaoUsuario(UUID usuarioId);
    void atualizarPontuacao(UUID usuarioId, int novosPontos);
    void recalcularRanking();
}