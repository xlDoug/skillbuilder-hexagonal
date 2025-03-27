package br.com.nttdata.skillbuilder.domain.model;

import java.time.LocalDate;
import java.util.UUID;

public class Ranking {

    private final UUID id;
    private final UUID usuarioId;
    private int posicaoGlobal;
    private int totalPontos;
    private LocalDate dataAtualizacao;

    public Ranking(UUID usuarioId, int posicaoGlobal, int totalPontos) {
        this.id = UUID.randomUUID();
        this.usuarioId = usuarioId;
        this.posicaoGlobal = posicaoGlobal;
        this.totalPontos = totalPontos;
        this.dataAtualizacao = LocalDate.now();
    }

    public void atualizar(int novaPosicao, int novosPontos) {
        this.posicaoGlobal = novaPosicao;
        this.totalPontos = novosPontos;
        this.dataAtualizacao = LocalDate.now();
    }

    public UUID getId() {
        return id;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public int getPosicaoGlobal() {
        return posicaoGlobal;
    }

    public int getTotalPontos() {
        return totalPontos;
    }

    public LocalDate getDataAtualizacao() {
        return dataAtualizacao;
    }
}
