package com.ProgWebII.biotrack.controller;

import com.ProgWebII.biotrack.dto.request.MeasureRequest;
import com.ProgWebII.biotrack.dto.response.MedidaResponse;
import com.ProgWebII.biotrack.service.MeasureService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MeasureController.class)
@DisplayName("Testes de Controller - MeasureController")
class MeasureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MeasureService measureService;

    private MeasureRequest measureRequest;

    @BeforeEach
    void setUp() {
        measureRequest = new MeasureRequest(
                LocalDateTime.of(2024, 1, 15, 10, 30),
                75.5,
                175.0,
                null, null, null, null, null, null, null, null
        );
    }

    @Test
    @DisplayName("POST /medidas/{userId} - Deve criar medida com sucesso")
    void deveCriarMedidaComSucesso() throws Exception {
        doNothing().when(measureService).CreateMeasure(any(MeasureRequest.class), eq(1L));
        mockMvc.perform(post("/medidas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(measureRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Medida criada com sucesso!"));
        verify(measureService, times(1)).CreateMeasure(any(MeasureRequest.class), eq(1L));
    }

    @Test
    @DisplayName("GET /medidas/{usuarioId}/medidas/{medidaId} - Deve buscar medida específica")
    void deveBuscarMedidaEspecifica() throws Exception {
        MedidaResponse medidaResponse = new MedidaResponse(
                1L,
                LocalDateTime.of(2024, 1, 15, 10, 30),
                75.5,
                175.0,
                null, null, null, null, null, null, null
        );
        when(measureService.buscarMedidaPorId(1L, 1L)).thenReturn(medidaResponse);
        mockMvc.perform(get("/medidas/1/medidas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.weightKg").value(75.5));
        verify(measureService, times(1)).buscarMedidaPorId(1L, 1L);
    }

    @Test
    @DisplayName("DELETE /medidas/{medidaId} - Deve remover medida com sucesso")
    void deveRemoverMedidaComSucesso() throws Exception {
        doNothing().when(measureService).removerMedida(1L);
        mockMvc.perform(delete("/medidas/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Medida removida com sucesso!"));
        verify(measureService, times(1)).removerMedida(1L);
    }
}
