package br.com.nttdata.skillbuilder.application.port.out;

import br.com.nttdata.skillbuilder.domain.model.Progresso;
import br.com.nttdata.skillbuilder.domain.model.StatusProgresso;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProgressoRepository {
    Progresso salvar(Progresso progresso);
    Optional<Progresso> buscarPorId(UUID id);
    Optional<Progresso> buscarPorUsuarioECurso(UUID usuarioId, UUID cursoId);
    List<Progresso> buscarPorUsuarioId(UUID usuarioId);
    List<Progresso> buscarPorCursoId(UUID cursoId);
    List<Progresso> buscarPorStatus(StatusProgresso status);
    int contarConcluidosPorCurso(UUID cursoId);
}