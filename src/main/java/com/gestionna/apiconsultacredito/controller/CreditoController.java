package com.gestionna.apiconsultacredito.controller;

import com.gestionna.apiconsultacredito.model.dto.CreditoDto;
import com.gestionna.apiconsultacredito.service.CreditoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/creditos")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class CreditoController {
    private final CreditoService servico;

    @GetMapping("/{numeroNfse}")
    public ResponseEntity<List<CreditoDto>> getPorNfse(@PathVariable String numeroNfse) {
        return ResponseEntity.ok(servico.buscarPorNfse(numeroNfse));
    }

    @GetMapping("/credito/{numeroCredito}")
    public ResponseEntity<CreditoDto> getPorNumero(@PathVariable String numeroCredito) {
        return ResponseEntity.ok(servico.buscarPorNumeroCredito(numeroCredito));
    }
}

