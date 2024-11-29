package dev.lucamartins.spdagendavacinacao.service.vacina.dto;

import dev.lucamartins.spdagendavacinacao.domain.vacina.PeriodicidadeIntervaloDose;
import dev.lucamartins.spdagendavacinacao.domain.vacina.Vacina;

import java.util.UUID;

public record VacinaView(
        UUID id,
        String titulo,
        String descricao,
        Integer doses,
        PeriodicidadeIntervaloDose periodicidade,
        Integer intervalo
) {
    public VacinaView (Vacina vacina) {
        this(
                vacina.getId(),
                vacina.getTitulo(),
                vacina.getDescricao(),
                vacina.getDoses(),
                vacina.getPeriodicidade(),
                vacina.getIntervalo()
        );
    }
}
