package br.com.nttdata.skillbuilder.application.port.in;

import br.com.nttdata.skillbuilder.application.dto.UsuarioDTO;
import br.com.nttdata.skillbuilder.domain.model.Usuario;

public interface CadastrarUsuarioUseCase {
    Usuario executar(String nome, String email, String senha, String tipo);
    Usuario criarUsuario(UsuarioDTO usuarioDTO);
}