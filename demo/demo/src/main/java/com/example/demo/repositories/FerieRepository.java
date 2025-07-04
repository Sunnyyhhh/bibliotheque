package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.demo.entities.Ferie;

import jakarta.transaction.Transactional;

public interface FerieRepository extends JpaRepository<Ferie, Integer> {

    /*@Query("SELECT e.stock FROM Ferie e WHERE e.livre.id = :id")
    Integer getStockByFerie(@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query("UPDATE Ferie e SET e.stock = :stock WHERE e.livre.id = :id")
    void updateFerie(@Param("id") Integer id, @Param("stock") Integer stock);*/
}
