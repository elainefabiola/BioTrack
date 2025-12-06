package com.ProgWebII.biotrack.dto.response;

import java.time.LocalDate;
import java.util.List;

public record UsuarioComMedidasResponse(
        Long id,
        String name,
        LocalDate birthDate,
        String zipCode,
        String email,
        List<MedidaResponse> medidas
) {}
