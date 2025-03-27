package br.com.nttdata.skillbuilder.adapter.out.persistence.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nttdata.skillbuilder.adapter.out.persistence.entity.AtividadeEntity;

@Repository
public interface AtividadeJpaRepository extends JpaRepository<AtividadeEntity, String> {
    List<AtividadeEntity> findByCursoId(String cursoId);
    List<AtividadeEntity> findByCursoIdAndDataDisponibilidadeLessThanEqual(String cursoId, LocalDate data);
}
