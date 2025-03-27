package br.com.nttdata.skillbuilder.application.usecase;

import br.com.nttdata.skillbuilder.application.port.in.InscreverAlunoUseCase;
import br.com.nttdata.skillbuilder.application.port.out.CursoRepository;
import br.com.nttdata.skillbuilder.application.port.out.ProgressoRepository;
import br.com.nttdata.skillbuilder.application.port.out.UsuarioRepository;
import br.com.nttdata.skillbuilder.domain.exception.RegraDeNegocioException;
import br.com.nttdata.skillbuilder.domain.model.Curso;
import br.com.nttdata.skillbuilder.domain.model.Progresso;
import br.com.nttdata.skillbuilder.domain.model.Usuario;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class InscreverAlunoUseCaseImpl implements InscreverAlunoUseCase {

    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;
    private final ProgressoRepository progressoRepository;

    public InscreverAlunoUseCaseImpl(UsuarioRepository usuarioRepository,
                               CursoRepository cursoRepository,
                               ProgressoRepository progressoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository = cursoRepository;
        this.progressoRepository = progressoRepository;
    }

    @Override
    @Transactional
    public Progresso executar(UUID alunoId, UUID cursoId) {
        // Verificar se o aluno existe
        Optional<Usuario> alunoOpt = usuarioRepository.buscarPorId(alunoId);
        if (alunoOpt.isEmpty()) {
            throw new RegraDeNegocioException("Aluno não encontrado");
        }
        
        Usuario aluno = alunoOpt.get();
        
        // Verificar se o usuário é realmente um aluno
        if (!aluno.ehAluno()) {
            throw new RegraDeNegocioException("Apenas alunos podem se inscrever em cursos");
        }
        
        // Verificar se o curso existe
        Optional<Curso> cursoOpt = cursoRepository.buscarPorId(cursoId);
        if (cursoOpt.isEmpty()) {
            throw new RegraDeNegocioException("Curso não encontrado");
        }
        
        // Verificar se o aluno já está inscrito no curso
        Optional<Progresso> progressoExistente = progressoRepository.buscarPorUsuarioECurso(alunoId, cursoId);
        if (progressoExistente.isPresent()) {
            throw new RegraDeNegocioException("Aluno já está inscrito neste curso");
        }
        
        // Criar progresso para o aluno
        Progresso novoProgresso = new Progresso(alunoId, cursoId);
        
        return progressoRepository.salvar(novoProgresso);
    }
}