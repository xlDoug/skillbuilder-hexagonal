package br.com.nttdata.skillbuilder.application.port.in;

import br.com.nttdata.skillbuilder.application.dto.RankingDTO;

import java.util.List;
import java.util.UUID;

public interface GerenciarRankingUseCase {
    void atualizarRanking(UUID usuarioId, int novosPontos);
    List<RankingDTO> listarRankingGlobal(int limite);
    List<RankingDTO> listarRankingPorCurso(UUID cursoId, int limite);
    int obterPosicaoUsuario(UUID usuarioId);
}