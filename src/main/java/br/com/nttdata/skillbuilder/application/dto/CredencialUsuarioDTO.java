package br.com.nttdata.skillbuilder.application.dto;

import java.util.UUID;

public class CredencialUsuarioDTO {
    private UUID usuarioId;
    private String email;
    private String senha;
    private boolean ativo;

    public CredencialUsuarioDTO() {
    }

    public CredencialUsuarioDTO(UUID usuarioId, String email, String senha) {
        this.usuarioId = usuarioId;
        this.email = email;
        this.senha = senha;
        this.ativo = true;
    }

    // Getters e Setters
    public UUID getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UUID usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}