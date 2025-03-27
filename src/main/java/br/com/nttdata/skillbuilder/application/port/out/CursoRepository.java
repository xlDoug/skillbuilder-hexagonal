package br.com.nttdata.skillbuilder.application.port.out;

import br.com.nttdata.skillbuilder.domain.model.Curso;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CursoRepository {
    Curso salvar(Curso curso);
    Optional<Curso> buscarPorId(UUID id);
    List<Curso> buscarTodos();
    List<Curso> buscarPorProfessorId(UUID professorId);
    void deletar(UUID id);
}