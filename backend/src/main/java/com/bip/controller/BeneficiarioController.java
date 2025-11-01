package com.bip.controller;

import com.bip.domain.Beneficiario;
import com.bip.domain.Transferencia;
import com.bip.dto.TransferenciaRequest;
import com.bip.service.BeneficiarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/beneficiarios")
@RequiredArgsConstructor
@Tag(name = "Beneficiarios", description = "API de Beneficiarios")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BeneficiarioController {

    private final BeneficiarioService beneficiarioService;

    @GetMapping
    @Operation(summary = "Listar todos os beneficiarios")
    public ResponseEntity<List<Beneficiario>> listar() {
        return ResponseEntity.ok(beneficiarioService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter beneficiario por ID")
    public ResponseEntity<Beneficiario> obter(@PathVariable Long id) {
        return ResponseEntity.ok(beneficiarioService.obter(id));
    }

    @PostMapping
    @Operation(summary = "Criar novo beneficiario")
    public ResponseEntity<Beneficiario> criar(@RequestBody Beneficiario beneficiario) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(beneficiarioService.criar(beneficiario));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar beneficiario")
    public ResponseEntity<Beneficiario> atualizar(
            @PathVariable Long id,
            @RequestBody Beneficiario beneficiario) {
        return ResponseEntity.ok(beneficiarioService.atualizar(id, beneficiario));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar beneficiario")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        beneficiarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/transferir")
    @Operation(summary = "Transferir valores entre beneficiarios")
    public ResponseEntity<Transferencia> transferir(@RequestBody TransferenciaRequest request) {
        Transferencia transferencia = beneficiarioService.transferir(
            request.getIdOrigem(),
            request.getIdDestino(),
            request.getValor()
            );
        return ResponseEntity.status(HttpStatus.CREATED).body(transferencia);
    }
}