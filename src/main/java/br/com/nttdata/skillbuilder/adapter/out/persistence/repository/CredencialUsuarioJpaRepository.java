package br.com.nttdata.skillbuilder.adapter.out.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nttdata.skillbuilder.adapter.out.persistence.entity.CredencialUsuarioEntity;

@Repository
public interface CredencialUsuarioJpaRepository extends JpaRepository<CredencialUsuarioEntity, String> {
    Optional<CredencialUsuarioEntity> findByEmail(String email);
}
