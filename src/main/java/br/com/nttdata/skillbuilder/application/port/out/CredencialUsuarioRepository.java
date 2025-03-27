package br.com.nttdata.skillbuilder.application.port.out;

import br.com.nttdata.skillbuilder.domain.model.CredencialUsuario;

import java.util.Optional;
import java.util.UUID;

public interface CredencialUsuarioRepository {
    CredencialUsuario salvar(CredencialUsuario credencial);
    Optional<CredencialUsuario> buscarPorEmail(String email);
    Optional<CredencialUsuario> buscarPorUsuarioId(UUID usuarioId);
    void atualizarSenha(UUID usuarioId, String novaSenhaHash);
    void ativarUsuario(UUID usuarioId);
    void desativarUsuario(UUID usuarioId);
}