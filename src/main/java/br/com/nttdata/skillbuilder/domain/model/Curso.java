package br.com.nttdata.skillbuilder.domain.model;

import java.time.LocalDate;
import java.util.UUID;

public class Curso {

    private final UUID id;
    private final String nome;
    private final String descricao;
    private final UUID professorId;
    private final int metaPontos;
    private final LocalDate dataCriacao;

    public Curso(UUID id, String nome, String descricao, UUID professorId, int metaPontos, LocalDate dataCriacao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.professorId = professorId;
        this.metaPontos = metaPontos;
        this.dataCriacao = dataCriacao;
    }

    public Curso(String nome, String descricao, UUID professorId, int metaPontos) {
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.descricao = descricao;
        this.professorId = professorId;
        this.metaPontos = metaPontos;
        this.dataCriacao = LocalDate.now();
    }

    public boolean metaAtingida(int pontosAlunos) {
        return pontosAlunos >= metaPontos;
    }

    // Getters públicos (ou use lombok depois, se desejar)
    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public UUID getProfessorId() {
        return professorId;
    }

    public int getMetaPontos() {
        return metaPontos;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }
}


