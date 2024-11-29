package dev.lucamartins.spdagendavacinacao.domain.vacina;

import dev.lucamartins.spdagendavacinacao.domain.agenda.Agenda;
import dev.lucamartins.spdagendavacinacao.service.vacina.dto.AddVacinaRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "vacinas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Vacina {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "UUID")
    private UUID id;

    private String titulo;

    private String descricao;

    private Integer doses;

    @Enumerated(EnumType.STRING)
    private PeriodicidadeIntervaloDose periodicidade;

    private Integer intervalo;

    @OneToMany(mappedBy = "vacina", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Agenda> agendas;

    public Vacina(AddVacinaRequest addVacinaRequest) {
        this.titulo = addVacinaRequest.titulo();
        this.descricao = addVacinaRequest.descricao();
        this.doses = addVacinaRequest.doses();
        this.periodicidade = addVacinaRequest.periodicidade();
        this.intervalo = addVacinaRequest.intervalo();
    }

    public boolean canBeDeleted() {
        return agendas.isEmpty();
    }
}
