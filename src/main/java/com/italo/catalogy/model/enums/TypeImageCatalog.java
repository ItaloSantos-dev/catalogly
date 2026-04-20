package com.italo.catalogy.model.enums;

public enum TypeImageCatalog{
    ICON("ICON"),
    BANNER("BANNER");

    private String typeImageCatalog;

    TypeImageCatalog (String typeImageCatalog){
        this.typeImageCatalog=typeImageCatalog;
    }

    public String getTypeImageCatalog(){
        return this.typeImageCatalog;
    }
}
