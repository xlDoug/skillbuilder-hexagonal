package br.com.nttdata.skillbuilder.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.LocalDate;
import java.util.UUID;

import br.com.nttdata.skillbuilder.domain.model.Progresso;
import br.com.nttdata.skillbuilder.domain.model.StatusProgresso;

@Entity
@Table(name = "progressos", 
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_usuario_curso", columnNames = {"usuario_id", "curso_id"})
       })
public class ProgressoEntity {

    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    private String id;

    @Column(name = "usuario_id", nullable = false, columnDefinition = "VARCHAR(36)")
    private String usuarioId;

    @Column(name = "curso_id", nullable = false, columnDefinition = "VARCHAR(36)")
    private String cursoId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusProgresso status;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_conclusao")
    private LocalDate dataConclusao;

    @Column(name = "pontos_acumulados", nullable = false)
    private int pontosAcumulados;

    public ProgressoEntity() {
    }

    public ProgressoEntity(Progresso progresso) {
        this.id = progresso.getId().toString();
        this.usuarioId = progresso.getUsuarioId().toString();
        this.cursoId = progresso.getCursoId().toString();
        this.status = progresso.getStatus();
        this.dataInicio = progresso.getDataInicio();
        this.dataConclusao = progresso.getDataConclusao();
        this.pontosAcumulados = progresso.getPontosAcumulados();
    }

    public Progresso toDomainModel() {
        return new Progresso(
                UUID.fromString(id),
                UUID.fromString(usuarioId),
                UUID.fromString(cursoId),
                status,
                dataInicio,
                dataConclusao,
                pontosAcumulados
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

    public StatusProgresso getStatus() {
        return status;
    }

    public void setStatus(StatusProgresso status) {
        this.status = status;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public int getPontosAcumulados() {
        return pontosAcumulados;
    }

    public void setPontosAcumulados(int pontosAcumulados) {
        this.pontosAcumulados = pontosAcumulados;
    }
}
