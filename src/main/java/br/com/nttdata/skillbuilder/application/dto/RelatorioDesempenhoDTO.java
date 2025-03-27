package br.com.nttdata.skillbuilder.application.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class RelatorioDesempenhoDTO {
    private UUID usuarioId;
    private String usuarioNome;
    private int totalCursosInscritos;
    private int totalCursosConcluidos;
    private int totalAtividadesConcluidas;
    private int pontuacaoTotal;
    private int posicaoRanking;
    private List<ProgressoDTO> cursos;
    private List<PontuacaoDTO> pontuacoes;
    private LocalDate dataRelatorio;

    public RelatorioDesempenhoDTO() {
    }

    public RelatorioDesempenhoDTO(UUID usuarioId, String usuarioNome, int totalCursosInscritos, int totalCursosConcluidos,
                                  int totalAtividadesConcluidas, int pontuacaoTotal, int posicaoRanking,
                                  List<ProgressoDTO> cursos, List<PontuacaoDTO> pontuacoes) {
        this.usuarioId = usuarioId;
        this.usuarioNome = usuarioNome;
        this.totalCursosInscritos = totalCursosInscritos;
        this.totalCursosConcluidos = totalCursosConcluidos;
        this.totalAtividadesConcluidas = totalAtividadesConcluidas;
        this.pontuacaoTotal = pontuacaoTotal;
        this.posicaoRanking = posicaoRanking;
        this.cursos = cursos;
        this.pontuacoes = pontuacoes;
        this.dataRelatorio = LocalDate.now();
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

    public int getTotalCursosInscritos() {
        return totalCursosInscritos;
    }

    public void setTotalCursosInscritos(int totalCursosInscritos) {
        this.totalCursosInscritos = totalCursosInscritos;
    }

    public int getTotalCursosConcluidos() {
        return totalCursosConcluidos;
    }

    public void setTotalCursosConcluidos(int totalCursosConcluidos) {
        this.totalCursosConcluidos = totalCursosConcluidos;
    }

    public int getTotalAtividadesConcluidas() {
        return totalAtividadesConcluidas;
    }

    public void setTotalAtividadesConcluidas(int totalAtividadesConcluidas) {
        this.totalAtividadesConcluidas = totalAtividadesConcluidas;
    }

    public int getPontuacaoTotal() {
        return pontuacaoTotal;
    }

    public void setPontuacaoTotal(int pontuacaoTotal) {
        this.pontuacaoTotal = pontuacaoTotal;
    }

    public int getPosicaoRanking() {
        return posicaoRanking;
    }

    public void setPosicaoRanking(int posicaoRanking) {
        this.posicaoRanking = posicaoRanking;
    }

    public List<ProgressoDTO> getCursos() {
        return cursos;
    }

    public void setCursos(List<ProgressoDTO> cursos) {
        this.cursos = cursos;
    }

    public List<PontuacaoDTO> getPontuacoes() {
        return pontuacoes;
    }

    public void setPontuacoes(List<PontuacaoDTO> pontuacoes) {
        this.pontuacoes = pontuacoes;
    }

    public LocalDate getDataRelatorio() {
        return dataRelatorio;
    }

    public void setDataRelatorio(LocalDate dataRelatorio) {
        this.dataRelatorio = dataRelatorio;
    }
}