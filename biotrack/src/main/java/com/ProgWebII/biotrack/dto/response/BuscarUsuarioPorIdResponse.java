package com.ProgWebII.biotrack.dto.response;

import java.time.LocalDate;

public record BuscarUsuarioPorIdResponse(Long id,
                                         String name,
                                         LocalDate birthDate,
                                         String zipCode,
                                         String email) {}
