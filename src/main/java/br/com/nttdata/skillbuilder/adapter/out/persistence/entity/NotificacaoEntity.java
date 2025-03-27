package br.com.nttdata.skillbuilder.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.nttdata.skillbuilder.domain.model.Notificacao;
import br.com.nttdata.skillbuilder.domain.model.StatusNotificacao;
import br.com.nttdata.skillbuilder.domain.model.TipoNotificacao;

@Entity
@Table(name = "notificacoes")
public class NotificacaoEntity {

    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    private String id;

    @Column(name = "usuario_id", nullable = false, columnDefinition = "VARCHAR(36)")
    private String usuarioId;

    @Column(name = "mensagem", nullable = false, length = 500)
    private String mensagem;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoNotificacao tipo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusNotificacao status;

    @Column(name = "data_envio", nullable = false)
    private LocalDateTime dataEnvio;

    public NotificacaoEntity() {
    }

    public NotificacaoEntity(Notificacao notificacao) {
        this.id = notificacao.getId().toString();
        this.usuarioId = notificacao.getUsuarioId().toString();
        this.mensagem = notificacao.getMensagem();
        this.tipo = notificacao.getTipo();
        this.status = notificacao.getStatus();
        this.dataEnvio = notificacao.getDataEnvio();
    }

    public Notificacao toDomainModel() {
        return new Notificacao(
                UUID.fromString(id),
                UUID.fromString(usuarioId),
                mensagem,
                tipo,
                status,
                dataEnvio
        );
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public TipoNotificacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoNotificacao tipo) {
        this.tipo = tipo;
    }

    public StatusNotificacao getStatus() {
        return status;
    }

    public void setStatus(StatusNotificacao status) {
        this.status = status;
    }

    public LocalDateTime getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(LocalDateTime dataEnvio) {
        this.dataEnvio = dataEnvio;
    }
}
