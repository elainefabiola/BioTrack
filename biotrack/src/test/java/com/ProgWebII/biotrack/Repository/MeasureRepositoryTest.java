package com.ProgWebII.biotrack.repository;

import com.ProgWebII.biotrack.model.Measure;
import com.ProgWebII.biotrack.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Testes de Integração - MeasureRepository")
class MeasureRepositoryTest {

    @Autowired
    private MeasureRepository measureRepository;

    @Autowired
    private TestEntityManager entityManager;

    private User usuarioTeste;
    private Measure medidaTeste;

    @BeforeEach
    void setUp() {
        measureRepository.deleteAll();
        usuarioTeste = User.builder()
                .name("João Silva")
                .birthDate(LocalDate.of(1990, 5, 15))
                .zipCode("12345-678")
                .email("joao.silva@email.com")
                .password("senhaHasheada123")
                .build();
        usuarioTeste = entityManager.persistAndFlush(usuarioTeste);

        medidaTeste = Measure.builder()
                .measurementDate(LocalDateTime.of(2024, 1, 15, 10, 30))
                .weightKg(75.5)
                .heightCm(175.0)
                .user(usuarioTeste)
                .build();
    }

    @Test
    @DisplayName("Deve salvar uma medida com sucesso")
    void deveSalvarMedidaComSucesso() {
        Measure medidaSalva = measureRepository.save(medidaTeste);
        assertThat(medidaSalva).isNotNull();
        assertThat(medidaSalva.getId()).isNotNull();
        assertThat(medidaSalva.getWeightKg()).isEqualTo(75.5);
    }

    @Test
    @DisplayName("Deve buscar medida por ID")
    void deveBuscarMedidaPorId() {
        Measure medidaSalva = entityManager.persistAndFlush(medidaTeste);
        Optional<Measure> resultado = measureRepository.findById(medidaSalva.getId());
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getWeightKg()).isEqualTo(75.5);
    }

    @Test
    @DisplayName("Deve deletar uma medida")
    void deveDeletarMedida() {
        Measure medidaSalva = entityManager.persistAndFlush(medidaTeste);
        measureRepository.deleteById(medidaSalva.getId());
        Optional<Measure> resultado = measureRepository.findById(medidaSalva.getId());
        assertThat(resultado).isEmpty();
    }
}
