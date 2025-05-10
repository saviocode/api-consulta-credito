package com.gestionna.apiconsultacredito.model.dto;

import com.gestionna.apiconsultacredito.model.enums.SimplesNacional;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreditoDto(
        String numeroCredito,
        String numeroNfse,
        LocalDate dataConstituicao,
        BigDecimal valorIssqn,
        String tipoCredito,
        SimplesNacional simplesNacional,   // “Sim” ou “Não”
        BigDecimal aliquota,
        BigDecimal valorFaturado,
        BigDecimal valorDeducao,
        BigDecimal baseCalculo
) {}