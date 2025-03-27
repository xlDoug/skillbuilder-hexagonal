package br.com.nttdata.skillbuilder.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.UUID;

import br.com.nttdata.skillbuilder.domain.model.Atividade;
import br.com.nttdata.skillbuilder.domain.model.TipoAtividade;

@Entity
@Table(name = "atividades")
public class AtividadeEntity {

    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    private String id;

    @Column(name = "curso_id", nullable = false, columnDefinition = "VARCHAR(36)")
    private String cursoId;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_atividade", nullable = false)
    private TipoAtividade tipoAtividade;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "pontuacao", nullable = false)
    private int pontuacao;

    @Column(name = "data_disponibilidade", nullable = false)
    private LocalDate dataDisponibilidade;

    public AtividadeEntity() {
    }

    public AtividadeEntity(Atividade atividade) {
        this.id = atividade.getId().toString();
        this.cursoId = atividade.getCursoId().toString();
        this.tipoAtividade = atividade.getTipoAtividade();
        this.descricao = atividade.getDescricao();
        this.pontuacao = atividade.getPontuacao();
        this.dataDisponibilidade = atividade.getDataDisponibilidade();
    }

    public Atividade toDomainModel() {
        return new Atividade(
                //UUID.fromString(id),
                UUID.fromString(cursoId),
                tipoAtividade,
                descricao,
                pontuacao,
                dataDisponibilidade
        );
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCursoId() {
        return cursoId;
    }

    public void setCursoId(String cursoId) {
        this.cursoId = cursoId;
    }

    public TipoAtividade getTipoAtividade() {
        return tipoAtividade;
    }

    public void setTipoAtividade(TipoAtividade tipoAtividade) {
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
}
