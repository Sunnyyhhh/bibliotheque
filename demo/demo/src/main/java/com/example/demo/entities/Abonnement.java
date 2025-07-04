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
public class Abonnement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer IdAbonnement;

    @Column(name = "debut")
    private Date Debut;

    @Column(name = "fin")
    private Date Fin;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur utilisateur;

    public Abonnement() {
    }

    public Abonnement(Integer id, Utilisateur user, Date deb, Date fin) {
        this.IdAbonnement = id;
        this.utilisateur = user;
        this.Debut = deb;
        this.Fin = fin;
    }

    public Abonnement(Utilisateur user, Date deb, Date fin) {
        this.utilisateur = user;
        this.Debut = deb;
        this.Fin = fin;
    }
}
