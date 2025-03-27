package br.com.nttdata.skillbuilder.application.dto;

import java.time.LocalDateTime;

public class ErrorDTO {
    private String mensagem;
    private int codigo;
    private String path;
    private LocalDateTime timestamp;

    public ErrorDTO() {
        this.timestamp = LocalDateTime.now();
    }

    public ErrorDTO(String mensagem, int codigo, String path) {
        this.mensagem = mensagem;
        this.codigo = codigo;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }

    // Getters e Setters
    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}