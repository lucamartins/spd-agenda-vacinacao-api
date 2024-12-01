package dev.lucamartins.spdagendavacinacao.domain.agenda;

import dev.lucamartins.spdagendavacinacao.domain.usuario.Usuario;
import dev.lucamartins.spdagendavacinacao.domain.vacina.Vacina;
import dev.lucamartins.spdagendavacinacao.service.agenda.dto.AddAgendaRequest;
import dev.lucamartins.spdagendavacinacao.service.agenda.dto.BaixaAgendaRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "agendas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Agenda {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "UUID")
    private UUID id;

    private OffsetDateTime data;

    @Enumerated(EnumType.STRING)
    private SituacaoAgenda situacao;

    @Column(nullable = true)
    private OffsetDateTime dataSituacao;

    private String observacoes;

    @ManyToOne
    private Vacina vacina;

    @ManyToOne
    private Usuario usuario;

    public Agenda(AddAgendaRequest addAgendaRequest, Vacina vacina, Usuario usuario, OffsetDateTime data) {
        this.data = data;
        this.observacoes = addAgendaRequest.observacoes();
        this.vacina = vacina;
        this.usuario = usuario;
        this.situacao = SituacaoAgenda.SCHEDULED;
        this.dataSituacao = null;
    }

    public boolean canBeDeleted() {
        return false;
    }

    public boolean canBeBaixada() {
        return situacao == SituacaoAgenda.SCHEDULED;
    }

    public void baixar(BaixaAgendaRequest request) {
        this.situacao = request.situacao();
        this.dataSituacao = OffsetDateTime.now();
    }
}
