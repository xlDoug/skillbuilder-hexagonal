package br.com.nttdata.skillbuilder.application.port.in;

import br.com.nttdata.skillbuilder.application.dto.CursoDTO;

import java.util.List;
import java.util.UUID;

public interface ListarCursosUseCase {
    List<CursoDTO> listarTodos();
    List<CursoDTO> listarPorProfessor(UUID professorId);
    List<CursoDTO> listarPorAluno(UUID alunoId);
}