package br.com.nttdata.skillbuilder.application.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class RelatorioCursoDTO {
    private UUID cursoId;
    private String cursoNome;
    private String descricao;
    private UUID professorId;
    private String professorNome;
    private int totalAlunos;
    private int totalAlunosConcluintes;
    private double taxaConclusao;
    private int totalAtividades;
    private double mediaAtividadesPorAluno;
    private double mediaPontuacaoPorAluno;
    private List<AtividadeDTO> atividades;
    private LocalDate dataRelatorio;

    public RelatorioCursoDTO() {
    }

    public RelatorioCursoDTO(UUID cursoId, String cursoNome, String descricao, UUID professorId,
                             String professorNome, int totalAlunos, int totalAlunosConcluintes,
                             double taxaConclusao, int totalAtividades, double mediaAtividadesPorAluno,
                             double mediaPontuacaoPorAluno, List<AtividadeDTO> atividades) {
        this.cursoId = cursoId;
        this.cursoNome = cursoNome;
        this.descricao = descricao;
        this.professorId = professorId;
        this.professorNome = professorNome;
        this.totalAlunos = totalAlunos;
        this.totalAlunosConcluintes = totalAlunosConcluintes;
        this.taxaConclusao = taxaConclusao;
        this.totalAtividades = totalAtividades;
        this.mediaAtividadesPorAluno = mediaAtividadesPorAluno;
        this.mediaPontuacaoPorAluno = mediaPontuacaoPorAluno;
        this.atividades = atividades;
        this.dataRelatorio = LocalDate.now();
    }

    // Getters e Setters
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public UUID getProfessorId() {
        return professorId;
    }

    public void setProfessorId(UUID professorId) {
        this.professorId = professorId;
    }

    public String getProfessorNome() {
        return professorNome;
    }

    public void setProfessorNome(String professorNome) {
        this.professorNome = professorNome;
    }

    public int getTotalAlunos() {
        return totalAlunos;
    }

    public void setTotalAlunos(int totalAlunos) {
        this.totalAlunos = totalAlunos;
    }

    public int getTotalAlunosConcluintes() {
        return totalAlunosConcluintes;
    }

    public void setTotalAlunosConcluintes(int totalAlunosConcluintes) {
        this.totalAlunosConcluintes = totalAlunosConcluintes;
    }

    public double getTaxaConclusao() {
        return taxaConclusao;
    }

    public void setTaxaConclusao(double taxaConclusao) {
        this.taxaConclusao = taxaConclusao;
    }

    public int getTotalAtividades() {
        return totalAtividades;
    }

    public void setTotalAtividades(int totalAtividades) {
        this.totalAtividades = totalAtividades;
    }

    public double getMediaAtividadesPorAluno() {
        return mediaAtividadesPorAluno;
    }

    public void setMediaAtividadesPorAluno(double mediaAtividadesPorAluno) {
        this.mediaAtividadesPorAluno = mediaAtividadesPorAluno;
    }

    public double getMediaPontuacaoPorAluno() {
        return mediaPontuacaoPorAluno;
    }

    public void setMediaPontuacaoPorAluno(double mediaPontuacaoPorAluno) {
        this.mediaPontuacaoPorAluno = mediaPontuacaoPorAluno;
    }

    public List<AtividadeDTO> getAtividades() {
        return atividades;
    }

    public void setAtividades(List<AtividadeDTO> atividades) {
        this.atividades = atividades;
    }

    public LocalDate getDataRelatorio() {
        return dataRelatorio;
    }

    public void setDataRelatorio(LocalDate dataRelatorio) {
        this.dataRelatorio = dataRelatorio;
    }
}