package br.com.nttdata.skillbuilder.adapter.out.persistence.entity;

import br.com.nttdata.skillbuilder.domain.model.Recompensa;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "recompensas")
public class RecompensaEntity {

    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    private String id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "custo_pontos", nullable = false)
    private int custoPontos;

    @Column(name = "quantidade_disponivel", nullable = false)
    private int quantidadeDisponivel;

    @Column(name = "data_criacao", nullable = false)
    private LocalDate dataCriacao;

    public RecompensaEntity() {
    }

    public RecompensaEntity(Recompensa recompensa) {
        this.id = recompensa.getId().toString();
        this.nome = recompensa.getNome();
        this.descricao = recompensa.getDescricao();
        this.custoPontos = recompensa.getCustoPontos();
        this.quantidadeDisponivel = recompensa.getQuantidadeDisponivel();
        this.dataCriacao = recompensa.getDataCriacao();
    }

    public Recompensa toDomainModel() {
        return new Recompensa(
                UUID.fromString(id),
                nome,
                descricao,
                custoPontos,
                quantidadeDisponivel,
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

    public int getCustoPontos() {
        return custoPontos;
    }

    public void setCustoPontos(int custoPontos) {
        this.custoPontos = custoPontos;
    }

    public int getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public void setQuantidadeDisponivel(int quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}