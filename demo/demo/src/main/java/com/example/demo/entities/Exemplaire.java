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
@Table(name="exemplaire")
public class Exemplaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idExemplaire;

    @ManyToOne
    @JoinColumn(name = "id_livre", nullable = false)
    private Livre livre;

    @Column(nullable = false)
    private int nb;

    @Column
    private Integer stock;

    public Exemplaire() {}

    public Exemplaire(Livre livre, int nb, Integer stock) {
        this.livre = livre;
        this.nb = nb;
        this.stock = stock;
    }

    public Integer getIdExemplaire() {
        return idExemplaire;
    }

    public void setIdExemplaire(Integer idExemplaire) {
        this.idExemplaire = idExemplaire;
    }

    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public int getNb() {
        return nb;
    }

    public void setNb(int nb) {
        this.nb = nb;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
