package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

@Entity
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTheme;

    @Column(nullable = false, length = 50)
    private String libelle;

    public Theme() {}

    public Theme(Integer idTheme, String libelle) {
        this.idTheme = idTheme;
        this.libelle = libelle;
    }

    public Integer getIdTheme() {
        return idTheme;
    }

    public void setIdTheme(Integer idTheme) {
        this.idTheme = idTheme;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
