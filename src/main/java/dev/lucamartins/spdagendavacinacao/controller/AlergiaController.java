package dev.lucamartins.spdagendavacinacao.controller;

import dev.lucamartins.spdagendavacinacao.infra.protocol.ApiResponse;
import dev.lucamartins.spdagendavacinacao.service.alergia.AlergiaService;
import dev.lucamartins.spdagendavacinacao.service.alergia.dto.AddAlergiaRequest;
import dev.lucamartins.spdagendavacinacao.service.alergia.dto.AlergiaView;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/alergias")
@RequiredArgsConstructor
public class AlergiaController {

    private final AlergiaService service;

    @PostMapping
    @Transactional
    public void addAlergia(
            @RequestBody @Valid AddAlergiaRequest request
    ) {
       service.addAlergia(request);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AlergiaView>>> listAlergias() {
        var alergias = service.getAlergias();

        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                alergias
        ));
    }

    @DeleteMapping("{id}")
    @Transactional
    public void deleteAlergia(
            @PathVariable UUID id
    ) {
        service.deleteAlergia(id);
    }

}
