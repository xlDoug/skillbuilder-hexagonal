package br.com.nttdata.skillbuilder.application.port.in;

import br.com.nttdata.skillbuilder.application.dto.CursoDTO;
import br.com.nttdata.skillbuilder.domain.model.Curso;

import java.util.UUID;

public interface CriarCursoUseCase {
    Curso executar(CursoDTO cursoDTO, UUID professorId);
}