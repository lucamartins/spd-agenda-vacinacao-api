package dev.lucamartins.spdagendavacinacao.service.agenda;

import dev.lucamartins.spdagendavacinacao.domain.agenda.Agenda;
import dev.lucamartins.spdagendavacinacao.domain.agenda.AgendaRepository;
import dev.lucamartins.spdagendavacinacao.domain.agenda.SituacaoAgenda;
import dev.lucamartins.spdagendavacinacao.domain.usuario.UsuarioRepository;
import dev.lucamartins.spdagendavacinacao.domain.vacina.PeriodicidadeIntervaloDose;
import dev.lucamartins.spdagendavacinacao.domain.vacina.Vacina;
import dev.lucamartins.spdagendavacinacao.domain.vacina.VacinaRepository;
import dev.lucamartins.spdagendavacinacao.infra.exception.custom.BadRequestException;
import dev.lucamartins.spdagendavacinacao.infra.exception.custom.NotFoundException;
import dev.lucamartins.spdagendavacinacao.service.agenda.dto.AddAgendaRequest;
import dev.lucamartins.spdagendavacinacao.service.agenda.dto.AgendaView;
import dev.lucamartins.spdagendavacinacao.service.agenda.dto.BaixaAgendaRequest;
import dev.lucamartins.spdagendavacinacao.service.agenda.dto.RescheduleAgendaRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AgendaService {

    private final AgendaRepository agendaRepository;
    private final UsuarioRepository usuarioRepository;
    private final VacinaRepository vacinaRepository;

    private OffsetDateTime calculateNextDoseDate(Vacina vacina, OffsetDateTime lastScheduledDate) {
        PeriodicidadeIntervaloDose periodicidade = vacina.getPeriodicidade();
        Integer intervalo = vacina.getIntervalo();

        return switch (periodicidade) {
            case DIAS -> lastScheduledDate.plusDays(intervalo);
            case SEMANAS -> lastScheduledDate.plusWeeks(intervalo);
            case MESES -> lastScheduledDate.plusMonths(intervalo);
            case ANOS -> lastScheduledDate.plusYears(intervalo);
            default -> throw new BadRequestException("Periodicidade não suportada");
        };
    }

    public void addAgenda(AddAgendaRequest addAgendaRequest) {
        var usuario = usuarioRepository
                .findById(addAgendaRequest.usuarioId())
                .orElseThrow(() -> new BadRequestException("Usuário não encontrado"));

        var vacina = vacinaRepository
                .findById(addAgendaRequest.vacinaId())
                .orElseThrow(() -> new BadRequestException("Vacina não encontrada"));

        OffsetDateTime lastScheduledDate = null;
        for (int i = 0; i < vacina.getDoses(); i++) {
            OffsetDateTime data = i == 0 ? addAgendaRequest.data() : calculateNextDoseDate(vacina, lastScheduledDate);
            var agenda = new Agenda(addAgendaRequest, vacina, usuario, data, i);
            agendaRepository.save(agenda);
            lastScheduledDate = data;
        }
    }

    public List<AgendaView> getAgendas(
            SituacaoAgenda situacao,
            OffsetDateTime dataStart,
            OffsetDateTime dataEnd,
            UUID usuarioId
    ) {
        return agendaRepository
                .findAgendasByFilters(situacao, dataStart, dataEnd, usuarioId)
                .stream().map(AgendaView::new).toList();
    }

    public void deleteAgenda(UUID id) {
        var agenda = agendaRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Agenda não encontrada"));

        if (!agenda.canBeDeleted()) {
            throw new BadRequestException("Agenda não pode ser excluída");
        }

        agendaRepository.delete(agenda);
    }

    public void baixaAgenda(UUID id, BaixaAgendaRequest request) {
        var agenda = agendaRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Agenda não encontrada"));

        if (!agenda.canBeBaixada()) {
            throw new BadRequestException("Baixa só é permitido se em situação de agendada");
        }

        if (!List.of(SituacaoAgenda.DONE, SituacaoAgenda.CANCELED).contains(request.situacao())) {
            throw new BadRequestException("Situação inválida");
        }

        agenda.baixar(request);
        agendaRepository.save(agenda);
    }

    public void confirmAttendance(UUID id) {
        var agenda = agendaRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Agenda não encontrada"));

        if (agenda.canConfirmAttendance()) {
            throw new BadRequestException(
                    "Agenda só pode ser confirmada se ainda estiver " +
                    "agendada e dentro do prazo de confirmação (3 dias)"
            );
        }

        agenda.setAttendanceConfirmed(true);
        agendaRepository.save(agenda);
    }

    public void rescheduleAgenda(UUID id, RescheduleAgendaRequest request) {
        var agenda = agendaRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Agenda não encontrada"));

        if (!agenda.canReschedule()) {
            throw new BadRequestException("Agenda só pode ser reagendada se estiver atualmente agendada");
        }

        agenda.setData(request.data());
        agendaRepository.save(agenda);
    }
}
