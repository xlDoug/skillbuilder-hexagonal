package br.com.nttdata.skillbuilder.adapter.in.web;

import br.com.nttdata.skillbuilder.application.dto.RecompensaDTO;
import br.com.nttdata.skillbuilder.application.dto.ResgateDTO;
import br.com.nttdata.skillbuilder.application.port.in.ResgatarRecompensaUseCase;
import br.com.nttdata.skillbuilder.application.port.out.RecompensaRepository;
import br.com.nttdata.skillbuilder.application.port.out.SessaoRepository;
import br.com.nttdata.skillbuilder.application.port.out.UsuarioRepository;
import br.com.nttdata.skillbuilder.domain.exception.RegraDeNegocioException;
import br.com.nttdata.skillbuilder.domain.model.Recompensa;
import br.com.nttdata.skillbuilder.domain.model.Resgate;
import br.com.nttdata.skillbuilder.domain.model.Usuario;

import org.springframework.http.HttpStatus;
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
@RequestMapping("/recompensas")
public class RecompensaController {

    private final ResgatarRecompensaUseCase resgatarRecompensaUseCase;
    private final RecompensaRepository recompensaRepository;
    private final UsuarioRepository usuarioRepository;
    private final SessaoRepository sessaoRepository;

    public RecompensaController(ResgatarRecompensaUseCase resgatarRecompensaUseCase,
                              RecompensaRepository recompensaRepository,
                              UsuarioRepository usuarioRepository,
                              SessaoRepository sessaoRepository) {
        this.resgatarRecompensaUseCase = resgatarRecompensaUseCase;
        this.recompensaRepository = recompensaRepository;
        this.usuarioRepository = usuarioRepository;
        this.sessaoRepository = sessaoRepository;
    }

    @GetMapping
    public ResponseEntity<List<RecompensaDTO>> listarRecompensas() {
        List<Recompensa> recompensas = recompensaRepository.buscarTodas();
        
        if (recompensas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        List<RecompensaDTO> recompensaDTOs = recompensas.stream()
                .map(recompensa -> new RecompensaDTO(
                        recompensa.getId(),
                        recompensa.getNome(),
                        recompensa.getDescricao(),
                        recompensa.getCustoPontos(),
                        recompensa.getQuantidadeDisponivel(),
                        recompensa.getDataCriacao(),
                        recompensa.getQuantidadeDisponivel() > 0
                ))
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(recompensaDTOs);
    }

    @GetMapping("/disponiveis")
    public ResponseEntity<List<RecompensaDTO>> listarRecompensasDisponiveis() {
        List<Recompensa> recompensas = recompensaRepository.buscarDisponiveis();
        
        if (recompensas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        List<RecompensaDTO> recompensaDTOs = recompensas.stream()
                .map(recompensa -> new RecompensaDTO(
                        recompensa.getId(),
                        recompensa.getNome(),
                        recompensa.getDescricao(),
                        recompensa.getCustoPontos(),
                        recompensa.getQuantidadeDisponivel(),
                        recompensa.getDataCriacao(),
                        true
                ))
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(recompensaDTOs);
    }

    @GetMapping("/usuario")
    public ResponseEntity<List<RecompensaDTO>> listarRecompensasDisponiveisParaUsuario(
            @RequestHeader("Authorization") String authorization) {
        UUID usuarioId = getUsuarioId(authorization);
        List<Recompensa> recompensas = resgatarRecompensaUseCase.listarRecompensasDisponiveis(usuarioId);
        
        if (recompensas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        List<RecompensaDTO> recompensaDTOs = recompensas.stream()
                .map(recompensa -> new RecompensaDTO(
                        recompensa.getId(),
                        recompensa.getNome(),
                        recompensa.getDescricao(),
                        recompensa.getCustoPontos(),
                        recompensa.getQuantidadeDisponivel(),
                        recompensa.getDataCriacao(),
                        true
                ))
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(recompensaDTOs);
    }

    @PostMapping("/{recompensaId}/resgatar")
    public ResponseEntity<ResgateDTO> resgatarRecompensa(
            @PathVariable UUID recompensaId,
            @RequestHeader("Authorization") String authorization) {
        UUID usuarioId = getUsuarioId(authorization);
        Resgate resgate = resgatarRecompensaUseCase.executar(usuarioId, recompensaId);
        
        // Buscar nome do usuário
        Optional<Usuario> usuarioOpt = usuarioRepository.buscarPorId(resgate.getUsuarioId());
        String nomeUsuario = usuarioOpt.map(Usuario::getNome).orElse("Usuário não encontrado");
        
        // Buscar nome da recompensa
        Optional<Recompensa> recompensaOpt = recompensaRepository.buscarPorId(resgate.getRecompensaId());
        String nomeRecompensa = recompensaOpt.map(Recompensa::getNome).orElse("Recompensa não encontrada");
        
        ResgateDTO resgateDTO = new ResgateDTO(
                resgate.getId(),
                resgate.getUsuarioId(),
                nomeUsuario,
                resgate.getRecompensaId(),
                nomeRecompensa,
                resgate.getDataResgate()
        );
        
        return ResponseEntity.status(HttpStatus.CREATED).body(resgateDTO);
    }

    @GetMapping("/resgates")
    public ResponseEntity<List<ResgateDTO>> listarResgatesUsuario(
            @RequestHeader("Authorization") String authorization) {
        UUID usuarioId = getUsuarioId(authorization);
        List<Resgate> resgates = resgatarRecompensaUseCase.consultarResgatesUsuario(usuarioId);
        
        if (resgates.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        List<ResgateDTO> resgateDTOs = resgates.stream()
                .map(resgate -> {
                    // Buscar nome do usuário
                    Optional<Usuario> usuarioOpt = usuarioRepository.buscarPorId(resgate.getUsuarioId());
                    String nomeUsuario = usuarioOpt.map(Usuario::getNome).orElse("Usuário não encontrado");
                    
                    // Buscar nome da recompensa
                    Optional<Recompensa> recompensaOpt = recompensaRepository.buscarPorId(resgate.getRecompensaId());
                    String nomeRecompensa = recompensaOpt.map(Recompensa::getNome).orElse("Recompensa não encontrada");
                    
                    return new ResgateDTO(
                            resgate.getId(),
                            resgate.getUsuarioId(),
                            nomeUsuario,
                            resgate.getRecompensaId(),
                            nomeRecompensa,
                            resgate.getDataResgate()
                    );
                })
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(resgateDTOs);
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
