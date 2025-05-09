package com.gestionna.apiconsultacredito.entity.dto;

import com.gestionna.apiconsultacredito.entity.enums.SimplesNacional;

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