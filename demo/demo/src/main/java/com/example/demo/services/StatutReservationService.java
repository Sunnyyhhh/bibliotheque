package com.example.demo.services;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import com.example.demo.entities.StatutReservation;
import com.example.demo.entities.Reservation;
import com.example.demo.repositories.StatutReservationRepository;
import com.example.demo.entities.Livre;
import com.example.demo.repositories.LivreRepository;
import java.lang.Object;

@Service
public class StatutReservationService {

    private StatutReservationRepository StatReservationRepo;

    private LivreRepository livreRepo;

    public StatutReservationService(StatutReservationRepository ur, LivreRepository lr) {
        this.StatReservationRepo = ur;
        this.livreRepo = lr;
    }

    public void save(StatutReservation p) {
        this.StatReservationRepo.save(p);
    }

}
