package br.com.nttdata.skillbuilder.adapter.out.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import br.com.nttdata.skillbuilder.application.port.out.NotificacaoGateway;
import br.com.nttdata.skillbuilder.domain.model.Notificacao;

/**
 * Implementação simulada do gateway de notificações.
 * Em um ambiente real, isso se conectaria a serviços de e-mail, push notifications, etc.
 */
@Component
public class NotificacaoGatewayAdapter implements NotificacaoGateway {

    private static final Logger logger = LoggerFactory.getLogger(NotificacaoGatewayAdapter.class);

    @Override
    public void enviarNotificacao(Notificacao notificacao) {
        // Em um ambiente real, enviar por vários canais
        // Aqui, apenas logamos para demonstração
        logger.info("Enviando notificação: [{}] para usuário: {} - Mensagem: {}",
                notificacao.getTipo(), notificacao.getUsuarioId(), notificacao.getMensagem());

        // Simulação de envio baseado no tipo
        switch (notificacao.getTipo()) {
            case NOVO_CURSO:
                logger.info("Notificação de novo curso enviada ao usuário: {}", notificacao.getUsuarioId());
                break;
            case PROGRESSO_ATUALIZADO:
                logger.info("Notificação de progresso enviada ao usuário: {}", notificacao.getUsuarioId());
                break;
            case META_PROFESSOR:
                logger.info("Notificação de meta atingida enviada ao professor: {}", notificacao.getUsuarioId());
                break;
            case RECOMPENSA_RESGATADA:
                logger.info("Notificação de recompensa resgatada enviada ao usuário: {}", notificacao.getUsuarioId());
                break;
            default:
                logger.info("Notificação de tipo desconhecido enviada");
        }
    }

    @Override
    public void enviarNotificacaoEmail(String email, String assunto, String conteudo) {
        // Simulação de envio de e-mail
        logger.info("Enviando e-mail para: {} - Assunto: {}", email, assunto);
        logger.info("Conteúdo: {}", conteudo);
    }

    @Override
    public void enviarNotificacaoPush(String deviceToken, String titulo, String mensagem) {
        // Simulação de envio de notificação push
        logger.info("Enviando push para device: {} - Título: {}", deviceToken, titulo);
        logger.info("Mensagem: {}", mensagem);
    }
}
