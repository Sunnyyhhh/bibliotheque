package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "detail_exemplaire")
public class DetailExemplaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDetailExemplaire;

    @ManyToOne
    @JoinColumn(name = "id_livre", nullable = false)
    private Livre livre;

    @Column(name = "ref", nullable = false)
    private String ref;

    @Column(name = "statut", nullable = false)
    private String statut;

    public DetailExemplaire() {
    }

    public DetailExemplaire(Livre livre, String ref,String stat) {
        this.livre = livre;
        this.ref = ref;
        this.statut = stat;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Integer getIdDetailExemplaire() {
        return idDetailExemplaire;
    }

    public void setIdDetailExemplaire(Integer idDetailExemplaire) {
        this.idDetailExemplaire = idDetailExemplaire;
    }

    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

}
