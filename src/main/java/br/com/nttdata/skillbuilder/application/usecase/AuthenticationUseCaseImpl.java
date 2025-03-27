package br.com.nttdata.skillbuilder.application.usecase;

import br.com.nttdata.skillbuilder.application.dto.CredenciaisDTO;
import br.com.nttdata.skillbuilder.application.dto.TokenDTO;
import br.com.nttdata.skillbuilder.application.port.in.AuthenticationUseCase;
import br.com.nttdata.skillbuilder.application.port.out.CredencialUsuarioRepository;
import br.com.nttdata.skillbuilder.application.port.out.SessaoRepository;
import br.com.nttdata.skillbuilder.domain.exception.RegraDeNegocioException;
import br.com.nttdata.skillbuilder.domain.model.CredencialUsuario;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationUseCaseImpl implements AuthenticationUseCase {

    private final CredencialUsuarioRepository credencialUsuarioRepository;
    private final SessaoRepository sessaoRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationUseCaseImpl(CredencialUsuarioRepository credencialUsuarioRepository,
                                     SessaoRepository sessaoRepository,
                                     PasswordEncoder passwordEncoder) {
        this.credencialUsuarioRepository = credencialUsuarioRepository;
        this.sessaoRepository = sessaoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public TokenDTO autenticar(CredenciaisDTO credenciais) {
        Optional<CredencialUsuario> credencialOpt = credencialUsuarioRepository.buscarPorEmail(credenciais.getEmail());

        if (credencialOpt.isEmpty()) {
            throw new RegraDeNegocioException("Credenciais inválidas");
        }

        CredencialUsuario credencial = credencialOpt.get();

        if (!credencial.isAtivo()) {
            throw new RegraDeNegocioException("Usuário desativado");
        }

        if (!passwordEncoder.matches(credenciais.getSenha(), credencial.getHashSenha())) {
            throw new RegraDeNegocioException("Credenciais inválidas");
        }

        String token = sessaoRepository.criarSessao(credencial.getUsuarioId());

        // Definindo expiração de 24 horas (em segundos)
        long expiracaoEmSegundos = 24 * 60 * 60;

        return new TokenDTO(token, "Bearer", expiracaoEmSegundos);
    }

    @Override
    public void logout(String token) {
        sessaoRepository.encerrarSessao(token);
    }

    @Override
    public boolean validarToken(String token) {
        return sessaoRepository.validarSessao(token);
    }
}