package br.com.nttdata.skillbuilder.application.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class DashboardDTO {
    private UUID usuarioId;
    private String usuarioNome;
    private int totalPontos;
    private int posicaoRanking;
    private int cursosConcluidos;
    private int cursosEmAndamento;
    private int atividadesConcluidas;
    private List<ProgressoDTO> progressoRecente;
    private List<NotificacaoDTO> notificacoesRecentes;
    private List<RecompensaDTO> recompensasDisponiveis;
    private LocalDate dataAtualizacao;

    public DashboardDTO() {
    }

    public DashboardDTO(UUID usuarioId, String usuarioNome, int totalPontos, int posicaoRanking,
                        int cursosConcluidos, int cursosEmAndamento, int atividadesConcluidas,
                        List<ProgressoDTO> progressoRecente, List<NotificacaoDTO> notificacoesRecentes,
                        List<RecompensaDTO> recompensasDisponiveis) {
        this.usuarioId = usuarioId;
        this.usuarioNome = usuarioNome;
        this.totalPontos = totalPontos;
        this.posicaoRanking = posicaoRanking;
        this.cursosConcluidos = cursosConcluidos;
        this.cursosEmAndamento = cursosEmAndamento;
        this.atividadesConcluidas = atividadesConcluidas;
        this.progressoRecente = progressoRecente;
        this.notificacoesRecentes = notificacoesRecentes;
        this.recompensasDisponiveis = recompensasDisponiveis;
        this.dataAtualizacao = LocalDate.now();
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

    public int getTotalPontos() {
        return totalPontos;
    }

    public void setTotalPontos(int totalPontos) {
        this.totalPontos = totalPontos;
    }

    public int getPosicaoRanking() {
        return posicaoRanking;
    }

    public void setPosicaoRanking(int posicaoRanking) {
        this.posicaoRanking = posicaoRanking;
    }

    public int getCursosConcluidos() {
        return cursosConcluidos;
    }

    public void setCursosConcluidos(int cursosConcluidos) {
        this.cursosConcluidos = cursosConcluidos;
    }

    public int getCursosEmAndamento() {
        return cursosEmAndamento;
    }

    public void setCursosEmAndamento(int cursosEmAndamento) {
        this.cursosEmAndamento = cursosEmAndamento;
    }

    public int getAtividadesConcluidas() {
        return atividadesConcluidas;
    }

    public void setAtividadesConcluidas(int atividadesConcluidas) {
        this.atividadesConcluidas = atividadesConcluidas;
    }

    public List<ProgressoDTO> getProgressoRecente() {
        return progressoRecente;
    }

    public void setProgressoRecente(List<ProgressoDTO> progressoRecente) {
        this.progressoRecente = progressoRecente;
    }

    public List<NotificacaoDTO> getNotificacoesRecentes() {
        return notificacoesRecentes;
    }

    public void setNotificacoesRecentes(List<NotificacaoDTO> notificacoesRecentes) {
        this.notificacoesRecentes = notificacoesRecentes;
    }

    public List<RecompensaDTO> getRecompensasDisponiveis() {
        return recompensasDisponiveis;
    }

    public void setRecompensasDisponiveis(List<RecompensaDTO> recompensasDisponiveis) {
        this.recompensasDisponiveis = recompensasDisponiveis;
    }

    public LocalDate getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDate dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}