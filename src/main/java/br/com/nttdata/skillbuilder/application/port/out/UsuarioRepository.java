package br.com.nttdata.skillbuilder.application.port.out;

import br.com.nttdata.skillbuilder.domain.model.Usuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository {
    Usuario salvar(Usuario usuario);
    Optional<Usuario> buscarPorId(UUID id);
    Optional<Usuario> buscarPorEmail(String email);
    List<Usuario> buscarTodos();
    List<Usuario> buscarPorTipo(String tipo);
    void deletar(UUID id);
}