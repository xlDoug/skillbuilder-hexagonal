package br.com.nttdata.skillbuilder.domain.model;

import java.time.LocalDate;
import java.util.UUID;

public class Atividade {

    private final UUID id;
    private final UUID cursoId;
    private final TipoAtividade tipoAtividade;
    private final String descricao;
    private final int pontuacao;
    private final LocalDate dataDisponibilidade;

    public Atividade(UUID cursoId, TipoAtividade tipoAtividade, String descricao, int pontuacao) {
        this.id = UUID.randomUUID();
        this.cursoId = cursoId;
        this.tipoAtividade = tipoAtividade;
        this.descricao = descricao;
        this.pontuacao = pontuacao;
        this.dataDisponibilidade = LocalDate.now();
    }

    public Atividade(UUID cursoId, TipoAtividade tipoAtividade, String descricao, int pontuacao, LocalDate dataDisponibilidade) {
        this.id = UUID.randomUUID();
        this.cursoId = cursoId;
        this.tipoAtividade = tipoAtividade;
        this.descricao = descricao;
        this.pontuacao = pontuacao;
        this.dataDisponibilidade = dataDisponibilidade;
    }

    public boolean estaDisponivel() {
        return !dataDisponibilidade.isAfter(LocalDate.now());
    }

    public UUID getId() {
        return id;
    }

    public UUID getCursoId() {
        return cursoId;
    }

    public TipoAtividade getTipoAtividade() {
        return tipoAtividade;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public LocalDate getDataDisponibilidade() {
        return dataDisponibilidade;
    }
}