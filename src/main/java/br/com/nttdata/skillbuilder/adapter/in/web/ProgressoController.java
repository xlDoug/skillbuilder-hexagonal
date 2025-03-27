package br.com.nttdata.skillbuilder.adapter.in.web;

import br.com.nttdata.skillbuilder.application.dto.ProgressoDTO;
import br.com.nttdata.skillbuilder.application.port.in.AtualizarProgressoUseCase;
import br.com.nttdata.skillbuilder.application.port.in.InscreverAlunoUseCase;
import br.com.nttdata.skillbuilder.application.port.out.ProgressoRepository;
import br.com.nttdata.skillbuilder.application.port.out.SessaoRepository;
import br.com.nttdata.skillbuilder.domain.exception.RegraDeNegocioException;
import br.com.nttdata.skillbuilder.domain.model.Progresso;

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
@RequestMapping("/progressos")
public class ProgressoController {

    private final InscreverAlunoUseCase inscreverAlunoUseCase;
    private final AtualizarProgressoUseCase atualizarProgressoUseCase;
    private final ProgressoRepository progressoRepository;
    private final SessaoRepository sessaoRepository;

    public ProgressoController(InscreverAlunoUseCase inscreverAlunoUseCase,
                             AtualizarProgressoUseCase atualizarProgressoUseCase,
                             ProgressoRepository progressoRepository,
                             SessaoRepository sessaoRepository) {
        this.inscreverAlunoUseCase = inscreverAlunoUseCase;
        this.atualizarProgressoUseCase = atualizarProgressoUseCase;
        this.progressoRepository = progressoRepository;
        this.sessaoRepository = sessaoRepository;
    }

    @PostMapping("/cursos/{cursoId}/inscrever")
    public ResponseEntity<ProgressoDTO> inscreverEmCurso(@PathVariable UUID cursoId,
                                                      @RequestHeader("Authorization") String authorization) {
        UUID alunoId = getUsuarioId(authorization);
        Progresso progresso = inscreverAlunoUseCase.executar(alunoId, cursoId);
        
        ProgressoDTO progressoDTO = criarProgressoDTO(progresso);
        return ResponseEntity.status(HttpStatus.CREATED).body(progressoDTO);
    }

    @GetMapping("/cursos/{cursoId}")
    public ResponseEntity<ProgressoDTO> consultarProgressoCurso(@PathVariable UUID cursoId,
                                                             @RequestHeader("Authorization") String authorization) {
        UUID alunoId = getUsuarioId(authorization);
        Optional<Progresso> progressoOpt = progressoRepository.buscarPorUsuarioECurso(alunoId, cursoId);
        
        if (progressoOpt.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        Progresso progresso = progressoOpt.get();
        ProgressoDTO progressoDTO = criarProgressoDTO(progresso);
        
        // Calcular percentual de conclusão
        double percentual = atualizarProgressoUseCase.calcularPercentualProgresso(alunoId, cursoId);
        progressoDTO.setPercentualConclusao(percentual);
        
        return ResponseEntity.ok(progressoDTO);
    }

    @GetMapping
    public ResponseEntity<List<ProgressoDTO>> listarProgressosAluno(@RequestHeader("Authorization") String authorization) {
        UUID alunoId = getUsuarioId(authorization);
        List<Progresso> progressos = progressoRepository.buscarPorUsuarioId(alunoId);
        
        if (progressos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        List<ProgressoDTO> progressoDTOs = progressos.stream()
                .map(this::criarProgressoDTO)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(progressoDTOs);
    }

    @PostMapping("/cursos/{cursoId}/verificar-conclusao")
    public ResponseEntity<ProgressoDTO> verificarConclusaoCurso(@PathVariable UUID cursoId,
                                                             @RequestHeader("Authorization") String authorization) {
        UUID alunoId = getUsuarioId(authorization);
        Progresso progresso = atualizarProgressoUseCase.verificarConclusaoCurso(alunoId, cursoId);
        ProgressoDTO progressoDTO = criarProgressoDTO(progresso);
        return ResponseEntity.ok(progressoDTO);
    }

    private ProgressoDTO criarProgressoDTO(Progresso progresso) {
        return new ProgressoDTO(
                progresso.getId(),
                progresso.getUsuarioId(),
                null, // Nome do usuário (seria necessário buscar)
                progresso.getCursoId(),
                null, // Nome do curso (seria necessário buscar)
                progresso.getStatus().toString(),
                progresso.getDataInicio(),
                progresso.getDataConclusao(),
                progresso.getPontosAcumulados(),
                0.0 // Percentual de conclusão (calculado depois se necessário)
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
