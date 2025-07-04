package com.example.demo.services;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.entities.Livre;
import com.example.demo.repositories.LivreRepository;
import com.example.demo.entities.Pret;
import com.example.demo.repositories.PretRepository;

@Service
public class LivreService {

    @Autowired
    private LivreRepository LivreRepo;

    @Autowired
    private PretRepository PretRepo;

    public LivreService(LivreRepository ur) {
        this.LivreRepo = ur;
    }

    public List<Livre> getAllLivre() {
        return this.LivreRepo.findAll();
    }

    public Optional<Livre> getLivreById(Integer id) {
        return this.LivreRepo.findById(id);
    }

    public void deleteLivre(Integer id) {
        this.LivreRepo.deleteById(id);
    }

}
