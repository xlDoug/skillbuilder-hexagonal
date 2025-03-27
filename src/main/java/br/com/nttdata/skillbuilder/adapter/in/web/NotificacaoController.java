package br.com.nttdata.skillbuilder.adapter.in.web;

import br.com.nttdata.skillbuilder.application.dto.NotificacaoDTO;
import br.com.nttdata.skillbuilder.application.port.in.EnviarNotificacaoUseCase;
import br.com.nttdata.skillbuilder.application.port.out.NotificacaoRepository;
import br.com.nttdata.skillbuilder.application.port.out.SessaoRepository;
import br.com.nttdata.skillbuilder.application.port.out.UsuarioRepository;
import br.com.nttdata.skillbuilder.domain.exception.RegraDeNegocioException;
import br.com.nttdata.skillbuilder.domain.model.Notificacao;
import br.com.nttdata.skillbuilder.domain.model.StatusNotificacao;
import br.com.nttdata.skillbuilder.domain.model.TipoNotificacao;
import br.com.nttdata.skillbuilder.domain.model.Usuario;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notificacoes")
public class NotificacaoController {

    private final EnviarNotificacaoUseCase enviarNotificacaoUseCase;
    private final NotificacaoRepository notificacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final SessaoRepository sessaoRepository;

    public NotificacaoController(EnviarNotificacaoUseCase enviarNotificacaoUseCase,
                               NotificacaoRepository notificacaoRepository,
                               UsuarioRepository usuarioRepository,
                               SessaoRepository sessaoRepository) {
        this.enviarNotificacaoUseCase = enviarNotificacaoUseCase;
        this.notificacaoRepository = notificacaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.sessaoRepository = sessaoRepository;
    }

    @GetMapping
    public ResponseEntity<List<NotificacaoDTO>> listarNotificacoesUsuario(
            @RequestHeader("Authorization") String authorization) {
        UUID usuarioId = getUsuarioId(authorization);
        List<Notificacao> notificacoes = enviarNotificacaoUseCase.listarNotificacoesUsuario(usuarioId);
        
        if (notificacoes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        List<NotificacaoDTO> notificacaoDTOs = notificacoes.stream()
                .map(notificacao -> {
                    // Buscar nome do usuário
                    Optional<Usuario> usuarioOpt = usuarioRepository.buscarPorId(notificacao.getUsuarioId());
                    String nomeUsuario = usuarioOpt.map(Usuario::getNome).orElse("Usuário não encontrado");
                    
                    return new NotificacaoDTO(
                            notificacao.getId(),
                            notificacao.getUsuarioId(),
                            nomeUsuario,
                            notificacao.getMensagem(),
                            notificacao.getTipo().toString(),
                            notificacao.getStatus().toString(),
                            notificacao.getDataEnvio()
                    );
                })
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(notificacaoDTOs);
    }

    @GetMapping("/nao-lidas")
    public ResponseEntity<List<NotificacaoDTO>> listarNotificacoesNaoLidas(
            @RequestHeader("Authorization") String authorization) {
        UUID usuarioId = getUsuarioId(authorization);
        List<Notificacao> notificacoes = notificacaoRepository.buscarPorUsuarioEStatus(
                usuarioId, StatusNotificacao.NAO_LIDA);
        
        if (notificacoes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        List<NotificacaoDTO> notificacaoDTOs = notificacoes.stream()
                .map(notificacao -> {
                    // Buscar nome do usuário
                    Optional<Usuario> usuarioOpt = usuarioRepository.buscarPorId(notificacao.getUsuarioId());
                    String nomeUsuario = usuarioOpt.map(Usuario::getNome).orElse("Usuário não encontrado");
                    
                    return new NotificacaoDTO(
                            notificacao.getId(),
                            notificacao.getUsuarioId(),
                            nomeUsuario,
                            notificacao.getMensagem(),
                            notificacao.getTipo().toString(),
                            notificacao.getStatus().toString(),
                            notificacao.getDataEnvio()
                    );
                })
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(notificacaoDTOs);
    }

    @PostMapping("/{notificacaoId}/ler")
    public ResponseEntity<Void> marcarComoLida(@PathVariable UUID notificacaoId) {
        enviarNotificacaoUseCase.marcarNotificacaoComoLida(notificacaoId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<NotificacaoDTO>> listarNotificacoesPorTipo(@PathVariable String tipo) {
        TipoNotificacao tipoNotificacao;
        try {
            tipoNotificacao = TipoNotificacao.valueOf(tipo.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RegraDeNegocioException("Tipo de notificação inválido");
        }
        
        List<Notificacao> notificacoes = notificacaoRepository.buscarPorTipo(tipoNotificacao);
        
        if (notificacoes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        List<NotificacaoDTO> notificacaoDTOs = notificacoes.stream()
                .map(notificacao -> {
                    // Buscar nome do usuário
                    Optional<Usuario> usuarioOpt = usuarioRepository.buscarPorId(notificacao.getUsuarioId());
                    String nomeUsuario = usuarioOpt.map(Usuario::getNome).orElse("Usuário não encontrado");
                    
                    return new NotificacaoDTO(
                            notificacao.getId(),
                            notificacao.getUsuarioId(),
                            nomeUsuario,
                            notificacao.getMensagem(),
                            notificacao.getTipo().toString(),
                            notificacao.getStatus().toString(),
                            notificacao.getDataEnvio()
                    );
                })
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(notificacaoDTOs);
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
