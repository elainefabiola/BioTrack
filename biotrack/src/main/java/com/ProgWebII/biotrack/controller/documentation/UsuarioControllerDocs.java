package com.ProgWebII.biotrack.controller.documentation;

import com.ProgWebII.biotrack.dto.request.UserPatchRequest;
import com.ProgWebII.biotrack.dto.request.UserRequest;
import com.ProgWebII.biotrack.dto.response.BuscarUsuarioPorIdResponse;
import com.ProgWebII.biotrack.dto.response.ListarTodosUsuariosResponse;
import com.ProgWebII.biotrack.dto.response.UsuarioResponse;
import com.ProgWebII.biotrack.dto.response.UsuarioSemMedidasResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface UsuarioControllerDocs {

    @Operation(summary = "Cria um usuário", description = "Cria um novo usuário no sistema com as informações fornecidas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos."),
            @ApiResponse(responseCode = "500", description = "Erro interno ao criar usuário.")
    })
    @PostMapping
    ResponseEntity<String> criarUsuario(@RequestBody UserRequest userRequest);

    @Operation(summary = "Filtra usuários pela faixa de IMC", description = "Retorna os usuários pertencentes à faixa de IMC informada.")
    @GetMapping("/filtro-imc")
    ResponseEntity<List<UsuarioResponse>> filtrarUsuariosPorImc(@Parameter(description = "Faixa do IMC para filtro") @RequestParam String faixa);

    @Operation(summary = "Lista todos os usuários", description = "Retorna todos os usuários sem suas medidas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso.")
    })
    @GetMapping
    ResponseEntity<List<ListarTodosUsuariosResponse>> listarTodos();

    @Operation(summary = "Busca usuário por ID", description = "Retorna os dados de um usuário sem medidas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
    })
    @GetMapping("/{id}")
    ResponseEntity<BuscarUsuarioPorIdResponse> buscarPorId(@Parameter(description = "ID do usuário") @PathVariable Long id);

    @Operation(summary = "Lista usuários sem medidas", description = "Retorna todos os usuários cadastrados sem medições.")
    @GetMapping("/sem-medidas")
    ResponseEntity<List<UsuarioSemMedidasResponse>> listarTodosSemMedidas();

    @Operation(summary = "Busca usuário + todas medidas", description = "Retorna os dados do usuário incluindo todas as medições registradas.")
    @GetMapping("/{id}/todas-medidas")
    ResponseEntity<UsuarioResponse> trazerUsuarioComTodasMedidas(@PathVariable Long id);

    @Operation(summary = "Busca usuário + última medida", description = "Retorna os dados do usuário incluindo a última medição registrada.")
    @GetMapping("/{id}/ultima-medida")
    ResponseEntity<UsuarioResponse> trazerUsuarioComUltimaMedida(@PathVariable Long id);

    @Operation(summary = "Atualiza completamente um usuário", description = "Substitui totalmente os dados de um usuário.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos.")
    })
    @PutMapping("/{id}")
    ResponseEntity<String> atualizarUsuario(@PathVariable Long id, @RequestBody UserRequest userRequest);

    @Operation(summary = "Atualiza parcialmente um usuário", description = "Atualiza apenas os campos enviados no corpo.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
    })
    @PatchMapping("/{id}")
    ResponseEntity<String> atualizarParcialUsuario(@PathVariable Long id, @RequestBody UserPatchRequest userPatchRequest);

    @Operation(summary = "Remove um usuário", description = "Deleta um usuário existente pelo ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário removido com sucesso."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<String> removerUsuario(@PathVariable Long id);
}
