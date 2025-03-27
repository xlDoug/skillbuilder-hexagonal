package br.com.nttdata.skillbuilder.application.dto;

import java.time.LocalDate;
import java.util.UUID;

public class ResgateDTO {
    private UUID id;
    private UUID usuarioId;
    private String usuarioNome;
    private UUID recompensaId;
    private String recompensaNome;
    private LocalDate dataResgate;

    public ResgateDTO() {
    }

    public ResgateDTO(UUID id, UUID usuarioId, String usuarioNome, UUID recompensaId, String recompensaNome, LocalDate dataResgate) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.usuarioNome = usuarioNome;
        this.recompensaId = recompensaId;
        this.recompensaNome = recompensaNome;
        this.dataResgate = dataResgate;
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

    public UUID getRecompensaId() {
        return recompensaId;
    }

    public void setRecompensaId(UUID recompensaId) {
        this.recompensaId = recompensaId;
    }

    public String getRecompensaNome() {
        return recompensaNome;
    }

    public void setRecompensaNome(String recompensaNome) {
        this.recompensaNome = recompensaNome;
    }

    public LocalDate getDataResgate() {
        return dataResgate;
    }

    public void setDataResgate(LocalDate dataResgate) {
        this.dataResgate = dataResgate;
    }
}