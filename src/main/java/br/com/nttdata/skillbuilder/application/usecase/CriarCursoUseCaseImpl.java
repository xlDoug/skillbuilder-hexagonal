package br.com.nttdata.skillbuilder.application.usecase;

import br.com.nttdata.skillbuilder.application.dto.CursoDTO;
import br.com.nttdata.skillbuilder.application.port.in.CriarCursoUseCase;
import br.com.nttdata.skillbuilder.application.port.out.CursoRepository;
import br.com.nttdata.skillbuilder.application.port.out.UsuarioRepository;
import br.com.nttdata.skillbuilder.domain.exception.RegraDeNegocioException;
import br.com.nttdata.skillbuilder.domain.model.Curso;
import br.com.nttdata.skillbuilder.domain.model.Usuario;
import br.com.nttdata.skillbuilder.domain.service.ValidacaoDominioService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class CriarCursoUseCaseImpl implements CriarCursoUseCase {

    private final CursoRepository cursoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ValidacaoDominioService validacaoService;

    public CriarCursoUseCaseImpl(CursoRepository cursoRepository,
                                 UsuarioRepository usuarioRepository,
                                 ValidacaoDominioService validacaoService) {
        this.cursoRepository = cursoRepository;
        this.usuarioRepository = usuarioRepository;
        this.validacaoService = validacaoService;
    }

    @Override
    @Transactional
    public Curso executar(CursoDTO cursoDTO, UUID professorId) {
        // Verificar se o professor existe
        Optional<Usuario> professorOpt = usuarioRepository.buscarPorId(professorId);

        if (professorOpt.isEmpty()) {
            throw new RegraDeNegocioException("Professor não encontrado");
        }

        Usuario professor = professorOpt.get();

        // Validar se o usuário pode criar cursos
        validacaoService.validarCriacaoCurso(professor);

        // Validar meta de pontos
        if (cursoDTO.getMetaPontos() <= 0) {
            throw new RegraDeNegocioException("Meta de pontos deve ser maior que zero");
        }

        // Criar curso
        Curso novoCurso = new Curso(
                cursoDTO.getNome(),
                cursoDTO.getDescricao(),
                professorId,
                cursoDTO.getMetaPontos()
        );

        return cursoRepository.salvar(novoCurso);
    }
}