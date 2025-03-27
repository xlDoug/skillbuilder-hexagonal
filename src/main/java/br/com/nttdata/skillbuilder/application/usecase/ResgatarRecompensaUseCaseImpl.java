package br.com.nttdata.skillbuilder.application.usecase;

import br.com.nttdata.skillbuilder.application.port.in.EnviarNotificacaoUseCase;
import br.com.nttdata.skillbuilder.application.port.in.ResgatarRecompensaUseCase;
import br.com.nttdata.skillbuilder.application.port.out.PontuacaoRepository;
import br.com.nttdata.skillbuilder.application.port.out.RecompensaRepository;
import br.com.nttdata.skillbuilder.application.port.out.ResgateRepository;
import br.com.nttdata.skillbuilder.application.port.out.UsuarioRepository;
import br.com.nttdata.skillbuilder.domain.exception.RegraDeNegocioException;
import br.com.nttdata.skillbuilder.domain.model.Recompensa;
import br.com.nttdata.skillbuilder.domain.model.Resgate;
import br.com.nttdata.skillbuilder.domain.model.TipoNotificacao;
import br.com.nttdata.skillbuilder.domain.model.Usuario;
import br.com.nttdata.skillbuilder.domain.service.RecompensaService;
import br.com.nttdata.skillbuilder.domain.service.ValidacaoDominioService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ResgatarRecompensaUseCaseImpl implements ResgatarRecompensaUseCase {

    private final RecompensaRepository recompensaRepository;
    private final ResgateRepository resgateRepository;
    private final UsuarioRepository usuarioRepository;
    private final PontuacaoRepository pontuacaoRepository;
    private final EnviarNotificacaoUseCase enviarNotificacaoUseCase;
    private final RecompensaService recompensaService;
    private final ValidacaoDominioService validacaoDominioService;

    public ResgatarRecompensaUseCaseImpl(
            RecompensaRepository recompensaRepository,
            ResgateRepository resgateRepository,
            UsuarioRepository usuarioRepository,
            PontuacaoRepository pontuacaoRepository,
            EnviarNotificacaoUseCase enviarNotificacaoUseCase,
            RecompensaService recompensaService,
            ValidacaoDominioService validacaoDominioService) {
        this.recompensaRepository = recompensaRepository;
        this.resgateRepository = resgateRepository;
        this.usuarioRepository = usuarioRepository;
        this.pontuacaoRepository = pontuacaoRepository;
        this.enviarNotificacaoUseCase = enviarNotificacaoUseCase;
        this.recompensaService = recompensaService;
        this.validacaoDominioService = validacaoDominioService;
    }

    @Override
    @Transactional
    public Resgate executar(UUID usuarioId, UUID recompensaId) {
        // Verificar se o usuário existe
        Optional<Usuario> usuarioOpt = usuarioRepository.buscarPorId(usuarioId);
        if (usuarioOpt.isEmpty()) {
            throw new RegraDeNegocioException("Usuário não encontrado");
        }

        Usuario usuario = usuarioOpt.get();

        // Verificar se a recompensa existe
        Optional<Recompensa> recompensaOpt = recompensaRepository.buscarPorId(recompensaId);
        if (recompensaOpt.isEmpty()) {
            throw new RegraDeNegocioException("Recompensa não encontrada");
        }

        Recompensa recompensa = recompensaOpt.get();

        // Obter pontos do usuário
        int pontosUsuario = pontuacaoRepository.calcularTotalPontosPorUsuario(usuarioId);

        // Validar regras de domínio para o resgate
        validacaoDominioService.validarResgateRecompensa(usuario, recompensa, pontosUsuario);

        // Executar resgate
        Resgate resgate = recompensaService.realizarResgate(usuarioId, recompensa, pontosUsuario);

        // Atualizar quantidade disponível da recompensa
        recompensaRepository.atualizarQuantidadeDisponivel(recompensaId, recompensa.getQuantidadeDisponivel() - 1);

        // Salvar registro de resgate
        Resgate resgateSalvo = resgateRepository.salvar(resgate);

        // Enviar notificação ao usuário
        enviarNotificacaoUseCase.enviarNotificacao(
                usuarioId,
                "Você resgatou a recompensa: " + recompensa.getNome(),
                TipoNotificacao.RECOMPENSA_RESGATADA
        );

        return resgateSalvo;
    }

    @Override
    public List<Recompensa> listarRecompensasDisponiveis(UUID usuarioId) {
        // Verificar se o usuário existe
        if (!usuarioRepository.buscarPorId(usuarioId).isPresent()) {
            throw new RegraDeNegocioException("Usuário não encontrado");
        }

        // Buscar recompensas disponíveis
        List<Recompensa> recompensasDisponiveis = recompensaRepository.buscarDisponiveis();

        // Obter pontos do usuário
        int pontosUsuario = pontuacaoRepository.calcularTotalPontosPorUsuario(usuarioId);

        // Filtrar apenas as recompensas que o usuário tem pontos suficientes para resgatar
        return recompensasDisponiveis.stream()
                .filter(recompensa -> recompensa.podeResgatar(pontosUsuario))
                .collect(Collectors.toList());
    }

    @Override
    public List<Resgate> consultarResgatesUsuario(UUID usuarioId) {
        // Verificar se o usuário existe
        if (!usuarioRepository.buscarPorId(usuarioId).isPresent()) {
            throw new RegraDeNegocioException("Usuário não encontrado");
        }

        return resgateRepository.buscarPorUsuarioId(usuarioId);
    }
}