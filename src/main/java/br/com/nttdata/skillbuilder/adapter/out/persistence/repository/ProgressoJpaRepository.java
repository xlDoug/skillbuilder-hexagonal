package br.com.nttdata.skillbuilder.adapter.out.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.nttdata.skillbuilder.adapter.out.persistence.entity.ProgressoEntity;
import br.com.nttdata.skillbuilder.domain.model.StatusProgresso;

@Repository
public interface ProgressoJpaRepository extends JpaRepository<ProgressoEntity, String> {
    List<ProgressoEntity> findByUsuarioId(String usuarioId);
    
    List<ProgressoEntity> findByCursoId(String cursoId);
    
    List<ProgressoEntity> findByStatus(StatusProgresso status);
    
    Optional<ProgressoEntity> findByUsuarioIdAndCursoId(String usuarioId, String cursoId);
    
    @Query("SELECT COUNT(p) FROM ProgressoEntity p WHERE p.cursoId = :cursoId AND p.status = 'CONCLUIDO'")
    int countConcluidosByCursoId(@Param("cursoId") String cursoId);
}
