package dev.lucamartins.spdagendavacinacao.controller;

import dev.lucamartins.spdagendavacinacao.domain.agenda.SituacaoAgenda;
import dev.lucamartins.spdagendavacinacao.infra.protocol.ApiResponse;
import dev.lucamartins.spdagendavacinacao.service.agenda.AgendaService;
import dev.lucamartins.spdagendavacinacao.service.agenda.dto.AddAgendaRequest;
import dev.lucamartins.spdagendavacinacao.service.agenda.dto.AgendaView;
import dev.lucamartins.spdagendavacinacao.service.agenda.dto.BaixaAgendaRequest;
import dev.lucamartins.spdagendavacinacao.service.agenda.dto.RescheduleAgendaRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/agendas")
@RequiredArgsConstructor
public class AgendaController {

    private final AgendaService service;

    @PostMapping
    @Transactional
    public void addAgenda(
            @RequestBody @Valid AddAgendaRequest request
    ) {
        service.addAgenda(request);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AgendaView>>> getAgendas(
            @RequestParam(required = false) SituacaoAgenda situacao,
            @RequestParam(required = false) OffsetDateTime dataStart,
            @RequestParam(required = false) OffsetDateTime dataEnd,
            @RequestParam(required = false) UUID usuarioId
    ) {
        var agendas = service.getAgendas(
                situacao,
                dataStart,
                dataEnd,
                usuarioId
        );

        var response = new ApiResponse<>(
                HttpStatus.OK.value(),
                agendas
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    @Transactional
    public void deleteAgenda(
            @PathVariable UUID id
    ) {
        service.deleteAgenda(id);
    }

    @PostMapping("{id}/baixa")
    @Transactional
    public void baixaAgenda(
            @PathVariable UUID id,
            @RequestBody @Valid BaixaAgendaRequest request
    ) {
        service.baixaAgenda(id, request);
    }

    @PostMapping("{id}/confirmar-presenca")
    @Transactional
    public void confirmarPresenca(
            @PathVariable UUID id
    ) {
        service.confirmAttendance(id);
    }

    @PostMapping("{id}/reagendar")
    @Transactional
    public void reagendar(
            @PathVariable UUID id,
            @RequestBody @Valid RescheduleAgendaRequest request
    ) {
        service.rescheduleAgenda(id, request);
    }
}
