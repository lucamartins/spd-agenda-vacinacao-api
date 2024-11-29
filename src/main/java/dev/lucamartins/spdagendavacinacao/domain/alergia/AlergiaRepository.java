package dev.lucamartins.spdagendavacinacao.domain.alergia;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AlergiaRepository extends JpaRepository<Alergia, UUID> {
}
