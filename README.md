# 🧪 Guia de Testes - BioTrack

## 📚 Conceitos Básicos de Testes

### O que são testes de software?
Testes verificam se o código funciona corretamente. Eles garantem que:
- ✅ O código faz o que deveria fazer
- ✅ Funciona mesmo após mudanças
- ✅ Detecta erros antes de ir para produção

### Padrão AAA (Arrange, Act, Assert)
Todos os testes seguem este padrão:
1. **Arrange (Given)**: Prepara os dados necessários
2. **Act (When)**: Executa a ação que queremos testar
3. **Assert (Then)**: Verifica se o resultado está correto

### Tipos de Testes no Projeto

#### 1. Testes de Repository (Integração)
- **O que testam?** A interação com o banco de dados
- **Como funcionam?** Usam um banco H2 em memória (não precisa de banco real)
- **São rápidos?** Não, mas garantem que o repository funciona

#### 2. Testes de Service (Unitários)
- **O que testam?** A lógica de negócio do service
- **Como funcionam?** Usam **Mocks** (objetos falsos que simulam dependências)
- **São rápidos?** Sim! Não dependem de banco ou outras classes

#### 3. Testes de Controller (Web)
- **O que testam?** Os endpoints HTTP (GET, POST, PUT, DELETE)
- **Como funcionam?** Simulam requisições HTTP sem subir o servidor
- **São rápidos?** Sim! Testam apenas a camada web

---

## 📋 Estrutura de Testes

O projeto possui 3 camadas de testes:

### 1. **Testes de Repository** (Integração)
- **Localização:** `src/test/java/com/ProgWebII/biotrack/repository/`
- **Anotação:** `@DataJpaTest`
- **Arquivos:** `UserRepositoryTest.java`, `MeasureRepositoryTest.java`
- **O que testam:** Salvar, buscar, atualizar e deletar no banco

### 2. **Testes de Service** (Unitários)
- **Localização:** `src/test/java/com/ProgWebII/biotrack/service/`
- **Anotação:** `@ExtendWith(MockitoExtension.class)`
- **Arquivos:** `UserServiceTest.java`, `MeasureServiceTest.java`
- **O que testam:** Lógica de negócio usando mocks

### 3. **Testes de Controller** (Web)
- **Localização:** `src/test/java/com/ProgWebII/biotrack/controller/`
- **Anotação:** `@WebMvcTest`
- **Arquivos:** `UsuarioControllerTest.java`, `MeasureControllerTest.java`
- **O que testam:** Endpoints HTTP e respostas JSON

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

| Tecnologia | O que faz? |
|------------|------------|
| **JUnit 5** | Framework para escrever e executar testes |
| **Mockito** | Cria mocks (objetos falsos) para testes unitários |
| **AssertJ** | Facilita fazer verificações (assertions) no código |
| **MockMvc** | Simula requisições HTTP para testar controllers |
| **Jacoco** | Mede quantas linhas do código foram testadas |
| **H2 Database** | Banco de dados em memória para testes |

---

## 🎓 Exemplos Práticos

### Exemplo 1: Teste de Repository
```java
@Test
void deveSalvarUsuarioComSucesso() {
    // When - Executa a ação
    User usuarioSalvo = userRepository.save(usuarioTeste);
    
    // Then - Verifica o resultado
    assertThat(usuarioSalvo).isNotNull();
    assertThat(usuarioSalvo.getId()).isNotNull();
}
```

### Exemplo 2: Teste de Service com Mock
```java
@Test
void deveCriarUsuarioComSucesso() {
    // Given - Configura o mock
    when(userRepository.save(any(User.class))).thenReturn(usuarioTeste);
    
    // When - Executa o método
    userService.createUser(userRequest);
    
    // Then - Verifica se foi chamado
    verify(userRepository, times(1)).save(any(User.class));
}
```

### Exemplo 3: Teste de Controller
```java
@Test
void deveCriarUsuarioComSucesso() throws Exception {
    // When & Then - Simula requisição HTTP
    mockMvc.perform(post("/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userRequest)))
        .andExpect(status().isOk());
}
```

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

**Desenvolvedora Elaine Soares ✅ - 2025**
