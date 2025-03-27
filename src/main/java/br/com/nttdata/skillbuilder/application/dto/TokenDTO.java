package br.com.nttdata.skillbuilder.application.dto;

public class TokenDTO {
    private String token;
    private String tipo;
    private long expiracaoEmSegundos;

    public TokenDTO() {
    }

    public TokenDTO(String token, String tipo, long expiracaoEmSegundos) {
        this.token = token;
        this.tipo = tipo;
        this.expiracaoEmSegundos = expiracaoEmSegundos;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public long getExpiracaoEmSegundos() {
        return expiracaoEmSegundos;
    }

    public void setExpiracaoEmSegundos(long expiracaoEmSegundos) {
        this.expiracaoEmSegundos = expiracaoEmSegundos;
    }
}