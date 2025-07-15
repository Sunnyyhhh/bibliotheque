package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entities.DetailExemplaire;

import jakarta.transaction.Transactional;

import java.util.Optional;

public interface DetailExemplaireRepository extends JpaRepository<DetailExemplaire, Integer> {

    @Query("select d from DetailExemplaire d where d.livre.idLivre = :idLivre and d.statut = 'Disponible'")
    Optional<DetailExemplaire> findByLivreIdLivreAndStatut(@Param("idLivre") Integer idLivre);

    @Query("select d from DetailExemplaire d where d.livre.idLivre = :idLivre")
    List<DetailExemplaire> getAllByLivre(@Param("idLivre") Integer idLivre);

    @Query("SELECT d FROM DetailExemplaire d "
            + "WHERE d.livre.idLivre = :idLivre "
            + "AND d.statut = 'Disponible' "
            + "AND d.idDetailExemplaire = ("
            + "SELECT MIN(d2.idDetailExemplaire) "
            + "FROM DetailExemplaire d2 "
            + "WHERE d2.livre.idLivre = :idLivre "
            + "AND d2.statut = 'Disponible')")
    Optional<DetailExemplaire> findFirstDisponibleByLivreId(@Param("idLivre") Integer idLivre);

    @Modifying
    @Transactional
    @Query("update DetailExemplaire d set d.statut = 'Non disponible' where d.idDetailExemplaire = :idDetailExemplaire")
    void updateDetailExemplaireToNonDispo(@Param("idDetailExemplaire") Integer idDetailExemplaire);

    @Modifying
    @Transactional
    @Query("update DetailExemplaire d set d.statut = 'Disponible' where d.idDetailExemplaire = :idDetailExemplaire")
    void updateDetailExemplaireToDispo(@Param("idDetailExemplaire") Integer idDetailExemplaire);

}
