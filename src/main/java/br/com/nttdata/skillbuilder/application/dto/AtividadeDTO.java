package br.com.nttdata.skillbuilder.application.dto;

import java.time.LocalDate;
import java.util.UUID;

public class AtividadeDTO {
    private UUID id;
    private UUID cursoId;
    private String cursoNome;
    private String tipoAtividade;
    private String descricao;
    private int pontuacao;
    private LocalDate dataDisponibilidade;
    private boolean concluida;

    public AtividadeDTO() {
    }

    public AtividadeDTO(UUID cursoId, String tipoAtividade, String descricao, int pontuacao) {
        this.cursoId = cursoId;
        this.tipoAtividade = tipoAtividade;
        this.descricao = descricao;
        this.pontuacao = pontuacao;
    }

    public AtividadeDTO(UUID id, UUID cursoId, String cursoNome, String tipoAtividade, String descricao,
                        int pontuacao, LocalDate dataDisponibilidade, boolean concluida) {
        this.id = id;
        this.cursoId = cursoId;
        this.cursoNome = cursoNome;
        this.tipoAtividade = tipoAtividade;
        this.descricao = descricao;
        this.pontuacao = pontuacao;
        this.dataDisponibilidade = dataDisponibilidade;
        this.concluida = concluida;
    }

    // Getters e Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCursoId() {
        return cursoId;
    }

    public void setCursoId(UUID cursoId) {
        this.cursoId = cursoId;
    }

    public String getCursoNome() {
        return cursoNome;
    }

    public void setCursoNome(String cursoNome) {
        this.cursoNome = cursoNome;
    }

    public String getTipoAtividade() {
        return tipoAtividade;
    }

    public void setTipoAtividade(String tipoAtividade) {
        this.tipoAtividade = tipoAtividade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public LocalDate getDataDisponibilidade() {
        return dataDisponibilidade;
    }

    public void setDataDisponibilidade(LocalDate dataDisponibilidade) {
        this.dataDisponibilidade = dataDisponibilidade;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }
}