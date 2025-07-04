package com.example.demo.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idReservation;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur", nullable = false)
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "id_livre", nullable = false)
    private Livre livre;

    @Column(name = "date_reservation", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateReservation;

    @Column(name = "etat_reservation")
    private Integer etatReservation;

    @Column(name = "date_action", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAction;

    @Column(name = "date_traitement", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTraitement;

    public Reservation() {
    }

    public Reservation(Livre b)
    {
        this.livre=b;
    }
    public Reservation(Utilisateur utilisateur, Livre livre, Date dateReservation, Integer etat) {
        this.utilisateur = utilisateur;
        this.livre = livre;
        this.dateReservation = dateReservation;
        this.etatReservation = etat;
    }

    public Reservation(Utilisateur utilisateur, Livre livre, Date dateReservation, Date dateAction, Integer etat) {
        this.utilisateur = utilisateur;
        this.livre = livre;
        this.dateReservation = dateReservation;
        this.dateAction = dateAction;
        this.etatReservation = etat;
    }

    public Reservation(Utilisateur utilisateur, Livre livre, Date dateReservation) {
        this.utilisateur = utilisateur;
        this.livre = livre;
        this.dateReservation = dateReservation;
    }

    public Reservation(Livre livre, Date dtResa) {
        this.livre = livre;
        this.dateReservation = dtResa;
    }

    public Integer getEtatReservation() {
        return this.etatReservation;
    }

    public void setEtatReservation(Integer etat) {
        this.etatReservation = etat;
    }

    public Integer getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(Integer idReservation) {
        this.idReservation = idReservation;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public Date getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
    }

    public Date getDateTraitement() {
        return dateTraitement;
    }

    public void setDateTraitement(Date dateTraitement) {
        this.dateTraitement = dateTraitement;
    }

    public Date getDateAction() {
        return dateAction;
    }

    public void setDateAction(Date dateAction) {
        this.dateAction = dateAction;
    }
}
