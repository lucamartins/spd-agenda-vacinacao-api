package dev.lucamartins.spdagendavacinacao.controller;

import dev.lucamartins.spdagendavacinacao.infra.protocol.ApiResponse;
import dev.lucamartins.spdagendavacinacao.service.vacina.VacinaService;
import dev.lucamartins.spdagendavacinacao.service.vacina.dto.AddVacinaRequest;
import dev.lucamartins.spdagendavacinacao.service.vacina.dto.VacinaView;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/vacinas")
@RequiredArgsConstructor
public class VacinaController {

    private final VacinaService service;

    @PostMapping
    @Transactional
    public void addVacina(
            @RequestBody @Valid AddVacinaRequest request
    ) {
        service.addVacina(request);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<VacinaView>>> listVacinas() {
        var vacinas = service.getVacinas();

        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                vacinas
        ));
    }

    @DeleteMapping("{id}")
    @Transactional
    public void deleteVacina(
            @PathVariable UUID id
    ) {
        service.deleteVacina(id);
    }

}
