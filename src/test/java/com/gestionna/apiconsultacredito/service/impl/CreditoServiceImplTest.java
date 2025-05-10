package com.gestionna.apiconsultacredito.service.impl;

import com.gestionna.apiconsultacredito.model.dto.CreditoDto;
import com.gestionna.apiconsultacredito.model.entity.Credito;
import com.gestionna.apiconsultacredito.repository.CreditoRepository;
import com.gestionna.apiconsultacredito.service.Impl.CreditoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CreditoServiceImplTest {

    @Mock
    private CreditoRepository repositorio;

    @Mock
    private KafkaTemplate<String, CreditoDto> kafkaTemplate;

    @InjectMocks
    private CreditoServiceImpl service;

    @Test
    void buscarPorNumeroCredito_quandoExiste_retornaDtoEEnvioKafka() {
        Credito credito = new Credito();
        credito.setNumeroCredito("123");
        credito.setNumeroNfse("NFS001");
        credito.setDataConstituicao(LocalDate.now());
        credito.setValorIssqn(new BigDecimal("100.00"));

        when(repositorio.findByNumeroCredito("123"))
                .thenReturn(Optional.of(credito));

        var dto = service.buscarPorNumeroCredito("123");

        assertThat(dto.numeroCredito()).isEqualTo("123");
        verify(kafkaTemplate).send(anyString(), any(CreditoDto.class));
    }

    @Test
    void buscarPorNumeroCredito_quandoNaoExiste_lancaException() {
        when(repositorio.findByNumeroCredito("999"))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscarPorNumeroCredito("999"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("não encontrado");
    }

    @Test
    void buscarPorNfse_retornaListaEEnvioKafka() {
        var c1 = new Credito(); c1.setNumeroCredito("A");
        var c2 = new Credito(); c2.setNumeroCredito("B");
        when(repositorio.findByNumeroNfse("NF001"))
                .thenReturn(List.of(c1, c2));

        List<CreditoDto> lista = service.buscarPorNfse("NF001");

        assertThat(lista).hasSize(2)
                .extracting(CreditoDto::numeroCredito)
                .containsExactly("A","B");
        verify(kafkaTemplate, times(2)).send(anyString(), any(CreditoDto.class));
    }
}
