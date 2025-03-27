package br.com.nttdata.skillbuilder.adapter.in.web;

import br.com.nttdata.skillbuilder.application.dto.CursoDTO;
import br.com.nttdata.skillbuilder.application.port.in.CriarCursoUseCase;
import br.com.nttdata.skillbuilder.application.port.in.ListarCursosUseCase;
import br.com.nttdata.skillbuilder.application.port.out.SessaoRepository;
import br.com.nttdata.skillbuilder.domain.exception.RegraDeNegocioException;
import br.com.nttdata.skillbuilder.domain.model.Curso;

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

@RestController
@RequestMapping("/cursos")
public class CursoController {

    private final CriarCursoUseCase criarCursoUseCase;
    private final ListarCursosUseCase listarCursosUseCase;
    private final SessaoRepository sessaoRepository;

    public CursoController(CriarCursoUseCase criarCursoUseCase, 
                          ListarCursosUseCase listarCursosUseCase,
                          SessaoRepository sessaoRepository) {
        this.criarCursoUseCase = criarCursoUseCase;
        this.listarCursosUseCase = listarCursosUseCase;
        this.sessaoRepository = sessaoRepository;
    }

    @PostMapping
    public ResponseEntity<CursoDTO> criarCurso(@Valid @RequestBody CursoDTO cursoDTO,
                                             @RequestHeader("Authorization") String authorization) {
        UUID professorId = getUsuarioId(authorization);
        Curso curso = criarCursoUseCase.executar(cursoDTO, professorId);
        
        // Converter o domínio para DTO
        CursoDTO responseDTO = new CursoDTO(
                curso.getId(),
                curso.getNome(),
                curso.getDescricao(),
                curso.getProfessorId(),
                null, // Nome do professor (seria necessário buscar)
                curso.getMetaPontos(),
                curso.getDataCriacao(),
                0, // Total de atividades
                0, // Total de alunos
                0.0 // Percentual de conclusão média
        );
        
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<CursoDTO>> listarTodos() {
        List<CursoDTO> cursos = listarCursosUseCase.listarTodos();
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/professor")
    public ResponseEntity<List<CursoDTO>> listarPorProfessor(@RequestHeader("Authorization") String authorization) {
        UUID professorId = getUsuarioId(authorization);
        List<CursoDTO> cursos = listarCursosUseCase.listarPorProfessor(professorId);
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/aluno")
    public ResponseEntity<List<CursoDTO>> listarPorAluno(@RequestHeader("Authorization") String authorization) {
        UUID alunoId = getUsuarioId(authorization);
        List<CursoDTO> cursos = listarCursosUseCase.listarPorAluno(alunoId);
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/{professorId}")
    public ResponseEntity<List<CursoDTO>> listarPorProfessorId(@PathVariable UUID professorId) {
        List<CursoDTO> cursos = listarCursosUseCase.listarPorProfessor(professorId);
        return ResponseEntity.ok(cursos);
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
