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

    @Query("select e from Pret e where e.utilisateur.idUtilisateur = :id and e.livre.idLivre = :idlivre and e.dtRetour is null and e.idRef = :ref")
    Pret getPretSpecifiqueRef(@Param("id") Integer id, @Param("idlivre") Integer idLivre, @Param("ref") Integer ref);

    @Query("SELECT p FROM Pret p WHERE p.utilisateur.idUtilisateur = :id AND p.livre.idLivre = :idlivre AND p.dtRetour IS NULL AND "
            + "p.idPret = (SELECT p2.idPret FROM Pret p2 WHERE p2.utilisateur.idUtilisateur = :id AND p2.livre.idLivre = :idlivre AND p2.dtRetour IS NULL)")
    Pret getPretMaxSpecifique(@Param("id") Integer id, @Param("idlivre") Integer idlivre);

    @Query("SELECT p FROM Pret p WHERE p.utilisateur.idUtilisateur = :id AND p.livre.idLivre = :idlivre AND p.dtRetour IS NULL ORDER BY p.idPret DESC")
    List<Pret> getPretMaxSpecifiqueListe(@Param("id") Integer id, @Param("idlivre") Integer idlivre);

    @Modifying
    @Transactional
    @Query("UPDATE Pret e SET e.dtRetour = :dtRetour WHERE e.idPret = :idPret")
    void updateExemplaire(@Param("dtRetour") Date dtRetour, @Param("idPret") Integer idPret);

}
