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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.entities.Livre;
import com.example.demo.entities.Adherent;
import com.example.demo.services.LivreService;
import com.example.demo.entities.Exemplaire;
import com.example.demo.services.ExemplaireService;
import com.example.demo.entities.Pret;
import com.example.demo.services.PretService;
import com.example.demo.entities.ParametreEmprunt;
import com.example.demo.services.ParametreEmpruntService;
import com.example.demo.services.AdherentService;

import com.example.demo.entities.Exemplaire;
import com.example.demo.services.ExemplaireService;
import com.example.demo.entities.Penalite;
import com.example.demo.services.PenaliteService;
import com.example.demo.entities.Utilisateur;
import com.example.demo.services.UtilisateurService;
import com.example.demo.entities.Prolongement;
import com.example.demo.services.ProlongementService;
import com.example.demo.entities.ParametreEmprunt;
import com.example.demo.services.ParametreEmpruntService;
import com.example.demo.entities.Ferie;
import com.example.demo.services.DetailExemplaireService;
import com.example.demo.services.FerieService;
import com.example.demo.entities.DetailExemplaire;

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

    @Autowired
    private ProlongementService prolongementService;

    @Autowired
    private ParametreEmpruntService parametreempruntService;

    @Autowired
    private FerieService ferieService;

    @Autowired
    private DetailExemplaireService detailExemplaireService;

    public boolean isMemeJour(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
                && c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH);
    }

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

        //avoir le detail exemplaire a update 
        detailExemplaireService.rendreDisponible(emprunt.getIdRef());

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

        //avoir le nb de jours de penalite 
        Adherent ad = user.getAdherent();
        Integer peine = ad.getNbPenalite();

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
                calendar.add(Calendar.DAY_OF_MONTH, peine);
                nouvelleFin = calendar.getTime();

                penaliteService.updatePenalite(peineExistante.getIdPenalite(), nouvelleFin);
            } else {
                //creer une nouvelle penalite
                calendar.setTime(dt);
                calendar.add(Calendar.DAY_OF_MONTH, peine);
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
        } else if ("SUR_PLACE".equals(modeEmprunt)) {
            utilisateurService.updateQuotaPlace(quotaGeneral + 1, user.getIdUtilisateur());
        }
        List<Livre> all = this.LivreService.getAllLivre();
        model.addAttribute("livres", all);
        //return "listeLivre";
        //return "listeLivreEmprunte";
        return "home";
    }

    @PostMapping("/prolonger")
    public String prolongerEmpruntLivre(@RequestParam("dt") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dt,
            @RequestParam("idLivre") Integer idlivre, Model model, HttpSession session) {
        //avoir le quota de prolongement de la personne
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

        //si la personne a encore un quota de prolongement
        if (utilisateur.getNbProlongement() > 0) {
            //avoir le pret de la personne le plus recent pour ce livre
            Pret pret = pretService.getPretMaxSpecifique(utilisateur.getIdUtilisateur(), idlivre);

            //insertion de prolongement
            Prolongement p = new Prolongement(utilisateur, pret, 0, dt, null);

            prolongementService.saveProlongement(p);
        } else {
            model.addAttribute("message", "Vous n'avez plus le droit d'effectuer un prolongement");
            return "listeLivreEmprunte";
        }

        model.addAttribute("message", "Votre demande de prolongement a ete enregistree ");
        List<Livre> all = this.LivreService.getLivreEmpruntesById(utilisateur.getIdUtilisateur());

        model.addAttribute("livres", all);
        return "listeLivreEmprunte";

    }

    @PostMapping("validerProlongement")
    public String validerProlongement(@RequestParam("idProlongement") Integer idProlongement,
            @RequestParam("dt") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dt,
            @RequestParam("iduser") Integer idUser,
            Model model) {
        //update le prolongement
        prolongementService.updateProlongement(idProlongement, dt);

        //diminuer le quota de l'user
        //avoir l'user
        Optional<Utilisateur> users = utilisateurService.getUtilisateurById(idUser);
        Utilisateur user = null;
        if (users.isPresent()) {
            user = users.get();
        }

        //avoir le livre
        Optional<Prolongement> prolong = prolongementService.getProlongementById(idProlongement);
        Prolongement pro = null;
        if (prolong.isPresent()) {
            pro = prolong.get();
        }

        Livre livre = pro.getPret().getLivre();

        //update le quota
        utilisateurService.updateQuotaProlongement(user.getNbProlongement() - 1, idUser);

        //update le prolongement
        prolongementService.updateProlongement(idProlongement, dt);

        //inserer le pret
        ParametreEmprunt param = parametreempruntService.getParametreByAdherent(user.getAdherent().getId_Adherent(), livre.getIdLivre());

        Integer nbprevueRetour = param.getNbJour();

        //mettre une date de retour pour l'ancien pret
        Pret borrow = pretService.getPretMaxSpecifique(idUser, livre.getIdLivre());
        List<Pret> pretsListes = pretService.getPretMaxSpecifiqueListe(user.getIdUtilisateur(), borrow.getLivre().getIdLivre());
        pretService.updateExemplaireRetour(pretsListes.get(0).getIdPret(), dt);

        //avoir la date prevue de retour
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dt);
        calendar.add(Calendar.DAY_OF_MONTH, nbprevueRetour);
        Date prevuRetour = calendar.getTime();

        //check si c'est un jour ouvrable
        List<Ferie> allFerie = ferieService.getAllFerie();
        List<Date> feries = new ArrayList<>();
        if (!allFerie.isEmpty()) {
            for (Ferie f : allFerie) {
                Date temp = f.getDate();
                feries.add(temp);
            }
        }
        //si c'est ferie / dimanche / samedi 
        // recalculer la date jusqu'à ce qu'elle soit OK (ni férié, ni samedi/dimanche)
        boolean doitRecaler = true;
        while (doitRecaler) {
            doitRecaler = false;

            for (Date ref : feries) {
                if (isMemeJour(prevuRetour, ref)) {
                    Calendar c = Calendar.getInstance();
                    c.setTime(prevuRetour);
                    c.add(Calendar.DAY_OF_MONTH, 1);
                    prevuRetour = c.getTime();
                    doitRecaler = true;
                    break;
                }
            }

            Calendar c = Calendar.getInstance();
            c.setTime(prevuRetour);
            int jour = c.get(Calendar.DAY_OF_WEEK);
            if (jour == Calendar.SATURDAY) {
                c.add(Calendar.DAY_OF_MONTH, 2);
                prevuRetour = c.getTime();
                doitRecaler = true;
            } else if (jour == Calendar.SUNDAY) {
                c.add(Calendar.DAY_OF_MONTH, 1);
                prevuRetour = c.getTime();
                doitRecaler = true;
            }
        }

        //date emprunt,date retour,date prevue retour ,livre,utilisateur
        Pret p = new Pret(dt, null, prevuRetour, livre, user, param);
        //enregistrer le pret
        pretService.savePret(p);

        //retour
        List<Prolongement> prolongements = prolongementService.getListProlongement();
        model.addAttribute("prolongements", prolongements);
        return "listeProlongement";
    }

}
