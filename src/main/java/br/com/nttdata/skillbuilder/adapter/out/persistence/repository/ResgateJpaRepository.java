package br.com.nttdata.skillbuilder.adapter.out.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nttdata.skillbuilder.adapter.out.persistence.entity.ResgateEntity;

@Repository
public interface ResgateJpaRepository extends JpaRepository<ResgateEntity, String> {
    List<ResgateEntity> findByUsuarioId(String usuarioId);
    
    List<ResgateEntity> findByRecompensaId(String recompensaId);
}
