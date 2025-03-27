package br.com.nttdata.skillbuilder.domain.model;

import java.time.LocalDate;
import java.util.UUID;

public class Pontuacao {

    private final UUID id;
    private final UUID usuarioId;
    private final int pontos;
    private final OrigemPontuacao origemPontuacao;
    private final LocalDate dataRegistro;

    public Pontuacao(UUID usuarioId, int pontos, OrigemPontuacao origemPontuacao) {
        this.id = UUID.randomUUID();
        this.usuarioId = usuarioId;
        this.pontos = pontos;
        this.origemPontuacao = origemPontuacao;
        this.dataRegistro = LocalDate.now();
    }

    public UUID getId() {
        return id;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public int getPontos() {
        return pontos;
    }

    public OrigemPontuacao getOrigemPontuacao() {
        return origemPontuacao;
    }

    public LocalDate getDataRegistro() {
        return dataRegistro;
    }
}