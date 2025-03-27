package br.com.nttdata.skillbuilder.adapter.out.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.nttdata.skillbuilder.adapter.out.persistence.entity.AtividadeConcluidaEntity;

@Repository
public interface AtividadeConcluidaJpaRepository extends JpaRepository<AtividadeConcluidaEntity, String> {
    List<AtividadeConcluidaEntity> findByUsuarioIdAndCursoId(String usuarioId, String cursoId);
    
    Optional<AtividadeConcluidaEntity> findByUsuarioIdAndAtividadeId(String usuarioId, String atividadeId);
    
    @Query("SELECT COUNT(ac) FROM AtividadeConcluidaEntity ac WHERE ac.usuarioId = :usuarioId AND ac.cursoId = :cursoId")
    int countByUsuarioIdAndCursoId(@Param("usuarioId") String usuarioId, @Param("cursoId") String cursoId);
    
    @Query("SELECT ac.atividadeId FROM AtividadeConcluidaEntity ac WHERE ac.usuarioId = :usuarioId AND ac.cursoId = :cursoId")
    List<String> findAtividadesIdsByUsuarioIdAndCursoId(@Param("usuarioId") String usuarioId, @Param("cursoId") String cursoId);
}
