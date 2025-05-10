package com.gestionna.apiconsultacredito.model.enums;

import lombok.Getter;

@Getter
public enum SimplesNacional {
    SIM("Sim"),
    NAO("Não");

    private final String label;

    SimplesNacional(String label) { this.label = label; }

}
