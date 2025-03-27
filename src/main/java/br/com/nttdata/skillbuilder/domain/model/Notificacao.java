package br.com.nttdata.skillbuilder.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Notificacao {

    private final UUID id;
    private final UUID usuarioId;
    private final String mensagem;
    private final TipoNotificacao tipo;
    private StatusNotificacao status;
    private final LocalDateTime dataEnvio;

    public Notificacao(UUID usuarioId, String mensagem, TipoNotificacao tipo) {
        this.id = UUID.randomUUID();
        this.usuarioId = usuarioId;
        this.mensagem = mensagem;
        this.tipo = tipo;
        this.status = StatusNotificacao.NAO_LIDA;
        this.dataEnvio = LocalDateTime.now();
    }

    public Notificacao(UUID id, UUID usuarioId, String mensagem, TipoNotificacao tipo, StatusNotificacao status, LocalDateTime dataEnvio) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.mensagem = mensagem;
        this.tipo = tipo;
        this.status = status;
        this.dataEnvio = dataEnvio;
    }

    public void marcarComoLida() {
        this.status = StatusNotificacao.LIDA;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public String getMensagem() {
        return mensagem;
    }

    public TipoNotificacao getTipo() {
        return tipo;
    }

    public StatusNotificacao getStatus() {
        return status;
    }

    public LocalDateTime getDataEnvio() {
        return dataEnvio;
    }
}
