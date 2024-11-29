package dev.lucamartins.spdagendavacinacao.service.usuario.dto;

import dev.lucamartins.spdagendavacinacao.domain.usuario.Sexo;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

public record AddUsuarioRequest(
        @NotEmpty
        @Length(min = 3, max = 60)
        String nome,
        @NotNull
        LocalDate dataNascimento,
        @NotNull
        Sexo sexo,
        @NotEmpty
        @Length(min = 3, max = 60)
        String logradouro,
        @NotEmpty
        @Length(min = 1, max = 10)
        String numero,
        @NotEmpty
        @Length(min = 3, max = 40)
        String setor,
        @NotEmpty
        @Length(min = 3, max = 40)
        String cidade,
        @NotEmpty
        @Length(min = 2, max = 2)
        String uf
        ) {
}
