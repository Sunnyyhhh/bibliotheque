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
import com.example.demo.services.FerieService;

@Controller
@RequestMapping("/Exemplaires")
public class ExemplaireController {

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
    public String getDetail(@RequestParam("id_livre") Integer id, Model model) {
        try {
            Integer stock = exemplaireService.getStockByExemplaire(id);

            model.addAttribute("stock", stock);
            return "ExemplaireDetail";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("erreur", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/EmprunterMaison")
    public String EmprunterMaison(@RequestParam("id_livre") Integer id, @RequestParam("dt") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dt,
            HttpSession session, Model model) {
        try {
            //check si un exemplaire est dispo
            boolean checkExemplaire = exemplaireService.checkExemplaire(id);

            //infos pour preter
            Utilisateur user = (Utilisateur) session.getAttribute("utilisateur");
            Optional<Livre> bookref = livreService.getLivreById(id);
            CategorieLivre categorie = null;

            //check si la personne est penalisee
            boolean isPenalise = penaliteService.findPenaliteByDateByUse(user.getIdUtilisateur(), dt);

            //check si la personne peut emmener le livre a la maison
            boolean isAllowedHome = false;

            //avoir quotamaison + quotasimple
            Integer quotaGeneral = user.getQuotaPerso();
            //System.out.println("QUOTA GENERAL " + quotaGeneral);
            Integer quotaMaison = user.getQuotaPersoMaison();
            //System.out.println("QUOTA MAISON " + quotaMaison);
            Livre livre = null;

            //si on peut voir les caracteristiques du livre
            if (bookref.isPresent()) {
                livre = bookref.get();
                categorie = livre.getCategorie();
                if (categorie != null) {
                    isAllowedHome = parametreempruntService.isAllowedHome(user.getAdherent().getId_Adherent(), livre.getIdLivre());
                } else {
                    model.addAttribute("erreur", "Aucune catégorie associée au livre");
                    return "error";
                }
            } else {
                model.addAttribute("erreur", "Livre non trouvé");
                return "error";
            }

            //check l'age de l'user
            boolean statutAge = utilisateurService.checkAge(user, categorie);

            //check si l'user est toujours actif
            boolean isActif = utilisateurService.isActive(user, dt);

            if (checkExemplaire == true && statutAge == true
                    && quotaGeneral > 0 && quotaMaison > 0
                    && isPenalise == false && isAllowedHome == true
                    && isActif == true) {
                model.addAttribute("message", "Emprunt effectue avec succes");

                //update le stock de cet exemplaire
                exemplaireService.updateStock(livre.getIdLivre());
                //update le quotaTotal de la personne et quota maison d'une personne
                utilisateurService.updateQuota(quotaGeneral - 1, quotaMaison - 1, user.getIdUtilisateur());
                //inserer le pret 
                //Avoir les parametres de l'emprunt
                //ParametreEmprunt param = parametreempruntService.getParametreByAdherent(user.getAdherent().getId_Adherent(),livre.getIdLivre());
                ParametreEmprunt param = parametreempruntService.getParametreByAdherent(user.getAdherent().getId_Adherent(), livre.getIdLivre());

                Integer nbprevueRetour = param.getNbJour();

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

            } else {
                /*System.out.println("AGE " + statutAge);
                System.out.println("DISPONIBILITE " + checkExemplaire);
                System.out.println("QUOTA G " + quotaGeneral);
                System.out.println("QUOTA M " + quotaMaison);
                System.out.println("ALLOWED HOME " + isAllowedHome);
                System.out.println("ACTIF " + isActif);
                System.out.println("PENALITE" + isPenalise);*/

                model.addAttribute("message", "Echec de l'emprunt");
                if (checkExemplaire == false) {
                    model.addAttribute("messageDisponibilite", "Livre non disponible");
                }
                if (statutAge == false) {
                    model.addAttribute("messageAge", "Vous n'avez pas l'age requis pour emprunter ce livre");
                }
                if (quotaGeneral == 0) {
                    model.addAttribute("messageQuotaGeneral", "Plus de quota d'emprunt");
                }
                if (quotaMaison == 0) {
                    model.addAttribute("messageQuotaMaison", "Plus de quota pour l'emprunt a domicile");
                }
                if (isAllowedHome == false) {
                    model.addAttribute("messageHome", "Vous n'avez pas le droit d'emprunt a domicile pour ce livre");
                }
                if (isActif == false) {
                    model.addAttribute("messageAdhesion", "Vous devez vous reabonner");
                }
                if (isPenalise == true) {
                    model.addAttribute("messagePenalisation", "Vous etes encore penalise");
                }
            }

            return "reponsepret";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("erreur", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/EmprunterPlace")
    public String EmprunterPlace(@RequestParam("id_livre") Integer id, @RequestParam("dt") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dt,
            HttpSession session, Model model) {
        try {
            //check si un exemplaire est dispo
            boolean checkExemplaire = exemplaireService.checkExemplaire(id);

            //check si la personne est penalisee
            boolean isPenalise = penaliteService.findPenaliteByDateByUse(id, dt);

            Utilisateur user = (Utilisateur) session.getAttribute("utilisateur");
            Optional<Livre> bookref = livreService.getLivreById(id);
            CategorieLivre categorie = null;
            Livre livre = null;

            if (bookref.isPresent()) {
                livre = bookref.get();
                categorie = livre.getCategorie();
                if (categorie == null) {
                    model.addAttribute("erreur", "Aucune catégorie associée au livre");
                    return "error";
                }
            } else {
                model.addAttribute("erreur", "Livre non trouvé");
                return "error";
            }

            //check le quotaGeneral
            Integer quotaGeneral = user.getQuotaPerso();

            //check si l'age est valide
            boolean statutAge = utilisateurService.checkAge(user, categorie);

            //check si l'user est toujours actif
            boolean isActif = utilisateurService.isActive(user, dt);

            //statut final
            boolean statutFinal = false;

            if (quotaGeneral > 0 && statutAge == true
                    && checkExemplaire == true && isPenalise == false
                    & isActif == true) {
                statutFinal = true;

                //inserer dans la table pret
                //update le quota de livre general
                utilisateurService.updateQuotaPlace(quotaGeneral - 1, user.getIdUtilisateur());
                //update le stock de livre 
                exemplaireService.updateStock(livre.getIdLivre());
                model.addAttribute("statutFinal", "mety sur place");

                //inserer le pret 
                //avoir la date prevue de retour
                Integer nbprevueRetour = user.getAdherent().getNbJourEmprunt();

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dt);
                calendar.add(Calendar.DAY_OF_MONTH, nbprevueRetour);
                Date prevuRetour = calendar.getTime();

                //date emprunt,date retour,date prevue retour ,livre,utilisateur
                ParametreEmprunt param = parametreempruntService.getParametreByAdherentPlace(user.getAdherent().getId_Adherent());
                //ParametreEmprunt param = parametreempruntService.getParametreByAdherent(user.getAdherent().getId_Adherent(), livre.getIdLivre());
                Pret p = new Pret(dt, null, prevuRetour, livre, user, param);

                //enregistrer le pret
                pretService.savePret(p);
            } else {
                model.addAttribute("message", "Echec de l'emprunt");
                if (checkExemplaire == false) {
                    model.addAttribute("messageDisponibilite", "Livre non disponible");
                }
                if (statutAge == false) {
                    model.addAttribute("messageAge", "Vous n'avez pas l'age requis pour emprunter ce livre");
                }
                if (quotaGeneral == 0) {
                    model.addAttribute("messageQuotaGeneral", "Plus de quota d'emprunt");
                }
                if (isActif == false) {
                    model.addAttribute("messageAdhesion", "Vous devez vous reabonner");
                }
                if (isPenalise == true) {
                    model.addAttribute("messagePenalisation", "Vous etes encore penalise");
                }
            }

            return "reponsepret";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("erreur", e.getMessage());
            return "error";
        }
    }

}
