package dev.lucamartins.spdagendavacinacao.domain.agenda;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface AgendaRepository extends JpaRepository<Agenda, UUID> {
    @Query("SELECT a FROM Agenda a WHERE "
            + "(COALESCE(:situacao, NULL) IS NULL OR a.situacao = :situacao) AND "
            + "(COALESCE(:dataStart, NULL) IS NULL OR a.data >= :dataStart) AND "
            + "(COALESCE(:dataEnØd, NULL) IS NULL OR a.data <= :dataEnd) AND "
            + "(COALESCE(:usuarioId, NULL) IS NULL OR a.usuario.id = :usuarioId)")
    List<Agenda> findAgendasByFilters(
            @Param("situacao") SituacaoAgenda situacao,
            @Param("dataStart") OffsetDateTime dataStart,
            @Param("dataEnd") OffsetDateTime dataEnd,
            @Param("usuarioId") UUID usuarioId
    );
}
