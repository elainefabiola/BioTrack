# 🧪 Guia de Testes - BioTrack

## 📋 Estrutura de Testes

O projeto possui 3 camadas de testes:

### 1. **Testes de Repository** (Integração)
- **Localização:** `src/test/java/com/ProgWebII/biotrack/repository/`
- **Tipo:** Testes de integração com banco H2 em memória
- **Anotação:** `@DataJpaTest`
- **Arquivos:** `UserRepositoryTest.java`, `MeasureRepositoryTest.java`

### 2. **Testes de Service** (Unitários)
- **Localização:** `src/test/java/com/ProgWebII/biotrack/service/`
- **Tipo:** Testes unitários com mocks
- **Anotação:** `@ExtendWith(MockitoExtension.class)`
- **Arquivos:** `UserServiceTest.java`, `MeasureServiceTest.java`

### 3. **Testes de Controller** (Web)
- **Localização:** `src/test/java/com/ProgWebII/biotrack/controller/`
- **Tipo:** Testes de API com MockMvc
- **Anotação:** `@WebMvcTest`
- **Arquivos:** `UsuarioControllerTest.java`, `MeasureControllerTest.java`

---

## 🚀 Como Executar os Testes

### Executar todos os testes:
```bash
./mvnw test
```

### Executar testes de uma classe específica:
```bash
./mvnw test -Dtest=UserServiceTest
```

### Executar testes de um pacote:
```bash
# Repository
./mvnw test -Dtest=com.ProgWebII.biotrack.repository.*

# Service
./mvnw test -Dtest=com.ProgWebII.biotrack.service.*

# Controller
./mvnw test -Dtest=com.ProgWebII.biotrack.controller.*
```

---

## 📊 Cobertura de Código

### Gerar relatório de cobertura:
```bash
./mvnw clean test jacoco:report
```

### Visualizar o relatório:
Abra o arquivo: `target/site/jacoco/index.html`

### Verificar cobertura mínima (80%):
```bash
./mvnw verify
```

**Pacotes com cobertura exigida (80%):**
- ✅ `com.ProgWebII.biotrack.service`
- ✅ `com.ProgWebII.biotrack.controller`
- ✅ `com.ProgWebII.biotrack.repository`

**Pacotes excluídos:** `config`, `dto`, `model`, `BiotrackApplication`

---

## 📚 Tecnologias Utilizadas

| Tecnologia | Uso |
|------------|-----|
| **JUnit 5** | Framework de testes |
| **Mockito** | Mocks para testes unitários |
| **AssertJ** | Assertions fluentes |
| **MockMvc** | Testes de controllers |
| **Jacoco** | Cobertura de código |
| **H2 Database** | Banco em memória para testes |

---

## 🐛 Troubleshooting

**Testes falhando com erro de conexão:**
```bash
./mvnw test -Dspring.profiles.active=test
```

**Cobertura abaixo de 80%:**
```bash
./mvnw clean test jacoco:report
```
Verifique o relatório em `target/site/jacoco/index.html`

**Teste específico falhando:**
```bash
./mvnw test -Dtest=NomeDoTeste -X
```

---

## ✅ Checklist de Qualidade

- [x] Todos os testes passam
- [x] Cobertura > 80% em service, controller e repository
- [x] Testes de integração, unitários e web implementados
- [x] Validações e tratamento de exceções testados

---

**Desenvolvido com 🧪 e ✅ - 2025**
