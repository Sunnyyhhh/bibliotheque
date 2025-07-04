package com.example.demo.controllers;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import com.example.demo.entities.Utilisateur;
import com.example.demo.services.UtilisateurService;

import jakarta.servlet.http.HttpSession;

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

import java.util.List;
import java.util.Map;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.entities.Livre;
import com.example.demo.services.LivreService;
import com.example.demo.entities.Exemplaire;
import com.example.demo.services.ExemplaireService;
import com.example.demo.entities.Pret;
import com.example.demo.services.PretService;
import com.example.demo.entities.ParametreEmprunt;
import com.example.demo.services.ParametreEmpruntService;

import com.example.demo.entities.Exemplaire;
import com.example.demo.services.ExemplaireService;
import com.example.demo.entities.Penalite;
import com.example.demo.services.PenaliteService;

@Controller
@RequestMapping("/Livres")
public class LivreController {

    @Autowired
    private LivreService LivreService;

    @Autowired
    private PretService pretService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private ExemplaireService exemplaireService;

    @Autowired
    private PenaliteService penaliteService;

    @GetMapping("/liste")
    public String showLivre(Model model) {
        List<Livre> all = this.LivreService.getAllLivre();
        model.addAttribute("livres", all);
        return "listeLivre";
    }

    @GetMapping("/retourLivre")
    public String retourLivre(Model model) {
        return "home";
    }

}
