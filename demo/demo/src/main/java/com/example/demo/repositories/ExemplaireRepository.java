package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.demo.entities.Exemplaire;

import jakarta.transaction.Transactional;

public interface ExemplaireRepository extends JpaRepository<Exemplaire, Integer> {

    @Query("SELECT e.stock FROM Exemplaire e WHERE e.livre.id = :id")
    Integer getStockByExemplaire(@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query("UPDATE Exemplaire e SET e.stock = :stock WHERE e.livre.idLivre = :id")
    void updateExemplaire(@Param("id") Integer id, @Param("stock") Integer stock);

}
