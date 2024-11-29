package dev.lucamartins.spdagendavacinacao.service.usuario.dto;

import dev.lucamartins.spdagendavacinacao.domain.usuario.Usuario;

import java.util.UUID;

public record UsuarioView(
        UUID id,
        String nome,
        String dataNascimento,
        String sexo,
        String logradouro,
        String numero,
        String setor,
        String cidade,
        String uf
) {
    public UsuarioView(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getNome(),
                usuario.getDataNascimento().toString(),
                usuario.getSexo().toString(),
                usuario.getLogradouro(),
                usuario.getNumero(),
                usuario.getSetor(),
                usuario.getCidade(),
                usuario.getUf()
        );
    }
}

