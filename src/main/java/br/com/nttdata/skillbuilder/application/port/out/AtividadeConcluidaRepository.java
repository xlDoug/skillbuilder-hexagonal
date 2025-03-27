package br.com.nttdata.skillbuilder.application.port.out;

import java.util.List;
import java.util.UUID;

public interface AtividadeConcluidaRepository {
    void registrarConclusao(UUID usuarioId, UUID cursoId, UUID atividadeId);
    List<UUID> buscarAtividadesConcluidasPorUsuarioECurso(UUID usuarioId, UUID cursoId);
    boolean verificarConclusao(UUID usuarioId, UUID atividadeId);
    int contarConcluidasPorUsuarioECurso(UUID usuarioId, UUID cursoId);
}