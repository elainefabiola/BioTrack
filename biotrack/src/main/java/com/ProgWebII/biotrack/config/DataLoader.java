package com.ProgWebII.biotrack.config;

import com.ProgWebII.biotrack.model.Measure;
import com.ProgWebII.biotrack.model.User;
import com.ProgWebII.biotrack.repository.MeasureRepository;
import com.ProgWebII.biotrack.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Configuration
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final MeasureRepository measureRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(UserRepository userRepository, MeasureRepository measureRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.measureRepository = measureRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if (userRepository.count() > 0) {
            System.out.println("Dados já existentes — DataLoader ignorado.");
            return;
        }

        //Usuários
        //gerando o hash através do passwordEncoder via injeção de deendencias pelo construtor
        User ana = new User(null, "Ana Costa", LocalDate.of(1992, 5, 10), "01001-000",
                "ana.costa@email.com", passwordEncoder.encode("123456"), null);
        User joao = new User(null, "João Silva", LocalDate.of(1988, 3, 22), "01310-200",
                "joao.silva@email.com", passwordEncoder.encode("123456"), null);
        User carla = new User(null, "Carla Mendes", LocalDate.of(1995, 11, 5), "04045-100",
                "carla.mendes@email.com", passwordEncoder.encode("123456") , null);

        userRepository.saveAll(Arrays.asList(ana, joao, carla));

        //Medidas associadas
        Measure m1 = new Measure(null, LocalDate.of(2024, 1, 10).atStartOfDay(), 68.5, 165.0, 70.0, 95.0, 88.0, 28.0, 27.5, 55.0, 54.5, 22.0, ana);
        Measure m2 = new Measure(null, LocalDate.of(2024, 6, 10).atStartOfDay(), 69.0, 165.0, 69.5, 94.5, 88.5, 28.5, 27.8, 55.2, 54.6, 21.8, ana);

        Measure m3 = new Measure(null, LocalDate.of(2024, 3, 20).atStartOfDay(), 82.0, 180.0, 90.0, 100.0, 98.0, 33.0, 32.0, 58.0, 57.5, 18.5, joao);
        Measure m4 = new Measure(null, LocalDate.of(2024, 8, 15).atStartOfDay(), 81.5, 180.0, 89.0, 99.0, 97.5, 33.2, 32.1, 57.9, 57.3, 18.2, joao);

        Measure m5 = new Measure(null, LocalDate.of(2024, 9, 5).atStartOfDay(), 59.0, 170.0, 65.0, 92.0, 84.0, 27.0, 26.8, 53.0, 52.5, 25.0, carla);

        List<Measure> medidas = Arrays.asList(m1, m2, m3, m4, m5);
        measureRepository.saveAll(medidas);

        System.out.println("Dados iniciais carregados com sucesso!");
    }
}
