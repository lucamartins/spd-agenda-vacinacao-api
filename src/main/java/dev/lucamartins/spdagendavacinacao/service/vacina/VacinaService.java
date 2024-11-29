package dev.lucamartins.spdagendavacinacao.service.vacina;

import dev.lucamartins.spdagendavacinacao.domain.vacina.Vacina;
import dev.lucamartins.spdagendavacinacao.domain.vacina.VacinaRepository;
import dev.lucamartins.spdagendavacinacao.infra.exception.custom.BadRequestException;
import dev.lucamartins.spdagendavacinacao.infra.exception.custom.NotFoundException;
import dev.lucamartins.spdagendavacinacao.service.vacina.dto.AddVacinaRequest;
import dev.lucamartins.spdagendavacinacao.service.vacina.dto.VacinaView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VacinaService {

    private final VacinaRepository vacinaRepository;

    public void addVacina(AddVacinaRequest addVacinaRequest) {
        var newVacina = new Vacina(
                addVacinaRequest
        );

        vacinaRepository.save(newVacina);
    }

    public List<VacinaView> getVacinas() {
        return vacinaRepository.findAll().stream()
                .map(VacinaView::new)
                .toList();
    }

    public void deleteVacina(UUID id) {
        var vacina = vacinaRepository
                .findById(id)
                .orElseThrow(NotFoundException::new);

        if (!vacina.canBeDeleted()) {
            throw new BadRequestException("Vacina não pode ser deletada");
        }

        vacinaRepository.delete(vacina);
    }
}
