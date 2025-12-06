package com.ProgWebII.biotrack.service;

import com.ProgWebII.biotrack.dto.request.MeasureRequest;
import com.ProgWebII.biotrack.dto.response.MedidaResponse;
import com.ProgWebII.biotrack.model.Measure;
import com.ProgWebII.biotrack.model.User;
import com.ProgWebII.biotrack.repository.MeasureRepository;
import com.ProgWebII.biotrack.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes Unitários - MeasureService")
class MeasureServiceTest {

    @Mock
    private MeasureRepository measureRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MeasureService measureService;

    private User usuarioTeste;
    private Measure medidaTeste;
    private MeasureRequest measureRequest;

    @BeforeEach
    void setUp() {
        usuarioTeste = User.builder()
                .id(1L)
                .name("João Silva")
                .birthDate(LocalDate.of(1990, 5, 15))
                .zipCode("12345-678")
                .email("joao.silva@email.com")
                .password("senhaHasheada123")
                .measures(new ArrayList<>())
                .build();

        medidaTeste = Measure.builder()
                .id(1L)
                .measurementDate(LocalDateTime.of(2024, 1, 15, 10, 30))
                .weightKg(75.5)
                .heightCm(175.0)
                .user(usuarioTeste)
                .build();

        measureRequest = new MeasureRequest(
                LocalDateTime.of(2024, 1, 15, 10, 30),
                75.5,
                175.0,
                null, null, null, null, null, null, null, null
        );
    }

    @Test
    @DisplayName("Deve criar medida com sucesso")
    void deveCriarMedidaComSucesso() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(usuarioTeste));
        when(measureRepository.save(any(Measure.class))).thenReturn(medidaTeste);
        measureService.CreateMeasure(measureRequest, 1L);
        verify(userRepository, times(1)).findById(1L);
        verify(measureRepository, times(1)).save(any(Measure.class));
    }

    @Test
    @DisplayName("Deve buscar medida específica por ID")
    void deveBuscarMedidaEspecificaPorId() {
        usuarioTeste.setMeasures(Arrays.asList(medidaTeste));
        when(userRepository.findById(1L)).thenReturn(Optional.of(usuarioTeste));
        MedidaResponse resultado = measureService.buscarMedidaPorId(1L, 1L);
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getWeightKg()).isEqualTo(75.5);
    }

    @Test
    @DisplayName("Deve remover medida com sucesso")
    void deveRemoverMedidaComSucesso() {
        when(measureRepository.existsById(1L)).thenReturn(true);
        doNothing().when(measureRepository).deleteById(1L);
        measureService.removerMedida(1L);
        verify(measureRepository, times(1)).existsById(1L);
        verify(measureRepository, times(1)).deleteById(1L);
    }
}
