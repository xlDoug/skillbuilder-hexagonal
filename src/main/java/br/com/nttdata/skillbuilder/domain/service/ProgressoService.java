package br.com.nttdata.skillbuilder.domain.service;

import br.com.nttdata.skillbuilder.domain.model.Progresso;
import br.com.nttdata.skillbuilder.domain.model.StatusProgresso;
import br.com.nttdata.skillbuilder.domain.model.TipoAtividade;

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
}
