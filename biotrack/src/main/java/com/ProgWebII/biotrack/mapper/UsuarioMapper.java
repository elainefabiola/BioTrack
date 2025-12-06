package com.ProgWebII.biotrack.mapper;

import com.ProgWebII.biotrack.dto.response.MedidaResponse;
import com.ProgWebII.biotrack.dto.response.UsuarioResponse;
import com.ProgWebII.biotrack.model.Measure;
import com.ProgWebII.biotrack.model.User;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {


    public UsuarioResponse toResponse(User user) {
        return new UsuarioResponse(
                user.getId(),
                user.getName(),
                user.getBirthDate(),
                user.getZipCode(),
                user.getEmail(),
                user.getMeasures().stream()
                        .map(this::toMedidaResponse)
                        .toList()
        );
    }

    private MedidaResponse toMedidaResponse(Measure m) {
        return new MedidaResponse(
                m.getId(),
                m.getMeasurementDate(),
                m.getWeightKg(),
                m.getHeightCm(),
                m.getWaistCm(),
                m.getHipCm(),
                m.getChestCm(),
                m.getArmRightCm(),
                m.getArmLeftCm(),
                m.getThighRightCm(),
                m.getThighLeftCm(),
                m.getBodyFatPercentage()
        );
    }
}
