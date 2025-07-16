package com.example.demo.controllers;

import com.example.demo.entities.Utilisateur;
import com.example.demo.services.UtilisateurService;
import com.example.demo.entities.Adherent;
import com.example.demo.services.AdherentService;
import com.example.demo.services.AbonnementService;
import com.example.demo.services.PenaliteService;
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
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

import java.util.*;

@Controller
@RequestMapping("/utilisateurs")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private AdherentService adherentService;

    @Autowired
    private AbonnementService abonnementService;

    @Autowired
    private PenaliteService penaliteService;

    @GetMapping("/goInscription")
    public String goInscription(Model model) {
        List<Adherent> ad = adherentService.getAllAdherent();
        model.addAttribute("adherents", ad);
        return "inscription";
    }

    @PostMapping("/login")
    public String login(@RequestParam("nom") String nom, @RequestParam("motDePasse") String motDePasse,
            Model model, HttpSession session) {
        try {
            boolean statut = utilisateurService.authenticate(nom, motDePasse);
            if (statut) {
                //creer l'utilisateur
                Utilisateur user = utilisateurService.getInfo(nom, motDePasse);
                session.setAttribute("utilisateur", user);

                return "home";
            } else {
                return "index";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("erreur", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/inscription")
    public String inscription(@RequestParam("nom") String nom, @RequestParam("motDePasse") String motDePasse,
            @RequestParam("idAdherent") Integer idAdherent, @RequestParam("dt") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dtn,
            Model model, HttpSession session) {
        try {
            Optional<Adherent> optAd = adherentService.getAdherentById(idAdherent);
            if (optAd.isEmpty()) {
                model.addAttribute("erreur", "Adhérent introuvable");
                return "error";
            }
            Adherent ad = optAd.get();
            //Utilisateur user=new Utilisateur(0, ad, nom, motDePasse, dtn, ad.getQuota(), ad.getQuotaMaison());

            Utilisateur user = new Utilisateur(ad, nom, motDePasse, dtn, ad.getQuota(), ad.getQuotaMaison());

            utilisateurService.saveUtilisateur(user);
            session.setAttribute("utilisateur", user);

            return "home";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("erreur", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/liste")
    public String getListeUser(Model model) {
        List<Utilisateur> users = utilisateurService.getAllUtilisateurs();
        model.addAttribute("utilisateurs", users);

        return "listeUtilisateur";
    }

    @PostMapping("/Detail")
    public ResponseEntity<Map<String, Object>> getUtilisateurDetails(@RequestParam("id_utilisateur") Integer id) {
        Optional<Utilisateur> userOpt = utilisateurService.getUtilisateurById(id);

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("error", "Utilisateur non trouvé avec l'ID " + id));
        }

        Utilisateur u = userOpt.get();

        //check si l'user est actif mtnt 
        boolean isActive = abonnementService.checkAbonnementNow(u.getIdUtilisateur());

        //check si l'user est penalise
        boolean isPenalized = penaliteService.checkPenaliteNow(u.getIdUtilisateur());

        //Details user
        Map<String, Object> response = new HashMap<>();
        //si actif 
        if (isActive) {
            response.put("status", "Actif");
        } else {
            response.put("status", "Inactif");
        }
        //si penalise
        if (isPenalized) {
            response.put("penalite", "Penalise");
        } else {
            response.put("penalite", "Non penalise");
        }

        response.put("num", u.getNumAdherent());
        response.put("id", u.getIdUtilisateur());
        response.put("nom", u.getNom() != null ? u.getNom() : "Inconnu");
        response.put("statut", u.getStatut() != null ? u.getStatut() : "Non défini");
        response.put("dtn", u.getDtn() != null ? u.getDtn().toString() : "Non précisée");
        response.put("quotaPerso", u.getQuotaPerso() != null ? u.getQuotaPerso() : 0);
        response.put("quotaPersoMaison", u.getQuotaPersoMaison() != null ? u.getQuotaPersoMaison() : 0);
        response.put("nbProlongement", u.getNbProlongement() != null ? u.getNbProlongement() : 0);

        //adherent retourne
        Adherent ad = u.getAdherent();
        if (ad != null) {
            Map<String, Object> adherentMap = new HashMap<>();
            adherentMap.put("nom", ad.getNom_Adherent() != null ? ad.getNom_Adherent() : "Inconnu");
            adherentMap.put("quota", ad.getQuota() != null ? ad.getQuota() : 0);
            adherentMap.put("quotaMaison", ad.getQuotaMaison() != null ? ad.getQuotaMaison() : 0);
            adherentMap.put("nbJourEmprunt", ad.getNbJourEmprunt() != null ? ad.getNbJourEmprunt() : 0);
            response.put("adherent", adherentMap);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/detailsPage")
    public String showDetailsUtilisateurPage(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("id", id);
        return "detailsUtilisateur";
    }

}
