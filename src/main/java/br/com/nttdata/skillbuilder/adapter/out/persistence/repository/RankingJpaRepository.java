package br.com.nttdata.skillbuilder.adapter.out.persistence.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.nttdata.skillbuilder.adapter.out.persistence.entity.RankingEntity;

@Repository
public interface RankingJpaRepository extends JpaRepository<RankingEntity, String> {
    
    @Query("SELECT r FROM RankingEntity r ORDER BY r.totalPontos DESC")
    List<RankingEntity> findTopRanking(Pageable pageable);
    
    @Query("SELECT COUNT(r) + 1 FROM RankingEntity r WHERE r.totalPontos > (SELECT r2.totalPontos FROM RankingEntity r2 WHERE r2.usuarioId = :usuarioId)")
    int findPosicaoByUsuarioId(@Param("usuarioId") String usuarioId);

}
