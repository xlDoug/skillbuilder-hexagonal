package br.com.nttdata.skillbuilder.domain.service;

import br.com.nttdata.skillbuilder.domain.exception.RegraDeNegocioException;
import br.com.nttdata.skillbuilder.domain.model.Recompensa;
import br.com.nttdata.skillbuilder.domain.model.Usuario;

public class ValidacaoDominioService {

    public void validarResgateRecompensa(Usuario usuario, Recompensa recompensa, int pontosDisponiveisUsuario) {
        if (!usuario.ehAluno()) {
            throw new RegraDeNegocioException("Apenas alunos podem resgatar recompensas");
        }

        if (pontosDisponiveisUsuario < recompensa.getCustoPontos()) {
            throw new RegraDeNegocioException("Pontos insuficientes para resgatar esta recompensa");
        }

        if (recompensa.getQuantidadeDisponivel() <= 0) {
            throw new RegraDeNegocioException("Recompensa esgotada");
        }
    }

    public void validarCriacaoCurso(Usuario usuario) {
        if (!usuario.ehProfessor() && !usuario.ehAdmin()) {
            throw new RegraDeNegocioException("Apenas professores e administradores podem criar cursos");
        }
    }

    public void validarCriacaoAtividade(Usuario usuario) {
        if (!usuario.ehProfessor() && !usuario.ehAdmin()) {
            throw new RegraDeNegocioException("Apenas professores e administradores podem criar atividades");
        }
    }
}