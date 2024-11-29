package dev.lucamartins.spdagendavacinacao.service.usuario;

import dev.lucamartins.spdagendavacinacao.domain.usuario.Usuario;
import dev.lucamartins.spdagendavacinacao.domain.usuario.UsuarioRepository;
import dev.lucamartins.spdagendavacinacao.infra.exception.custom.BadRequestException;
import dev.lucamartins.spdagendavacinacao.infra.exception.custom.NotFoundException;
import dev.lucamartins.spdagendavacinacao.service.usuario.dto.AddUsuarioRequest;
import dev.lucamartins.spdagendavacinacao.service.usuario.dto.UsuarioView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

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
}
