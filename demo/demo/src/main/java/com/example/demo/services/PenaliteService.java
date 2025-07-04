package com.example.demo.services;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.demo.entities.Penalite;
import com.example.demo.repositories.PenaliteRepository;

@Service
public class PenaliteService {

    private PenaliteRepository PenaliteRepo;

    public PenaliteService(PenaliteRepository ur) {
        this.PenaliteRepo = ur;
    }

    public List<Penalite> getAllPenalite() {
        return this.PenaliteRepo.findAll();
    }

    public Optional<Penalite> getPenaliteById(Integer id) {
        return this.PenaliteRepo.findById(id);
    }

    public void deletePenalite(Integer id) {
        this.PenaliteRepo.deleteById(id);
    }

    public boolean findPenaliteByDateByUse(Integer id, Date dt) {
        Optional<Penalite> penalites = this.PenaliteRepo.findPenaliteByDateByUser(id, dt);

        if (penalites.isPresent()) {
            return true;
        }
        return false;
    }

    public Optional<Penalite> getPenaliteByDateUser(Integer id, Date dt) {
        Optional<Penalite> peine = this.PenaliteRepo.findPenaliteByDateByUser(id, dt);
        return peine;
    }

    public void savePenalite(Penalite e) {
        this.PenaliteRepo.save(e);
    }

    public void updatePenalite(Integer idPenalite, Date dt) {
        this.PenaliteRepo.updateDtFin(idPenalite, dt);
    }

}
