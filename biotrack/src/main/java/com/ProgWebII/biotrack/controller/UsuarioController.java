package com.ProgWebII.biotrack.controller;

import com.ProgWebII.biotrack.controller.documentation.UsuarioControllerDocs;
import com.ProgWebII.biotrack.dto.response.BuscarUsuarioPorIdResponse;
import com.ProgWebII.biotrack.dto.response.ListarTodosUsuariosResponse;
import com.ProgWebII.biotrack.dto.response.UsuarioResponse;
import com.ProgWebII.biotrack.dto.response.UsuarioSemMedidasResponse;
import com.ProgWebII.biotrack.mapper.UsuarioMapper;
import com.ProgWebII.biotrack.model.Imc;
import com.ProgWebII.biotrack.repository.UserRepository;
import com.ProgWebII.biotrack.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.ProgWebII.biotrack.dto.request.UserRequest;
import com.ProgWebII.biotrack.dto.request.UserPatchRequest;

@RestController
@RequestMapping("/usuarios")/*
mapeamento de rota base (ou endpoint base) de um controller REST no Spring Boot.*/
public class UsuarioController implements UsuarioControllerDocs {

    private final UserRepository userRepository;
    private final UserService userService;
    private final Imc imc;
    private final UsuarioMapper usuarioMapper;

    public UsuarioController(UserRepository userRepository, UserService userService, Imc imc, UsuarioMapper usuarioMapper) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.imc = imc;
        this.usuarioMapper = usuarioMapper;
    }

    @PostMapping()
    public ResponseEntity<String> criarUsuario(@RequestBody UserRequest userRequest) {
        userService.createUser(userRequest);
        return ResponseEntity.ok("Usuário criado com sucesso!");
    }

    @GetMapping("/filtro-imc")
    public ResponseEntity<List<UsuarioResponse>> filtrarUsuariosPorImc(@RequestParam String faixa) {

        List<UsuarioResponse> usuariosFiltrados = userRepository.findAll().stream()
                .filter(user -> {
                    Double imcUsuario = imc.obterImcUsuario(user.getId());
                    if (imcUsuario == null) return false;

                    String faixaUsuario = imc.classificarFaixaImc(imcUsuario);
                    return faixaUsuario != null && faixaUsuario.equalsIgnoreCase(faixa);
                })
                .map(usuarioMapper::toResponse)
                .sorted((a, b) -> a.id().compareTo(b.id()))
                .toList();

        return ResponseEntity.ok(usuariosFiltrados);
    }

    //GET /usuarios → lista todos (sem medidas)
    @GetMapping
    public ResponseEntity<List<ListarTodosUsuariosResponse>> listarTodos() {
        return ResponseEntity.ok(userService.listarTodos());
    }

    //GET /usuarios/{id} → busca usuário por ID (sem medidas)
    @GetMapping("/{id}")
    public ResponseEntity<BuscarUsuarioPorIdResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(userService.buscarPorId(id));
    }

    //GET /usuarios/sem-medidas → lista todos os usuários sem medidas
    @GetMapping("/sem-medidas")
    public ResponseEntity<List<UsuarioSemMedidasResponse>> listarTodosSemMedidas() {
        return ResponseEntity.ok(userService.listarUsuariosSemMedidas());
    }

    //GET /usuarios/{id}/todas-medidas → usuário + todas as medidas
    @GetMapping("/{id}/todas-medidas")
    public ResponseEntity<UsuarioResponse> trazerUsuarioComTodasMedidas(@PathVariable Long id) {
        return ResponseEntity.ok(userService.trazerUsuarioPorIdComTodasAsMedidas(id));
    }

    //GET /usuarios/{id}/ultima-medida → usuário + última medida
    @GetMapping("/{id}/ultima-medida")
    public ResponseEntity<UsuarioResponse> trazerUsuarioComUltimaMedida(@PathVariable Long id) {
        return ResponseEntity.ok(userService.trazerUsuarioPorIdComUltimaMedida(id));
    }

    
    // PUT /usuarios/{id} → atualiza completamente um usuário
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarUsuario(
            @PathVariable Long id,
            @RequestBody UserRequest userRequest) {
        userService.atualizarUsuario(id, userRequest);
        return ResponseEntity.ok("Usuário atualizado com sucesso!");
    }
    
    // PATCH /usuarios/{id} → atualiza parcialmente um usuário
    @PatchMapping("/{id}")
    public ResponseEntity<String> atualizarParcialUsuario(
            @PathVariable Long id,
            @RequestBody UserPatchRequest userPatchRequest) {
        userService.atualizarParcialUsuario(id, userPatchRequest);
        return ResponseEntity.ok("Usuário atualizado parcialmente com sucesso!");
    }
    
    // DELETE /usuarios/{id} → remove um usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removerUsuario(@PathVariable Long id) {
        userService.removerUsuario(id);
        return ResponseEntity.ok("Usuário removido com sucesso!");
    }
}

