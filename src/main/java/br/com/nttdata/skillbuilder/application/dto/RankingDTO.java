package br.com.nttdata.skillbuilder.application.dto;

import java.time.LocalDate;
import java.util.UUID;

public class RankingDTO {
    private int posicao;
    private UUID usuarioId;
    private String usuarioNome;
    private int totalPontos;
    private LocalDate dataAtualizacao;

    public RankingDTO() {
    }

    public RankingDTO(int posicao, UUID usuarioId, String usuarioNome, int totalPontos, LocalDate dataAtualizacao) {
        this.posicao = posicao;
        this.usuarioId = usuarioId;
        this.usuarioNome = usuarioNome;
        this.totalPontos = totalPontos;
        this.dataAtualizacao = dataAtualizacao;
    }

    // Getters e Setters
    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
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

    public int getTotalPontos() {
        return totalPontos;
    }

    public void setTotalPontos(int totalPontos) {
        this.totalPontos = totalPontos;
    }

    public LocalDate getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDate dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}