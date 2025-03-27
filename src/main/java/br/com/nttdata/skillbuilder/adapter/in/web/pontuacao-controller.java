package br.com.nttdata.skillbuilder.adapter.in.web;

import br.com.nttdata.skillbuilder.application.dto.PontuacaoDTO;
import br.com.nttdata.skillbuilder.application.port.in.RegistrarPontuacaoUseCase;
import br.com.nttdata.skillbuilder.application.port.out.PontuacaoRepository;
import br.com.nttdata.skillbuilder.application.port.out.SessaoRepository;
import br.com.nttdata.skillbuilder.application.port.out.UsuarioRepository;
import br.com.nttdata.skillbuilder.domain.exception.RegraDeNegocioException;
import br.com.nttdata.skillbuilder.domain.model.OrigemPontuacao;
import br.com.nttdata.skillbuilder.domain.model.Pontuacao;
import br.com.nttdata.skillbuilder.domain.model.Usuario;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pontuacoes")
public class PontuacaoController {

    private final PontuacaoRepository pontuacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final RegistrarPontuacaoUseCase registrarPontuacaoUseCase;
    private final SessaoRepository sessaoRepository;

    public PontuacaoController(PontuacaoRepository pontuacaoRepository,
                             UsuarioRepository usuarioRepository,
                             RegistrarPontuacaoUseCase registrarPontuacaoUseCase,
                             SessaoRepository sessaoRepository) {
        this.pontuacaoRepository = pontuacaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.registrarPontuacaoUseCase = registrarPontuacaoUseCase;
        this.sessaoRepository = sessaoRepository;
    }

    @GetMapping
    public ResponseEntity<List<PontuacaoDTO>> listarPontuacoesUsuario(
            @RequestHeader("Authorization") String authorization) {
        UUID usuarioId = getUsuarioId(authorization);
        
        List<Pontuacao> pontuacoes = pontuacaoRepository.buscarPorUsuarioId(usuarioId);
        
        if (pontuacoes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        List<PontuacaoDTO> pontuacaoDTOs = pontuacoes.stream()
                .map(pontuacao -> {
                    Optional<Usuario> usuarioOpt = usuarioRepository.buscarPorId(pontuacao.getUsuarioId());
                    String nomeUsuario = usuarioOpt.map(Usuario::getNome).orElse("Usuário não encontrado");
                    
                    return new PontuacaoDTO(
                            pontuacao.getId(),
                            pontuacao.getUsuarioId(),
                            nomeUsuario,
                            pontuacao.getPontos(),
                            pontuacao.getOrigemPontuacao().toString(),
                            pontuacao.getDataRegistro()
                    );
                })
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(pontuacaoDTOs);
    }

    @GetMapping("/total")
    public ResponseEntity<Integer> obterPontuacaoTotal(
            @RequestHeader("Authorization") String authorization) {
        UUID usuarioId = getUsuarioId(authorization);
        
        int totalPontos = registrarPontuacaoUseCase.consultarPontosTotais(usuarioId);
        
        return ResponseEntity.ok(totalPontos);
    }

    @GetMapping("/usuarios/{usuarioId}")
    public ResponseEntity<List<PontuacaoDTO>> listarPontuacoesPorUsuarioId(
            @PathVariable UUID usuarioId,
            @RequestHeader("Authorization") String authorization) {
        // Verificação de permissão (simplificação - em sistema real verificaria se é admin ou professor)
        getUsuarioId(authorization);
        
        // Verificar se o usuário existe
        if (!usuarioRepository.buscarPorId(usuarioId).isPresent()) {
            throw new RegraDeNegocioException("Usuário não encontrado");
        }
        
        List<Pontuacao> pontuacoes = pontuacaoRepository.buscarPorUsuarioId(usuarioId);
        
        if (pontuacoes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        List<PontuacaoDTO> pontuacaoDTOs = pontuacoes.stream()
                .map(pontuacao -> {
                    Optional<Usuario> usuarioOpt = usuarioRepository.buscarPorId(pontuacao.getUsuarioId());
                    String nomeUsuario = usuarioOpt.map(Usuario::getNome).orElse("Usuário não encontrado");
                    
                    return new PontuacaoDTO(
                            pontuacao.getId(),
                            pontuacao.getUsuarioId(),
                            nomeUsuario,
                            pontuacao.getPontos(),
                            pontuacao.getOrigemPontuacao().toString(),
                            pontuacao.getDataRegistro()
                    );
                })
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(pontuacaoDTOs);
    }

    @GetMapping("/usuarios/{usuarioId}/total")
    public ResponseEntity<Integer> obterPontuacaoTotalPorUsuarioId(
            @PathVariable UUID usuarioId,
            @RequestHeader("Authorization") String authorization) {
        // Verificação de permissão (simplificação - em sistema real verificaria se é admin ou professor)
        getUsuarioId(authorization);
        
        // Verificar se o usuário existe
        if (!usuarioRepository.buscarPorId(usuarioId).isPresent()) {
            throw new RegraDeNegocioException("Usuário não encontrado");
        }
        
        int totalPontos = registrarPontuacaoUseCase.consultarPontosTotais(usuarioId);
        
        return ResponseEntity.ok(totalPontos);
    }

    @GetMapping("/origem")
    public ResponseEntity<List<PontuacaoDTO>> listarPontuacoesPorOrigem(
            @RequestParam String origem,
            @RequestHeader("Authorization") String authorization) {
        // Verificação de permissão (simplificação - em sistema real verificaria se é admin)
        getUsuarioId(authorization);
        
        OrigemPontuacao origemPontuacao;
        try {
            origemPontuacao = OrigemPontuacao.valueOf(origem.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RegraDeNegocioException("Origem de pontuação inválida");
        }
        
        List<Pontuacao> pontuacoes = pontuacaoRepository.buscarPorOrigem(origemPontuacao);
        
        if (pontuacoes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        List<PontuacaoDTO> pontuacaoDTOs = pontuacoes.stream()
                .map(pontuacao -> {
                    Optional<Usuario> usuarioOpt = usuarioRepository.buscarPorId(pontuacao.getUsuarioId());
                    String nomeUsuario = usuarioOpt.map(Usuario::getNome).orElse("Usuário não encontrado");
                    
                    return new PontuacaoDTO(
                            pontuacao.getId(),
                            pontuacao.getUsuarioId(),
                            nomeUsuario,
                            pontuacao.getPontos(),
                            pontuacao.getOrigemPontuacao().toString(),
                            pontuacao.getDataRegistro()
                    );
                })
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(pontuacaoDTOs);
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
