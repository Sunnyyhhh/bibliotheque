package com.example.demo.entities;

import jakarta.persistence.*;
import java.util.*;
import java.util.Calendar;
import java.util.Date;

@Entity
public class ParametreEmprunt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idParametreEmprunt;

    @Column(nullable = false)
    private String modeEmprunt;

    @ManyToOne
    @JoinColumn(name = "id_adherent", nullable = false)
    private Adherent adherent;

    @Column(nullable = false)
    private Integer nbJour;

    @ManyToOne
    @JoinColumn(name = "id_livre")
    private Livre livre;

    @Column(name = "take_home", nullable = true)
    private Boolean empruntDomicile;

    public ParametreEmprunt(String mode, Adherent ad, Integer nbJour, Livre book, Boolean edomicile) {
        this.setAdherent(ad);
        this.setNbJour(nbJour);
        this.setModeEmprunt(mode);
        this.setLivre(book);
        this.setEmpruntDomicile(edomicile);
    }

    public Integer getIdParametreEmprunt() {
        return idParametreEmprunt;
    }

    public void setIdParametreEmprunt(Integer idParametreEmprunt) {
        this.idParametreEmprunt = idParametreEmprunt;
    }

    public String getModeEmprunt() {
        return modeEmprunt;
    }

    public void setModeEmprunt(String modeEmprunt) {
        this.modeEmprunt = modeEmprunt;
    }

    public Adherent getAdherent() {
        return adherent;
    }

    public void setAdherent(Adherent adherent) {
        this.adherent = adherent;
    }

    public Integer getNbJour() {
        return nbJour;
    }

    public void setNbJour(Integer nbJour) {
        this.nbJour = nbJour;
    }

    public ParametreEmprunt() {
    }

    public ParametreEmprunt(String mode, Adherent ad, Integer nbJour) {
        this.setAdherent(ad);
        this.setNbJour(nbJour);
        this.setModeEmprunt(mode);
    }

    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public void setEmpruntDomicile(Boolean empruntDomicile) {
        this.empruntDomicile = empruntDomicile;
    }

    public Boolean getEmpruntDomicile() {
        return this.empruntDomicile;
    }
}
