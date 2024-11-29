package dev.lucamartins.spdagendavacinacao.service.alergia.dto;

import org.hibernate.validator.constraints.Length;

public record AddAlergiaRequest(
        @Length(min = 3, max = 40)
        String nome
) {
}
