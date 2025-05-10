package com.gestionna.apiconsultacredito.service.Impl;

import com.gestionna.apiconsultacredito.entity.Credito;
import com.gestionna.apiconsultacredito.entity.dto.CreditoDto;
import com.gestionna.apiconsultacredito.entity.enums.SimplesNacional;
import com.gestionna.apiconsultacredito.repository.CreditoRepository;
import com.gestionna.apiconsultacredito.service.CreditoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Value;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreditoServiceImpl implements CreditoService {

    private final CreditoRepository repositorio;
    private final KafkaTemplate<String, CreditoDto> kafkaTemplate;


    @Value("${kafka.topic.consulta-creditos:consulta-creditos-topic}")
    private String topico;

    @Override
    @Transactional(readOnly = true)
    public List<CreditoDto> buscarPorNfse(String numeroNfse) {
        List<CreditoDto> dtos = repositorio.findByNumeroNfse(numeroNfse)
                .stream()
                .map(this::mapToDto)
                .toList();

        for (CreditoDto dto : dtos)
            kafkaTemplate.send(topico, dto)
                    .whenComplete((result, ex) -> {
                        if (ex != null) {
                            log.error("Falha ao enviar evento Kafka para crédito {}: {}", dto.numeroCredito(), ex.getMessage(), ex);
                        } else {
                            log.info("Evento Kafka enviado com sucesso para crédito {} " + "no tópico {} (partition: {}, offset: {})",
                                    dto.numeroCredito(),
                                    topico,
                                    result.getRecordMetadata().partition(),
                                    result.getRecordMetadata().offset());
                        }
                    });
        return dtos;
    }

    @Override
    @Transactional(readOnly = true)
    public CreditoDto buscarPorNumeroCredito(String numeroCredito) {
        Credito cred = repositorio.findByNumeroCredito(numeroCredito)
                .orElseThrow(() -> new ResourceNotFoundException("Crédito não encontrado"));

        CreditoDto dto = mapToDto(cred);
        kafkaTemplate.send(topico, dto);
        return dto;
    }

    private CreditoDto mapToDto(Credito cred) {
        return new CreditoDto(
                cred.getNumeroCredito(),
                cred.getNumeroNfse(),
                cred.getDataConstituicao(),
                cred.getValorIssqn(),
                cred.getTipoCredito(),
                cred.isSimplesNacional() ? SimplesNacional.SIM : SimplesNacional.NAO,
                cred.getAliquota(),
                cred.getValorFaturado(),
                cred.getValorDeducao(),
                cred.getBaseCalculo()
        );
    }
}