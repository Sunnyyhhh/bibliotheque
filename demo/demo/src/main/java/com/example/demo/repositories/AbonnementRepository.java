package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.demo.entities.Abonnement;

import java.util.*;

import jakarta.transaction.Transactional;

public interface AbonnementRepository extends JpaRepository<Abonnement, Integer> {

    /*@Query("SELECT e.stock FROM Abonnement e WHERE e.livre.id = :id")
    Integer getStockByAbonnement(@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query("UPDATE Abonnement e SET e.stock = :stock WHERE e.livre.id = :id")
    void updateAbonnement(@Param("id") Integer id, @Param("stock") Integer stock);*/
    @Query("SELECT a FROM Abonnement a WHERE a.utilisateur.idUtilisateur = :idUtilisateur AND :date BETWEEN a.Debut AND a.Fin")
    Optional<Abonnement> findAbonnementByDateByUser(@Param("idUtilisateur") Integer idUtilisateur, @Param("date") Date date);
}
