package br.com.nttdata.skillbuilder.application.dto;

import java.time.LocalDate;
import java.util.UUID;

public class PontuacaoDTO {
    private UUID id;
    private UUID usuarioId;
    private String usuarioNome;
    private int pontos;
    private String origemPontuacao;
    private LocalDate dataRegistro;

    public PontuacaoDTO() {
    }

    public PontuacaoDTO(UUID id, UUID usuarioId, String usuarioNome, int pontos, String origemPontuacao, LocalDate dataRegistro) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.usuarioNome = usuarioNome;
        this.pontos = pontos;
        this.origemPontuacao = origemPontuacao;
        this.dataRegistro = dataRegistro;
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

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public String getOrigemPontuacao() {
        return origemPontuacao;
    }

    public void setOrigemPontuacao(String origemPontuacao) {
        this.origemPontuacao = origemPontuacao;
    }

    public LocalDate getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDate dataRegistro) {
        this.dataRegistro = dataRegistro;
    }
}