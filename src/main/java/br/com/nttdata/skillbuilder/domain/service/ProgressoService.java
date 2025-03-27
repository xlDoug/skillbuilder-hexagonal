package br.com.nttdata.skillbuilder.domain.service;

import br.com.nttdata.skillbuilder.domain.exception.RegraDeNegocioException;
import br.com.nttdata.skillbuilder.domain.model.Atividade;
import br.com.nttdata.skillbuilder.domain.model.Progresso;
import br.com.nttdata.skillbuilder.domain.model.StatusProgresso;
import br.com.nttdata.skillbuilder.domain.model.TipoAtividade;

import java.util.List;

public class ProgressoService {

    public void registrarProgresso(Progresso progresso, TipoAtividade atividadeConcluida) {
        int pontos = switch (atividadeConcluida) {
            case VIDEO -> 10;
            case QUIZ -> 15;
            case TAREFA -> 20;
        };
        progresso.acumularPontos(pontos);
    }

    public void finalizarCurso(Progresso progresso) {
        if (progresso.getStatus() == StatusProgresso.CONCLUIDO) {
            throw new IllegalStateException("Curso já foi concluído");
        }
        progresso.concluir();
    }

    /**
     * Registra a conclusão de uma atividade e atualiza o progresso
     */
    public void registrarAtividadeConcluida(Progresso progresso, Atividade atividade) {
        if (progresso.estaConcluido()) {
            throw new RegraDeNegocioException("Não é possível registrar atividades em um curso já concluído");
        }

        if (!atividade.estaDisponivel()) {
            throw new RegraDeNegocioException("Esta atividade ainda não está disponível");
        }

        progresso.acumularPontos(atividade.getPontuacao());
    }

    /**
     * Verifica se todas as atividades foram concluídas e marca o curso como concluído
     */
    public boolean verificarConclusaoCurso(Progresso progresso, List<Atividade> todasAtividades, List<Atividade> atividadesConcluidas) {
        // Se já está concluído, retorna verdadeiro
        if (progresso.estaConcluido()) {
            return true;
        }

        // Se todas as atividades foram concluídas, marca como concluído
        if (todasAtividades.size() == atividadesConcluidas.size()) {
            progresso.concluir();
            return true;
        }

        return false;
    }

    /**
     * Calcula o percentual de progresso no curso
     */
    public double calcularPercentualProgresso(int atividadesConcluidas, int totalAtividades) {
        if (totalAtividades == 0) return 0;
        return (double) atividadesConcluidas / totalAtividades * 100;
    }
}