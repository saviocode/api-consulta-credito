package com.gestionna.apiconsultacredito.controller;

import com.gestionna.apiconsultacredito.model.dto.CreditoDto;
import com.gestionna.apiconsultacredito.model.enums.SimplesNacional;
import com.gestionna.apiconsultacredito.service.CreditoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CreditoController.class)
class CreditoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Mock
    private CreditoService service;

    @Test
    void getPorNumeroCredito_retornaJson() throws Exception {
        var dto = new CreditoDto("123","NF001", LocalDate.now(),
                new BigDecimal("100"),"ISSQN", SimplesNacional.SIM,
                new BigDecimal("5"), new BigDecimal("1000"), new BigDecimal("0"), new BigDecimal("1000"));

        when(service.buscarPorNumeroCredito("123")).thenReturn(dto);

        mvc.perform(get("/api/creditos/credito/123456")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroCredito").value("123"))
                .andExpect(jsonPath("$.tipoCredito").value("ISSQN"));
    }

    @Test
    void getPorNfse_retornaListaJson() throws Exception {
        var dto1 = new CreditoDto("1","NF001",LocalDate.now(),
                BigDecimal.ONE,"ISSQN",SimplesNacional.SIM, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ONE);
        when(service.buscarPorNfse("NF001")).thenReturn(List.of(dto1));

        mvc.perform(get("/api/creditos/7891011")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].numeroCredito").value("1"));
    }
}
