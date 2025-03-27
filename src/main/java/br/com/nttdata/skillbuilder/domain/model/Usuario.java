package br.com.nttdata.skillbuilder.domain.model;

import java.time.LocalDate;
import java.util.UUID;

public class Usuario {

    private final UUID id;
    private final String nome;
    private final String email;
    private final TipoUsuario tipo;
    private final LocalDate dataCadastro;

    public Usuario(UUID id, String nome, String email, TipoUsuario tipo, LocalDate dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.tipo = tipo;
        this.dataCadastro = dataCadastro;
    }

    public Usuario(String nome, String email, TipoUsuario tipo) {
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.email = email;
        this.tipo = tipo;
        this.dataCadastro = LocalDate.now();
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public boolean ehAluno() {
        return tipo == TipoUsuario.ALUNO;
    }

    public boolean ehProfessor() {
        return tipo == TipoUsuario.PROFESSOR;
    }

    public boolean ehAdmin() {
        return tipo == TipoUsuario.ADMIN;
    }

}
