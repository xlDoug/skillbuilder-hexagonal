package br.com.nttdata.skillbuilder.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.UUID;

import br.com.nttdata.skillbuilder.domain.model.InformacaoProfessor;

@Entity
@Table(name = "informacoes_professores")
public class InformacaoProfessorEntity {

    @Id
    @Column(name = "usuario_id", columnDefinition = "VARCHAR(36)")
    private String usuarioId;

    @Column(name = "descricao", nullable = false, length = 1000)
    private String descricao;

    @Column(name = "meta_pontos", nullable = false)
    private int metaPontos;

    @Column(name = "data_criacao", nullable = false)
    private LocalDate dataCriacao;

    public InformacaoProfessorEntity() {
    }

    public InformacaoProfessorEntity(InformacaoProfessor informacaoProfessor) {
        this.usuarioId = informacaoProfessor.getUsuarioId().toString();
        this.descricao = informacaoProfessor.getDescricao();
        this.metaPontos = informacaoProfessor.getMetaPontos();
        this.dataCriacao = informacaoProfessor.getDataCriacao();
    }

    public InformacaoProfessor toDomainModel() {
        return new InformacaoProfessor(
                UUID.fromString(usuarioId),
                descricao,
                metaPontos,
                dataCriacao
        );
    }

    // Getters e Setters
    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
