package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class Ferie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFerie;

    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    public Ferie() {
    }

    public Ferie(Date date) {
        this.date = date;
    }

    public Integer getIdFerie() {
        return idFerie;
    }

    public void setIdFerie(Integer idFerie) {
        this.idFerie = idFerie;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
