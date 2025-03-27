package br.com.nttdata.skillbuilder.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

import br.com.nttdata.skillbuilder.domain.model.CredencialUsuario;

@Entity
@Table(name = "credenciais_usuarios")
public class CredencialUsuarioEntity {

    @Id
    @Column(name = "usuario_id", columnDefinition = "VARCHAR(36)")
    private String usuarioId;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "hash_senha", nullable = false)
    private String hashSenha;

    @Column(name = "ativo", nullable = false)
    private boolean ativo;

    public CredencialUsuarioEntity() {
    }

    public CredencialUsuarioEntity(CredencialUsuario credencial) {
        this.usuarioId = credencial.getUsuarioId().toString();
        this.email = credencial.getEmail();
        this.hashSenha = credencial.getHashSenha();
        this.ativo = credencial.isAtivo();
    }

    public CredencialUsuario toDomainModel() {
        return new CredencialUsuario(
                UUID.fromString(usuarioId),
                email,
                hashSenha,
                ativo
        );
    }

    // Getters e Setters
    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashSenha() {
        return hashSenha;
    }

    public void setHashSenha(String hashSenha) {
        this.hashSenha = hashSenha;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
