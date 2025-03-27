package br.com.nttdata.skillbuilder.adapter.in.web;

import br.com.nttdata.skillbuilder.application.dto.AtividadeDTO;
import br.com.nttdata.skillbuilder.application.port.in.AtualizarProgressoUseCase;
import br.com.nttdata.skillbuilder.application.port.in.CriarAtividadeUseCase;
import br.com.nttdata.skillbuilder.application.port.out.AtividadeRepository;
import br.com.nttdata.skillbuilder.application.port.out.SessaoRepository;
import br.com.nttdata.skillbuilder.domain.exception.RegraDeNegocioException;
import br.com.nttdata.skillbuilder.domain.model.Atividade;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/atividades")
public class AtividadeController {

    private final CriarAtividadeUseCase criarAtividadeUseCase;
    private final AtualizarProgressoUseCase atualizarProgressoUseCase;
    private final AtividadeRepository atividadeRepository;
    private final SessaoRepository sessaoRepository;

    public AtividadeController(CriarAtividadeUseCase criarAtividadeUseCase,
                             AtualizarProgressoUseCase atualizarProgressoUseCase,
                             AtividadeRepository atividadeRepository,
                             SessaoRepository sessaoRepository) {
        this.criarAtividadeUseCase = criarAtividadeUseCase;
        this.atualizarProgressoUseCase = atualizarProgressoUseCase;
        this.atividadeRepository = atividadeRepository;
        this.sessaoRepository = sessaoRepository;
    }

    @PostMapping("/cursos/{cursoId}")
    public ResponseEntity<AtividadeDTO> criarAtividade(@PathVariable UUID cursoId,
                                                     @Valid @RequestBody AtividadeDTO atividadeDTO,
                                                     @RequestHeader("Authorization") String authorization) {
        // Verificar autenticação
        getUsuarioId(authorization);
        
        Atividade atividade = criarAtividadeUseCase.executar(atividadeDTO, cursoId);
        
        // Converter domínio para DTO
        AtividadeDTO responseDTO = new AtividadeDTO(
                atividade.getId(),
                atividade.getCursoId(),
                null, // Nome do curso (seria necessário buscar)
                atividade.getTipoAtividade().toString(),
                atividade.getDescricao(),
                atividade.getPontuacao(),
                atividade.getDataDisponibilidade(),
                false
        );
        
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/cursos/{cursoId}")
    public ResponseEntity<List<AtividadeDTO>> listarAtividadesPorCurso(@PathVariable UUID cursoId) {
        List<Atividade> atividades = atividadeRepository.buscarPorCursoId(cursoId);
        
        List<AtividadeDTO> atividadesDTO = atividades.stream()
                .map(atividade -> new AtividadeDTO(
                        atividade.getId(),
                        atividade.getCursoId(),
                        null, // Nome do curso (seria necessário buscar)
                        atividade.getTipoAtividade().toString(),
                        atividade.getDescricao(),
                        atividade.getPontuacao(),
                        atividade.getDataDisponibilidade(),
                        false
                ))
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(atividadesDTO);
    }

    @PostMapping("/{atividadeId}/concluir/cursos/{cursoId}")
    public ResponseEntity<Void> concluirAtividade(@PathVariable UUID atividadeId,
                                                @PathVariable UUID cursoId,
                                                @RequestHeader("Authorization") String authorization) {
        UUID alunoId = getUsuarioId(authorization);
        atualizarProgressoUseCase.registrarAtividadeConcluida(alunoId, cursoId, atividadeId);
        return ResponseEntity.ok().build();
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
