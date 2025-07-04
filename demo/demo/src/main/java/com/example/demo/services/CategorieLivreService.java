package com.example.demo.services;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.example.demo.entities.CategorieLivre;
import com.example.demo.repositories.CategorieLivreRepository;

@Service
public class CategorieLivreService 
{
    private CategorieLivreRepository CategorieLivreRepo;

    public CategorieLivreService(CategorieLivreRepository ur)
    {
        this.CategorieLivreRepo=ur;
    }

    

    


}
