package br.com.nttdata.skillbuilder.application.dto;

import java.time.LocalDate;
import java.util.UUID;

public class AtividadeConcluidaDTO {
    private UUID usuarioId;
    private String usuarioNome;
    private UUID cursoId;
    private String cursoNome;
    private UUID atividadeId;
    private String atividadeDescricao;
    private LocalDate dataConclusao;
    private int pontuacao;

    public AtividadeConcluidaDTO() {
    }

    public AtividadeConcluidaDTO(UUID usuarioId, String usuarioNome, UUID cursoId, String cursoNome,
                                 UUID atividadeId, String atividadeDescricao, LocalDate dataConclusao, int pontuacao) {
        this.usuarioId = usuarioId;
        this.usuarioNome = usuarioNome;
        this.cursoId = cursoId;
        this.cursoNome = cursoNome;
        this.atividadeId = atividadeId;
        this.atividadeDescricao = atividadeDescricao;
        this.dataConclusao = dataConclusao;
        this.pontuacao = pontuacao;
    }

    // Getters e Setters
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

    public UUID getAtividadeId() {
        return atividadeId;
    }

    public void setAtividadeId(UUID atividadeId) {
        this.atividadeId = atividadeId;
    }

    public String getAtividadeDescricao() {
        return atividadeDescricao;
    }

    public void setAtividadeDescricao(String atividadeDescricao) {
        this.atividadeDescricao = atividadeDescricao;
    }

    public LocalDate getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }
}