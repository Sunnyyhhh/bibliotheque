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
import java.util.HashMap;
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
import com.example.demo.services.FerieService;
import com.example.demo.entities.DetailExemplaire;
import com.example.demo.services.DetailExemplaireService;

@Controller
@RequestMapping("/DetailExemplaire")
public class DetailExemplaireController {

    @Autowired
    private ExemplaireService exemplaireService;

    @Autowired
    private DetailExemplaireService detailExemplaireService;

    @Autowired
    private LivreService livreService;

    public boolean isMemeJour(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
                && c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH);
    }

    @PostMapping("/Detail")
    public ResponseEntity<Map<String, Object>> getDetails(@RequestParam("id_livre") Integer id) {
        List<DetailExemplaire> details = detailExemplaireService.getAllByLivre(id);

        Exemplaire exemplaire = exemplaireService.getExemplaireByLivre(id);

        if (details == null || details.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("error", "Aucun exemplaire trouvé pour le livre avec l'ID " + id));
        }

        List<Map<String, String>> detailList = new ArrayList<>();
        for (DetailExemplaire d : details) {
            Map<String, String> detailMap = new HashMap<>();
            detailMap.put("ref", d.getRef() != null ? d.getRef() : "");
            detailMap.put("statut", d.getStatut() != null ? d.getStatut() : "");
            detailList.add(detailMap);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("details", detailList);
        response.put("nb", exemplaire != null ? exemplaire.getNb() : 0);
        response.put("stock", exemplaire != null ? exemplaire.getStock() : 0);

        //avoir les details du livre
        Optional<Livre> book = livreService.getLivreById(id);
        Livre l = new Livre();
        if (book.isPresent()) {
            l = book.get();
        }

        //titre
        String titre = l.getTitre() != null ? l.getTitre() : "Inconnu";

        //auteur
        String auteur = l.getAuteur() != null ? l.getAuteur() : "Inconnu";

        //categorie
        CategorieLivre categorie = l.getCategorie();
        String categorieNom = (categorie != null && categorie.getNomCategorie() != null) ? categorie.getNomCategorie() : "Inconnu";

        //theme
        String themeNom = (l.getTheme() != null && l.getTheme().getLibelle() != null) ? l.getTheme().getLibelle() : "Inconnu";

        response.put("titre", titre);
        response.put("auteur", auteur);
        response.put("categorie", categorieNom);
        response.put("theme", themeNom);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/detailsPage")
    public String showDetailsPage(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("idLivre", id);
        return "detailsLivre";
    }

}
