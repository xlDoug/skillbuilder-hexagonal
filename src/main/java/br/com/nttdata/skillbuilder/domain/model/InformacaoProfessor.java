package br.com.nttdata.skillbuilder.domain.model;

import java.time.LocalDate;
import java.util.UUID;

public class InformacaoProfessor {
    private final UUID usuarioId;
    private final String descricao;
    private final int metaPontos;
    private final LocalDate dataCriacao;

    public InformacaoProfessor(UUID usuarioId, String descricao, int metaPontos) {
        this.usuarioId = usuarioId;
        this.descricao = descricao;
        this.metaPontos = metaPontos;
        this.dataCriacao = LocalDate.now();
    }

    public InformacaoProfessor(UUID usuarioId, String descricao, int metaPontos, LocalDate dataCriacao) {
        this.usuarioId = usuarioId;
        this.descricao = descricao;
        this.metaPontos = metaPontos;
        this.dataCriacao = dataCriacao;
    }

    public UUID getUsuarioId() {
        return usuarioId;
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