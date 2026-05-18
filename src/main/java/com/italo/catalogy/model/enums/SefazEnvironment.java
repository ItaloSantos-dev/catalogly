package com.italo.catalogy.model.enums;

public enum SefazEnvironment {
    PRODUCTION("PRODUCTION"),
    HOMOLOGATION("HOMOLOGATION"),
    DEVELOPMENT("DEVELOPMENT");
    private String environment;

    SefazEnvironment(String environment) {
        this.environment = environment;
    }

    public String getEnvironment() {
        return environment;
    }
}
