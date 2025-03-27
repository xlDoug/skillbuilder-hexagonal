package br.com.nttdata.skillbuilder.application.usecase;

import br.com.nttdata.skillbuilder.application.dto.RankingDTO;
import br.com.nttdata.skillbuilder.application.port.in.GerenciarRankingUseCase;
import br.com.nttdata.skillbuilder.application.port.out.CursoRepository;
import br.com.nttdata.skillbuilder.application.port.out.PontuacaoRepository;
import br.com.nttdata.skillbuilder.application.port.out.ProgressoRepository;
import br.com.nttdata.skillbuilder.application.port.out.RankingRepository;
import br.com.nttdata.skillbuilder.application.port.out.UsuarioRepository;
import br.com.nttdata.skillbuilder.domain.exception.RegraDeNegocioException;
import br.com.nttdata.skillbuilder.domain.model.Curso;
import br.com.nttdata.skillbuilder.domain.model.Progresso;
import br.com.nttdata.skillbuilder.domain.model.Ranking;
import br.com.nttdata.skillbuilder.domain.model.StatusProgresso;
import br.com.nttdata.skillbuilder.domain.model.Usuario;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GerenciarRankingUseCaseImpl implements GerenciarRankingUseCase {

    private final RankingRepository rankingRepository;
    private final UsuarioRepository usuarioRepository;
    private final PontuacaoRepository pontuacaoRepository;
    private final CursoRepository cursoRepository;
    private final ProgressoRepository progressoRepository;

    public GerenciarRankingUseCaseImpl(
            RankingRepository rankingRepository,
            UsuarioRepository usuarioRepository,
            PontuacaoRepository pontuacaoRepository,
            CursoRepository cursoRepository,
            ProgressoRepository progressoRepository) {
        this.rankingRepository = rankingRepository;
        this.usuarioRepository = usuarioRepository;
        this.pontuacaoRepository = pontuacaoRepository;
        this.cursoRepository = cursoRepository;
        this.progressoRepository = progressoRepository;
    }

    @Override
    @Transactional
    public void atualizarRanking(UUID usuarioId, int novosPontos) {
        // Verificar se o usuário existe
        Optional<Usuario> usuarioOpt = usuarioRepository.buscarPorId(usuarioId);
        if (usuarioOpt.isEmpty()) {
            throw new RegraDeNegocioException("Usuário não encontrado");
        }

        Usuario usuario = usuarioOpt.get();

        // Buscar ranking atual do usuário
        Optional<Ranking> rankingExistenteOpt = rankingRepository.buscarPorUsuarioId(usuarioId);

        if (rankingExistenteOpt.isPresent()) {
            // Atualizar pontuação do usuário no ranking
            rankingRepository.atualizarPontuacao(usuarioId, novosPontos);
        } else {
            // Criar nova entrada no ranking
            int totalPontos = pontuacaoRepository.calcularTotalPontosPorUsuario(usuarioId);
            int posicaoInicial = rankingRepository.buscarTopRanking(Integer.MAX_VALUE).size() + 1;

            Ranking novoRanking = new Ranking(usuarioId, posicaoInicial, totalPontos);
            rankingRepository.salvar(novoRanking);
        }

        // Recalcular posições no ranking
        rankingRepository.recalcularRanking();
    }

    @Override
    public List<RankingDTO> listarRankingGlobal(int limite) {
        // Buscar os top N usuários do ranking
        List<Ranking> topRanking = rankingRepository.buscarTopRanking(limite);

        // Converter para DTOs
        return converterParaDTO(topRanking);
    }

    @Override
    public List<RankingDTO> listarRankingPorCurso(UUID cursoId, int limite) {
        // Verificar se o curso existe
        Optional<Curso> cursoOpt = cursoRepository.buscarPorId(cursoId);
        if (cursoOpt.isEmpty()) {
            throw new RegraDeNegocioException("Curso não encontrado");
        }

        // Buscar todos os progressos deste curso
        List<Progresso> progressos = progressoRepository.buscarPorCursoId(cursoId);

        // Extrair os IDs dos usuários com progresso neste curso
        List<UUID> usuariosNoCursoIds = progressos.stream()
                .map(Progresso::getUsuarioId)
                .collect(Collectors.toList());

        // Calcular pontuação por usuário neste curso específico
        Map<UUID, Integer> pontuacaoPorUsuario = new HashMap<>();

        for (UUID usuarioId : usuariosNoCursoIds) {
            Optional<Progresso> progressoUsuarioOpt = progressos.stream()
                    .filter(p -> p.getUsuarioId().equals(usuarioId))
                    .findFirst();

            if (progressoUsuarioOpt.isPresent()) {
                Progresso progressoUsuario = progressoUsuarioOpt.get();
                pontuacaoPorUsuario.put(usuarioId, progressoUsuario.getPontosAcumulados());
            }
        }

        // Ordenar usuários por pontuação neste curso
        List<UUID> usuariosOrdenados = pontuacaoPorUsuario.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(limite)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // Criar lista de RankingDTO específico do curso
        List<RankingDTO> rankingCurso = new ArrayList<>();

        int posicao = 1;
        for (UUID usuarioId : usuariosOrdenados) {
            Optional<Usuario> usuarioOpt = usuarioRepository.buscarPorId(usuarioId);
            if (usuarioOpt.isEmpty()) continue;

            Usuario usuario = usuarioOpt.get();
            int pontos = pontuacaoPorUsuario.getOrDefault(usuarioId, 0);

            RankingDTO dto = new RankingDTO(
                    posicao,
                    usuarioId,
                    usuario.getNome(),
                    pontos,
                    LocalDate.now()
            );

            rankingCurso.add(dto);
            posicao++;
        }

        return rankingCurso;
    }

    @Override
    public int obterPosicaoUsuario(UUID usuarioId) {
        // Verificar se o usuário existe
        Optional<Usuario> usuarioOpt = usuarioRepository.buscarPorId(usuarioId);
        if (usuarioOpt.isEmpty()) {
            throw new RegraDeNegocioException("Usuário não encontrado");
        }

        return rankingRepository.buscarPosicaoUsuario(usuarioId);
    }

    // Método auxiliar para converter entidades de Ranking para DTOs
    private List<RankingDTO> converterParaDTO(List<Ranking> rankings) {
        if (rankings == null || rankings.isEmpty()) {
            return new ArrayList<>();
        }

        return rankings.stream()
                .map(ranking -> {
                    // Buscar nome do usuário
                    Optional<Usuario> usuarioOpt = usuarioRepository.buscarPorId(ranking.getUsuarioId());
                    String nomeUsuario = usuarioOpt.map(Usuario::getNome).orElse("Usuário não encontrado");

                    return new RankingDTO(
                            ranking.getPosicaoGlobal(),
                            ranking.getUsuarioId(),
                            nomeUsuario,
                            ranking.getTotalPontos(),
                            ranking.getDataAtualizacao()
                    );
                })
                .collect(Collectors.toList());
    }
}