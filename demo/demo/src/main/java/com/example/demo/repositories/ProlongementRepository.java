package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entities.Prolongement;
import java.util.Date;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import jakarta.transaction.Transactional;

public interface ProlongementRepository extends JpaRepository<Prolongement, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE Prolongement e SET e.etatProlongement = 1,e.dtValidationProlongement=:dt WHERE e.idProlongement = :id")
    void updateProlongement(@Param("id") Integer idProlongement, @Param("dt") Date dt);

    @Query("SELECT p FROM Prolongement p WHERE p.dtValidationProlongement IS NULL")
    List<Prolongement> getListProlongement();
}
