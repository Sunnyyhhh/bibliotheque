package com.example.demo.repositories;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;

import org.springframework.data.jpa.repository.Modifying;

import com.example.demo.entities.Pret;

import jakarta.transaction.Transactional;

public interface PretRepository extends JpaRepository<Pret, Integer> {

    @Query("SELECT e FROM Pret e WHERE e.utilisateur.idUtilisateur = :id AND e.dtRetour IS NULL")
    List<Pret> getPretByIdUtilisateur(@Param("id") Integer id);

    @Query("select e from Pret e where e.utilisateur.idUtilisateur=:id and e.livre.idLivre= :idlivre and e.dtRetour is NULL")
    Pret getPretSpecifique(@Param("id") Integer id, @Param("idlivre") Integer idlivre);

    /*@Modifying
    @Transactional
    @Query("UPDATE Pret e SET e.dtRetour = :dtr WHERE id_pret = :idPret")
    void updateExemplaire(@Param("dtRetour") Integer id, @Param("idPret") Integer idPret);*/
    @Modifying
    @Transactional
    @Query("UPDATE Pret e SET e.dtRetour = :dtRetour WHERE e.idPret = :idPret")
    void updateExemplaire(@Param("dtRetour") Date dtRetour, @Param("idPret") Integer idPret);

}
