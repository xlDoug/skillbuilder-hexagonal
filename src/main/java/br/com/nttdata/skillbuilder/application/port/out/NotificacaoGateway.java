package br.com.nttdata.skillbuilder.application.port.out;

import br.com.nttdata.skillbuilder.domain.model.Notificacao;

public interface NotificacaoGateway {
    void enviarNotificacao(Notificacao notificacao);
    void enviarNotificacaoEmail(String email, String assunto, String conteudo);
    void enviarNotificacaoPush(String deviceToken, String titulo, String mensagem);
}