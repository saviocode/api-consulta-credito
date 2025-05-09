package com.gestionna.apiconsultacredito.entity.enums;

public enum SimplesNacional {
    SIM("Sim"),
    NAO("Não");

    private final String label;

    SimplesNacional(String label) { this.label = label; }

    public String getLabel() { return label; }
}
