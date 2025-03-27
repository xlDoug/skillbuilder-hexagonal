package br.com.nttdata.skillbuilder.application.dto;

import java.time.LocalDate;
import java.util.UUID;

public class InformacaoProfessorDTO {
    private UUID usuarioId;
    private String nome;
    private String email;
    private String descricao;
    private int metaPontos;
    private LocalDate dataCriacao;
    private int totalCursos;
    private int totalAlunos;

    public InformacaoProfessorDTO() {
    }

    public InformacaoProfessorDTO(UUID usuarioId, String descricao, int metaPontos) {
        this.usuarioId = usuarioId;
        this.descricao = descricao;
        this.metaPontos = metaPontos;
    }

    public InformacaoProfessorDTO(UUID usuarioId, String nome, String email, String descricao, int metaPontos,
                                  LocalDate dataCriacao, int totalCursos, int totalAlunos) {
        this.usuarioId = usuarioId;
        this.nome = nome;
        this.email = email;
        this.descricao = descricao;
        this.metaPontos = metaPontos;
        this.dataCriacao = dataCriacao;
        this.totalCursos = totalCursos;
        this.totalAlunos = totalAlunos;
    }

    // Getters e Setters
    public UUID getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UUID usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    public int getTotalCursos() {
        return totalCursos;
    }

    public void setTotalCursos(int totalCursos) {
        this.totalCursos = totalCursos;
    }

    public int getTotalAlunos() {
        return totalAlunos;
    }

    public void setTotalAlunos(int totalAlunos) {
        this.totalAlunos = totalAlunos;
    }
}