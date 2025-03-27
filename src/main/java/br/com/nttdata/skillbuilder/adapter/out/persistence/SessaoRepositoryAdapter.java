package br.com.nttdata.skillbuilder.adapter.out.persistence;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.nttdata.skillbuilder.adapter.out.persistence.entity.SessaoEntity;
import br.com.nttdata.skillbuilder.adapter.out.persistence.repository.SessaoJpaRepository;
import br.com.nttdata.skillbuilder.application.port.out.SessaoRepository;

@Component
public class SessaoRepositoryAdapter implements SessaoRepository {

    private final SessaoJpaRepository sessaoJpaRepository;
    
    // Tempo de expiração do token em horas
    private static final int EXPIRACAO_HORAS = 24;

    public SessaoRepositoryAdapter(SessaoJpaRepository sessaoJpaRepository) {
        this.sessaoJpaRepository = sessaoJpaRepository;
    }

    @Override
    @Transactional
    public String criarSessao(UUID usuarioId) {
        // Gerar um token único
        String token = UUID.randomUUID().toString();
        
        // Calcular data de expiração
        LocalDateTime dataExpiracao = LocalDateTime.now().plusHours(EXPIRACAO_HORAS);
        
        // Criar e salvar a entidade
        SessaoEntity entity = new SessaoEntity(token, usuarioId, dataExpiracao);
        sessaoJpaRepository.save(entity);
        
        return token;
    }

    @Override
    public boolean validarSessao(String token) {
        // Invalidar sessões expiradas primeiro
        limparSessoesExpiradas();
        
        // Verificar se a sessão existe e está válida
        Optional<SessaoEntity> sessaoOpt = sessaoJpaRepository.findByToken(token);
        return sessaoOpt.isPresent() && sessaoOpt.get().isValida();
    }

    @Override
    @Transactional
    public void encerrarSessao(String token) {
        sessaoJpaRepository.invalidateToken(token);
    }

    @Override
    public Optional<UUID> obterUsuarioIdPorToken(String token) {
        return sessaoJpaRepository.findByToken(token)
                .filter(SessaoEntity::isValida)
                .map(SessaoEntity::getUsuarioId)
                .map(UUID::fromString);
    }
    
    @Transactional
    private void limparSessoesExpiradas() {
        sessaoJpaRepository.invalidateExpiredTokens(LocalDateTime.now());
    }
}
