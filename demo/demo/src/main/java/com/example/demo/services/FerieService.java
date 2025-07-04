package com.example.demo.services;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.example.demo.entities.Ferie;
import com.example.demo.repositories.FerieRepository;

@Service
public class FerieService {

    private FerieRepository FerieRepo;

    public FerieService(FerieRepository ur) {
        this.FerieRepo = ur;
    }

    public Optional<Ferie> getFerieById(Integer id) {
        return this.FerieRepo.findById(id);
    }

    public List<Ferie> getAllFerie() {
        return this.FerieRepo.findAll();
    }

}
