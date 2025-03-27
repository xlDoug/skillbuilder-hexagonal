package br.com.nttdata.skillbuilder.adapter.in.web;

import br.com.nttdata.skillbuilder.application.dto.DashboardDTO;
import br.com.nttdata.skillbuilder.application.port.in.AtualizarProgressoUseCase;
import br.com.nttdata.skillbuilder.application.port.in.GerenciarRankingUseCase;
import br.com.nttdata.skillbuilder.application.port.out.NotificacaoRepository;
import br.com.nttdata.skillbuilder.application.port.out.PontuacaoRepository;
import br.com.nttdata.skillbuilder.application.port.out.ProgressoRepository;
import br.com.nttdata.skillbuilder.application.port.out.SessaoRepository;
import br.com.nttdata.skillbuilder.application.port.out.UsuarioRepository;
import br.com.nttdata.skillbuilder.domain.exception.RegraDeNegocioException;
import br.com.nttdata.skillbuilder.domain.model.Notificacao;
import br.com.nttdata.skillbuilder.domain.model.Progresso;
import br.com.nttdata.skillbuilder.domain.model.StatusNotificacao;
import br.com.nttdata.skillbuilder.domain.model.StatusProgresso;
import br.com.nttdata.skillbuilder.domain.model.Usuario;
import br.com.nttdata.skillbuilder.application.dto.NotificacaoDTO;
import br.com.nttdata.skillbuilder.application.dto.ProgressoDTO;
import br.com.nttdata.skillbuilder.application.dto.RecompensaDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final UsuarioRepository usuarioRepository;
    private final ProgressoRepository progressoRepository;
    private final NotificacaoRepository notificacaoRepository;
    private final PontuacaoRepository pontuacaoRepository;
    private final GerenciarRankingUseCase gerenciarRankingUseCase;
    private final AtualizarProgressoUseCase atualizarProgressoUseCase;
    private final SessaoRepository sessaoRepository;

    public DashboardController(
            UsuarioRepository usuarioRepository,
            ProgressoRepository progressoRepository,
            NotificacaoRepository notificacaoRepository,
            PontuacaoRepository pontuacaoRepository,
            GerenciarRankingUseCase gerenciarRankingUseCase,
            AtualizarProgressoUseCase atualizarProgressoUseCase,
            SessaoRepository sessaoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.progressoRepository = progressoRepository;
        this.notificacaoRepository = notificacaoRepository;
        this.pontuacaoRepository = pontuacaoRepository;
        this.gerenciarRankingUseCase = gerenciarRankingUseCase;
        this.atualizarProgressoUseCase = atualizarProgressoUseCase;
        this.sessaoRepository = sessaoRepository;
    }

    @GetMapping
    public ResponseEntity<DashboardDTO> obterDashboard(@RequestHeader("Authorization") String authorization) {
        UUID usuarioId = getUsuarioId(authorization);
        
        // Buscar informações do usuário
        Optional<Usuario> usuarioOpt = usuarioRepository.buscarPorId(usuarioId);
        if (usuarioOpt.isEmpty()) {
            throw new RegraDeNegocioException("Usuário não encontrado");
        }
        
        Usuario usuario = usuarioOpt.get();
        
        // Buscar pontuação total
        int totalPontos = pontuacaoRepository.calcularTotalPontosPorUsuario(usuarioId);
        
        // Buscar posição no ranking
        int posicaoRanking = gerenciarRankingUseCase.obterPosicaoUsuario(usuarioId);
        
        // Buscar progressos
        List<Progresso> progressos = progressoRepository.buscarPorUsuarioId(usuarioId);
        
        // Calcular cursos concluídos e em andamento
        int cursosConcluidos = (int) progressos.stream()
                .filter(p -> p.getStatus() == StatusProgresso.CONCLUIDO)
                .count();
        
        int cursosEmAndamento = progressos.size() - cursosConcluidos;
        
        // Simplificação: contagem de atividades concluídas
        int atividadesConcluidas = 0;
        for (Progresso progresso : progressos) {
            // Em um sistema real, seria baseado nas atividades concluídas
            if (progresso.getPontosAcumulados() > 0) {
                atividadesConcluidas += 1;
            }
        }
        
        // Obter progresso recente (últimos 5)
        List<ProgressoDTO> progressoRecente = progressos.stream()
                .limit(5)
                .map(this::mapearProgressoParaDTO)
                .collect(Collectors.toList());
        
        // Obter notificações não lidas
        List<Notificacao> notificacoes = notificacaoRepository.buscarPorUsuarioEStatus(
                usuarioId, StatusNotificacao.NAO_LIDA);
        
        List<NotificacaoDTO> notificacoesRecentes = notificacoes.stream()
                .limit(5)
                .map(this::mapearNotificacaoParaDTO)
                .collect(Collectors.toList());
        
        // Lista vazia de recompensas (simplificação)
        List<RecompensaDTO> recompensasDisponiveis = List.of();
        
        // Criar DTO do dashboard
        DashboardDTO dashboard = new DashboardDTO(
                usuarioId,
                usuario.getNome(),
                totalPontos,
                posicaoRanking,
                cursosConcluidos,
                cursosEmAndamento,
                atividadesConcluidas,
                progressoRecente,
                notificacoesRecentes,
                recompensasDisponiveis
        );
        
        return ResponseEntity.ok(dashboard);
    }
    
    private ProgressoDTO mapearProgressoParaDTO(Progresso progresso) {
        // Buscar nome do curso
        String cursoNome = "Curso"; // Simplificação - em produção, buscar pelo cursoRepository
        
        // Calcular percentual de conclusão
        double percentualConclusao = atualizarProgressoUseCase.calcularPercentualProgresso(
                progresso.getUsuarioId(), progresso.getCursoId());
        
        return new ProgressoDTO(
                progresso.getId(),
                progresso.getUsuarioId(),
                null, // Nome do usuário - simplificação
                progresso.getCursoId(),
                cursoNome,
                progresso.getStatus().toString(),
                progresso.getDataInicio(),
                progresso.getDataConclusao(),
                progresso.getPontosAcumulados(),
                percentualConclusao
        );
    }
    
    private NotificacaoDTO mapearNotificacaoParaDTO(Notificacao notificacao) {
        return new NotificacaoDTO(
                notificacao.getId(),
                notificacao.getUsuarioId(),
                null, // Nome do usuário - simplificação
                notificacao.getMensagem(),
                notificacao.getTipo().toString(),
                notificacao.getStatus().toString(),
                notificacao.getDataEnvio()
        );
    }
    
    private UUID getUsuarioId(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            return sessaoRepository.obterUsuarioIdPorToken(token)
                    .orElseThrow(() -> new RegraDeNegocioException("Token inválido ou expirado"));
        }
        throw new RegraDeNegocioException("Token não encontrado no cabeçalho");
    }
}
