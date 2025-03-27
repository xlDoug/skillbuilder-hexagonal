package br.com.nttdata.skillbuilder.application.port.out;

import br.com.nttdata.skillbuilder.domain.model.Notificacao;
import br.com.nttdata.skillbuilder.domain.model.StatusNotificacao;
import br.com.nttdata.skillbuilder.domain.model.TipoNotificacao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificacaoRepository {
    Notificacao salvar(Notificacao notificacao);
    Optional<Notificacao> buscarPorId(UUID id);
    List<Notificacao> buscarPorUsuarioId(UUID usuarioId);
    List<Notificacao> buscarPorUsuarioEStatus(UUID usuarioId, StatusNotificacao status);
    List<Notificacao> buscarPorTipo(TipoNotificacao tipo);
    void marcarComoLida(UUID id);
    void deletar(UUID id);
}