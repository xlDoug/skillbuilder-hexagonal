package br.com.nttdata.skillbuilder.application.port.in;

import br.com.nttdata.skillbuilder.domain.model.Progresso;

import java.util.UUID;

public interface AtualizarProgressoUseCase {
    Progresso registrarAtividadeConcluida(UUID alunoId, UUID cursoId, UUID atividadeId);
    Progresso verificarConclusaoCurso(UUID alunoId, UUID cursoId);
    double calcularPercentualProgresso(UUID alunoId, UUID cursoId);
}