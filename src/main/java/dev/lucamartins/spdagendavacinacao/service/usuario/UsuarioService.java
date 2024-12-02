package dev.lucamartins.spdagendavacinacao.service.usuario;

import dev.lucamartins.spdagendavacinacao.domain.alergia.AlergiaRepository;
import dev.lucamartins.spdagendavacinacao.domain.usuario.Usuario;
import dev.lucamartins.spdagendavacinacao.domain.usuario.UsuarioRepository;
import dev.lucamartins.spdagendavacinacao.infra.exception.custom.BadRequestException;
import dev.lucamartins.spdagendavacinacao.infra.exception.custom.NotFoundException;
import dev.lucamartins.spdagendavacinacao.service.usuario.dto.AddUsuarioAlergiaRequest;
import dev.lucamartins.spdagendavacinacao.service.usuario.dto.AddUsuarioRequest;
import dev.lucamartins.spdagendavacinacao.service.usuario.dto.UsuarioView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final AlergiaRepository alergiaRepository;

    public void addUsuario(AddUsuarioRequest addUsuarioRequest) {
        var usuario = new Usuario(addUsuarioRequest);
        usuarioRepository.save(usuario);
    }

    public List<UsuarioView> listUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(UsuarioView::new)
                .collect(Collectors.toList());
    }

    public void deleteUsuario(UUID id) {
        var usuario = usuarioRepository
                .findById(id)
                .orElseThrow(NotFoundException::new);

        if (!usuario.canBeDeleted()) {
            throw new BadRequestException("Usuário não pode ser deletado");
        }

        usuarioRepository.delete(usuario);
    }

    public void addUsuarioAlergia(UUID id, AddUsuarioAlergiaRequest request) {
        var usuario = usuarioRepository
                .findById(id)
                .orElseThrow(NotFoundException::new);


        List<UUID> duplicatedAlergias = new ArrayList<>();
        List<UUID> notFoundAlergias = new ArrayList<>();

        request.alergias().forEach(alergiaId -> {
            var alergia = alergiaRepository
                    .findById(alergiaId)
                    .orElse(null);

            if (alergia == null) {
                notFoundAlergias.add(alergiaId);
            } else if (usuario.getAlergias().contains(alergia)) {
                duplicatedAlergias.add(alergiaId);
            } else {
                usuario.getAlergias().add(alergia);
            }
        });

        String duplicatedAlergiasMessage = duplicatedAlergias.stream()
                .map(UUID::toString)
                .collect(Collectors.joining(", "));
        String notFoundAlergiasMessage = notFoundAlergias.stream()
                .map(UUID::toString)
                .collect(Collectors.joining(", "));

        if (!duplicatedAlergias.isEmpty() || !notFoundAlergias.isEmpty()) {
            throw new BadRequestException(
                    "Alergias duplicadas: " + duplicatedAlergiasMessage +
                            " | Alergias não encontradas: " + notFoundAlergiasMessage
            );
        }

        usuarioRepository.save(usuario);
    }

    public void removeUsuarioAlergia(UUID usuarioId, UUID alergiaId) {
        var usuario = usuarioRepository
                .findById(usuarioId)
                .orElseThrow(NotFoundException::new);


        var alergia = usuario.getAlergias().stream()
                .filter(a -> a.getId().equals(alergiaId))
                .findFirst()
                .orElseThrow(NotFoundException::new);

        usuario.getAlergias().remove(alergia);

        usuarioRepository.save(usuario);
    }
}
