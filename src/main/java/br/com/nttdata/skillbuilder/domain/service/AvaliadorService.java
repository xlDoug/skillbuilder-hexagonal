package br.com.nttdata.skillbuilder.domain.service;

import br.com.nttdata.skillbuilder.domain.model.Curso;

public class AvaliadorService {
        /**
         * Verifica se a soma dos pontos dos alunos atingiu a meta definida no curso.
         */
        public boolean metaAtingida(Curso curso, int totalPontosDosAlunos) {
            return totalPontosDosAlunos >= curso.getMetaPontos();
        }
    }


