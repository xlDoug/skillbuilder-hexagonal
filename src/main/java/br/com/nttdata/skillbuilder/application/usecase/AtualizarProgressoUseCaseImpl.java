package br.com.nttdata.skillbuilder.application.usecase;

import br.com.nttdata.skillbuilder.application.port.in.AtualizarProgressoUseCase;
import br.com.nttdata.skillbuilder.application.port.out.AtividadeConcluidaRepository;
import br.com.nttdata.skillbuilder.application.port.out.AtividadeRepository;
import br.com.nttdata.skillbuilder.application.port.out.ProgressoRepository;
import br.com.nttdata.skillbuilder.domain.exception.RegraDeNegocioException;
import br.com.nttdata.skillbuilder.domain.model.Atividade;
import br.com.nttdata.skillbuilder.domain.model.Progresso;
import br.com.nttdata.skillbuilder.domain.service.ProgressoService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AtualizarProgressoUseCaseImpl implements AtualizarProgressoUseCase {

    private final ProgressoRepository progressoRepository;
    private final AtividadeRepository atividadeRepository;
    private final AtividadeConcluidaRepository atividadeConcluidaRepository;
    private final ProgressoService progressoService;

    public AtualizarProgressoUseCaseImpl(ProgressoRepository progressoRepository,
                                   AtividadeRepository atividadeRepository,
                                   AtividadeConcluidaRepository atividadeConcluidaRepository,
                                   ProgressoService progressoService) {
        this.progressoRepository = progressoRepository;
        this.atividadeRepository = atividadeRepository;
        this.atividadeConcluidaRepository = atividadeConcluidaRepository;
        this.progressoService = progressoService;
    }

    @Override
    @Transactional
    public Progresso registrarAtividadeConcluida(UUID alunoId, UUID cursoId, UUID atividadeId) {
        // Verificar se o progresso existe
        Optional<Progresso> progressoOpt = progressoRepository.buscarPorUsuarioECurso(alunoId, cursoId);
        if (progressoOpt.isEmpty()) {
            throw new RegraDeNegocioException("Aluno não está inscrito neste curso");
        }
        
        Progresso progresso = progressoOpt.get();
        
        // Verificar se o curso já foi concluído
        if (progresso.estaConcluido()) {
            throw new RegraDeNegocioException("Curso já foi concluído");
        }
        
        // Verificar se a atividade existe e pertence ao curso
        Optional<Atividade> atividadeOpt = atividadeRepository.buscarPorId(atividadeId);
        if (atividadeOpt.isEmpty()) {
            throw new RegraDeNegocioException("Atividade não encontrada");
        }
        
        Atividade atividade = atividadeOpt.get();
        
        if (!atividade.getCursoId().equals(cursoId)) {
            throw new RegraDeNegocioException("Atividade não pertence a este curso");
        }
        
        // Verificar se a atividade já foi concluída
        if (atividadeConcluidaRepository.verificarConclusao(alunoId, atividadeId)) {
            throw new RegraDeNegocioException("Atividade já foi concluída");
        }
        
        // Registrar conclusão da atividade
        atividadeConcluidaRepository.registrarConclusao(alunoId, cursoId, atividadeId);
        
        // Atualizar progresso
        progressoService.registrarAtividadeConcluida(progresso, atividade);
        progresso = progressoRepository.salvar(progresso);
        
        // Verificar se o curso foi concluído
        verificarConclusaoCurso(alunoId, cursoId);
        
        return progresso;
    }

    @Override
    @Transactional
    public Progresso verificarConclusaoCurso(UUID alunoId, UUID cursoId) {
        Optional<Progresso> progressoOpt = progressoRepository.buscarPorUsuarioECurso(alunoId, cursoId);
        if (progressoOpt.isEmpty()) {
            throw new RegraDeNegocioException("Aluno não está inscrito neste curso");
        }
        
        Progresso progresso = progressoOpt.get();
        
        if (progresso.estaConcluido()) {
            return progresso; // Já está concluído
        }
        
        // Obter todas as atividades do curso
        List<Atividade> todasAtividades = atividadeRepository.buscarPorCursoId(cursoId);
        
        // Obter atividades concluídas
        List<UUID> atividadesConcluidasIds = atividadeConcluidaRepository.buscarAtividadesConcluidasPorUsuarioECurso(alunoId, cursoId);
        
        // Se todas as atividades foram concluídas, marcar curso como concluído
        if (todasAtividades.size() > 0 && atividadesConcluidasIds.size() >= todasAtividades.size()) {
            progresso.concluir();
            progresso = progressoRepository.salvar(progresso);
        }
        
        return progresso;
    }

    @Override
    public double calcularPercentualProgresso(UUID alunoId, UUID cursoId) {
        // Obter total de atividades do curso
        List<Atividade> todasAtividades = atividadeRepository.buscarPorCursoId(cursoId);
        
        if (todasAtividades.isEmpty()) {
            return 0.0; // Sem atividades, 0% de progresso
        }
        
        // Obter total de atividades concluídas
        int atividadesConcluidas = atividadeConcluidaRepository.contarConcluidasPorUsuarioECurso(alunoId, cursoId);
        
        // Calcular percentual
        return progressoService.calcularPercentualProgresso(atividadesConcluidas, todasAtividades.size());
    }
}