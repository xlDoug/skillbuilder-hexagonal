package br.com.nttdata.skillbuilder.application.usecase;

import br.com.nttdata.skillbuilder.application.dto.UsuarioDTO;
import br.com.nttdata.skillbuilder.application.port.in.CadastrarUsuarioUseCase;
import br.com.nttdata.skillbuilder.application.port.out.CredencialUsuarioRepository;
import br.com.nttdata.skillbuilder.application.port.out.UsuarioRepository;
import br.com.nttdata.skillbuilder.domain.exception.RegraDeNegocioException;
import br.com.nttdata.skillbuilder.domain.model.CredencialUsuario;
import br.com.nttdata.skillbuilder.domain.model.TipoUsuario;
import br.com.nttdata.skillbuilder.domain.model.Usuario;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CadastrarUsuarioUseCaseImpl implements CadastrarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final CredencialUsuarioRepository credencialUsuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public CadastrarUsuarioUseCaseImpl(UsuarioRepository usuarioRepository,
                                       CredencialUsuarioRepository credencialUsuarioRepository,
                                       PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.credencialUsuarioRepository = credencialUsuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public Usuario executar(String nome, String email, String senha, String tipo) {
        // Verificar se email já existe
        Optional<Usuario> usuarioExistente = usuarioRepository.buscarPorEmail(email);
        if (usuarioExistente.isPresent()) {
            throw new RegraDeNegocioException("Email já cadastrado");
        }

        // Validar tipo de usuário
        TipoUsuario tipoUsuario;
        try {
            tipoUsuario = TipoUsuario.valueOf(tipo.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RegraDeNegocioException("Tipo de usuário inválido");
        }

        // Criar usuário
        Usuario novoUsuario = new Usuario(nome, email, tipoUsuario);
        Usuario usuarioSalvo = usuarioRepository.salvar(novoUsuario);

        // Criar credencial
        String senhaHash = passwordEncoder.encode(senha);
        CredencialUsuario credencial = new CredencialUsuario(usuarioSalvo.getId(), email, senhaHash);
        credencialUsuarioRepository.salvar(credencial);

        return usuarioSalvo;
    }

    @Override
    @Transactional
    public Usuario criarUsuario(UsuarioDTO usuarioDTO) {
        return executar(
                usuarioDTO.getNome(),
                usuarioDTO.getEmail(),
                usuarioDTO.getSenha(),
                usuarioDTO.getTipo()
        );
    }
}