package com.ProgWebII.biotrack.service;

import com.ProgWebII.biotrack.dto.request.UserRequest;
import com.ProgWebII.biotrack.dto.request.UserPatchRequest;
import com.ProgWebII.biotrack.dto.response.*;
import com.ProgWebII.biotrack.model.Measure;
import com.ProgWebII.biotrack.model.User;
import com.ProgWebII.biotrack.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(UserRequest userRequest) {
        try{
            User user = User.builder()
                .name(userRequest.name())
                .birthDate(userRequest.birthDate())
                .zipCode(userRequest.zipCode())
                .email(userRequest.email())
                .password(hashPassword(userRequest.password())) // O hash aqui
                .build(); // Finaliza a construção do objeto
            userRepository.save(user);
        } catch (Exception e) {
            System.err.println("Erro ao criar usuário: " + e.getMessage());
            throw new RuntimeException("Falha ao processar a criação do usuário.");
        }
    }
    private String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    //Lista todos os usuários (sem medidas)
    public List<ListarTodosUsuariosResponse> listarTodos() {
        List<User> usuarios = userRepository.findAll();

        if (usuarios.isEmpty()) {
            throw new EntityNotFoundException("Nenhum usuário encontrado no sistema.");
        }

        return usuarios.stream()
                .filter(Objects::nonNull)
                .map(user -> new ListarTodosUsuariosResponse(
                        user.getId(),
                        user.getName(),
                        user.getBirthDate(),
                        user.getZipCode(),
                        user.getEmail()
                ))
                .toList();
    }

    //Busca um usuário por ID (sem medidas)
    public BuscarUsuarioPorIdResponse buscarPorId(Long id) {
        validarId(id, "ID do usuário");

        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o ID: " + id));

        validarCamposObrigatorios(user);

        return new BuscarUsuarioPorIdResponse(
                user.getId(),
                user.getName(),
                user.getBirthDate(),
                user.getZipCode(),
                user.getEmail()
        );
    }
    public List<UsuarioSemMedidasResponse> listarUsuariosSemMedidas() {
        return userRepository.findUsersWithoutMeasures()
                .stream()
                .map(user -> new UsuarioSemMedidasResponse(
                        user.getId(),
                        user.getName(),
                        user.getBirthDate(),
                        user.getZipCode(),
                        user.getEmail()
                ))
                .toList();
    }

    //Traz um usuário com todas as suas medidas
    public UsuarioResponse trazerUsuarioPorIdComTodasAsMedidas(Long idUsuario) {
        validarId(idUsuario, "ID do usuário");

        User user = userRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o ID: " + idUsuario));

        validarCamposObrigatorios(user);

        List<Measure> medidasUsuario = user.getMeasures();
        if (medidasUsuario == null || medidasUsuario.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma medida encontrada para o usuário: " + user.getName());
        }

        List<MedidaResponse> medidas = medidasUsuario.stream()
                .filter(Objects::nonNull)
                .map(this::mapToMedidaResponse)
                .toList();

        return new UsuarioResponse(
                user.getId(),
                user.getName(),
                user.getBirthDate(),
                user.getZipCode(),
                user.getEmail(),
                medidas
        );
    }

    //Traz um usuário com apenas a sua última medida
    public UsuarioResponse trazerUsuarioPorIdComUltimaMedida(Long idUsuario) {
        validarId(idUsuario, "ID do usuário");

        User user = userRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o ID: " + idUsuario));

        validarCamposObrigatorios(user);

        Measure ultima = user.getMeasures().stream()
                .filter(Objects::nonNull)
                .max(Comparator.comparing(Measure::getMeasurementDate))
                .orElse(null);

        if (ultima == null) {
            throw new EntityNotFoundException("Nenhuma medida registrada para o usuário " + user.getName());
        }

        List<MedidaResponse> ultimaMedida = List.of(mapToMedidaResponse(ultima));

        return new UsuarioResponse(
                user.getId(),
                user.getName(),
                user.getBirthDate(),
                user.getZipCode(),
                user.getEmail(),
                ultimaMedida
        );
    }

    // Conversão de entidade → DTO (com validação de campos nulos)
    private MedidaResponse mapToMedidaResponse(Measure m) {
        Assert.notNull(m, "A medida não pode ser nula.");
        Assert.notNull(m.getMeasurementDate(), "Data da medição não pode ser nula.");

        return new MedidaResponse(
                m.getId(),
                m.getMeasurementDate(),
                m.getWeightKg(),
                m.getHeightCm(),
                m.getWaistCm(),
                m.getHipCm(),
                m.getChestCm(),
                m.getArmRightCm(),
                m.getArmLeftCm(),
                m.getThighRightCm(),
                m.getThighLeftCm(),
                m.getBodyFatPercentage()
        );
    }

    //Métodos auxiliares de validação
    private void validarId(Long id, String nomeCampo) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(nomeCampo + " deve ser um número positivo e não nulo.");
        }
    }

    // Atualiza um usuário existente
    public void atualizarUsuario(Long id, UserRequest userRequest) {
        validarId(id, "ID do usuário");
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o ID: " + id));
        
        // Atualiza os campos do usuário com os novos valores
        user.setName(userRequest.name());
        user.setBirthDate(userRequest.birthDate());
        user.setZipCode(userRequest.zipCode());
        user.setEmail(userRequest.email());
        
        // Se a senha foi fornecida, atualiza a senha com hash
        if (userRequest.password() != null && !userRequest.password().isBlank()) {
            user.setPassword(hashPassword(userRequest.password()));
        }
        
        // Valida os campos obrigatórios
        validarCamposObrigatorios(user);
        
        // Salva as alterações
        userRepository.save(user);
    }
    
    // Atualiza parcialmente um usuário existente (PATCH)
    public void atualizarParcialUsuario(Long id, UserPatchRequest userPatchRequest) {
        validarId(id, "ID do usuário");
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o ID: " + id));
        
        // Atualiza apenas os campos fornecidos (não nulos)
        if (userPatchRequest.name() != null && !userPatchRequest.name().isBlank()) {
            user.setName(userPatchRequest.name());
        }
        
        if (userPatchRequest.birthDate() != null) {
            user.setBirthDate(userPatchRequest.birthDate());
        }
        
        if (userPatchRequest.zipCode() != null && !userPatchRequest.zipCode().isBlank()) {
            user.setZipCode(userPatchRequest.zipCode());
        }
        
        if (userPatchRequest.email() != null && !userPatchRequest.email().isBlank()) {
            user.setEmail(userPatchRequest.email());
        }
        
        // Se a senha foi fornecida, atualiza a senha com hash
        if (userPatchRequest.password() != null && !userPatchRequest.password().isBlank()) {
            user.setPassword(hashPassword(userPatchRequest.password()));
        }
        
        // Valida os campos obrigatórios após a atualização parcial
        validarCamposObrigatorios(user);
        
        // Salva as alterações
        userRepository.save(user);
    }
    
    // Remove um usuário pelo ID
    public void removerUsuario(Long id) {
        validarId(id, "ID do usuário");
        
        // Verifica se o usuário existe
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário não encontrado com o ID: " + id);
        }
        
        // Remove o usuário
        userRepository.deleteById(id);
    }
    
    private void validarCamposObrigatorios(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            throw new IllegalArgumentException("O nome do usuário é obrigatório.");
        }
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new IllegalArgumentException("O e-mail do usuário é obrigatório.");
        }
        if (user.getBirthDate() == null) {
            throw new IllegalArgumentException("A data de nascimento é obrigatória.");
        }
    }
}
