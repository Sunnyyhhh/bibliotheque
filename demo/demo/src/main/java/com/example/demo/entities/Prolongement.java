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
public class Prolongement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProlongement;

    @ManyToOne
    @JoinColumn(name = "idUtilisateur", nullable = false)
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "idPret", nullable = false)
    private Pret pret;

    @Column
    private Integer etatProlongement;

    @Column
    private Date dtDemandeProlongement;

    @Column
    private Date dtValidationProlongement;

    public Prolongement() {
    }

    public Prolongement(Utilisateur utilisateur, Pret pret, Integer etatProlongement, Date dtDemandeProlongement, Date dtValidationProlongement) {
        this.utilisateur = utilisateur;
        this.pret = pret;
        this.etatProlongement = etatProlongement;
        this.dtDemandeProlongement = dtDemandeProlongement;
        this.dtValidationProlongement = dtValidationProlongement;
    }

    public Integer getIdProlongement() {
        return idProlongement;
    }

    public void setIdProlongement(Integer idProlongement) {
        this.idProlongement = idProlongement;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Pret getPret() {
        return pret;
    }

    public void setPret(Pret pret) {
        this.pret = pret;
    }

    public Integer getEtatProlongement() {
        return etatProlongement;
    }

    public void setEtatProlongement(Integer etatProlongement) {
        this.etatProlongement = etatProlongement;
    }

    public Date getDtDemandeProlongement() {
        return dtDemandeProlongement;
    }

    public void setDtDemandeProlongement(Date dtDemandeProlongement) {
        this.dtDemandeProlongement = dtDemandeProlongement;
    }

    public Date getDtValidationProlongement() {
        return dtValidationProlongement;
    }

    public void setDtValidationProlongement(Date dtValidationProlongement) {
        this.dtValidationProlongement = dtValidationProlongement;
    }
}
