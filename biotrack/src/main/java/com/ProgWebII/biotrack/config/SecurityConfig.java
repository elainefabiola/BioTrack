package com.ProgWebII.biotrack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  /** define que spring security permita todos os acessos
   * @return SecurityFilterChain
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
    return http.build();
  }

  /**
   * Define o BCryptPasswordEncoder como o bean padrão para codificação de senha.
   * @return A implementação de PasswordEncoder (BCryptPasswordEncoder).
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    // Usa o fator de força padrão (cost) 10, que é seguro para a maioria dos casos.
    return new BCryptPasswordEncoder();
  }
}