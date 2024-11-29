package dev.lucamartins.spdagendavacinacao.service.usuario.dto;

import dev.lucamartins.spdagendavacinacao.domain.usuario.Usuario;
import dev.lucamartins.spdagendavacinacao.service.alergia.dto.AlergiaView;

import java.util.List;
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
        String uf,
        List<AlergiaView> alergias
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
                usuario.getUf(),
                usuario.getAlergias().stream()
                        .map(AlergiaView::new)
                        .toList()
        );
    }
}

