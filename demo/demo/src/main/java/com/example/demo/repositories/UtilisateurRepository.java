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

    @Modifying
    @Transactional
    @Query("UPDATE Utilisateur u SET u.QuotaPerso = :quota_perso, u.QuotaPersoMaison = :quota_maison WHERE u.idUtilisateur = :id_utilisateur")
    void updateQuota(@Param("quota_perso") Integer qperso,
            @Param("quota_maison") Integer qmaison,
            @Param("id_utilisateur") Integer iduser);

    @Modifying
    @Transactional
    @Query("UPDATE Utilisateur u SET u.QuotaPerso = :quota_perso WHERE u.idUtilisateur = :id_utilisateur")
    void updateQuotaPlace(@Param("quota_perso") Integer qperso,
            @Param("id_utilisateur") Integer iduser);

}
