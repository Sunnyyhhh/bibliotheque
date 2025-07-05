package com.example.demo.controllers;

import com.example.demo.entities.Utilisateur;
import com.example.demo.services.UtilisateurService;

import jakarta.servlet.http.HttpSession;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.example.demo.entities.CategorieLivre;
import com.example.demo.entities.Exemplaire;
import com.example.demo.services.ExemplaireService;
import com.example.demo.entities.Livre;
import com.example.demo.services.LivreService;
import com.example.demo.entities.Penalite;
import com.example.demo.services.PenaliteService;
import com.example.demo.entities.ParametreEmprunt;
import com.example.demo.services.ParametreEmpruntService;
import com.example.demo.entities.Pret;
import com.example.demo.services.PretService;
import com.example.demo.entities.Abonnement;
import com.example.demo.services.AbonnementService;
import com.example.demo.entities.Ferie;
import com.example.demo.entities.Prolongement;
import com.example.demo.services.ProlongementService;
import com.example.demo.services.FerieService;

@Controller
@RequestMapping("/Prolongements")
public class ProlongementController {

    @Autowired
    private ExemplaireService exemplaireService;
    @Autowired
    private LivreService livreService;
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private PenaliteService penaliteService;
    @Autowired
    private ParametreEmpruntService parametreempruntService;
    @Autowired
    private PretService pretService;
    @Autowired
    private AbonnementService abonnementService;
    @Autowired
    private FerieService ferieService;
    @Autowired
    private ProlongementService prolongementService;

    @GetMapping("/listeProlongement")
    public String showProlongements(Model model) {
        List<Prolongement> prolongements = prolongementService.getListProlongement();
        model.addAttribute("prolongements", prolongements);
        return "listeProlongement";
    }

}
