package com.example.demo.services;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.example.demo.entities.Adherent;
import com.example.demo.repositories.AdherentRepository;

@Service
public class AdherentService 
{
    private AdherentRepository AdherentRepo;

    public AdherentService(AdherentRepository ur)
    {
        this.AdherentRepo=ur;
    }

    public Optional<Adherent> getAdherentById(Integer id)
    {
        return this.AdherentRepo.findById(id);
    }

    public List<Adherent> getAllAdherent()
    {
        return this.AdherentRepo.findAll();
    }


}
