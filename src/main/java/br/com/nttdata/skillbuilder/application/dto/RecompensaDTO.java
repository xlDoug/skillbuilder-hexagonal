package br.com.nttdata.skillbuilder.application.dto;

import java.time.LocalDate;
import java.util.UUID;

public class RecompensaDTO {
    private UUID id;
    private String nome;
    private String descricao;
    private int custoPontos;
    private int quantidadeDisponivel;
    private LocalDate dataCriacao;
    private boolean disponivel;

    public RecompensaDTO() {
    }

    public RecompensaDTO(String nome, String descricao, int custoPontos, int quantidadeDisponivel) {
        this.nome = nome;
        this.descricao = descricao;
        this.custoPontos = custoPontos;
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public RecompensaDTO(UUID id, String nome, String descricao, int custoPontos, int quantidadeDisponivel,
                         LocalDate dataCriacao, boolean disponivel) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.custoPontos = custoPontos;
        this.quantidadeDisponivel = quantidadeDisponivel;
        this.dataCriacao = dataCriacao;
        this.disponivel = disponivel;
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

    public int getCustoPontos() {
        return custoPontos;
    }

    public void setCustoPontos(int custoPontos) {
        this.custoPontos = custoPontos;
    }

    public int getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public void setQuantidadeDisponivel(int quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }
}