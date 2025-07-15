package com.example.demo.controllers;

import com.example.demo.entities.Utilisateur;
import com.example.demo.services.UtilisateurService;
import com.example.demo.entities.Adherent;
import com.example.demo.services.AdherentService;

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

    @GetMapping("/goInscription")
    public String goInscription(Model model)
    {
        List<Adherent> ad=adherentService.getAllAdherent();
        model.addAttribute("adherents", ad);
        return "inscription";
    }

    @PostMapping("/login")
    public String login(@RequestParam("nom") String nom,@RequestParam("motDePasse") String motDePasse,
    Model model,HttpSession session) 
    {
        try{
            boolean statut = utilisateurService.authenticate(nom, motDePasse);
                if (statut) {
                    //creer l'utilisateur
                    Utilisateur user=utilisateurService.getInfo(nom,motDePasse);
                    session.setAttribute("utilisateur", user);

                    return "home"; 
                } else {
                    return "index"; 
                }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            model.addAttribute("erreur", e.getMessage());
            return "error"; 
        }
    }

    @PostMapping("/inscription")
    public String inscription(@RequestParam("nom") String nom,@RequestParam("motDePasse") String motDePasse,
    @RequestParam("idAdherent") Integer idAdherent,@RequestParam("dt") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dtn,
    Model model,HttpSession session)
    {
        try {
            Optional<Adherent> optAd = adherentService.getAdherentById(idAdherent);
            if (optAd.isEmpty()) 
            {
                model.addAttribute("erreur", "Adhérent introuvable");
                return "error";
            }
            Adherent ad = optAd.get();
            //Utilisateur user=new Utilisateur(0, ad, nom, motDePasse, dtn, ad.getQuota(), ad.getQuotaMaison());

            Utilisateur user = new Utilisateur(ad, nom, motDePasse, dtn, ad.getQuota(), ad.getQuotaMaison());

            utilisateurService.saveUtilisateur(user);
            session.setAttribute("utilisateur",user);

            return "home";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("erreur", e.getMessage());
            return "error"; 
        }
    }

    @GetMapping("/liste")
    public String getListeUser(Model model)
    {
        List<Utilisateur> users=utilisateurService.getAllUtilisateurs();
        model.addAttribute("utilisateurs", users);

        return "listeUtilisateur";
    }
}