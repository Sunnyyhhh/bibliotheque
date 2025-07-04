package com.example.demo.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Pret {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPret;

    @Column
    private Date dtEmprunt;

    @Column(nullable = true)
    private Date dtRetour;

    @Column
    private Date dtRetourPrevue;

    @ManyToOne
    @JoinColumn(name = "idLivre", nullable = false)
    private Livre livre;

    @ManyToOne
    @JoinColumn(name = "idUtilisateur", nullable = false)
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "idParametreEmprunt", nullable = false)
    private ParametreEmprunt parametreEmprunt;

    public Pret() {
    }

    public Pret(Date dtEmprunt, Date dtRetour, Date dtRetourPrevue, Livre livre, Utilisateur utilisateur, ParametreEmprunt parametreEmprunt) {
        this.dtEmprunt = dtEmprunt;
        this.dtRetour = dtRetour;
        this.dtRetourPrevue = dtRetourPrevue;
        this.livre = livre;
        this.utilisateur = utilisateur;
        this.parametreEmprunt = parametreEmprunt;
    }

    public Integer getIdPret() {
        return idPret;
    }

    public void setIdPret(Integer idPret) {
        this.idPret = idPret;
    }

    public Date getDtEmprunt() {
        return dtEmprunt;
    }

    public void setDtEmprunt(Date dtEmprunt) {
        this.dtEmprunt = dtEmprunt;
    }

    public Date getDtRetour() {
        return dtRetour;
    }

    public void setDtRetour(Date dtRetour) {
        this.dtRetour = dtRetour;
    }

    public Date getDtRetourPrevue() {
        return dtRetourPrevue;
    }

    public void setDtRetourPrevue(Date dtRetourPrevue) {
        this.dtRetourPrevue = dtRetourPrevue;
    }

    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public ParametreEmprunt getParametreEmprunt() {
        return parametreEmprunt;
    }

    public void setParametreEmprunt(ParametreEmprunt parametreEmprunt) {
        this.parametreEmprunt = parametreEmprunt;
    }
}
