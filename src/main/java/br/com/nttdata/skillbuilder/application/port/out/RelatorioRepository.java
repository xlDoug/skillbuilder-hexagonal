package br.com.nttdata.skillbuilder.application.port.out;

import br.com.nttdata.skillbuilder.application.dto.RelatorioDesempenhoDTO;
import br.com.nttdata.skillbuilder.application.dto.RelatorioCursoDTO;

import java.util.UUID;

public interface RelatorioRepository {
    RelatorioDesempenhoDTO gerarRelatorioDesempenhoAluno(UUID alunoId);
    RelatorioCursoDTO gerarRelatorioCurso(UUID cursoId);
}