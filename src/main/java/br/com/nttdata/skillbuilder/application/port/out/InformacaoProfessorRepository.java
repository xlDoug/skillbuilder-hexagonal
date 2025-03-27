package br.com.nttdata.skillbuilder.application.port.out;

import br.com.nttdata.skillbuilder.domain.model.InformacaoProfessor;

import java.util.Optional;
import java.util.UUID;

public interface InformacaoProfessorRepository {
    InformacaoProfessor salvar(InformacaoProfessor informacaoProfessor);
    Optional<InformacaoProfessor> buscarPorUsuarioId(UUID usuarioId);
    void atualizar(InformacaoProfessor informacaoProfessor);
}