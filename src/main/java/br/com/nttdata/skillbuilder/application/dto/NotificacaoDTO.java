package br.com.nttdata.skillbuilder.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class NotificacaoDTO {
    private UUID id;
    private UUID usuarioId;
    private String usuarioNome;
    private String mensagem;
    private String tipo;
    private String status;
    private LocalDateTime dataEnvio;

    public NotificacaoDTO() {
    }

    public NotificacaoDTO(UUID id, UUID usuarioId, String usuarioNome, String mensagem, String tipo,
                          String status, LocalDateTime dataEnvio) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.usuarioNome = usuarioNome;
        this.mensagem = mensagem;
        this.tipo = tipo;
        this.status = status;
        this.dataEnvio = dataEnvio;
    }

    // Getters e Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UUID usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioNome() {
        return usuarioNome;
    }

    public void setUsuarioNome(String usuarioNome) {
        this.usuarioNome = usuarioNome;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(LocalDateTime dataEnvio) {
        this.dataEnvio = dataEnvio;
    }
}