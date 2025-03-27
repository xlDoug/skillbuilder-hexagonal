package br.com.nttdata.skillbuilder.adapter.out.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.nttdata.skillbuilder.adapter.out.persistence.entity.CredencialUsuarioEntity;
import br.com.nttdata.skillbuilder.adapter.out.persistence.repository.CredencialUsuarioJpaRepository;
import br.com.nttdata.skillbuilder.application.port.out.CredencialUsuarioRepository;
import br.com.nttdata.skillbuilder.domain.model.CredencialUsuario;

@Component
public class CredencialUsuarioRepositoryAdapter implements CredencialUsuarioRepository {

    private final CredencialUsuarioJpaRepository credencialUsuarioJpaRepository;

    public CredencialUsuarioRepositoryAdapter(CredencialUsuarioJpaRepository credencialUsuarioJpaRepository) {
        this.credencialUsuarioJpaRepository = credencialUsuarioJpaRepository;
    }

    @Override
    public CredencialUsuario salvar(CredencialUsuario credencial) {
        CredencialUsuarioEntity entity = new CredencialUsuarioEntity(credencial);
        CredencialUsuarioEntity savedEntity = credencialUsuarioJpaRepository.save(entity);
        return savedEntity.toDomainModel();
    }

    @Override
    public Optional<CredencialUsuario> buscarPorEmail(String email) {
        return credencialUsuarioJpaRepository.findByEmail(email)
                .map(CredencialUsuarioEntity::toDomainModel);
    }

    @Override
    public Optional<CredencialUsuario> buscarPorUsuarioId(UUID usuarioId) {
        return credencialUsuarioJpaRepository.findById(usuarioId.toString())
                .map(CredencialUsuarioEntity::toDomainModel);
    }

    @Override
    @Transactional
    public void atualizarSenha(UUID usuarioId, String novaSenhaHash) {
        credencialUsuarioJpaRepository.findById(usuarioId.toString())
                .ifPresent(entity -> {
                    entity.setHashSenha(novaSenhaHash);
                    credencialUsuarioJpaRepository.save(entity);
                });
    }

    @Override
    @Transactional
    public void ativarUsuario(UUID usuarioId) {
        credencialUsuarioJpaRepository.findById(usuarioId.toString())
                .ifPresent(entity -> {
                    entity.setAtivo(true);
                    credencialUsuarioJpaRepository.save(entity);
                });
    }

    @Override
    @Transactional
    public void desativarUsuario(UUID usuarioId) {
        credencialUsuarioJpaRepository.findById(usuarioId.toString())
                .ifPresent(entity -> {
                    entity.setAtivo(false);
                    credencialUsuarioJpaRepository.save(entity);
                });
    }
}
