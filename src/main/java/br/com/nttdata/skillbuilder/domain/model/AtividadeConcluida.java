package br.com.nttdata.skillbuilder.domain.model;

import java.time.LocalDate;
import java.util.UUID;

public class AtividadeConcluida {
    private final UUID id;
    private final UUID usuarioId;
    private final UUID cursoId;
    private final UUID atividadeId;
    private final LocalDate dataConclusao;

    public AtividadeConcluida(UUID usuarioId, UUID cursoId, UUID atividadeId) {
        this.id = UUID.randomUUID();
        this.usuarioId = usuarioId;
        this.cursoId = cursoId;
        this.atividadeId = atividadeId;
        this.dataConclusao = LocalDate.now();
    }

    public UUID getId() {
        return id;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public UUID getCursoId() {
        return cursoId;
    }

    public UUID getAtividadeId() {
        return atividadeId;
    }

    public LocalDate getDataConclusao() {
        return dataConclusao;
    }
}