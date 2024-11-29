package dev.lucamartins.spdagendavacinacao.service.usuario.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record AddUsuarioAlergiaRequest(
        @NotEmpty
        List<@NotNull UUID> alergias
) {
}
