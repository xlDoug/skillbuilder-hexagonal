package br.com.nttdata.skillbuilder.application.port.in;

import br.com.nttdata.skillbuilder.domain.model.Recompensa;
import br.com.nttdata.skillbuilder.domain.model.Resgate;

import java.util.List;
import java.util.UUID;

public interface ResgatarRecompensaUseCase {
    Resgate executar(UUID usuarioId, UUID recompensaId);
    List<Recompensa> listarRecompensasDisponiveis(UUID usuarioId);
    List<Resgate> consultarResgatesUsuario(UUID usuarioId);
}