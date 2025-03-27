package br.com.nttdata.skillbuilder.adapter.out.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.nttdata.skillbuilder.adapter.out.persistence.entity.UsuarioEntity;
import br.com.nttdata.skillbuilder.adapter.out.persistence.repository.UsuarioJpaRepository;
import br.com.nttdata.skillbuilder.application.port.out.UsuarioRepository;
import br.com.nttdata.skillbuilder.domain.model.TipoUsuario;
import br.com.nttdata.skillbuilder.domain.model.Usuario;

@Component
public class UsuarioRepositoryAdapter implements UsuarioRepository {

    private final UsuarioJpaRepository usuarioJpaRepository;

    public UsuarioRepositoryAdapter(UsuarioJpaRepository usuarioJpaRepository) {
        this.usuarioJpaRepository = usuarioJpaRepository;
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        UsuarioEntity usuarioEntity = new UsuarioEntity(usuario);
        UsuarioEntity savedEntity = usuarioJpaRepository.save(usuarioEntity);
        return savedEntity.toDomainModel();
    }

    @Override
    public Optional<Usuario> buscarPorId(UUID id) {
        return usuarioJpaRepository.findById(id.toString())
                .map(UsuarioEntity::toDomainModel);
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioJpaRepository.findByEmail(email)
                .map(UsuarioEntity::toDomainModel);
    }

    @Override
    public List<Usuario> buscarTodos() {
        return usuarioJpaRepository.findAll().stream()
                .map(UsuarioEntity::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Usuario> buscarPorTipo(String tipo) {
        TipoUsuario tipoUsuario = TipoUsuario.valueOf(tipo);
        return usuarioJpaRepository.findByTipo(tipoUsuario).stream()
                .map(UsuarioEntity::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public void deletar(UUID id) {
        usuarioJpaRepository.deleteById(id.toString());
    }
}
