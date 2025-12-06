package com.ProgWebII.biotrack.dto.response;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class MedidaResponse {
    private Long id;
    private LocalDateTime measurementDate;
    private Double weightKg;
    private Double heightCm;
    private Double waistCm;
    private Double hipCm;
    private Double chestCm;
    private Double armRightCm;
    private Double armLeftCm;
    private Double thighRightCm;
    private Double thighLeftCm;
    private Double bodyFatPercentage;

    public MedidaResponse(Long id, LocalDateTime measurementDate, Double weightKg, Double heightCm,
                         Double waistCm, Double hipCm, Double chestCm, Double armRightCm,
                         Double armLeftCm, Double thighRightCm, Double thighLeftCm, Double bodyFatPercentage) {
        this.id = id;
        this.measurementDate = measurementDate;
        this.weightKg = weightKg;
        this.heightCm = heightCm;
        this.waistCm = waistCm;
        this.hipCm = hipCm;
        this.chestCm = chestCm;
        this.armRightCm = armRightCm;
        this.armLeftCm = armLeftCm;
        this.thighRightCm = thighRightCm;
        this.thighLeftCm = thighLeftCm;
        this.bodyFatPercentage = bodyFatPercentage;
    }
}
