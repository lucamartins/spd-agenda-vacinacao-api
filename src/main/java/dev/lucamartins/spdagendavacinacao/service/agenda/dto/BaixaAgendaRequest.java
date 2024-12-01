package dev.lucamartins.spdagendavacinacao.service.agenda.dto;

import dev.lucamartins.spdagendavacinacao.domain.agenda.SituacaoAgenda;
import jakarta.validation.constraints.NotNull;

public record BaixaAgendaRequest(
        @NotNull
        SituacaoAgenda situacao
) {
}
