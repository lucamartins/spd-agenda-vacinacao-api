package dev.lucamartins.spdagendavacinacao.controller;

import dev.lucamartins.spdagendavacinacao.infra.protocol.ApiResponse;
import dev.lucamartins.spdagendavacinacao.service.usuario.UsuarioService;
import dev.lucamartins.spdagendavacinacao.service.usuario.dto.AddUsuarioAlergiaRequest;
import dev.lucamartins.spdagendavacinacao.service.usuario.dto.AddUsuarioRequest;
import dev.lucamartins.spdagendavacinacao.service.usuario.dto.UsuarioView;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping
    @Transactional
    public void addUsuario(
            @RequestBody @Valid AddUsuarioRequest request
    ) {
        service.addUsuario(request);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UsuarioView>>> listUsuarios() {
        var usuarios = service.listUsuarios();

        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                usuarios
        ));
    }

    @DeleteMapping("{id}")
    @Transactional
    public void deleteUsuario(
            @PathVariable UUID id
    ) {
        service.deleteUsuario(id);
    }

    @PostMapping("{id}/alergias")
    @Transactional
    public void addUsuarioAlergia(
            @PathVariable UUID id,
            @RequestBody @Valid AddUsuarioAlergiaRequest request
            ) {
        service.addUsuarioAlergia(id, request);
    }

    @DeleteMapping("{usuarioId}/alergias/{alergiaId}")
    @Transactional
    public void addUsuarioAlergia(
            @PathVariable UUID usuarioId,
            @PathVariable UUID alergiaId
    ) {
        service.removeUsuarioAlergia(usuarioId, alergiaId);
    }
}
