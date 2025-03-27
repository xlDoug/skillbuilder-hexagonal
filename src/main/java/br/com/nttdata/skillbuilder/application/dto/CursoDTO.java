package br.com.nttdata.skillbuilder.application.dto;

import java.time.LocalDate;
import java.util.UUID;

public class CursoDTO {
    private UUID id;
    private String nome;
    private String descricao;
    private UUID professorId;
    private String professorNome;
    private int metaPontos;
    private LocalDate dataCriacao;
    private int totalAtividades;
    private int totalAlunos;
    private double percentualConclusaoMedia;

    public CursoDTO() {
    }

    public CursoDTO(String nome, String descricao, UUID professorId, int metaPontos) {
        this.nome = nome;
        this.descricao = descricao;
        this.professorId = professorId;
        this.metaPontos = metaPontos;
    }

    public CursoDTO(UUID id, String nome, String descricao, UUID professorId, String professorNome, int metaPontos,
                    LocalDate dataCriacao, int totalAtividades, int totalAlunos, double percentualConclusaoMedia) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.professorId = professorId;
        this.professorNome = professorNome;
        this.metaPontos = metaPontos;
        this.dataCriacao = dataCriacao;
        this.totalAtividades = totalAtividades;
        this.totalAlunos = totalAlunos;
        this.percentualConclusaoMedia = percentualConclusaoMedia;
    }

    // Getters e Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public UUID getProfessorId() {
        return professorId;
    }

    public void setProfessorId(UUID professorId) {
        this.professorId = professorId;
    }

    public String getProfessorNome() {
        return professorNome;
    }

    public void setProfessorNome(String professorNome) {
        this.professorNome = professorNome;
    }

    public int getMetaPontos() {
        return metaPontos;
    }

    public void setMetaPontos(int metaPontos) {
        this.metaPontos = metaPontos;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public int getTotalAtividades() {
        return totalAtividades;
    }

    public void setTotalAtividades(int totalAtividades) {
        this.totalAtividades = totalAtividades;
    }

    public int getTotalAlunos() {
        return totalAlunos;
    }

    public void setTotalAlunos(int totalAlunos) {
        this.totalAlunos = totalAlunos;
    }

    public double getPercentualConclusaoMedia() {
        return percentualConclusaoMedia;
    }

    public void setPercentualConclusaoMedia(double percentualConclusaoMedia) {
        this.percentualConclusaoMedia = percentualConclusaoMedia;
    }
}