package com.ProgWebII.biotrack.service;

import com.ProgWebII.biotrack.dto.response.MedidaResponse;
import com.ProgWebII.biotrack.dto.request.MeasureRequest;
import com.ProgWebII.biotrack.dto.response.UsuarioResponse;
import com.ProgWebII.biotrack.model.Measure;
import com.ProgWebII.biotrack.model.User;
import com.ProgWebII.biotrack.repository.MeasureRepository;
import com.ProgWebII.biotrack.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeasureService {

    private final MeasureRepository measureRepository;
    private final UserRepository userRepository;

    public MeasureService(MeasureRepository measureRepository, UserRepository userRepository) {
        this.measureRepository = measureRepository;
        this.userRepository = userRepository;
    }

    //Lista todas as medidas de um usuário específico
    public List<MedidaResponse> listarTodasAsMedidasDeUmUsuario(Long idUsuario) {
        User user = userRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        return user.getMeasures().stream()
                .map(this::mapToMedidaResponse)
                .toList();
    }

    //Busca uma medida específica de um usuário.
    public MedidaResponse buscarMedidaPorId(Long idUsuario, Long medidaId) {
        User user = userRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        Measure medida = user.getMeasures().stream()
                .filter(m -> m.getId().equals(medidaId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Medida não encontrada para este usuário"));

        return mapToMedidaResponse(medida);
    }

    //Converte entidade Measure → DTO MedidaResponse.
    private MedidaResponse mapToMedidaResponse(Measure m) {
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

    public void CreateMeasure(MeasureRequest measureRequest, Long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

            Measure measure = Measure.builder()
                    .measurementDate(measureRequest.measurementDate())
                    .weightKg(measureRequest.weightKg())
                    .heightCm(measureRequest.heightCm())
                    .waistCm(measureRequest.waistCm())
                    .hipCm(measureRequest.hipCm())
                    .chestCm(measureRequest.chestCm())
                    .armRightCm(measureRequest.armRightCm())
                    .armLeftCm(measureRequest.armLeftCm())
                    .thighRightCm(measureRequest.thighRightCm())
                    .thighLeftCm(measureRequest.thighLeftCm())
                    .bodyFatPercentage(measureRequest.bodyFatPercentage())
                    .user(user)
                    .build();
            measureRepository.save(measure);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar medida: " + e.getMessage());
        }
    }
    
    // Atualiza uma medida existente
    public void atualizarMedida(Long medidaId, MeasureRequest medidaRequest) {
        try {
            Measure medida = measureRepository.findById(medidaId)
                    .orElseThrow(() -> new EntityNotFoundException("Medida não encontrada com o ID: " + medidaId));
            
            // Atualiza os campos da medida com os novos valores
            medida.setMeasurementDate(medidaRequest.measurementDate());
            medida.setWeightKg(medidaRequest.weightKg());
            medida.setHeightCm(medidaRequest.heightCm());
            medida.setWaistCm(medidaRequest.waistCm());
            medida.setHipCm(medidaRequest.hipCm());
            medida.setChestCm(medidaRequest.chestCm());
            medida.setArmRightCm(medidaRequest.armRightCm());
            medida.setArmLeftCm(medidaRequest.armLeftCm());
            medida.setThighRightCm(medidaRequest.thighRightCm());
            medida.setThighLeftCm(medidaRequest.thighLeftCm());
            medida.setBodyFatPercentage(medidaRequest.bodyFatPercentage());
            
            // Salva as alterações
            measureRepository.save(medida);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar medida: " + e.getMessage());
        }
    }
    
    // Remove uma medida pelo ID
    public void removerMedida(Long medidaId) {
        try {
            // Verifica se a medida existe
            if (!measureRepository.existsById(medidaId)) {
                throw new EntityNotFoundException("Medida não encontrada com o ID: " + medidaId);
            }
            
            // Remove a medida
            measureRepository.deleteById(medidaId);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao remover medida: " + e.getMessage());
        }
    }
}
