package br.com.nttdata.skillbuilder.domain.service;

import br.com.nttdata.skillbuilder.domain.model.Notificacao;
import br.com.nttdata.skillbuilder.domain.model.TipoNotificacao;

import java.util.UUID;

public class NotificacaoService {

    public Notificacao criarNotificacaoNovoCurso(UUID usuarioId, String nomeCurso) {
        String mensagem = "Um novo curso foi disponibilizado: " + nomeCurso;
        return new Notificacao(usuarioId, mensagem, TipoNotificacao.NOVO_CURSO);
    }

    public Notificacao criarNotificacaoProgressoAtualizado(UUID usuarioId, String nomeCurso, double percentualProgresso) {
        String mensagem = String.format("Seu progresso no curso %s foi atualizado para %.1f%%", nomeCurso, percentualProgresso);
        return new Notificacao(usuarioId, mensagem, TipoNotificacao.PROGRESSO_ATUALIZADO);
    }

    public Notificacao criarNotificacaoMetaProfessor(UUID professorId, String nomeCurso) {
        String mensagem = "Você atingiu a meta de alunos para o curso: " + nomeCurso;
        return new Notificacao(professorId, mensagem, TipoNotificacao.META_PROFESSOR);
    }

    public Notificacao criarNotificacaoRecompensaResgatada(UUID usuarioId, String nomeRecompensa) {
        String mensagem = "Você resgatou a recompensa: " + nomeRecompensa;
        return new Notificacao(usuarioId, mensagem, TipoNotificacao.RECOMPENSA_RESGATADA);
    }
}