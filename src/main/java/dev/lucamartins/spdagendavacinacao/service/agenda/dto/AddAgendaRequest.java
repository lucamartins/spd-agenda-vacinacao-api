package dev.lucamartins.spdagendavacinacao.service.agenda.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.OffsetDateTime;
import java.util.UUID;

public record AddAgendaRequest(
        @NotNull
        @Future
        OffsetDateTime data,
        @Length(min = 3, max = 200)
        String observacoes,
        @NotNull
        UUID vacinaId,
        @NotNull
        UUID usuarioId
) {
}
