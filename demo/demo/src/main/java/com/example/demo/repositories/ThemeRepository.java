package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entities.Theme;

public interface ThemeRepository extends JpaRepository<Theme, Integer> 
{
}
