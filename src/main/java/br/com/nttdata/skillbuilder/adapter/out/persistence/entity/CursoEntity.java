package br.com.nttdata.skillbuilder.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.UUID;

import br.com.nttdata.skillbuilder.domain.model.Curso;

@Entity
@Table(name = "cursos")
public class CursoEntity {

    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    private String id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "professor_id", nullable = false, columnDefinition = "VARCHAR(36)")
    private String professorId;

    @Column(name = "meta_pontos", nullable = false)
    private int metaPontos;

    @Column(name = "data_criacao", nullable = false)
    private LocalDate dataCriacao;

    public CursoEntity() {
    }

    public CursoEntity(Curso curso) {
        this.id = curso.getId().toString();
        this.nome = curso.getNome();
        this.descricao = curso.getDescricao();
        this.professorId = curso.getProfessorId().toString();
        this.metaPontos = curso.getMetaPontos();
        this.dataCriacao = curso.getDataCriacao();
    }

    public Curso toDomainModel() {
        return new Curso(
                UUID.fromString(id),
                nome,
                descricao,
                UUID.fromString(professorId),
                metaPontos,
                dataCriacao
        );
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getProfessorId() {
        return professorId;
    }

    public void setProfessorId(String professorId) {
        this.professorId = professorId;
    }

    public int getMetaPontos() {
        return metaPontos;
    }

    public void setMetaPontos(int metaPontos) {
        this.metaPontos = metaPontos;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
