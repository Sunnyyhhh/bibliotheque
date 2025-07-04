package com.example.demo.services;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.example.demo.entities.Theme;
import com.example.demo.repositories.ThemeRepository;

@Service
public class ThemeService 
{
    private ThemeRepository ThemeRepo;

    public ThemeService(ThemeRepository ur)
    {
        this.ThemeRepo=ur;
    }


}
