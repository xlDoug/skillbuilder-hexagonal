package br.com.nttdata.skillbuilder.domain.service;

import br.com.nttdata.skillbuilder.domain.model.TipoAtividade;

import java.util.List;

public class PontuacaoService {
    public int calcularPontuacaoPadrao(boolean destaque, int atividades, int quizzes) {
        int pontos = (atividades * 10) + (quizzes * 15);
        return destaque ? pontos + 50 : pontos;
    }

    public int pontuacaoPorTipoAtividade(TipoAtividade tipo) {
        return switch (tipo) {
            case VIDEO -> 10;
            case QUIZ -> 15;
            case TAREFA -> 20;
        };
    }

    public int pontuacaoTotal(List<TipoAtividade> atividadesConcluidas) {
        return atividadesConcluidas
                .stream()
                .mapToInt(this::pontuacaoPorTipoAtividade)
                .sum();
    }
}
