package dev.lucamartins.spdagendavacinacao.infra.exception.custom;

import java.util.List;

public record CustomExceptionBody(
        Integer status,
        long timestamp,
        List<String> errorMessages
) {
    public CustomExceptionBody(Integer status, List<String> errorsMessages) {
        this(status, System.currentTimeMillis(), errorsMessages);
    }
}
