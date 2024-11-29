package dev.lucamartins.spdagendavacinacao.service.alergia.dto;

import dev.lucamartins.spdagendavacinacao.domain.alergia.Alergia;

import java.util.UUID;

public record AlergiaView(
        UUID id,
        String nome
) {
    public AlergiaView (Alergia alergia) {
        this (
                alergia.getId(),
                alergia.getNome()
        );
    }
}
