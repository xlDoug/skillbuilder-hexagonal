package br.com.nttdata.skillbuilder.adapter.out.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.nttdata.skillbuilder.adapter.out.persistence.entity.PontuacaoEntity;
import br.com.nttdata.skillbuilder.domain.model.OrigemPontuacao;

@Repository
public interface PontuacaoJpaRepository extends JpaRepository<PontuacaoEntity, String> {
    List<PontuacaoEntity> findByUsuarioId(String usuarioId);
    
    List<PontuacaoEntity> findByOrigemPontuacao(OrigemPontuacao origem);
    
    @Query("SELECT SUM(p.pontos) FROM PontuacaoEntity p WHERE p.usuarioId = :usuarioId")
    Integer sumPontosByUsuarioId(@Param("usuarioId") String usuarioId);
}
