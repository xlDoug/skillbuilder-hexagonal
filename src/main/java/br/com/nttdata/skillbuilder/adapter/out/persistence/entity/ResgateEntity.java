package br.com.nttdata.skillbuilder.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.UUID;

import br.com.nttdata.skillbuilder.domain.model.Resgate;

@Entity
@Table(name = "resgates")
public class ResgateEntity {

    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    private String id;

    @Column(name = "usuario_id", nullable = false, columnDefinition = "VARCHAR(36)")
    private String usuarioId;

    @Column(name = "recompensa_id", nullable = false, columnDefinition = "VARCHAR(36)")
    private String recompensaId;

    @Column(name = "data_resgate", nullable = false)
    private LocalDate dataResgate;

    public ResgateEntity() {
    }

    public ResgateEntity(Resgate resgate) {
        this.id = resgate.getId().toString();
        this.usuarioId = resgate.getUsuarioId().toString();
        this.recompensaId = resgate.getRecompensaId().toString();
        this.dataResgate = resgate.getDataResgate();
    }

    public Resgate toDomainModel() {
        return new Resgate(
                UUID.fromString(id),
                UUID.fromString(usuarioId),
                UUID.fromString(recompensaId),
                dataResgate
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

    public String getRecompensaId() {
        return recompensaId;
    }

    public void setRecompensaId(String recompensaId) {
        this.recompensaId = recompensaId;
    }

    public LocalDate getDataResgate() {
        return dataResgate;
    }

    public void setDataResgate(LocalDate dataResgate) {
        this.dataResgate = dataResgate;
    }
}
