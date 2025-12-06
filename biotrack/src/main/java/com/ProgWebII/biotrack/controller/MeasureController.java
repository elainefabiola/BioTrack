package com.ProgWebII.biotrack.controller;

import com.ProgWebII.biotrack.controller.documentation.MeasureControllerDocs;
import com.ProgWebII.biotrack.dto.response.MedidaResponse;
import com.ProgWebII.biotrack.dto.request.MeasureRequest;
import com.ProgWebII.biotrack.service.MeasureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medidas")
public class MeasureController implements MeasureControllerDocs {

  private final MeasureService measureService;

  public MeasureController(MeasureService measureService) {
    this.measureService = measureService;
  }

  //POST /api/v1/usuarios/{idUsuario}/medidas → cria uma nova medida para o usuário existente
  @PostMapping("/{userId}")
  public ResponseEntity<String> createMeasure(@RequestBody MeasureRequest measureRequest, @PathVariable Long userId) {
    measureService.CreateMeasure(measureRequest, userId);
    return ResponseEntity.ok("Medida criada com sucesso!");
  }
   //GET /api/v1/usuarios/{idUsuario}/medidas → todas as medidas do usuário
    @GetMapping("/{usuarioId}/medidas")
    public ResponseEntity<List<MedidaResponse>> listarTodasAsMedidas(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(measureService.listarTodasAsMedidasDeUmUsuario(usuarioId));
    }

    // GET /api/v1/usuarios/{idUsuario}/medidas/{medidaId} → busca uma medida específica de um usuário
    @GetMapping("/{usuarioId}/medidas/{medidaId}")
    public ResponseEntity<MedidaResponse> buscarMedidaPorId(
            @PathVariable Long usuarioId,
            @PathVariable Long medidaId) {
        MedidaResponse medida = measureService.buscarMedidaPorId(usuarioId, medidaId);
        return ResponseEntity.ok(medida);
    }
    
    // PUT /medidas/{medidaId} → atualiza completamente uma medida
    @PutMapping("/{medidaId}")
    public ResponseEntity<String> atualizarMedida(
            @PathVariable Long medidaId,
            @RequestBody MeasureRequest medidaRequest) {
        measureService.atualizarMedida(medidaId, medidaRequest);
        return ResponseEntity.ok("Medida atualizada com sucesso!");
    }
    
    // DELETE /medidas/{medidaId} → remove uma medida
    @DeleteMapping("/{medidaId}")
    public ResponseEntity<String> removerMedida(@PathVariable Long medidaId) {
        measureService.removerMedida(medidaId);
        return ResponseEntity.ok("Medida removida com sucesso!");
    }
}
