package com.gestionna.apiconsultacredito.entity.mapper;

import com.gestionna.apiconsultacredito.entity.Credito;
import com.gestionna.apiconsultacredito.entity.dto.CreditoDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CreditoMapper {
    @Mapping(target = "simplesNacional",
             expression = "java(entidade.isSimplesNacional() ? \"Sim\" : \"Não\")")
    CreditoDto toDto(Credito entidade);
}