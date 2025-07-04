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

    @GetMapping("/listeEmprunte")
    public String showLivreEmprunte(Model model, HttpSession session) {
        Utilisateur user = (Utilisateur) session.getAttribute("utilisateur");
        List<Livre> all = this.LivreService.getLivreEmpruntesById(user.getIdUtilisateur());

        model.addAttribute("livres", all);
        return "listeLivreEmprunte";
    }

    @GetMapping("/retourLivre")
    public String retourLivre(Model model) {
        return "home";
    }

    @PostMapping("/rendre")
    public String rendreLivre(@RequestParam("dt") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dt,
            @RequestParam("idLivre") Integer idlivre, Model model, HttpSession session) {

        //ref user
        Utilisateur user = (Utilisateur) session.getAttribute("utilisateur");

        //get emprunt
        Pret emprunt = pretService.getPretSpecifique(user.getIdUtilisateur(), idlivre);
        ParametreEmprunt parametre = emprunt.getParametreEmprunt();
        String modeEmprunt = parametre.getModeEmprunt();

        Integer quotaGeneral = user.getQuotaPerso();
        Integer quotaMaison = user.getQuotaPersoMaison();

        //update le stock
        exemplaireService.updateStockUp(idlivre);
        pretService.updateExemplaireRetour(emprunt.getIdPret(), dt);

        //check retard
        Date retourPrevu = emprunt.getDtRetourPrevue();
        if (dt == null || retourPrevu == null) {
            throw new IllegalArgumentException("Les dates de retour ne doivent pas être nulles");
        }

        long diffInMillis = dt.getTime() - retourPrevu.getTime();
        int diff = (int) (diffInMillis / (1000 * 60 * 60 * 24));

        //si ya un retard
        if (diff > 0) {
            //check si ya deja une penalite en cours au moment de l'emprunt
            Optional<Penalite> peineOpt = penaliteService.getPenaliteByDateUser(user.getIdUtilisateur(), dt);

            Calendar calendar = Calendar.getInstance();
            Date nouvelleFin;

            if (peineOpt.isPresent()) {
                // Prolonge la pénalité existante
                Penalite peineExistante = peineOpt.get();
                calendar.setTime(peineExistante.getDtFin());
                calendar.add(Calendar.DAY_OF_MONTH, diff);
                nouvelleFin = calendar.getTime();

                penaliteService.updatePenalite(peineExistante.getIdPenalite(), nouvelleFin);
            } else {
                //creer une nouvelle penalite
                calendar.setTime(dt);
                calendar.add(Calendar.DAY_OF_MONTH, diff);
                nouvelleFin = calendar.getTime();

                Penalite nouvellePeine = new Penalite(dt, nouvelleFin, user);
                penaliteService.savePenalite(nouvellePeine);
            }
        }
        //maj quota user
        if ("DOMICILE".equals(modeEmprunt)) {
            //System.out.println("    QUOTA GENERAL " + quotaGeneral);
            //System.out.println("    QUOTA MAISON " + quotaMaison);

            utilisateurService.updateQuota(quotaGeneral, quotaMaison, user.getIdUtilisateur());

            quotaGeneral = user.getQuotaPerso();
            quotaMaison = user.getQuotaPersoMaison();

            //System.out.println("AF    QUOTA GENERAL " + quotaGeneral);
            //System.out.println("AF    QUOTA MAISON " + quotaMaison);
        } else {
            utilisateurService.updateQuotaPlace(quotaGeneral + 1, user.getIdUtilisateur());
        }
        List<Livre> all = this.LivreService.getAllLivre();
        model.addAttribute("livres", all);
        //return "listeLivre";
        //return "listeLivreEmprunte";
        return "home";
    }

}
