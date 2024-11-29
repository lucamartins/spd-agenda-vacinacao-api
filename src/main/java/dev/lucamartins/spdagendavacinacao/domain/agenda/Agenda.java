package dev.lucamartins.spdagendavacinacao.domain.agenda;

import dev.lucamartins.spdagendavacinacao.domain.usuario.Usuario;
import dev.lucamartins.spdagendavacinacao.domain.vacina.Vacina;
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

    private OffsetDateTime dataSituacao;

    private String observacoes;

    @ManyToOne
    private Vacina vacina;

    @ManyToOne
    private Usuario usuario;
}