package br.com.nttdata.skillbuilder.application.port.out;

import java.util.Optional;
import java.util.UUID;

public interface SessaoRepository {
    String criarSessao(UUID usuarioId);
    boolean validarSessao(String token);
    void encerrarSessao(String token);
    Optional<UUID> obterUsuarioIdPorToken(String token);
}