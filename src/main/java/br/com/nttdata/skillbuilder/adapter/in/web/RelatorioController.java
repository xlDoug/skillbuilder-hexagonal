package br.com.nttdata.skillbuilder.adapter.in.web;

import br.com.nttdata.skillbuilder.application.dto.RelatorioDesempenhoDTO;
import br.com.nttdata.skillbuilder.application.dto.RelatorioCursoDTO;
import br.com.nttdata.skillbuilder.application.port.in.GerarRelatorioUseCase;
import br.com.nttdata.skillbuilder.application.port.out.SessaoRepository;
import br.com.nttdata.skillbuilder.domain.exception.RegraDeNegocioException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    private final GerarRelatorioUseCase gerarRelatorioUseCase;
    private final SessaoRepository sessaoRepository;

    public RelatorioController(GerarRelatorioUseCase gerarRelatorioUseCase,
                             SessaoRepository sessaoRepository) {
        this.gerarRelatorioUseCase = gerarRelatorioUseCase;
        this.sessaoRepository = sessaoRepository;
    }

    @GetMapping("/alunos/desempenho")
    public ResponseEntity<RelatorioDesempenhoDTO> gerarRelatorioDesempenhoAluno(
            @RequestHeader("Authorization") String authorization) {
        UUID alunoId = getUsuarioId(authorization);
        RelatorioDesempenhoDTO relatorio = gerarRelatorioUseCase.gerarRelatorioDesempenhoAluno(alunoId);
        return ResponseEntity.ok(relatorio);
    }

    @GetMapping("/alunos/{alunoId}/desempenho")
    public ResponseEntity<RelatorioDesempenhoDTO> gerarRelatorioDesempenhoAlunoById(
            @PathVariable UUID alunoId,
            @RequestHeader("Authorization") String authorization) {
        // Aqui poderia haver uma verificação de permissão (somente admin ou professor)
        RelatorioDesempenhoDTO relatorio = gerarRelatorioUseCase.gerarRelatorioDesempenhoAluno(alunoId);
        return ResponseEntity.ok(relatorio);
    }

    @GetMapping("/cursos/{cursoId}")
    public ResponseEntity<RelatorioCursoDTO> gerarRelatorioCurso(
            @PathVariable UUID cursoId,
            @RequestHeader("Authorization") String authorization) {
        // Aqui poderia haver uma verificação de permissão (somente admin ou professor responsável)
        RelatorioCursoDTO relatorio = gerarRelatorioUseCase.gerarRelatorioCurso(cursoId);
        return ResponseEntity.ok(relatorio);
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
