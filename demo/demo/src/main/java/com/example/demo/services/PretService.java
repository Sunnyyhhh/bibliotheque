package com.example.demo.services;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.demo.entities.Pret;
import com.example.demo.repositories.PretRepository;

@Service
public class PretService {

    private PretRepository PretRepo;

    public PretService(PretRepository ur) {
        this.PretRepo = ur;
    }

    public List<Pret> getAllPret() {
        return this.PretRepo.findAll();
    }

    public Optional<Pret> getPretById(Integer id) {
        return this.PretRepo.findById(id);
    }

    public void deletePret(Integer id) {
        this.PretRepo.deleteById(id);
    }

    public void savePret(Pret p) {
        this.PretRepo.save(p);
    }

    public List<Pret> getPretByUtilisateur(Integer id) {
        return this.PretRepo.getPretByIdUtilisateur(id);
    }

    public Pret getPretSpecifique(Integer iduser, Integer idlivre) {
        return this.PretRepo.getPretSpecifique(iduser, idlivre);
    }

    public void updateExemplaireRetour(Integer idpret,Date dt)
    {
        this.PretRepo.updateExemplaire(dt, idpret);
    }
}
