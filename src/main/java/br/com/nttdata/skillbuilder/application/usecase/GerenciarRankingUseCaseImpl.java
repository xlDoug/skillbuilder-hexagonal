package br.com.nttdata.skillbuilder.application.usecase;

import br.com.nttdata.skillbuilder.application.dto.RankingDTO;
import br.com.nttdata.skillbuilder.application.port.in.GerenciarRankingUseCase;
import br.com.nttdata.skillbuilder.application.port.out.RankingRepository;
import br.com.nttdata.skillbuilder.application.port.out.UsuarioRepository;
import br.com.nttdata.skillbuilder.domain.exception.RegraDeNegocioException;
import br.com.nttdata.skillbuilder.domain.model.Ranking;
import br.com.nttdata.skillbuilder.domain.model.Usuario;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GerenciarRankingUseCaseImpl implements GerenciarRankingUseCase {

    private final RankingRepository rankingRepository;
    private final UsuarioRepository usuarioRepository;

    public GerenciarRankingUseCaseImpl(RankingRepository rankingRepository,
                                 UsuarioRepository usuarioRepository) {
        this.rankingRepository = rankingRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional
    public void atualizarRanking(UUID usuarioId, int novosPontos) {
        // Verificar se o usuário existe
        if (!usuarioRepository.buscarPorId(usuarioId).isPresent()) {
            throw new RegraDeNegocioException("Usuário não encontrado");
        }
        
        // Atualizar pontuação do usuário no ranking
        rankingRepository.atualizarPontuacao(usuarioId, novosPontos);
        
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
        // Esta implementação requer mais informações e possivelmente repositórios adicionais
        // para filtrar o ranking por curso
        // Como simplificação, retornamos o ranking global
        return listarRankingGlobal(limite);
    }

    @Override
    public int obterPosicaoUsuario(UUID usuarioId) {
        // Verificar se o usuário existe
        if (!usuarioRepository.buscarPorId(usuarioId).isPresent()) {
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