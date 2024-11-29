package dev.lucamartins.spdagendavacinacao.domain.vacina;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VacinaRepository extends JpaRepository<Vacina, UUID> {
}
