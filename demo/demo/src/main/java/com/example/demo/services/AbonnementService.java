package com.example.demo.services;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Date;

import com.example.demo.entities.Abonnement;
import com.example.demo.repositories.AbonnementRepository;

@Service
public class AbonnementService {

    private AbonnementRepository AbonnementRepo;

    public AbonnementService(AbonnementRepository ur) {
        this.AbonnementRepo = ur;
    }

    public List<Abonnement> getAllAbonnement() {
        return this.AbonnementRepo.findAll();
    }

    public Optional<Abonnement> getAbonnementById(Integer id) {
        return this.AbonnementRepo.findById(id);
    }

    public void deleteAbonnement(Integer id) {
        this.AbonnementRepo.deleteById(id);
    }

    public Optional<Abonnement> getAbonnementUserByDate(Integer iduser, Date dt) {
        return this.AbonnementRepo.findAbonnementByDateByUser(iduser, dt);
    }

}
