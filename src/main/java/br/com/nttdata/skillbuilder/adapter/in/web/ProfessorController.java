package br.com.nttdata.skillbuilder.adapter.in.web;

import br.com.nttdata.skillbuilder.application.dto.InformacaoProfessorDTO;
import br.com.nttdata.skillbuilder.application.port.out.CursoRepository;
import br.com.nttdata.skillbuilder.application.port.out.InformacaoProfessorRepository;
import br.com.nttdata.skillbuilder.application.port.out.ProgressoRepository;
import br.com.nttdata.skillbuilder.application.port.out.SessaoRepository;
import br.com.nttdata.skillbuilder.application.port.out.UsuarioRepository;
import br.com.nttdata.skillbuilder.domain.exception.RegraDeNegocioException;
import br.com.nttdata.skillbuilder.domain.model.Curso;
import br.com.nttdata.skillbuilder.domain.model.InformacaoProfessor;
import br.com.nttdata.skillbuilder.domain.model.Usuario;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

    private final UsuarioRepository usuarioRepository;
    private final InformacaoProfessorRepository informacaoProfessorRepository;
    private final CursoRepository cursoRepository;
    private final ProgressoRepository progressoRepository;
    private final SessaoRepository sessaoRepository;

    public ProfessorController(UsuarioRepository usuarioRepository,
                              InformacaoProfessorRepository informacaoProfessorRepository,
                              CursoRepository cursoRepository,
                              ProgressoRepository progressoRepository,
                              SessaoRepository sessaoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.informacaoProfessorRepository = informacaoProfessorRepository;
        this.cursoRepository = cursoRepository;
        this.progressoRepository = progressoRepository;
        this.sessaoRepository = sessaoRepository;
    }

    @GetMapping("/perfil")
    public ResponseEntity<InformacaoProfessorDTO> obterPerfilProfessor(
            @RequestHeader("Authorization") String authorization) {
        UUID professorId = getUsuarioId(authorization);
        
        // Buscar usuário
        Optional<Usuario> professorOpt = usuarioRepository.buscarPorId(professorId);
        if (professorOpt.isEmpty()) {
            throw new RegraDeNegocioException("Professor não encontrado");
        }
        
        Usuario professor = professorOpt.get();
        
        if (!professor.ehProfessor()) {
            throw new RegraDeNegocioException("O usuário não é um professor");
        }
        
        // Buscar informações do professor
        Optional<InformacaoProfessor> infoOpt = informacaoProfessorRepository.buscarPorUsuarioId(professorId);
        if (infoOpt.isEmpty()) {
            throw new RegraDeNegocioException("Informações do professor não encontradas");
        }
        
        InformacaoProfessor info = infoOpt.get();
        
        // Contar cursos do professor
        List<Curso> cursos = cursoRepository.buscarPorProfessorId(professorId);
        int totalCursos = cursos.size();
        
        // Contar alunos dos cursos do professor
        int totalAlunos = 0;
        for (Curso curso : cursos) {
            totalAlunos += progressoRepository.buscarPorCursoId(curso.getId()).size();
        }
        
        // Montar DTO
        InformacaoProfessorDTO dto = new InformacaoProfessorDTO(
                professorId,
                professor.getNome(),
                professor.getEmail(),
                info.getDescricao(),
                info.getMetaPontos(),
                info.getDataCriacao(),
                totalCursos,
                totalAlunos
        );
        
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{professorId}")
    public ResponseEntity<InformacaoProfessorDTO> obterPerfilProfessorPorId(@PathVariable UUID professorId) {
        // Buscar usuário
        Optional<Usuario> professorOpt = usuarioRepository.buscarPorId(professorId);
        if (professorOpt.isEmpty()) {
            throw new RegraDeNegocioException("Professor não encontrado");
        }
        
        Usuario professor = professorOpt.get();
        
        if (!professor.ehProfessor()) {
            throw new RegraDeNegocioException("O usuário não é um professor");
        }
        
        // Buscar informações do professor
        Optional<InformacaoProfessor> infoOpt = informacaoProfessorRepository.buscarPorUsuarioId(professorId);
        if (infoOpt.isEmpty()) {
            throw new RegraDeNegocioException("Informações do professor não encontradas");
        }
        
        InformacaoProfessor info = infoOpt.get();
        
        // Contar cursos do professor
        List<Curso> cursos = cursoRepository.buscarPorProfessorId(professorId);
        int totalCursos = cursos.size();
        
        // Contar alunos dos cursos do professor
        int totalAlunos = 0;
        for (Curso curso : cursos) {
            totalAlunos += progressoRepository.buscarPorCursoId(curso.getId()).size();
        }
        
        // Montar DTO
        InformacaoProfessorDTO dto = new InformacaoProfessorDTO(
                professorId,
                professor.getNome(),
                professor.getEmail(),
                info.getDescricao(),
                info.getMetaPontos(),
                info.getDataCriacao(),
                totalCursos,
                totalAlunos
        );
        
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/perfil")
    public ResponseEntity<InformacaoProfessorDTO> criarPerfilProfessor(
            @Valid @RequestBody InformacaoProfessorDTO dto,
            @RequestHeader("Authorization") String authorization) {
        UUID professorId = getUsuarioId(authorization);
        
        // Verificar se o usuário existe e é professor
        Optional<Usuario> professorOpt = usuarioRepository.buscarPorId(professorId);
        if (professorOpt.isEmpty()) {
            throw new RegraDeNegocioException("Professor não encontrado");
        }
        
        Usuario professor = professorOpt.get();
        
        if (!professor.ehProfessor()) {
            throw new RegraDeNegocioException("O usuário não é um professor");
        }
        
        // Verificar se já existe perfil
        if (informacaoProfessorRepository.buscarPorUsuarioId(professorId).isPresent()) {
            throw new RegraDeNegocioException("Perfil de professor já existe");
        }
        
        // Criar informações do professor
        InformacaoProfessor novaInfo = new InformacaoProfessor(
                professorId,
                dto.getDescricao(),
                dto.getMetaPontos()
        );
        
        InformacaoProfessor infoSalva = informacaoProfessorRepository.salvar(novaInfo);
        
        // Montar DTO de resposta
        InformacaoProfessorDTO responseDTO = new InformacaoProfessorDTO(
                professorId,
                professor.getNome(),
                professor.getEmail(),
                infoSalva.getDescricao(),
                infoSalva.getMetaPontos(),
                infoSalva.getDataCriacao(),
                0, // Inicialmente sem cursos
                0  // Inicialmente sem alunos
        );
        
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/perfil")
    public ResponseEntity<InformacaoProfessorDTO> atualizarPerfilProfessor(
            @Valid @RequestBody InformacaoProfessorDTO dto,
            @RequestHeader("Authorization") String authorization) {
        UUID professorId = getUsuarioId(authorization);
        
        // Verificar se o usuário existe e é professor
        Optional<Usuario> professorOpt = usuarioRepository.buscarPorId(professorId);
        if (professorOpt.isEmpty()) {
            throw new RegraDeNegocioException("Professor não encontrado");
        }
        
        Usuario professor = professorOpt.get();
        
        if (!professor.ehProfessor()) {
            throw new RegraDeNegocioException("O usuário não é um professor");
        }
        
        // Verificar se existe perfil
        Optional<InformacaoProfessor> infoOpt = informacaoProfessorRepository.buscarPorUsuarioId(professorId);
        if (infoOpt.isEmpty()) {
            throw new RegraDeNegocioException("Perfil de professor não encontrado");
        }
        
        InformacaoProfessor info = infoOpt.get();
        
        // Atualizar informações
        //info.setDescricao(dto.getDescricao());
        //info.setMetaPontos(dto.getMetaPontos());
        
        InformacaoProfessor infoAtualizada = informacaoProfessorRepository.salvar(info);
        
        // Contar cursos do professor
        List<Curso> cursos = cursoRepository.buscarPorProfessorId(professorId);
        int totalCursos = cursos.size();
        
        // Contar alunos dos cursos do professor
        int totalAlunos = 0;
        for (Curso curso : cursos) {
            totalAlunos += progressoRepository.buscarPorCursoId(curso.getId()).size();
        }
        
        // Montar DTO de resposta
        InformacaoProfessorDTO responseDTO = new InformacaoProfessorDTO(
                professorId,
                professor.getNome(),
                professor.getEmail(),
                infoAtualizada.getDescricao(),
                infoAtualizada.getMetaPontos(),
                infoAtualizada.getDataCriacao(),
                totalCursos,
                totalAlunos
        );
        
        return ResponseEntity.ok(responseDTO);
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
