package dev.lucamartins.spdagendavacinacao.service.agenda.dto;

import dev.lucamartins.spdagendavacinacao.domain.agenda.Agenda;
import dev.lucamartins.spdagendavacinacao.domain.agenda.SituacaoAgenda;
import dev.lucamartins.spdagendavacinacao.service.usuario.dto.UsuarioView;
import dev.lucamartins.spdagendavacinacao.service.vacina.dto.VacinaView;

import java.time.OffsetDateTime;
import java.util.UUID;

public record AgendaView(
        UUID id,
        OffsetDateTime data,
        SituacaoAgenda situacao,
        OffsetDateTime dataSituacao,
        String observacoes,
        Integer doseIdx,
        VacinaView vacina,
        UsuarioView usuario
) {
    public AgendaView(Agenda agenda) {
        this(
                agenda.getId(),
                agenda.getData(),
                agenda.getSituacao(),
                agenda.getDataSituacao(),
                agenda.getObservacoes(),
                agenda.getDoseIdx(),
                new VacinaView(agenda.getVacina()),
                new UsuarioView(agenda.getUsuario())
        );
    }
}
