package com.gestionna.apiconsultacredito.repository;

import com.gestionna.apiconsultacredito.entity.Credito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface CreditoRepository extends JpaRepository<Credito, Long> {
    List<Credito> findByNumeroNfse(String numeroNfse);
    Optional<Credito> findByNumeroCredito(String numeroCredito);
}