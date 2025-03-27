package br.com.nttdata.skillbuilder.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.UUID;

import br.com.nttdata.skillbuilder.domain.model.Ranking;

@Entity
@Table(name = "rankings")
public class RankingEntity {

    @Id
    @Column(name = "usuario_id", columnDefinition = "VARCHAR(36)")
    private String usuarioId;

    @Column(name = "posicao_global", nullable = false)
    private int posicaoGlobal;

    @Column(name = "total_pontos", nullable = false)
    private int totalPontos;

    @Column(name = "data_atualizacao", nullable = false)
    private LocalDate dataAtualizacao;

    public RankingEntity() {
    }

    public RankingEntity(Ranking ranking) {
        this.usuarioId = ranking.getUsuarioId().toString();
        this.posicaoGlobal = ranking.getPosicaoGlobal();
        this.totalPontos = ranking.getTotalPontos();
        this.dataAtualizacao = ranking.getDataAtualizacao();
    }

    public Ranking toDomainModel() {
        return new Ranking(
                UUID.fromString(usuarioId),
                posicaoGlobal,
                totalPontos,
                dataAtualizacao
        );
    }

    // Getters e Setters
    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getPosicaoGlobal() {
        return posicaoGlobal;
    }

    public void setPosicaoGlobal(int posicaoGlobal) {
        this.posicaoGlobal = posicaoGlobal;
    }

    public int getTotalPontos() {
        return totalPontos;
    }

    public void setTotalPontos(int totalPontos) {
        this.totalPontos = totalPontos;
    }

    public LocalDate getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDate dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}
