package com.example.demo.repositories;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;

import org.springframework.data.repository.query.Param;

import com.example.demo.entities.Penalite;

import jakarta.transaction.Transactional;

public interface PenaliteRepository extends JpaRepository<Penalite, Integer> {

    @Query("SELECT p FROM Penalite p WHERE p.utilisateur.idUtilisateur = :idUtilisateur AND :date BETWEEN p.dtDebut AND p.dtFin")
    Optional<Penalite> findPenaliteByDateByUser(@Param("idUtilisateur") Integer idUtilisateur,
            @Param("date") Date date);

    @Modifying
    @Transactional
    @Query("UPDATE Penalite p SET p.dtFin = :newDateFin WHERE p.idPenalite = :id")
    void updateDtFin(@Param("id") Integer id, @Param("newDateFin") Date newDateFin);

    @Query("SELECT p FROM Penalite p WHERE p.utilisateur.idUtilisateur = :idUtilisateur AND :currentDate BETWEEN p.dtDebut AND p.dtFin")
    Optional<Penalite> findCurrentPenaliteByUser(@Param("idUtilisateur") Integer idUtilisateur, @Param("currentDate") Date currentDate);

}
