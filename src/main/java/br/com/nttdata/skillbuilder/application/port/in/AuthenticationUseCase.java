package br.com.nttdata.skillbuilder.application.port.in;

import br.com.nttdata.skillbuilder.application.dto.TokenDTO;
import br.com.nttdata.skillbuilder.application.dto.CredenciaisDTO;

public interface AuthenticationUseCase {
    TokenDTO autenticar(CredenciaisDTO credenciais);
    void logout(String token);
    boolean validarToken(String token);
}