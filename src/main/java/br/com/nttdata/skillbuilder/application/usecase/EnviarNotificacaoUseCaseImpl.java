package br.com.nttdata.skillbuilder.application.usecase;

import br.com.nttdata.skillbuilder.application.port.in.EnviarNotificacaoUseCase;
import br.com.nttdata.skillbuilder.application.port.out.NotificacaoGateway;
import br.com.nttdata.skillbuilder.application.port.out.NotificacaoRepository;
import br.com.nttdata.skillbuilder.application.port.out.UsuarioRepository;
import br.com.nttdata.skillbuilder.domain.exception.RegraDeNegocioException;
import br.com.nttdata.skillbuilder.domain.model.Notificacao;
import br.com.nttdata.skillbuilder.domain.model.StatusNotificacao;
import br.com.nttdata.skillbuilder.domain.model.TipoNotificacao;
import br.com.nttdata.skillbuilder.domain.service.NotificacaoService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EnviarNotificacaoUseCaseImpl implements EnviarNotificacaoUseCase {

    private final NotificacaoRepository notificacaoRepository;
    private final NotificacaoGateway notificacaoGateway;
    private final UsuarioRepository usuarioRepository;
    private final NotificacaoService notificacaoService;

    public EnviarNotificacaoUseCaseImpl(NotificacaoRepository notificacaoRepository,
                                  NotificacaoGateway notificacaoGateway,
                                  UsuarioRepository usuarioRepository,
                                  NotificacaoService notificacaoService) {
        this.notificacaoRepository = notificacaoRepository;
        this.notificacaoGateway = notificacaoGateway;
        this.usuarioRepository = usuarioRepository;
        this.notificacaoService = notificacaoService;
    }

    @Override
    @Transactional
    public void enviarNotificacao(UUID usuarioId, String mensagem, TipoNotificacao tipo) {
        // Verificar se o usuário existe
        if (!usuarioRepository.buscarPorId(usuarioId).isPresent()) {
            throw new RegraDeNegocioException("Usuário não encontrado");
        }
        
        // Criar notificação
        Notificacao notificacao = new Notificacao(usuarioId, mensagem, tipo);
        
        // Salvar notificação no repositório
        Notificacao notificacaoSalva = notificacaoRepository.salvar(notificacao);
        
        // Enviar notificação pelo gateway
        notificacaoGateway.enviarNotificacao(notificacaoSalva);
    }

    @Override
    public List<Notificacao> listarNotificacoesUsuario(UUID usuarioId) {
        // Verificar se o usuário existe
        if (!usuarioRepository.buscarPorId(usuarioId).isPresent()) {
            throw new RegraDeNegocioException("Usuário não encontrado");
        }
        
        return notificacaoRepository.buscarPorUsuarioId(usuarioId);
    }

    @Override
    @Transactional
    public void marcarNotificacaoComoLida(UUID notificacaoId) {
        Optional<Notificacao> notificacaoOpt = notificacaoRepository.buscarPorId(notificacaoId);
        if (notificacaoOpt.isEmpty()) {
            throw new RegraDeNegocioException("Notificação não encontrada");
        }
        
        Notificacao notificacao = notificacaoOpt.get();
        
        if (notificacao.getStatus() == StatusNotificacao.LIDA) {
            return; // Já está marcada como lida
        }
        
        notificacaoRepository.marcarComoLida(notificacaoId);
    }
}