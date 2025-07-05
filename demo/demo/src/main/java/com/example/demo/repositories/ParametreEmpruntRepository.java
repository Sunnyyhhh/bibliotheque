package com.example.demo.repositories;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entities.ParametreEmprunt;
import com.example.demo.entities.Penalite;

public interface ParametreEmpruntRepository extends JpaRepository<ParametreEmprunt, Integer> {

    @Query("SELECT p FROM ParametreEmprunt p WHERE p.adherent.Id_Adherent = :id_Adherent AND modeEmprunt='DOMICILE' and p.livre.idLivre=:id_livre")
    ParametreEmprunt getParametreEmpruntMaison(@Param("id_Adherent") Integer idAdherent, @Param("id_livre") Integer idLivre);

    @Query("SELECT p FROM ParametreEmprunt p WHERE p.adherent.Id_Adherent = :id_Adherent AND modeEmprunt='SUR_PLACE'")
    ParametreEmprunt getParametreEmpruntPlace(@Param("id_Adherent") Integer idAdherent);

}
