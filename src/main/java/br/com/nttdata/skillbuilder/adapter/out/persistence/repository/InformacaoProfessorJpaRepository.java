package br.com.nttdata.skillbuilder.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nttdata.skillbuilder.adapter.out.persistence.entity.InformacaoProfessorEntity;

@Repository
public interface InformacaoProfessorJpaRepository extends JpaRepository<InformacaoProfessorEntity, String> {
}
