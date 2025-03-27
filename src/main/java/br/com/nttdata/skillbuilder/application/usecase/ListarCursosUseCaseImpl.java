package br.com.nttdata.skillbuilder.application.usecase;

import br.com.nttdata.skillbuilder.application.dto.CursoDTO;
import br.com.nttdata.skillbuilder.application.port.in.ListarCursosUseCase;
import br.com.nttdata.skillbuilder.application.port.out.CursoRepository;
import br.com.nttdata.skillbuilder.application.port.out.ProgressoRepository;
import br.com.nttdata.skillbuilder.application.port.out.UsuarioRepository;
import br.com.nttdata.skillbuilder.domain.exception.RegraDeNegocioException;
import br.com.nttdata.skillbuilder.domain.model.Curso;
import br.com.nttdata.skillbuilder.domain.model.Progresso;
import br.com.nttdata.skillbuilder.domain.model.Usuario;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ListarCursosUseCaseImpl implements ListarCursosUseCase {

    private final CursoRepository cursoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProgressoRepository progressoRepository;

    public ListarCursosUseCaseImpl(CursoRepository cursoRepository,
                             UsuarioRepository usuarioRepository,
                             ProgressoRepository progressoRepository) {
        this.cursoRepository = cursoRepository;
        this.usuarioRepository = usuarioRepository;
        this.progressoRepository = progressoRepository;
    }

    @Override
    public List<CursoDTO> listarTodos() {
        List<Curso> cursos = cursoRepository.buscarTodos();
        return converterParaDTO(cursos);
    }

    @Override
    public List<CursoDTO> listarPorProfessor(UUID professorId) {
        // Verificar se o professor existe
        Optional<Usuario> professorOpt = usuarioRepository.buscarPorId(professorId);
        if (professorOpt.isEmpty()) {
            throw new RegraDeNegocioException("Professor não encontrado");
        }
        
        Usuario professor = professorOpt.get();
        
        if (!professor.ehProfessor()) {
            throw new RegraDeNegocioException("Usuário não é um professor");
        }
        
        List<Curso> cursos = cursoRepository.buscarPorProfessorId(professorId);
        return converterParaDTO(cursos);
    }

    @Override
    public List<CursoDTO> listarPorAluno(UUID alunoId) {
        // Verificar se o aluno existe
        Optional<Usuario> alunoOpt = usuarioRepository.buscarPorId(alunoId);
        if (alunoOpt.isEmpty()) {
            throw new RegraDeNegocioException("Aluno não encontrado");
        }
        
        Usuario aluno = alunoOpt.get();
        
        if (!aluno.ehAluno()) {
            throw new RegraDeNegocioException("Usuário não é um aluno");
        }
        
        // Buscar progressos do aluno
        List<Progresso> progressos = progressoRepository.buscarPorUsuarioId(alunoId);
        
        // Extrair IDs dos cursos
        List<UUID> cursosIds = progressos.stream()
            .map(Progresso::getCursoId)
            .collect(Collectors.toList());
        
        // Buscar detalhes dos cursos
        List<Curso> cursos = new ArrayList<>();
        for (UUID cursoId : cursosIds) {
            Optional<Curso> cursoOpt = cursoRepository.buscarPorId(cursoId);
            cursoOpt.ifPresent(cursos::add);
        }
        
        return converterParaDTO(cursos);
    }
    
    // Método auxiliar para converter entidades de Curso para DTOs
    private List<CursoDTO> converterParaDTO(List<Curso> cursos) {
        if (cursos == null || cursos.isEmpty()) {
            return new ArrayList<>();
        }
        
        return cursos.stream()
            .map(curso -> {
                // Buscar nome do professor
                Optional<Usuario> professorOpt = usuarioRepository.buscarPorId(curso.getProfessorId());
                String professorNome = professorOpt.map(Usuario::getNome).orElse("Professor não encontrado");
                
                // Contar total de alunos e atividades (lógica simplificada)
                int totalAlunos = 0; // Substituir por lógica real
                int totalAtividades = 0; // Substituir por lógica real
                double percentualConclusaoMedia = 0.0; // Substituir por lógica real
                
                return new CursoDTO(
                    curso.getId(),
                    curso.getNome(),
                    curso.getDescricao(),
                    curso.getProfessorId(),
                    professorNome,
                    curso.getMetaPontos(),
                    curso.getDataCriacao(),
                    totalAtividades,
                    totalAlunos,
                    percentualConclusaoMedia
                );
            })
            .collect(Collectors.toList());
    }
}