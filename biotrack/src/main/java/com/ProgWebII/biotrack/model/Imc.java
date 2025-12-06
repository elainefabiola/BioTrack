package com.ProgWebII.biotrack.model;

import com.ProgWebII.biotrack.repository.MeasureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Imc {

    @Autowired
    private MeasureRepository measuresRepository;

    public Double calcularImc(Double weightKg, Double heightCm) {
        if (weightKg == null || heightCm == null || weightKg <= 0 || heightCm <= 0) {
            return null;
        }
        
        double heightM = heightCm / 100.0;
        return weightKg / (heightM * heightM);
    }
    
    public String classificarFaixaImc(Double imc) {
        if (imc == null || imc <= 0) {
            return null;
        }
        
        if (imc < 18.5) {
            return "Abaixo do Peso";
        } else if (imc < 25) {
            return "Peso Normal";
        } else if (imc < 30) {
            return "Sobrepeso";
        } else {
            return "Obesidade";
        }
    }
    
    public Double obterImcUsuario(Long userId) {
        var medidaMaisRecente = measuresRepository.findTopByUserIdOrderByMeasurementDateDesc(userId);
        
        if (medidaMaisRecente == null ||
            medidaMaisRecente.getWeightKg() == null ||
            medidaMaisRecente.getHeightCm() == null) {
            return null;
        }

        return calcularImc(medidaMaisRecente.getWeightKg(), medidaMaisRecente.getHeightCm());

    }
}

