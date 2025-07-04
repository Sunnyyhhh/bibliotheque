package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.demo.entities.Ferie;

import jakarta.transaction.Transactional;

public interface FerieRepository extends JpaRepository<Ferie, Integer> {

}
