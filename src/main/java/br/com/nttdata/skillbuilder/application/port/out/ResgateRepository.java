package br.com.nttdata.skillbuilder.application.port.out;

import br.com.nttdata.skillbuilder.domain.model.Resgate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ResgateRepository {
    Resgate salvar(Resgate resgate);
    Optional<Resgate> buscarPorId(UUID id);
    List<Resgate> buscarPorUsuarioId(UUID usuarioId);
    List<Resgate> buscarPorRecompensaId(UUID recompensaId);
}