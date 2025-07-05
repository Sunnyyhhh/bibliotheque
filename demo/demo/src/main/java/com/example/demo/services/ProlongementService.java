package com.example.demo.services;

import java.util.Date;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.entities.Prolongement;
import com.example.demo.repositories.ProlongementRepository;

@Service
public class ProlongementService {

    @Autowired
    private ProlongementRepository prolongementRepo;

    public void saveProlongement(Prolongement p) {
        prolongementRepo.save(p);
    }

    public List<Prolongement> getListProlongement() {
        return prolongementRepo.getListProlongement();
    }

    public Optional<Prolongement> getProlongementById(Integer id) {
        return prolongementRepo.findById(id);
    }

    public void updateProlongement(Integer idProlongement, Date dt) {
        prolongementRepo.updateProlongement(idProlongement, dt);
    }
}
