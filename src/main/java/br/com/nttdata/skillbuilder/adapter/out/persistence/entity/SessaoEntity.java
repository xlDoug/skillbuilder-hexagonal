package br.com.nttdata.skillbuilder.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "sessoes")
public class SessaoEntity {

    @Id
    @Column(name = "token", columnDefinition = "VARCHAR(255)")
    private String token;

    @Column(name = "usuario_id", nullable = false, columnDefinition = "VARCHAR(36)")
    private String usuarioId;

    @Column(name = "data_expiracao", nullable = false)
    private LocalDateTime dataExpiracao;

    @Column(name = "ativa", nullable = false)
    private boolean ativa;

    public SessaoEntity() {
    }

    public SessaoEntity(String token, UUID usuarioId, LocalDateTime dataExpiracao) {
        this.token = token;
        this.usuarioId = usuarioId.toString();
        this.dataExpiracao = dataExpiracao;
        this.ativa = true;
    }

    public boolean isExpirada() {
        return LocalDateTime.now().isAfter(dataExpiracao);
    }

    public boolean isValida() {
        return ativa && !isExpirada();
    }

    // Getters e Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public LocalDateTime getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataExpiracao(LocalDateTime dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }
}
