package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.*;

import com.example.demo.entities.Livre;

public interface LivreRepository extends JpaRepository<Livre, Integer> 
{
  
}
