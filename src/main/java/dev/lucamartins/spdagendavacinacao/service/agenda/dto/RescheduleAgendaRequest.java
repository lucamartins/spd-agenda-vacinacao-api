package dev.lucamartins.spdagendavacinacao.service.agenda.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public record RescheduleAgendaRequest(
        @NotNull
        @Future
        OffsetDateTime data
) {
}
