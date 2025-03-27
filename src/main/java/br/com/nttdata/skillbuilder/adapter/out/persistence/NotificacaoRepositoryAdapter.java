package br.com.nttdata.skillbuilder.adapter.out.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.nttdata.skillbuilder.adapter.out.persistence.entity.NotificacaoEntity;
import br.com.nttdata.skillbuilder.adapter.out.persistence.repository.NotificacaoJpaRepository;
import br.com.nttdata.skillbuilder.application.port.out.NotificacaoRepository;
import br.com.nttdata.skillbuilder.domain.model.Notificacao;
import br.com.nttdata.skillbuilder.domain.model.StatusNotificacao;
import br.com.nttdata.skillbuilder.domain.model.TipoNotificacao;

@Component
public class NotificacaoRepositoryAdapter implements NotificacaoRepository {

    private final NotificacaoJpaRepository notificacaoJpaRepository;

    public NotificacaoRepositoryAdapter(NotificacaoJpaRepository notificacaoJpaRepository) {
        this.notificacaoJpaRepository = notificacaoJpaRepository;
    }

    @Override
    public Notificacao salvar(Notificacao notificacao) {
        NotificacaoEntity entity = new NotificacaoEntity(notificacao);
        NotificacaoEntity savedEntity = notificacaoJpaRepository.save(entity);
        return savedEntity.toDomainModel();
    }

    @Override
    public Optional<Notificacao> buscarPorId(UUID id) {
        return notificacaoJpaRepository.findById(id.toString())
                .map(NotificacaoEntity::toDomainModel);
    }

    @Override
    public List<Notificacao> buscarPorUsuarioId(UUID usuarioId) {
        return notificacaoJpaRepository.findByUsuarioId(usuarioId.toString()).stream()
                .map(NotificacaoEntity::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Notificacao> buscarPorUsuarioEStatus(UUID usuarioId, StatusNotificacao status) {
        return notificacaoJpaRepository.findByUsuarioIdAndStatus(usuarioId.toString(), status).stream()
                .map(NotificacaoEntity::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Notificacao> buscarPorTipo(TipoNotificacao tipo) {
        return notificacaoJpaRepository.findByTipo(tipo).stream()
                .map(NotificacaoEntity::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void marcarComoLida(UUID id) {
        notificacaoJpaRepository.findById(id.toString())
                .ifPresent(entity -> {
                    entity.setStatus(StatusNotificacao.LIDA);
                    notificacaoJpaRepository.save(entity);
                });
    }

    @Override
    public void deletar(UUID id) {
        notificacaoJpaRepository.deleteById(id.toString());
    }
}