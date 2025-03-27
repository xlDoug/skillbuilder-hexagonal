package br.com.nttdata.skillbuilder.adapter.out.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.nttdata.skillbuilder.adapter.out.persistence.entity.SessaoEntity;

@Repository
public interface SessaoJpaRepository extends JpaRepository<SessaoEntity, String> {
    
    Optional<SessaoEntity> findByToken(String token);
    
    List<SessaoEntity> findByUsuarioId(String usuarioId);
    
    @Modifying
    @Query("UPDATE SessaoEntity s SET s.ativa = false WHERE s.token = :token")
    void invalidateToken(@Param("token") String token);
    
    @Modifying
    @Query("UPDATE SessaoEntity s SET s.ativa = false WHERE s.dataExpiracao < :agora")
    void invalidateExpiredTokens(@Param("agora") LocalDateTime agora);
}
