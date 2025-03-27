package br.com.nttdata.skillbuilder.application.port.in;

import br.com.nttdata.skillbuilder.domain.model.Notificacao;
import br.com.nttdata.skillbuilder.domain.model.TipoNotificacao;

import java.util.List;
import java.util.UUID;

public interface EnviarNotificacaoUseCase {
    void enviarNotificacao(UUID usuarioId, String mensagem, TipoNotificacao tipo);
    List<Notificacao> listarNotificacoesUsuario(UUID usuarioId);
    void marcarNotificacaoComoLida(UUID notificacaoId);
}