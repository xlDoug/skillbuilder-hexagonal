package br.com.nttdata.skillbuilder.application.port.in;

import br.com.nttdata.skillbuilder.domain.model.Progresso;

import java.util.UUID;

public interface InscreverAlunoUseCase {
    Progresso executar(UUID alunoId, UUID cursoId);
}