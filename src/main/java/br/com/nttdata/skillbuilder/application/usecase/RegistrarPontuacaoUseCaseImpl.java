package br.com.nttdata.skillbuilder.application.usecase;

import br.com.nttdata.skillbuilder.application.port.in.GerenciarRankingUseCase;
import br.com.nttdata.skillbuilder.application.port.in.RegistrarPontuacaoUseCase;
import br.com.nttdata.skillbuilder.application.port.out.AtividadeRepository;
import br.com.nttdata.skillbuilder.application.port.out.CursoRepository;
import br.com.nttdata.skillbuilder.application.port.out.PontuacaoRepository;
import br.com.nttdata.skillbuilder.application.port.out.UsuarioRepository;
import br.com.nttdata.skillbuilder.domain.exception.RegraDeNegocioException;
import br.com.nttdata.skillbuilder.domain.model.Atividade;
import br.com.nttdata.skillbuilder.domain.model.Curso;
import br.com.nttdata.skillbuilder.domain.model.OrigemPontuacao;
import br.com.nttdata.skillbuilder.domain.model.Pontuacao;
import br.com.nttdata.skillbuilder.domain.model.Usuario;
import br.com.nttdata.skillbuilder.domain.service.PontuacaoService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class RegistrarPontuacaoUseCaseImpl implements RegistrarPontuacaoUseCase {

    private final PontuacaoRepository pontuacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final AtividadeRepository atividadeRepository;
    private final CursoRepository cursoRepository;
    private final GerenciarRankingUseCase gerenciarRankingUseCase;
    private final PontuacaoService pontuacaoService;

    public RegistrarPontuacaoUseCaseImpl(
            PontuacaoRepository pontuacaoRepository,
            UsuarioRepository usuarioRepository,
            AtividadeRepository atividadeRepository,
            CursoRepository cursoRepository,
            GerenciarRankingUseCase gerenciarRankingUseCase,
            PontuacaoService pontuacaoService) {
        this.pontuacaoRepository = pontuacaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.atividadeRepository = atividadeRepository;
        this.cursoRepository = cursoRepository;
        this.gerenciarRankingUseCase = gerenciarRankingUseCase;
        this.pontuacaoService = pontuacaoService;
    }

    @Override
    @Transactional
    public Pontuacao registrarPontos(UUID usuarioId, int pontos, OrigemPontuacao origem) {
        // Verificar se o usuário existe
        Optional<Usuario> usuarioOpt = usuarioRepository.buscarPorId(usuarioId);
        if (usuarioOpt.isEmpty()) {
            throw new RegraDeNegocioException("Usuário não encontrado");
        }
        
        // Validar pontos
        if (pontos <= 0) {
            throw new RegraDeNegocioException("A pontuação deve ser maior que zero");
        }
        
        // Criar registro de pontuação
        Pontuacao pontuacao = new Pontuacao(usuarioId, pontos, origem);
        Pontuacao pontuacaoSalva = pontuacaoRepository.salvar(pontuacao);
        
        // Atualizar ranking
        gerenciarRankingUseCase.atualizarRanking(usuarioId, pontos);
        
        return pontuacaoSalva;
    }

    @Override
    @Transactional
    public Pontuacao registrarPontosAtividade(UUID usuarioId, UUID atividadeId) {
        // Verificar se o usuário existe
        Optional<Usuario> usuarioOpt = usuarioRepository.buscarPorId(usuarioId);
        if (usuarioOpt.isEmpty()) {
            throw new RegraDeNegocioException("Usuário não encontrado");
        }
        
        Usuario usuario = usuarioOpt.get();
        
        // Verificar se é um aluno
        if (!usuario.ehAluno()) {
            throw new RegraDeNegocioException("Apenas alunos podem receber pontos por atividades");
        }
        
        // Buscar atividade
        Optional<Atividade> atividadeOpt = atividadeRepository.buscarPorId(atividadeId);
        if (atividadeOpt.isEmpty()) {
            throw new RegraDeNegocioException("Atividade não encontrada");
        }
        
        Atividade atividade = atividadeOpt.get();
        
        // Verificar se a atividade está disponível
        if (!atividade.estaDisponivel()) {
            throw new RegraDeNegocioException("Esta atividade ainda não está disponível");
        }
        
        // Registrar pontuação
        Pontuacao pontuacao = pontuacaoService.registrarPontuacaoAtividade(usuarioId, atividade);
        Pontuacao pontuacaoSalva = pontuacaoRepository.salvar(pontuacao);
        
        // Atualizar ranking
        gerenciarRankingUseCase.atualizarRanking(usuarioId, pontuacao.getPontos());
        
        return pontuacaoSalva;
    }

    @Override
    @Transactional
    public Pontuacao registrarPontosCurso(UUID usuarioId, UUID cursoId) {
        // Verificar se o usuário existe
        Optional<Usuario> usuarioOpt = usuarioRepository.buscarPorId(usuarioId);
        if (usuarioOpt.isEmpty()) {
            throw new RegraDeNegocioException("Usuário não encontrado");
        }
        
        Usuario usuario = usuarioOpt.get();
        
        // Verificar se é um aluno
        if (!usuario.ehAluno()) {
            throw new RegraDeNegocioException("Apenas alunos podem receber pontos por conclusão de curso");
        }
        
        // Buscar curso
        Optional<Curso> cursoOpt = cursoRepository.buscarPorId(cursoId);
        if (cursoOpt.isEmpty()) {
            throw new RegraDeNegocioException("Curso não encontrado");
        }
        
        Curso curso = cursoOpt.get();
        
        // Obter pontuação base do aluno neste curso
        int pontuacaoBase = 0; // Idealmente seria calculado com base nas atividades já concluídas
        
        // Registrar pontuação de conclusão do curso
        Pontuacao pontuacao = pontuacaoService.registrarPontuacaoCurso(usuarioId, pontuacaoBase);
        Pontuacao pontuacaoSalva = pontuacaoRepository.salvar(pontuacao);
        
        // Atualizar ranking
        gerenciarRankingUseCase.atualizarRanking(usuarioId, pontuacao.getPontos());
        
        return pontuacaoSalva;
    }

    @Override
    public int consultarPontosTotais(UUID usuarioId) {
        // Verificar se o usuário existe
        if (!usuarioRepository.buscarPorId(usuarioId).isPresent()) {
            throw new RegraDeNegocioException("Usuário não encontrado");
        }
        
        return pontuacaoRepository.calcularTotalPontosPorUsuario(usuarioId);
    }
}