package br.com.nttdata.skillbuilder.domain.service;

import br.com.nttdata.skillbuilder.domain.model.Atividade;
import br.com.nttdata.skillbuilder.domain.model.OrigemPontuacao;
import br.com.nttdata.skillbuilder.domain.model.Pontuacao;
import br.com.nttdata.skillbuilder.domain.model.TipoAtividade;

import java.util.List;
import java.util.UUID;

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

    /**
     * Calcula pontos para uma atividade concluída por um aluno
     */
    public Pontuacao registrarPontuacaoAtividade(UUID usuarioId, Atividade atividade) {
        return new Pontuacao(usuarioId, atividade.getPontuacao(), OrigemPontuacao.CONCLUSAO_ATIVIDADE);
    }

    /**
     * Calcula pontos de bônus por concluir um curso completo
     */
    public Pontuacao registrarPontuacaoCurso(UUID usuarioId, int pontuacaoBase) {
        // Bônus de 50 pontos por conclusão de curso
        int bonusConclusao = 50;
        return new Pontuacao(usuarioId, pontuacaoBase + bonusConclusao, OrigemPontuacao.CONCLUSAO_CURSO);
    }

    /**
     * Registra pontuação para professor que atingiu metas
     */
    public Pontuacao registrarPontuacaoProfessor(UUID professorId, int alunosConcluintes) {
        // 10 pontos por aluno que concluiu o curso
        int pontos = alunosConcluintes * 10;
        return new Pontuacao(professorId, pontos, OrigemPontuacao.META_ATINGIDA_PROFESSOR);
    }

    /**
     * Registra pontuação por destaque (aluno com melhor desempenho)
     */
    public Pontuacao registrarPontuacaoDestaque(UUID usuarioId) {
        return new Pontuacao(usuarioId, 100, OrigemPontuacao.DESTAQUE_DESEMPENHO);
    }
}