package br.com.nttdata.skillbuilder.domain.model;

import java.time.LocalDate;
import java.util.UUID;

public class Professor {

    private final UUID id;
    private final String nome;
    private final String descricao;
    private final int metaPontos;
    private final LocalDate dataCriacao;

    public Professor(String nome, String descricao, int metaPontos) {
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.descricao = descricao;
        this.metaPontos = metaPontos;
        this.dataCriacao = LocalDate.now();
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getMetaPontos() {
        return metaPontos;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }
}