package br.com.nttdata.skillbuilder.application.port.in;

import br.com.nttdata.skillbuilder.domain.model.Pontuacao;
import br.com.nttdata.skillbuilder.domain.model.OrigemPontuacao;

import java.util.UUID;

public interface RegistrarPontuacaoUseCase {
    Pontuacao registrarPontos(UUID usuarioId, int pontos, OrigemPontuacao origem);
    Pontuacao registrarPontosAtividade(UUID usuarioId, UUID atividadeId);
    Pontuacao registrarPontosCurso(UUID usuarioId, UUID cursoId);
    int consultarPontosTotais(UUID usuarioId);
}