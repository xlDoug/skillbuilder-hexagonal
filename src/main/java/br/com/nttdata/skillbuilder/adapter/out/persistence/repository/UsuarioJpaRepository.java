package br.com.nttdata.skillbuilder.adapter.out.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nttdata.skillbuilder.adapter.out.persistence.entity.UsuarioEntity;
import br.com.nttdata.skillbuilder.domain.model.TipoUsuario;

@Repository
public interface UsuarioJpaRepository extends JpaRepository<UsuarioEntity, String> {
    Optional<UsuarioEntity> findByEmail(String email);
    List<UsuarioEntity> findByTipo(TipoUsuario tipo);
}
