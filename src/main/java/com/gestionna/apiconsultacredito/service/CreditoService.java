package com.gestionna.apiconsultacredito.service;

import com.gestionna.apiconsultacredito.entity.dto.CreditoDto;

import java.util.List;

public interface CreditoService {
    List<CreditoDto> buscarPorNfse(String numeroNfse);
    CreditoDto buscarPorNumeroCredito(String numeroCredito);
}
