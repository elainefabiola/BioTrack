package com.ProgWebII.biotrack.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;
import lombok.Setter;

@Entity
@Table(name = "tb_users") // Boa prática para evitar conflitos com palavras reservadas
@Data // Gera Getters, Setters, toString, equals e hashCode
@Builder // Permite o uso do padrão Builder para criar instâncias
@NoArgsConstructor // Construtor sem argumentos
@AllArgsConstructor // Construtor com todos os argumentos
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id; // Chave primária auto-incrementada

  @Column(nullable = false, length = 100)
  private String name;

  @Column(name = "birth_date", nullable = false)
  private LocalDate birthDate; // Data de Nascimento

  @Column(length = 9) // Ex: "00000-000"
  private String zipCode; // CEP

  @Column(unique = true, nullable = false, length = 100)
  private String email;


  @Column(nullable = false)
  private String password; // A senha deve ser armazenada como um hash (criptografada)!

  // Relacionamento One-to-Many: Um Usuário tem muitas Medidas.
  // 'mappedBy' indica o campo na classe Measures que detém a chave estrangeira.
  // 'CascadeType.ALL' fará com que medidas sejam excluídas se o usuário for excluído.
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Measure> measures;
}