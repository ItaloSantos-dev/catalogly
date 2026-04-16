package com.italo.catalogy.model.enums;

public enum SefazEnvironment {
    PRODUCTION("PRODUCTION"),
    HOMOLOGATION("HOMOLOGATION");

    private String environment;

    SefazEnvironment(String environment) {
        this.environment = environment;
    }

    public String getEnvironment() {
        return environment;
    }
}
