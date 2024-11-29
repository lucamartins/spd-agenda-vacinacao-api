package dev.lucamartins.spdagendavacinacao.domain.agenda;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AgendaRepository extends JpaRepository<Agenda, UUID> {
}
