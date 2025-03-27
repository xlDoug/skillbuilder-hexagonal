package br.com.nttdata.skillbuilder.application.port.out;

import br.com.nttdata.skillbuilder.domain.model.Recompensa;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RecompensaRepository {
    Recompensa salvar(Recompensa recompensa);
    Optional<Recompensa> buscarPorId(UUID id);
    List<Recompensa> buscarTodas();
    List<Recompensa> buscarDisponiveis();
    void atualizarQuantidadeDisponivel(UUID id, int novaQuantidade);
    void deletar(UUID id);
}