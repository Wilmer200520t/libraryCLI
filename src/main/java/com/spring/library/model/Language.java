package com.spring.library.model;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Language {
    English("en"),
    Spanish("es"),
    Portuguese("pt"),
    German("de"),
    French("fr"),
    Italian("it");

    private String nomenclature;

    private Language(String nomenclature) {
        this.nomenclature = nomenclature;
    }

    @JsonValue
    public String getNomenclature() {
        return nomenclature;
    }

    public void setNomenclature(String nomenclature) {
        this.nomenclature = nomenclature;
    }

    public static Language getLanguage(String nomenclature) {
        for (Language lang : Language.values()) {
            if (lang.getNomenclature().equals(nomenclature)) {
                return lang;
            }
        }

        throw new IllegalArgumentException("No se ha encontrado el idioma " + nomenclature);
    }
}
