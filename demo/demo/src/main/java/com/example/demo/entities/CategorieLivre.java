package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

@Entity
public class CategorieLivre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCategorieLivre;

    @Column(nullable = false)
    private Integer ageMinimum;

    @Column(nullable = false, length = 50)
    private String nomCategorie;

    // Constructeurs
    public CategorieLivre() {}

    public CategorieLivre(Integer idCategorieLivre, Integer ageMinimum, String nomCategorie) {
        this.idCategorieLivre = idCategorieLivre;
        this.ageMinimum = ageMinimum;
        this.nomCategorie = nomCategorie;
    }

    // Getters & Setters
    public Integer getIdCategorieLivre() {
        return idCategorieLivre;
    }

    public void setIdCategorieLivre(Integer idCategorieLivre) {
        this.idCategorieLivre = idCategorieLivre;
    }

    public Integer getAgeMinimum() {
        return ageMinimum;
    }

    public void setAgeMinimum(Integer ageMinimum) {
        this.ageMinimum = ageMinimum;
    }

    public String getNomCategorie() {
        return nomCategorie;
    }

    public void setNomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }
}
