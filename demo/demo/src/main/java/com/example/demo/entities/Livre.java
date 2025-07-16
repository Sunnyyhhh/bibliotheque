package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Livre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLivre;

    @Column(nullable = false, length = 100)
    private String titre;

    @ManyToOne
    @JoinColumn(name = "id_categorie_livre")
    private CategorieLivre categorie;

    @Column(nullable = true, length = 100)
    private String auteur;

    @ManyToOne
    @JoinColumn(name = "id_theme")
    private Theme theme;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "langue")
    private String langue;

    public Livre() {
    }

    public Livre(Integer id, String titre) {
        this.idLivre = id;
        this.titre = titre;
    }

    public Livre(Integer id, String titre, String auteur) {
        this.idLivre = id;
        this.titre = titre;
        this.auteur = auteur;
    }

    public Livre(Integer id, String titre, String auteur, String is, String l) {
        this.idLivre = id;
        this.titre = titre;
        this.auteur = auteur;
        this.isbn = is;
        this.langue = l;
    }

    public Integer getIdLivre() {
        return idLivre;
    }

    public void setIdLivre(Integer idLivre) {
        this.idLivre = idLivre;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public CategorieLivre getCategorie() {
        return categorie;
    }

    public void setCategorie(CategorieLivre categorie) {
        this.categorie = categorie;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }
}
