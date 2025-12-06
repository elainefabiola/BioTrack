package com.ProgWebII.biotrack.service;

import com.ProgWebII.biotrack.dto.request.UserRequest;
import com.ProgWebII.biotrack.dto.response.BuscarUsuarioPorIdResponse;
import com.ProgWebII.biotrack.model.User;
import com.ProgWebII.biotrack.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes Unitários - UserService")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User usuarioTeste;
    private UserRequest userRequest;

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

        userRequest = new UserRequest(
                "João Silva",
                LocalDate.of(1990, 5, 15),
                "12345-678",
                "joao.silva@email.com",
                "senha123"
        );
    }

    @Test
    @DisplayName("Deve criar usuário com sucesso")
    void deveCriarUsuarioComSucesso() {
        when(passwordEncoder.encode(anyString())).thenReturn("senhaHasheada123");
        when(userRepository.save(any(User.class))).thenReturn(usuarioTeste);
        userService.createUser(userRequest);
        verify(passwordEncoder, times(1)).encode("senha123");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Deve buscar usuário por ID")
    void deveBuscarUsuarioPorId() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(usuarioTeste));
        BuscarUsuarioPorIdResponse resultado = userService.buscarPorId(1L);
        assertThat(resultado).isNotNull();
        assertThat(resultado.name()).isEqualTo("João Silva");
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar usuário inexistente")
    void deveLancarExcecaoAoBuscarUsuarioInexistente() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userService.buscarPorId(999L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Usuário não encontrado");
    }

    @Test
    @DisplayName("Deve remover usuário")
    void deveRemoverUsuario() {
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);
        userService.removerUsuario(1L);
        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }
}
