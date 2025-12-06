package com.ProgWebII.biotrack.repository;

import com.ProgWebII.biotrack.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Testes de Integração - UserRepository")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    private User usuarioTeste;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        usuarioTeste = User.builder()
                .name("João Silva")
                .birthDate(LocalDate.of(1990, 5, 15))
                .zipCode("12345-678")
                .email("joao.silva@email.com")
                .password("senhaHasheada123")
                .build();
    }

    @Test
    @DisplayName("Deve salvar um usuário com sucesso")
    void deveSalvarUsuarioComSucesso() {
        User usuarioSalvo = userRepository.save(usuarioTeste);
        assertThat(usuarioSalvo).isNotNull();
        assertThat(usuarioSalvo.getId()).isNotNull();
        assertThat(usuarioSalvo.getName()).isEqualTo("João Silva");
    }

    @Test
    @DisplayName("Deve buscar usuário por ID")
    void deveBuscarUsuarioPorId() {
        User usuarioSalvo = entityManager.persistAndFlush(usuarioTeste);
        Optional<User> resultado = userRepository.findById(usuarioSalvo.getId());
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getName()).isEqualTo("João Silva");
    }

    @Test
    @DisplayName("Deve deletar um usuário")
    void deveDeletarUsuario() {
        User usuarioSalvo = entityManager.persistAndFlush(usuarioTeste);
        userRepository.deleteById(usuarioSalvo.getId());
        Optional<User> resultado = userRepository.findById(usuarioSalvo.getId());
        assertThat(resultado).isEmpty();
    }
}
