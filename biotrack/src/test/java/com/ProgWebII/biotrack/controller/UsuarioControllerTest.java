package com.ProgWebII.biotrack.controller;

import com.ProgWebII.biotrack.dto.request.UserRequest;
import com.ProgWebII.biotrack.dto.response.BuscarUsuarioPorIdResponse;
import com.ProgWebII.biotrack.dto.response.ListarTodosUsuariosResponse;
import com.ProgWebII.biotrack.repository.UserRepository;
import com.ProgWebII.biotrack.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
@DisplayName("Testes de Controller - UsuarioController")
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private UserRequest userRequest;

    @BeforeEach
    void setUp() {
        userRequest = new UserRequest(
                "João Silva",
                LocalDate.of(1990, 5, 15),
                "12345-678",
                "joao.silva@email.com",
                "Senha123"
        );
    }

    @Test
    @DisplayName("POST /usuarios - Deve criar usuário com sucesso")
    void deveCriarUsuarioComSucesso() throws Exception {
        doNothing().when(userService).createUser(any(UserRequest.class));
        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuário criado com sucesso!"));
        verify(userService, times(1)).createUser(any(UserRequest.class));
    }

    @Test
    @DisplayName("GET /usuarios - Deve listar todos os usuários")
    void deveListarTodosUsuarios() throws Exception {
        ListarTodosUsuariosResponse usuarioResponse = new ListarTodosUsuariosResponse(
                1L,
                "João Silva",
                LocalDate.of(1990, 5, 15),
                "12345-678",
                "joao.silva@email.com"
        );
        when(userService.listarTodos()).thenReturn(Arrays.asList(usuarioResponse));
        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("João Silva"));
        verify(userService, times(1)).listarTodos();
    }

    @Test
    @DisplayName("GET /usuarios/{id} - Deve buscar usuário por ID")
    void deveBuscarUsuarioPorId() throws Exception {
        BuscarUsuarioPorIdResponse response = new BuscarUsuarioPorIdResponse(
                1L,
                "João Silva",
                LocalDate.of(1990, 5, 15),
                "12345-678",
                "joao.silva@email.com"
        );
        when(userService.buscarPorId(1L)).thenReturn(response);
        mockMvc.perform(get("/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("João Silva"));
        verify(userService, times(1)).buscarPorId(1L);
    }

    @Test
    @DisplayName("DELETE /usuarios/{id} - Deve remover usuário")
    void deveRemoverUsuario() throws Exception {
        doNothing().when(userService).removerUsuario(1L);
        mockMvc.perform(delete("/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuário removido com sucesso!"));
        verify(userService, times(1)).removerUsuario(1L);
    }
}
