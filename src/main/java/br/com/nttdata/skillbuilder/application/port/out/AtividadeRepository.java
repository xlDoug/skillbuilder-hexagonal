package br.com.nttdata.skillbuilder.application.port.out;

import br.com.nttdata.skillbuilder.domain.model.Atividade;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AtividadeRepository {
    Atividade salvar(Atividade atividade);
    Optional<Atividade> buscarPorId(UUID id);
    List<Atividade> buscarPorCursoId(UUID cursoId);
    List<Atividade> buscarAtividadesDisponiveis(UUID cursoId);
    void deletar(UUID id);
}