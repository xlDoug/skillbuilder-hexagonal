package br.com.nttdata.skillbuilder.domain.model;

import br.com.nttdata.skillbuilder.domain.exception.RegraDeNegocioException;

import java.time.LocalDate;
import java.util.UUID;

public class Progresso {

    private final UUID id;
    private final UUID usuarioId;
    private final UUID cursoId;
    private StatusProgresso status;
    private final LocalDate dataInicio;
    private LocalDate dataConclusao;
    private int pontosAcumulados;

    public Progresso(UUID usuarioId, UUID cursoId) {
        this.id = UUID.randomUUID();
        this.usuarioId = usuarioId;
        this.cursoId = cursoId;
        this.status = StatusProgresso.EM_ANDAMENTO;
        this.dataInicio = LocalDate.now();
        this.pontosAcumulados = 0;
    }

    public void acumularPontos(int pontos) {
        this.pontosAcumulados += pontos;
    }

    public void concluir() {
        if (this.status == StatusProgresso.CONCLUIDO) {
            throw new RegraDeNegocioException("Curso já foi concluído.");
        }
        this.status = StatusProgresso.CONCLUIDO;
        this.dataConclusao = LocalDate.now();
    }


    public boolean estaConcluido() {
        return status == StatusProgresso.CONCLUIDO;
    }

    // Getters públicos — pode gerar via IDE ou manualmente
    public UUID getId() {
        return id;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public UUID getCursoId() {
        return cursoId;
    }

    public StatusProgresso getStatus() {
        return status;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataConclusao() {
        return dataConclusao;
    }

    public int getPontosAcumulados() {
        return pontosAcumulados;
    }
}
