package br.com.nttdata.skillbuilder.application.port.in;

import br.com.nttdata.skillbuilder.application.dto.RelatorioDesempenhoDTO;
import br.com.nttdata.skillbuilder.application.dto.RelatorioCursoDTO;

import java.util.UUID;

public interface GerarRelatorioUseCase {
    RelatorioDesempenhoDTO gerarRelatorioDesempenhoAluno(UUID alunoId);
    RelatorioCursoDTO gerarRelatorioCurso(UUID cursoId);
}