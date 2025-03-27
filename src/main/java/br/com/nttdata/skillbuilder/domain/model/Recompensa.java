package br.com.nttdata.skillbuilder.domain.model;

import java.util.UUID;
import java.time.LocalDate;


public class Recompensa {

    private final UUID id;
    private final String nome;
    private final String descricao;
    private final int custoPontos;
    private int quantidadeDisponivel;
    private final LocalDate dataCriacao;

    public Recompensa(UUID id, String nome, String descricao, int custoPontos, int quantidadeDisponivel, LocalDate dataCriacao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.custoPontos = custoPontos;
        this.quantidadeDisponivel = quantidadeDisponivel;
        this.dataCriacao = dataCriacao;
    }

    public boolean podeResgatar(int pontosDoUsuario) {
        return pontosDoUsuario >= custoPontos && quantidadeDisponivel > 0;
    }

    public void consumirUnidade() {
        if (quantidadeDisponivel <= 0) {
            throw new IllegalStateException("Recompensa esgotada");
        }
        this.quantidadeDisponivel--;
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

    public int getCustoPontos() {
        return custoPontos;
    }

    public int getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }
}

