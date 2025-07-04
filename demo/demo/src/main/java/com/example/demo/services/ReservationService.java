package com.example.demo.services;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import com.example.demo.entities.Reservation;
import com.example.demo.repositories.ReservationRepository;
import com.example.demo.entities.Livre;
import com.example.demo.repositories.LivreRepository;
import java.lang.Object;

@Service
public class ReservationService {

    private ReservationRepository ReservationRepo;

    private LivreRepository livreRepo;

    public ReservationService(ReservationRepository ur, LivreRepository lr) {
        this.ReservationRepo = ur;
        this.livreRepo = lr;
    }

    public List<Reservation> getAllReservation() {
        return this.ReservationRepo.findAll();
    }

    public Optional<Reservation> getReservationById(Integer id) {
        return this.ReservationRepo.findById(id);
    }

    public void deleteReservation(Integer id) {
        this.ReservationRepo.deleteById(id);
    }

    public void saveReservation(Reservation p) {
        this.ReservationRepo.save(p);
    }

    public List<Object[]> getReservationByDateByLivre() {
        return this.ReservationRepo.getReservationByDateByLivre();

    }

    public List<Reservation> getReservationByLivre(Integer idlivre) {
        return this.ReservationRepo.getReservationByLivre(idlivre);
    }

    public void updateReservation(Date dt,Integer idlivre,Integer iduser,Date dtact)
    {
        this.ReservationRepo.updateReservation(dt,idlivre,iduser,dtact);
    }

}
