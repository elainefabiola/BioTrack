package com.ProgWebII.biotrack.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.DecimalMin;

import java.time.LocalDateTime;

public record MeasureRequest (
    @NotNull(message = "A data da medição é obrigatória.")
    @PastOrPresent(message = "A data da medição não pode ser no futuro.")
    LocalDateTime measurementDate,

    @NotNull(message = "O peso é obrigatório.")
    @DecimalMin(value = "0.1", message = "O peso deve ser um valor positivo.")
    Double weightKg,
    @DecimalMin(value = "0.1", message = "A altura deve ser um valor positivo.")
    Double heightCm,

    @DecimalMin(value = "0.1", message = "O valor da cintura deve ser positivo.")
    Double waistCm,

    @DecimalMin(value = "0.1", message = "O valor do quadril deve ser positivo.")
    Double hipCm,

    @DecimalMin(value = "0.1", message = "O valor do peito/tórax deve ser positivo.")
    Double chestCm,

    @DecimalMin(value = "0.1", message = "O valor do braço direito deve ser positivo.")
    Double armRightCm,

    @DecimalMin(value = "0.1", message = "O valor do braço esquerdo deve ser positivo.")
    Double armLeftCm,

    @DecimalMin(value = "0.1", message = "O valor da coxa direita deve ser positivo.")
    Double thighRightCm,

    @DecimalMin(value = "0.1", message = "O valor da coxa esquerda deve ser positivo.")
    Double thighLeftCm,

    @DecimalMin(value = "0.0", inclusive = true, message = "A porcentagem de gordura corporal não pode ser negativa.")
    Double bodyFatPercentage // Não precisa de @DecimalMax, a validação de domínio seria feita na camada de serviço.
){

}
