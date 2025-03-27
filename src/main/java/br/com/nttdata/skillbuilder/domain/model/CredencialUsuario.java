package br.com.nttdata.skillbuilder.domain.model;

import java.util.UUID;

public class CredencialUsuario {
    private final UUID usuarioId;
    private final String email;
    private final String hashSenha;
    private boolean ativo;

    public CredencialUsuario(UUID usuarioId, String email, String hashSenha, boolean ativo) {
        this.usuarioId = usuarioId;
        this.email = email;
        this.hashSenha = hashSenha;
        this.ativo = ativo;
    }

    public CredencialUsuario(UUID usuarioId, String email, String hashSenha) {
        this.usuarioId = usuarioId;
        this.email = email;
        this.hashSenha = hashSenha;
        this.ativo = true;
    }



    public void desativar() {
        this.ativo = false;
    }

    public void ativar() {
        this.ativo = true;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public String getEmail() {
        return email;
    }

    public String getHashSenha() {
        return hashSenha;
    }

    public boolean isAtivo() {
        return ativo;
    }
}