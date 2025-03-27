package br.com.nttdata.skillbuilder.application.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class ProgressoDTO {
    private UUID id;
    private UUID usuarioId;
    private String usuarioNome;
    private UUID cursoId;
    private String cursoNome;
    private String status;
    private LocalDate dataInicio;
    private LocalDate dataConclusao;
    private int pontosAcumulados;
    private double percentualConclusao;
    private List<AtividadeDTO> atividadesConcluidas;

    public ProgressoDTO() {
    }

    public ProgressoDTO(UUID id, UUID usuarioId, String usuarioNome, UUID cursoId, String cursoNome, String status,
                        LocalDate dataInicio, LocalDate dataConclusao, int pontosAcumulados, double percentualConclusao) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.usuarioNome = usuarioNome;
        this.cursoId = cursoId;
        this.cursoNome = cursoNome;
        this.status = status;
        this.dataInicio = dataInicio;
        this.dataConclusao = dataConclusao;
        this.pontosAcumulados = pontosAcumulados;
        this.percentualConclusao = percentualConclusao;
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

    public UUID getCursoId() {
        return cursoId;
    }

    public void setCursoId(UUID cursoId) {
        this.cursoId = cursoId;
    }

    public String getCursoNome() {
        return cursoNome;
    }

    public void setCursoNome(String cursoNome) {
        this.cursoNome = cursoNome;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public double getPercentualConclusao() {
        return percentualConclusao;
    }

    public void setPercentualConclusao(double percentualConclusao) {
        this.percentualConclusao = percentualConclusao;
    }

    public List<AtividadeDTO> getAtividadesConcluidas() {
        return atividadesConcluidas;
    }

    public void setAtividadesConcluidas(List<AtividadeDTO> atividadesConcluidas) {
        this.atividadesConcluidas = atividadesConcluidas;
    }
}