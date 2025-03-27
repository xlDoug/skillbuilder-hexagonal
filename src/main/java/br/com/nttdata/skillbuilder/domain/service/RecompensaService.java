package br.com.nttdata.skillbuilder.domain.service;

import br.com.nttdata.skillbuilder.domain.model.Recompensa;
import br.com.nttdata.skillbuilder.domain.model.Resgate;

import java.util.UUID;

public class RecompensaService {

    /**
     * Verifica se o usuário tem pontos suficientes e há recompensa disponível.
     */
    public boolean podeResgatar(Recompensa recompensa, int pontosDoUsuario) {
        return recompensa.podeResgatar(pontosDoUsuario);
    }

    /**
     * Executa o resgate e retorna o objeto Resgate criado.
     */
    public Resgate realizarResgate(UUID usuarioId, Recompensa recompensa, int pontosDoUsuario) {
        if (!recompensa.podeResgatar(pontosDoUsuario)) {
            throw new IllegalStateException("Usuário não pode resgatar esta recompensa");
        }

        recompensa.consumirUnidade(); // diminui a quantidade disponível
        return new Resgate(usuarioId, recompensa.getId()); // gera o resgate
    }
}
