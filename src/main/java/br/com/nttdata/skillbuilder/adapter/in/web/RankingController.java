package br.com.nttdata.skillbuilder.adapter.in.web;

import br.com.nttdata.skillbuilder.application.dto.RankingDTO;
import br.com.nttdata.skillbuilder.application.port.in.GerenciarRankingUseCase;
import br.com.nttdata.skillbuilder.application.port.out.SessaoRepository;
import br.com.nttdata.skillbuilder.domain.exception.RegraDeNegocioException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rankings")
public class RankingController {

    private final GerenciarRankingUseCase gerenciarRankingUseCase;
    private final SessaoRepository sessaoRepository;

    public RankingController(GerenciarRankingUseCase gerenciarRankingUseCase,
                          SessaoRepository sessaoRepository) {
        this.gerenciarRankingUseCase = gerenciarRankingUseCase;
        this.sessaoRepository = sessaoRepository;
    }

    @GetMapping("/global")
    public ResponseEntity<List<RankingDTO>> listarRankingGlobal(
            @RequestParam(defaultValue = "10") int limite) {
        List<RankingDTO> ranking = gerenciarRankingUseCase.listarRankingGlobal(limite);
        
        if (ranking.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.ok(ranking);
    }

    @GetMapping("/cursos/{cursoId}")
    public ResponseEntity<List<RankingDTO>> listarRankingPorCurso(
            @PathVariable UUID cursoId,
            @RequestParam(defaultValue = "10") int limite) {
        List<RankingDTO> ranking = gerenciarRankingUseCase.listarRankingPorCurso(cursoId, limite);
        
        if (ranking.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.ok(ranking);
    }

    @GetMapping("/posicao")
    public ResponseEntity<Integer> obterPosicaoUsuario(@RequestHeader("Authorization") String authorization) {
        UUID usuarioId = getUsuarioId(authorization);
        int posicao = gerenciarRankingUseCase.obterPosicaoUsuario(usuarioId);
        return ResponseEntity.ok(posicao);
    }

    @GetMapping("/usuarios/{usuarioId}/posicao")
    public ResponseEntity<Integer> obterPosicaoUsuarioPorId(@PathVariable UUID usuarioId) {
        int posicao = gerenciarRankingUseCase.obterPosicaoUsuario(usuarioId);
        return ResponseEntity.ok(posicao);
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
