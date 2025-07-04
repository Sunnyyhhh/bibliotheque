package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.demo.entities.Reservation;

import java.util.*;

import jakarta.transaction.Transactional;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    /*@Query("SELECT DISTINCT r.livre.idLivre, r.dateReservation FROM Reservation r ORDER BY r.livre.idLivre, r.dateReservation")
    List<Object[]> getReservationByDateByLivre();*/
    @Query("SELECT r.livre.idLivre, COUNT(r) "
            + "FROM Reservation r where r.etatReservation!=1"
            + "GROUP BY r.livre.idLivre "
            + "ORDER BY r.livre.idLivre")
    List<Object[]> getReservationByDateByLivre();

    @Query("select r from Reservation r where r.etatReservation!=1 and r.livre.idLivre=:idlivre order by r.dateAction asc")
    List<Reservation> getReservationByLivre(@Param("idlivre") Integer idLivre);

    @Modifying
    @Transactional
    @Query("UPDATE Reservation e SET e.etatReservation = 1,e.dateTraitement=:dt WHERE e.livre.idLivre=:idlivre and e.utilisateur.idUtilisateur=:iduser and e.dateAction=:dtact")
    void updateReservation(@Param("dt") Date dt, @Param("idlivre") Integer idlivre, @Param("iduser") Integer iduser, @Param("dtact") Date dtact);
}
