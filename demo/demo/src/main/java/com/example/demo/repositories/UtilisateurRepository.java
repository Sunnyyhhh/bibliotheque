package com.example.demo.repositories;

import com.example.demo.entities.Utilisateur;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {

    @Query("SELECT u FROM Utilisateur u WHERE u.nom = :nom AND u.motDePasse = :motDePasse")
    Utilisateur findByNomAndMotDePasse(@Param("nom") String nom, @Param("motDePasse") String motDePasse);
}
