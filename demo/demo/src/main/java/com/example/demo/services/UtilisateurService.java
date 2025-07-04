package com.example.demo.services;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.demo.entities.Utilisateur;
import com.example.demo.entities.Abonnement;
import com.example.demo.entities.CategorieLivre;
import com.example.demo.repositories.UtilisateurRepository;
import com.example.demo.repositories.AbonnementRepository;

@Service
public class UtilisateurService {

    private UtilisateurRepository utilisateurRepo;

    private AbonnementRepository abonnementRepo;

    public UtilisateurService(UtilisateurRepository ur, AbonnementRepository ab) {
        this.utilisateurRepo = ur;
        this.abonnementRepo = ab;
    }

    public Optional<Utilisateur> getUtilisateurById(Integer id) {
        return this.utilisateurRepo.findById(id);
    }

    public void saveUtilisateur(Utilisateur u) {
        this.utilisateurRepo.save(u);
    }

    public boolean authenticate(String nom, String motDePasse) {
        Utilisateur utilisateur = utilisateurRepo.findByNomAndMotDePasse(nom, motDePasse);
        if (utilisateur == null) {
            throw new RuntimeException("Nom ou mot de passe incorrect");
        }
        return true;
    }

    public Utilisateur getInfo(String nom, String motDePasse) {
        Utilisateur utilisateur = utilisateurRepo.findByNomAndMotDePasse(nom, motDePasse);
        if (utilisateur == null) {
            throw new RuntimeException("Nom ou mot de passe incorrect");
        }
        return utilisateur;
    }

}
