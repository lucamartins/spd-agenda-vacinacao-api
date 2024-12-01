package dev.lucamartins.spdagendavacinacao.service.vacina.dto;

import dev.lucamartins.spdagendavacinacao.domain.vacina.PeriodicidadeIntervaloDose;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record AddVacinaRequest(
        @Length(min = 3, max = 60)
        String titulo,
        @Length(min = 3, max = 200)
        String descricao,
        @NotNull
        @Min(1)
        Integer doses,
        PeriodicidadeIntervaloDose periodicidade,
        @Min(1)
        Integer intervalo
) {
}
