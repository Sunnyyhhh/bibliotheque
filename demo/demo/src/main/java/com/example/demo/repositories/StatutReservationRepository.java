package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entities.StatutReservation;

import jakarta.transaction.Transactional;

import java.util.Optional;

public interface StatutReservationRepository extends JpaRepository<StatutReservation, Integer> {
}
