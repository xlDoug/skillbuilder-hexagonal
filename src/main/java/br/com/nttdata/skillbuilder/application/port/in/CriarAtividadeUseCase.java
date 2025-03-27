package br.com.nttdata.skillbuilder.application.port.in;

import br.com.nttdata.skillbuilder.application.dto.AtividadeDTO;
import br.com.nttdata.skillbuilder.domain.model.Atividade;

import java.util.UUID;

public interface CriarAtividadeUseCase {
    Atividade executar(AtividadeDTO atividadeDTO, UUID cursoId);
}