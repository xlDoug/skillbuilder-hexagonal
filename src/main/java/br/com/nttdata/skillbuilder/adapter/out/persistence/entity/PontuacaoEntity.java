package br.com.nttdata.skillbuilder.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.UUID;

import br.com.nttdata.skillbuilder.domain.model.OrigemPontuacao;
import br.com.nttdata.skillbuilder.domain.model.Pontuacao;

@Entity
@Table(name = "pontuacoes")
public class PontuacaoEntity {

    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    private String id;

    @Column(name = "usuario_id", nullable = false, columnDefinition = "VARCHAR(36)")
    private String usuarioId;

    @Column(name = "pontos", nullable = false)
    private int pontos;

    @Enumerated(EnumType.STRING)
    @Column(name = "origem_pontuacao", nullable = false)
    private OrigemPontuacao origemPontuacao;

    @Column(name = "data_registro", nullable = false)
    private LocalDate dataRegistro;

    public PontuacaoEntity() {
    }

    public PontuacaoEntity(Pontuacao pontuacao) {
        this.id = pontuacao.getId().toString();
        this.usuarioId = pontuacao.getUsuarioId().toString();
        this.pontos = pontuacao.getPontos();
        this.origemPontuacao = pontuacao.getOrigemPontuacao();
        this.dataRegistro = pontuacao.getDataRegistro();
    }

    public Pontuacao toDomainModel() {
        return new Pontuacao(
                UUID.fromString(usuarioId),
                pontos,
                origemPontuacao
        );
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public OrigemPontuacao getOrigemPontuacao() {
        return origemPontuacao;
    }

    public void setOrigemPontuacao(OrigemPontuacao origemPontuacao) {
        this.origemPontuacao = origemPontuacao;
    }

    public LocalDate getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDate dataRegistro) {
        this.dataRegistro = dataRegistro;
    }
}