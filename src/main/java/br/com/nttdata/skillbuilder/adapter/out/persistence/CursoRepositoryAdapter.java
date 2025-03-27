package br.com.nttdata.skillbuilder.adapter.out.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.nttdata.skillbuilder.adapter.out.persistence.entity.CursoEntity;
import br.com.nttdata.skillbuilder.adapter.out.persistence.repository.CursoJpaRepository;
import br.com.nttdata.skillbuilder.application.port.out.CursoRepository;
import br.com.nttdata.skillbuilder.domain.model.Curso;

@Component
public class CursoRepositoryAdapter implements CursoRepository {

    private final CursoJpaRepository cursoJpaRepository;

    public CursoRepositoryAdapter(CursoJpaRepository cursoJpaRepository) {
        this.cursoJpaRepository = cursoJpaRepository;
    }

    @Override
    public Curso salvar(Curso curso) {
        CursoEntity entity = new CursoEntity(curso);
        CursoEntity savedEntity = cursoJpaRepository.save(entity);
        return savedEntity.toDomainModel();
    }

    @Override
    public Optional<Curso> buscarPorId(UUID id) {
        return cursoJpaRepository.findById(id.toString())
                .map(CursoEntity::toDomainModel);
    }

    @Override
    public List<Curso> buscarTodos() {
        return cursoJpaRepository.findAll().stream()
                .map(CursoEntity::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Curso> buscarPorProfessorId(UUID professorId) {
        return cursoJpaRepository.findByProfessorId(professorId.toString()).stream()
                .map(CursoEntity::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public void deletar(UUID id) {
        cursoJpaRepository.deleteById(id.toString());
    }
}
