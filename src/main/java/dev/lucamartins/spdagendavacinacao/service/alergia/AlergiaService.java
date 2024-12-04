package dev.lucamartins.spdagendavacinacao.service.alergia;

import dev.lucamartins.spdagendavacinacao.domain.alergia.Alergia;
import dev.lucamartins.spdagendavacinacao.domain.alergia.AlergiaRepository;
import dev.lucamartins.spdagendavacinacao.infra.exception.custom.BadRequestException;
import dev.lucamartins.spdagendavacinacao.infra.exception.custom.NotFoundException;
import dev.lucamartins.spdagendavacinacao.service.alergia.dto.AddAlergiaRequest;
import dev.lucamartins.spdagendavacinacao.service.alergia.dto.AlergiaView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AlergiaService {

    private final AlergiaRepository alergiaRepository;

    public void addAlergia(AddAlergiaRequest addAlergiaRequest) {
        var newAlergia = new Alergia(
                addAlergiaRequest
        );

        alergiaRepository.save(newAlergia);
    }

    public List<AlergiaView> getAlergias() {
        return alergiaRepository.findAll().stream()
                .map(AlergiaView::new)
                .toList();
    }

    public void deleteAlergia(UUID id) {
        var alergia = alergiaRepository
                .findById(id)
                .orElseThrow(NotFoundException::new);

        if (!alergia.canBeDeleted()) {
            throw new BadRequestException("Alergia só pode ser excluída se não estiver associada a nenhum usuário");
        }

        alergiaRepository.delete(alergia);
    }
}
