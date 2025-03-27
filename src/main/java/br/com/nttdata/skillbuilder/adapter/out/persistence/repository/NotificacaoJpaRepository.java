package br.com.nttdata.skillbuilder.adapter.out.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nttdata.skillbuilder.adapter.out.persistence.entity.NotificacaoEntity;
import br.com.nttdata.skillbuilder.domain.model.StatusNotificacao;
import br.com.nttdata.skillbuilder.domain.model.TipoNotificacao;

@Repository
public interface NotificacaoJpaRepository extends JpaRepository<NotificacaoEntity, String> {
    List<NotificacaoEntity> findByUsuarioId(String usuarioId);
    
    List<NotificacaoEntity> findByUsuarioIdAndStatus(String usuarioId, StatusNotificacao status);
    
    List<NotificacaoEntity> findByTipo(TipoNotificacao tipo);
}
