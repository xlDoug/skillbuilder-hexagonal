package br.com.nttdata.skillbuilder.adapter.out.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.nttdata.skillbuilder.adapter.out.persistence.entity.RecompensaEntity;

@Repository
public interface RecompensaJpaRepository extends JpaRepository<RecompensaEntity, String> {
    
    @Query("SELECT r FROM RecompensaEntity r WHERE r.quantidadeDisponivel > 0")
    List<RecompensaEntity> findDisponiveis();
}
