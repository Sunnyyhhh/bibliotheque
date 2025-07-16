package com.example.demo.entities;

import org.hibernate.annotations.ManyToAny;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.util.*;

@Entity
public class Adherent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer Id_Adherent;

    public void setId_Adherent(Integer id) {
        this.Id_Adherent = id;
    }

    public Integer getId_Adherent() {
        return this.Id_Adherent;
    }

    @Column
    String Nom_Adherent;

    @Column
    Integer QuotaMaison;

    @Column
    Integer Quota;

    @Column
    Integer NbJourEmprunt;

    @Column
    Integer nbPenalite;

    public Integer getQuota() {
        return this.Quota;
    }

    public void setQuota(Integer q) {
        this.Quota = q;
    }

    public Integer getQuotaMaison() {
        return this.QuotaMaison;
    }

    public void setQuotaMaison(Integer q) {
        this.QuotaMaison = q;
    }

    public void setNom_Adherent(String nom) {
        this.Nom_Adherent = nom;
    }

    public String getNom_Adherent() {
        return this.Nom_Adherent;
    }

    @OneToMany(mappedBy = "adherent")
    private List<Utilisateur> utilisateurs;

    public void setUtilisateur(List<Utilisateur> user) {
        this.utilisateurs = user;
    }

    public List<Utilisateur> getUtilisateur() {
        return this.utilisateurs;
    }

    public Adherent() {
    }

    public Adherent(Integer id, String nom, Integer q, Integer qo, Integer j) {
        this.setId_Adherent(id);
        this.setNom_Adherent(nom);
        this.setQuotaMaison(q);
        this.setQuota(qo);
        this.setNbJourEmprunt(j);
    }

    public Integer getNbJourEmprunt() {
        return NbJourEmprunt;
    }

    public void setNbJourEmprunt(Integer NbJourEmprunt) {
        this.NbJourEmprunt = NbJourEmprunt;
    }

    public Integer getNbPenalite() {
        return nbPenalite;
    }

    public void setNbPenalite(Integer nbPenalite) {
        this.nbPenalite = nbPenalite;
    }

}
