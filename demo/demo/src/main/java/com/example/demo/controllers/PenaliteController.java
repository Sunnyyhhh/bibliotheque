package com.example.demo.controllers;

import com.example.demo.entities.Utilisateur;
import com.example.demo.services.UtilisateurService;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.example.demo.entities.CategorieLivre;
import com.example.demo.entities.Penalite;
import com.example.demo.services.PenaliteService;
import com.example.demo.entities.Livre;
import com.example.demo.services.LivreService;
import com.example.demo.entities.Exemplaire;
import com.example.demo.services.ExemplaireService;


@Controller
@RequestMapping("/Penalites")
public class PenaliteController 
{
    @Autowired
    private PenaliteService PenaliteService;
    @Autowired
    private LivreService livreService;
    @Autowired
    private UtilisateurService utilisateurService;
}