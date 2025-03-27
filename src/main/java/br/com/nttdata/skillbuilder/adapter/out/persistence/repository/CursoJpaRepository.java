package br.com.nttdata.skillbuilder.adapter.out.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nttdata.skillbuilder.adapter.out.persistence.entity.CursoEntity;

@Repository
public interface CursoJpaRepository extends JpaRepository<CursoEntity, String> {
    List<CursoEntity> findByProfessorId(String professorId);
}
