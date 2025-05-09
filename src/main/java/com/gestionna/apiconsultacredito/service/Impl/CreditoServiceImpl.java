package com.gestionna.apiconsultacredito.service.Impl;

import com.gestionna.apiconsultacredito.entity.Credito;
import com.gestionna.apiconsultacredito.entity.dto.CreditoDto;
import com.gestionna.apiconsultacredito.entity.enums.SimplesNacional;
import com.gestionna.apiconsultacredito.repository.CreditoRepository;
import com.gestionna.apiconsultacredito.service.CreditoService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Value;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditoServiceImpl implements CreditoService {
    private final CreditoRepository repositorio;
    private final KafkaTemplate<String, CreditoDto> kafka;

    @Value("${kafka.topic.consulta-creditos}")
    private String topico;

    @Override
    @Transactional(readOnly = true)
    public List<CreditoDto> buscarPorNfse(String numeroNfse) {
        List<CreditoDto> dtos = repositorio.findByNumeroNfse(numeroNfse)
                .stream()
                .map(cred -> new CreditoDto(
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
                ))
                .toList();
        dtos.forEach(dto -> kafka.send(topico, dto));
        return dtos;
    }

    @Override
    @Transactional(readOnly = true)
    public CreditoDto buscarPorNumeroCredito(String numeroCredito) {
        Credito cred = repositorio.findByNumeroCredito(numeroCredito)
                .orElseThrow(() -> new ResourceNotFoundException("Crédito não encontrado"));
        CreditoDto dto = new CreditoDto(
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
        kafka.send(topico, dto);
        return dto;
    }

}