package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entities.CategorieLivre;

public interface CategorieLivreRepository extends JpaRepository<CategorieLivre, Integer> 
{
    
}
