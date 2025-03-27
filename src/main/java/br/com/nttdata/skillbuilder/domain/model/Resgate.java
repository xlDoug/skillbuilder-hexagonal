package br.com.nttdata.skillbuilder.domain.model;

import java.time.LocalDate;
import java.util.UUID;

public class Resgate {

    private final UUID id;
    private final UUID usuarioId;
    private final UUID recompensaId;
    private final LocalDate dataResgate;

    public Resgate(UUID usuarioId, UUID recompensaId) {
        this.id = UUID.randomUUID();
        this.usuarioId = usuarioId;
        this.recompensaId = recompensaId;
        this.dataResgate = LocalDate.now();
    }

    public Resgate(UUID id, UUID usuarioId, UUID recompensaId, LocalDate dataResgate) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.recompensaId = recompensaId;
        this.dataResgate = dataResgate;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public UUID getRecompensaId() {
        return recompensaId;
    }

    public LocalDate getDataResgate() {
        return dataResgate;
    }
}
