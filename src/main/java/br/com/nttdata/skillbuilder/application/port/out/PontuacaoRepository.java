package br.com.nttdata.skillbuilder.application.port.out;

import br.com.nttdata.skillbuilder.domain.model.OrigemPontuacao;
import br.com.nttdata.skillbuilder.domain.model.Pontuacao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PontuacaoRepository {
    Pontuacao salvar(Pontuacao pontuacao);
    Optional<Pontuacao> buscarPorId(UUID id);
    List<Pontuacao> buscarPorUsuarioId(UUID usuarioId);
    List<Pontuacao> buscarPorOrigem(OrigemPontuacao origem);
    int calcularTotalPontosPorUsuario(UUID usuarioId);
}