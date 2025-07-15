package com.example.demo.services;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import java.util.Optional;

import com.example.demo.entities.DetailExemplaire;
import com.example.demo.repositories.DetailExemplaireRepository;

@Service
public class DetailExemplaireService {

    private final DetailExemplaireRepository repo;

    public DetailExemplaireService(DetailExemplaireRepository repo) {
        this.repo = repo;
    }

    public Optional<DetailExemplaire> getDisponible(Integer idLivre) {
        return repo.findByLivreIdLivreAndStatut(idLivre);
    }

    public Optional<DetailExemplaire> getPremierDisponible(Integer idLivre) {
        return repo.findFirstDisponibleByLivreId(idLivre);
    }

    public void rendreIndisponible(Integer idDetailExemplaire) {
        repo.updateDetailExemplaireToNonDispo(idDetailExemplaire);
    }

    public void rendreDisponible(Integer idDetailExemplaire) {
        repo.updateDetailExemplaireToDispo(idDetailExemplaire);
    }

    public List<DetailExemplaire> getAllByLivre(Integer idLivre) {
        return repo.getAllByLivre(idLivre);
    }
}
