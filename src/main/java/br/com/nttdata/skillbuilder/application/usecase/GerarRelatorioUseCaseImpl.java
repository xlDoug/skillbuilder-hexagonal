package br.com.nttdata.skillbuilder.application.usecase;

import br.com.nttdata.skillbuilder.application.dto.AtividadeDTO;
import br.com.nttdata.skillbuilder.application.dto.PontuacaoDTO;
import br.com.nttdata.skillbuilder.application.dto.ProgressoDTO;
import br.com.nttdata.skillbuilder.application.dto.RelatorioDesempenhoDTO;
import br.com.nttdata.skillbuilder.application.dto.RelatorioCursoDTO;
import br.com.nttdata.skillbuilder.application.port.in.GerarRelatorioUseCase;
import br.com.nttdata.skillbuilder.application.port.out.AtividadeRepository;
import br.com.nttdata.skillbuilder.application.port.out.CursoRepository;
import br.com.nttdata.skillbuilder.application.port.out.PontuacaoRepository;
import br.com.nttdata.skillbuilder.application.port.out.ProgressoRepository;
import br.com.nttdata.skillbuilder.application.port.out.RankingRepository;
import br.com.nttdata.skillbuilder.application.port.out.UsuarioRepository;
import br.com.nttdata.skillbuilder.domain.exception.RegraDeNegocioException;
import br.com.nttdata.skillbuilder.domain.model.Atividade;
import br.com.nttdata.skillbuilder.domain.model.Curso;
import br.com.nttdata.skillbuilder.domain.model.Pontuacao;
import br.com.nttdata.skillbuilder.domain.model.Progresso;
import br.com.nttdata.skillbuilder.domain.model.StatusProgresso;
import br.com.nttdata.skillbuilder.domain.model.Usuario;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GerarRelatorioUseCaseImpl implements GerarRelatorioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;
    private final AtividadeRepository atividadeRepository;
    private final ProgressoRepository progressoRepository;
    private final PontuacaoRepository pontuacaoRepository;
    private final RankingRepository rankingRepository;

    public GerarRelatorioUseCaseImpl(
            UsuarioRepository usuarioRepository,
            CursoRepository cursoRepository,
            AtividadeRepository atividadeRepository,
            ProgressoRepository progressoRepository,
            PontuacaoRepository pontuacaoRepository,
            RankingRepository rankingRepository) {
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository = cursoRepository;
        this.atividadeRepository = atividadeRepository;
        this.progressoRepository = progressoRepository;
        this.pontuacaoRepository = pontuacaoRepository;
        this.rankingRepository = rankingRepository;
    }

    @Override
    public RelatorioDesempenhoDTO gerarRelatorioDesempenhoAluno(UUID alunoId) {
        // Verificar se o aluno existe
        Optional<Usuario> alunoOpt = usuarioRepository.buscarPorId(alunoId);
        if (alunoOpt.isEmpty()) {
            throw new RegraDeNegocioException("Aluno não encontrado");
        }

        Usuario aluno = alunoOpt.get();
        if (!aluno.ehAluno()) {
            throw new RegraDeNegocioException("O usuário não é um aluno");
        }

        // Buscar progressos do aluno
        List<Progresso> progressos = progressoRepository.buscarPorUsuarioId(alunoId);

        // Calcular informações do relatório
        int totalCursosInscritos = progressos.size();
        int totalCursosConcluidos = (int) progressos.stream()
                .filter(Progresso::estaConcluido)
                .count();

        // Obter pontuações do aluno
        List<Pontuacao> pontuacoes = pontuacaoRepository.buscarPorUsuarioId(alunoId);
        int pontuacaoTotal = pontuacaoRepository.calcularTotalPontosPorUsuario(alunoId);

        // Obter posição no ranking
        int posicaoRanking = rankingRepository.buscarPosicaoUsuario(alunoId);

        // Contar atividades concluídas (simplificação - em um sistema real, usaríamos AtividadeConcluida)
        int totalAtividadesConcluidas = pontuacoes.size();

        // Converter progressos para DTOs
        List<ProgressoDTO> progressoDTOs = mapearProgressos(progressos);

        // Converter pontuações para DTOs
        List<PontuacaoDTO> pontuacaoDTOs = mapearPontuacoes(pontuacoes);

        // Criar o relatório de desempenho
        return new RelatorioDesempenhoDTO(
                alunoId,
                aluno.getNome(),
                totalCursosInscritos,
                totalCursosConcluidos,
                totalAtividadesConcluidas,
                pontuacaoTotal,
                posicaoRanking,
                progressoDTOs,
                pontuacaoDTOs
        );
    }

    @Override
    public RelatorioCursoDTO gerarRelatorioCurso(UUID cursoId) {
        // Verificar se o curso existe
        Optional<Curso> cursoOpt = cursoRepository.buscarPorId(cursoId);
        if (cursoOpt.isEmpty()) {
            throw new RegraDeNegocioException("Curso não encontrado");
        }

        Curso curso = cursoOpt.get();

        // Buscar professor do curso
        Optional<Usuario> professorOpt = usuarioRepository.buscarPorId(curso.getProfessorId());
        String professorNome = professorOpt.map(Usuario::getNome).orElse("Professor não encontrado");

        // Buscar progressos de alunos no curso
        List<Progresso> progressos = progressoRepository.buscarPorCursoId(cursoId);

        // Calcular dados para o relatório
        int totalAlunos = progressos.size();
        int totalAlunosConcluintes = (int) progressos.stream()
                .filter(p -> p.getStatus() == StatusProgresso.CONCLUIDO)
                .count();

        double taxaConclusao = totalAlunos > 0 ?
                (double) totalAlunosConcluintes / totalAlunos * 100 : 0;

        // Buscar atividades do curso
        List<Atividade> atividades = atividadeRepository.buscarPorCursoId(cursoId);
        int totalAtividades = atividades.size();

        // Calcular médias
        double mediaAtividadesPorAluno = calcularMediaAtividadesPorAluno(progressos, cursoId);
        double mediaPontuacaoPorAluno = calcularMediaPontuacaoPorAluno(progressos);

        // Converter atividades para DTOs
        List<AtividadeDTO> atividadeDTOs = mapearAtividades(atividades);

        // Criar o relatório de curso
        return new RelatorioCursoDTO(
                cursoId,
                curso.getNome(),
                curso.getDescricao(),
                curso.getProfessorId(),
                professorNome,
                totalAlunos,
                totalAlunosConcluintes,
                taxaConclusao,
                totalAtividades,
                mediaAtividadesPorAluno,
                mediaPontuacaoPorAluno,
                atividadeDTOs
        );
    }

    // Métodos auxiliares para mapeamento e cálculos

    private List<ProgressoDTO> mapearProgressos(List<Progresso> progressos) {
        List<ProgressoDTO> progressoDTOs = new ArrayList<>();

        for (Progresso progresso : progressos) {
            // Buscar informações do curso
            Optional<Curso> cursoOpt = cursoRepository.buscarPorId(progresso.getCursoId());
            if (cursoOpt.isEmpty()) continue;

            Curso curso = cursoOpt.get();

            // Buscar informações do aluno
            Optional<Usuario> alunoOpt = usuarioRepository.buscarPorId(progresso.getUsuarioId());
            if (alunoOpt.isEmpty()) continue;

            Usuario aluno = alunoOpt.get();

            // Calcular percentual de conclusão
            double percentualConclusao = calcularPercentualConclusao(progresso);

            ProgressoDTO dto = new ProgressoDTO(
                    progresso.getId(),
                    progresso.getUsuarioId(),
                    aluno.getNome(),
                    progresso.getCursoId(),
                    curso.getNome(),
                    progresso.getStatus().toString(),
                    progresso.getDataInicio(),
                    progresso.getDataConclusao(),
                    progresso.getPontosAcumulados(),
                    percentualConclusao
            );

            progressoDTOs.add(dto);
        }

        return progressoDTOs;
    }

    private List<PontuacaoDTO> mapearPontuacoes(List<Pontuacao> pontuacoes) {
        List<PontuacaoDTO> pontuacaoDTOs = new ArrayList<>();

        for (Pontuacao pontuacao : pontuacoes) {
            // Buscar informações do usuário
            Optional<Usuario> usuarioOpt = usuarioRepository.buscarPorId(pontuacao.getUsuarioId());
            if (usuarioOpt.isEmpty()) continue;

            Usuario usuario = usuarioOpt.get();

            PontuacaoDTO dto = new PontuacaoDTO(
                    pontuacao.getId(),
                    pontuacao.getUsuarioId(),
                    usuario.getNome(),
                    pontuacao.getPontos(),
                    pontuacao.getOrigemPontuacao().toString(),
                    pontuacao.getDataRegistro()
            );

            pontuacaoDTOs.add(dto);
        }

        return pontuacaoDTOs;
    }

    private List<AtividadeDTO> mapearAtividades(List<Atividade> atividades) {
        return atividades.stream().map(atividade -> {
            UUID cursoId = atividade.getCursoId();
            Optional<Curso> cursoOpt = cursoRepository.buscarPorId(cursoId);
            String cursoNome = cursoOpt.map(Curso::getNome).orElse("Curso não encontrado");

            return new AtividadeDTO(
                    atividade.getId(),
                    atividade.getCursoId(),
                    cursoNome,
                    atividade.getTipoAtividade().toString(),
                    atividade.getDescricao(),
                    atividade.getPontuacao(),
                    atividade.getDataDisponibilidade(),
                    false
            );
        }).collect(Collectors.toList());
    }

    private double calcularPercentualConclusao(Progresso progresso) {
        // Simplificação - em um sistema real, seria calculado baseado nas atividades concluídas
        return progresso.estaConcluido() ? 100.0 : 0.0;
    }

    private double calcularMediaAtividadesPorAluno(List<Progresso> progressos, UUID cursoId) {
        // Simplificação - em um sistema real, seria calculado baseado nas atividades concluídas por aluno
        return 0.0;
    }

    private double calcularMediaPontuacaoPorAluno(List<Progresso> progressos) {
        if (progressos.isEmpty()) return 0.0;

        int totalPontos = progressos.stream()
                .mapToInt(Progresso::getPontosAcumulados)
                .sum();

        return (double) totalPontos / progressos.size();
    }
}