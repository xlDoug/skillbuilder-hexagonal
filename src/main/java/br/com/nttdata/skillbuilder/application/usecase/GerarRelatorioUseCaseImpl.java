package br.com.nttdata.skillbuilder.application.usecase;

import br.com.nttdata.skillbuilder.application.dto.RelatorioDesempenhoDTO;
import br.com.nttdata.skillbuilder.application.dto.RelatorioCursoDTO;
import br.com.nttdata.skillbuilder.application.port.in.GerarRelatorioUseCase;
import br.com.nttdata.skillbuilder.application.port.out.RelatorioRepository;
import br.com.nttdata.skillbuilder.application.port.out.CursoRepository;
import br.com.nttdata.skillbuilder.application.port.out.UsuarioRepository;
import br.com.nttdata.skillbuilder.domain.exception.RegraDeNegocioException;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GerarRelatorioUseCaseImpl implements GerarRelatorioUseCase {

    private final RelatorioRepository relatorioRepository;
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;

    public GerarRelatorioUseCaseImpl(RelatorioRepository relatorioRepository,
                               UsuarioRepository usuarioRepository,
                               CursoRepository cursoRepository) {
        this.relatorioRepository = relatorioRepository;
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository = cursoRepository;
    }

    @Override
    public RelatorioDesempenhoDTO gerarRelatorioDesempenhoAluno(UUID alunoId) {
        // Verificar se o aluno existe
        if (!usuarioRepository.buscarPorId(alunoId).isPresent()) {
            throw new RegraDeNegocioException("Aluno não encontrado");
        }
        
        // Gerar relatório usando o repositório específico
        return relatorioRepository.gerarRelatorioDesempenhoAluno(alunoId);
    }

    @Override
    public RelatorioCursoDTO gerarRelatorioCurso(UUID cursoId) {
        // Verificar se o curso existe
        if (!cursoRepository.buscarPorId(cursoId).isPresent()) {
            throw new RegraDeNegocioException("Curso não encontrado");
        }
        
        // Gerar relatório usando o repositório específico
        return relatorioRepository.gerarRelatorioCurso(cursoId);
    }
}