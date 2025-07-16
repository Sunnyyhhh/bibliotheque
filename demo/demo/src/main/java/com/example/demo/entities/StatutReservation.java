package com.example.demo.entities;

import java.util.Date;

import org.apache.tomcat.util.buf.Utf8Encoder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class StatutReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idStatutReservation;

    @Column(name = "id_reservation")
    private Integer idReservation;

    @Column(name = "dt_validation", nullable = false)
    private Date dtValidation;

    public StatutReservation() {
    }

    public StatutReservation(Integer idResa, Date dt) {
        this.idReservation = idResa;
        this.dtValidation = dt;
    }

}
