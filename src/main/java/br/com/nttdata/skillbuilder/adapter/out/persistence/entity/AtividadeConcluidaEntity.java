package br.com.nttdata.skillbuilder.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.LocalDate;
import java.util.UUID;

import br.com.nttdata.skillbuilder.domain.model.AtividadeConcluida;

@Entity
@Table(name = "atividades_concluidas",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_usuario_atividade", columnNames = {"usuario_id", "atividade_id"})
        })
public class AtividadeConcluidaEntity {

    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    private String id;

    @Column(name = "usuario_id", nullable = false, columnDefinition = "VARCHAR(36)")
    private String usuarioId;

    @Column(name = "curso_id", nullable = false, columnDefinition = "VARCHAR(36)")
    private String cursoId;

    @Column(name = "atividade_id", nullable = false, columnDefinition = "VARCHAR(36)")
    private String atividadeId;

    @Column(name = "data_conclusao", nullable = false)
    private LocalDate dataConclusao;

    public AtividadeConcluidaEntity() {
    }

    public AtividadeConcluidaEntity(AtividadeConcluida atividadeConcluida) {
        this.id = atividadeConcluida.getId().toString();
        this.usuarioId = atividadeConcluida.getUsuarioId().toString();
        this.cursoId = atividadeConcluida.getCursoId().toString();
        this.atividadeId = atividadeConcluida.getAtividadeId().toString();
        this.dataConclusao = atividadeConcluida.getDataConclusao();
    }

    public AtividadeConcluidaEntity(UUID usuarioId, UUID cursoId, UUID atividadeId) {
        this.id = UUID.randomUUID().toString();
        this.usuarioId = usuarioId.toString();
        this.cursoId = cursoId.toString();
        this.atividadeId = atividadeId.toString();
        this.dataConclusao = LocalDate.now();
    }

    public AtividadeConcluida toDomainModel() {
        return new AtividadeConcluida(
                UUID.fromString(usuarioId),
                UUID.fromString(cursoId),
                UUID.fromString(atividadeId)
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

    public String getCursoId() {
        return cursoId;
    }

    public void setCursoId(String cursoId) {
        this.cursoId = cursoId;
    }

    public String getAtividadeId() {
        return atividadeId;
    }

    public void setAtividadeId(String atividadeId) {
        this.atividadeId = atividadeId;
    }

    public LocalDate getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
    }
}