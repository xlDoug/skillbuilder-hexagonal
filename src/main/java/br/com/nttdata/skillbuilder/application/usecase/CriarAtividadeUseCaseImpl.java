package br.com.nttdata.skillbuilder.application.usecase;

import br.com.nttdata.skillbuilder.application.dto.AtividadeDTO;
import br.com.nttdata.skillbuilder.application.port.in.CriarAtividadeUseCase;
import br.com.nttdata.skillbuilder.application.port.out.AtividadeRepository;
import br.com.nttdata.skillbuilder.application.port.out.CursoRepository;
import br.com.nttdata.skillbuilder.application.port.out.UsuarioRepository;
import br.com.nttdata.skillbuilder.domain.exception.RegraDeNegocioException;
import br.com.nttdata.skillbuilder.domain.model.Atividade;
import br.com.nttdata.skillbuilder.domain.model.Curso;
import br.com.nttdata.skillbuilder.domain.model.TipoAtividade;
import br.com.nttdata.skillbuilder.domain.model.Usuario;
import br.com.nttdata.skillbuilder.domain.service.ValidacaoDominioService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class CriarAtividadeUseCaseImpl implements CriarAtividadeUseCase {

    private final AtividadeRepository atividadeRepository;
    private final CursoRepository cursoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ValidacaoDominioService validacaoService;

    public CriarAtividadeUseCaseImpl(AtividadeRepository atividadeRepository,
                               CursoRepository cursoRepository,
                               UsuarioRepository usuarioRepository,
                               ValidacaoDominioService validacaoService) {
        this.atividadeRepository = atividadeRepository;
        this.cursoRepository = cursoRepository;
        this.usuarioRepository = usuarioRepository;
        this.validacaoService = validacaoService;
    }

    @Override
    @Transactional
    public Atividade executar(AtividadeDTO atividadeDTO, UUID cursoId) {
        // Verificar se o curso existe
        Optional<Curso> cursoOpt = cursoRepository.buscarPorId(cursoId);
        if (cursoOpt.isEmpty()) {
            throw new RegraDeNegocioException("Curso não encontrado");
        }
        
        Curso curso = cursoOpt.get();
        
        // Verificar se quem está criando a atividade é o professor do curso ou um admin
        Optional<Usuario> professorOpt = usuarioRepository.buscarPorId(curso.getProfessorId());
        if (professorOpt.isEmpty()) {
            throw new RegraDeNegocioException("Professor do curso não encontrado");
        }
        
        Usuario professor = professorOpt.get();
        
        // Validar permissão para criar atividades
        validacaoService.validarCriacaoAtividade(professor);
        
        // Validar pontuação
        if (atividadeDTO.getPontuacao() <= 0) {
            throw new RegraDeNegocioException("Pontuação deve ser maior que zero");
        }
        
        // Converter tipo de atividade
        TipoAtividade tipoAtividade;
        try {
            tipoAtividade = TipoAtividade.valueOf(atividadeDTO.getTipoAtividade().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RegraDeNegocioException("Tipo de atividade inválido");
        }
        
        // Criar atividade
        Atividade novaAtividade;
        if (atividadeDTO.getDataDisponibilidade() != null) {
            novaAtividade = new Atividade(
                cursoId,
                tipoAtividade,
                atividadeDTO.getDescricao(),
                atividadeDTO.getPontuacao(),
                atividadeDTO.getDataDisponibilidade()
            );
        } else {
            // Se não informou data, utiliza a atual
            novaAtividade = new Atividade(
                cursoId,
                tipoAtividade,
                atividadeDTO.getDescricao(),
                atividadeDTO.getPontuacao()
            );
        }
        
        return atividadeRepository.salvar(novaAtividade);
    }
}