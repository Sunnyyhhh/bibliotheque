package com.example.demo.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.OneToMany;

@Entity
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUtilisateur;

    @Column(name = "nom")
    private String nom;

    @Column(name = "mot_de_passe")
    private String motDePasse;

    @Column(name = "dtn")
    private Date dtn;

    @Column(name = "nb_prolongement_perso")
    private Integer nbProlongement;

    public void setDtn(Date d) {
        this.dtn = d;
    }

    public Date getDtn() {
        return this.dtn;
    }

    @ManyToOne
    @JoinColumn(name = "id_adherent")
    private Adherent adherent;

    @Column(name = "quota_perso")
    private Integer QuotaPerso;

    public void setQuotaPerso(Integer i) {
        this.QuotaPerso = i;
    }

    public Integer getQuotaPerso() {
        return this.QuotaPerso;
    }

    @Column(name = "quota_persoMaison")
    private Integer QuotaPersoMaison;

    public void setQuotaPersoMaison(Integer i) {
        this.QuotaPersoMaison = i;
    }

    public Integer getQuotaPersoMaison() {
        return this.QuotaPersoMaison;
    }

    @Column(name = "statut")
    private String Statut;

    @OneToMany(mappedBy = "utilisateur")
    private List<Abonnement> abonnements = new ArrayList<>();

    public Utilisateur() {
    }

    public Utilisateur(Adherent adherent, String nom, String stat,
            String motDePasse, Date dt, Integer quotaPerso, Integer quotaPersoMaison) {
        this.adherent = adherent;
        this.nom = nom;
        this.motDePasse = motDePasse;
        this.QuotaPerso = quotaPerso;
        this.dtn = dt;
        this.QuotaPersoMaison = quotaPersoMaison;
        this.Statut = stat;
    }

    public Utilisateur(Integer idUtilisateur, Adherent adherent, String nom,
            String motDePasse, Date dt, Integer quotaPerso, Integer quotaPersoMaison) {
        this.idUtilisateur = idUtilisateur;
        this.adherent = adherent;
        this.nom = nom;
        this.motDePasse = motDePasse;
        this.QuotaPerso = quotaPerso;
        this.dtn = dt;
        this.QuotaPersoMaison = quotaPersoMaison;
    }

    public Utilisateur(Adherent ad, String nom, String mdp, Date dt, Integer qt, Integer qtMaison) {
        this.adherent = ad;
        this.nom = nom;
        this.motDePasse = mdp;
        this.dtn = dt;
        this.QuotaPerso = qt;
        this.QuotaPersoMaison = qtMaison;
    }

    public Utilisateur(Adherent adherent, String nom, List<Abonnement> abo,
            String motDePasse, Date dt, Integer quotaPerso, Integer quotaPersoMaison) {
        this.adherent = adherent;
        this.nom = nom;
        this.motDePasse = motDePasse;
        this.QuotaPerso = quotaPerso;
        this.dtn = dt;
        this.QuotaPersoMaison = quotaPersoMaison;
        this.abonnements = abo;
    }

    public Integer getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Integer idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public Adherent getAdherent() {
        return adherent;
    }

    public void setAdherent(Adherent adherent) {
        this.adherent = adherent;
    }

    public String getStatut() {
        return Statut;
    }

    public void setStatut(String Statut) {
        this.Statut = Statut;
    }

    public List<Abonnement> getAbonnement() {
        return abonnements;
    }

    public void setAbonnement(List<Abonnement> abonnement) {
        this.abonnements = abonnement;
    }

    public Integer getNbProlongement() {
        return nbProlongement;
    }

    public void setNbProlongement(Integer nbProlongement) {
        this.nbProlongement = nbProlongement;
    }
}
