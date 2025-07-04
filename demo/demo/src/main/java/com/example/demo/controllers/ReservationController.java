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
import com.example.demo.entities.Reservation;
import com.example.demo.services.ReservationService;
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
import com.example.demo.entities.Exemplaire;
import com.example.demo.services.ExemplaireService;
import com.example.demo.entities.Ferie;
import com.example.demo.services.FerieService;
import java.lang.Object;

@Controller
@RequestMapping("/Reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;
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
    private ExemplaireService exemplaireService;
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

    @GetMapping("/listeReservation")
    public String showReservation(Model model) {
        List<Object[]> obj = reservationService.getReservationByDateByLivre();
        List<Reservation> reserves = new ArrayList<>();
        List<Integer> nb = new ArrayList<>();

        if (obj != null) {
            for (Object[] o : obj) {

                Integer idlivre = (Integer) o[0];
                Optional<Livre> livre = livreService.getLivreById(idlivre);
                Livre book = null;
                if (livre.isPresent()) {
                    book = livre.get();
                }

                Long count = (Long) o[1];
                Reservation resTemp = new Reservation(book);
                reserves.add(resTemp);
                nb.add(count.intValue());
            }
        }
        model.addAttribute("reservations", reserves);
        model.addAttribute("nb", nb);

        return "listeReservation";
    }

    @PostMapping("/reserver")
    public String reserver(Model model, @RequestParam("id_livre") Integer id, HttpSession session,
            @RequestParam("dt") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dt,
            @RequestParam("dtAction") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dtAction) {
        Utilisateur user = (Utilisateur) session.getAttribute("utilisateur");
        Optional<Livre> livres = livreService.getLivreById(id);
        Livre book = null;
        CategorieLivre categorie = null;

        //check les criteres pour qu'il puisse reserver
        //check penalisation au moment de reservation
        boolean isPenalise = penaliteService.findPenaliteByDateByUse(id, dtAction);

        //check si peut emmener a la maison
        boolean isAllowedHome = false;
        if (livres.isPresent()) {
            book = livres.get();
            categorie = book.getCategorie();
            isAllowedHome = parametreempruntService.isAllowedHome(user.getAdherent().getId_Adherent(), book.getIdLivre());

        } else {
            model.addAttribute("erreur", "Livre non trouve");
            return "error";
        }

        //check si l'age est valide
        boolean statutAge = utilisateurService.checkAge(user, categorie);

        //check si l'user est toujours actif
        boolean isActif = utilisateurService.isActive(user, dtAction);

        if (isActif == true && statutAge == true && isAllowedHome == true && isPenalise == false) {
            //faire la reservation
            Reservation resa = new Reservation(user, book, dt, dtAction, 0);
            reservationService.saveReservation(resa);

            //liste des livres
            List<Livre> all = livreService.getAllLivre();
            model.addAttribute("livres", all);
            return "listeLivre";
        } else {
            model.addAttribute("message", "Echec de la reservation");
            if (statutAge == false) {
                model.addAttribute("messageAge", "Vous n'avez pas l'age requis pour emprunter ce livre");
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
    }

    @PostMapping("/valider")
    public String valider(Model model, @RequestParam("idlivre") Integer idlivre, @RequestParam("dtTraitement") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dt) {
        List<Reservation> resaNonTraite = reservationService.getReservationByLivre(idlivre);

        //check disponibilite
        boolean isDispo = exemplaireService.checkExemplaire(idlivre);

        //si dispo
        if (isDispo == true) {
            //transformer en pret
            //avoir le stock pour le livre 
            int stock = exemplaireService.getStockByExemplaire(idlivre);
            int i = 0;

            //avoir le livre
            Optional<Livre> book = livreService.getLivreById(idlivre);
            Livre boky = null;
            if (book.isPresent()) {
                boky = book.get();
            } else {
                model.addAttribute("erreur", "Livre non trouve");
                return "error";
            }

            while (stock > 0) {
                if (resaNonTraite.get(i) != null) {
                    Reservation resaTemp = resaNonTraite.get(i);
                    //check si la personne n'a pas ete penalise 
                    boolean isPenalise = penaliteService.findPenaliteByDateByUse(resaTemp.getUtilisateur().getIdUtilisateur(), dt);

                    //si pas de penalisation
                    if (isPenalise == false) {
                        //update le stock a chaque fois 
                        exemplaireService.updateStock(idlivre);

                        //avoir l'user
                        Utilisateur user = resaTemp.getUtilisateur();

                        //update la reservation
                        reservationService.updateReservation(dt, idlivre, user.getIdUtilisateur(), resaTemp.getDateAction());

                        //diminuer les quotas maison et simple
                        Integer quotaGeneral = user.getQuotaPerso();
                        Integer quotaMaison = user.getQuotaPersoMaison();

                        System.out.println("af QUOTA GENERAL" + quotaGeneral);
                        System.out.println("af QUOTA MAISON" + quotaMaison);

                        utilisateurService.updateQuota(quotaGeneral - 1, quotaMaison - 1, user.getIdUtilisateur());

                        quotaGeneral = user.getQuotaPerso();
                        quotaMaison = user.getQuotaPersoMaison();
                        System.out.println("af QUOTA GENERAL " + quotaGeneral);
                        System.out.println("af QUOTA MAISON " + quotaMaison);

                        //enregistrer un pret
                        //avoir les parametres
                        ParametreEmprunt param = parametreempruntService.getParametreByAdherent(user.getAdherent().getId_Adherent(), boky.getIdLivre());

                        //avoir la date prevue de retour
                        Integer nbprevueRetour = param.getNbJour();

                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(dt);
                        calendar.add(Calendar.DAY_OF_MONTH, nbprevueRetour);
                        Date prevuRetour = calendar.getTime();

                        //check si c'est un jour ouvrable
                        List<Ferie> allFerie = ferieService.getAllFerie();
                        List<Date> feries = new ArrayList<>();
                        if (!allFerie.isEmpty()) {
                            for (Ferie f : allFerie) {
                                Date fer = f.getDate();
                                feries.add(fer);
                            }
                        }
                        //si c'est ferie / dimanche / samedi 
                        // recalculer la date jusqu'e ce qu'elle soit OK (ni ferie, ni samedi/dimanche)
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
                        Pret p = new Pret(dt, null, prevuRetour, boky, user, param);
                        //enregistrer le pret
                        pretService.savePret(p);
                        stock--;
                    }
                    i++;
                } else {
                    break;
                }
            }

            String back = this.showReservation(model);
            return back;
        } else {
            model.addAttribute("messageDisponibilite", "Livre non disponible pour traiter les reservations");

            return "reponsepret";
        }
    }

    @GetMapping("/DetailByLivre")
    public String detailByLivre(Model model, @RequestParam("idLivre") Integer idLivre) {
        List<Reservation> resa = reservationService.getReservationByLivre(idLivre);
        model.addAttribute("reservations", resa);
        return "detailreservation";
    }

    @GetMapping("/retourReservation")
    public String retourReservation(Model model) {
        return "home";
    }

    @PostMapping("/validerIndividuel")
    public String validerIndividuel(Model model,
            @RequestParam("idlivre") Integer idlivre,
            @RequestParam("dtTraitement") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dt,
            @RequestParam("idReservation") Integer idreservation) {
        //reservation a traiter
        Optional<Reservation> reserve = reservationService.getReservationById(idreservation);

        if (!reserve.isPresent()) {
            model.addAttribute("erreur", "Reservation non trouvee");
            return "error";
        }

        Reservation resa = reserve.get();

        if (!resa.getLivre().getIdLivre().equals(idlivre)) {
            model.addAttribute("erreur", "La reservation ne correspond pas au livre specifie");
            return "error";
        }

        //check disponibilite
        boolean isDispo = exemplaireService.checkExemplaire(idlivre);

        if (isDispo) {
            //check penalite
            boolean isPenalise = penaliteService.findPenaliteByDateByUse(resa.getUtilisateur().getIdUtilisateur(), dt);

            if (!isPenalise) {
                //getLivre
                Optional<Livre> book = livreService.getLivreById(idlivre);
                Livre boky = null;
                if (book.isPresent()) {
                    boky = book.get();
                } else {
                    model.addAttribute("erreur", "Livre non trouve");
                    return "error";
                }

                //maj stock
                exemplaireService.updateStock(idlivre);

                //getuser
                Utilisateur user = resa.getUtilisateur();

                //maj reservation
                reservationService.updateReservation(dt, idlivre, user.getIdUtilisateur(), resa.getDateAction());

                //diminuer quotas
                Integer quotaGeneral = user.getQuotaPerso();
                Integer quotaMaison = user.getQuotaPersoMaison();
                utilisateurService.updateQuota(quotaGeneral - 1, quotaMaison - 1, user.getIdUtilisateur());

                //param emprunt
                ParametreEmprunt param = parametreempruntService.getParametreByAdherent(user.getAdherent().getId_Adherent(), boky.getIdLivre());

                //calcul date retourprev
                Integer nbprevueRetour = param.getNbJour();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dt);
                calendar.add(Calendar.DAY_OF_MONTH, nbprevueRetour);
                Date prevuRetour = calendar.getTime();

                //check samedi/dimanche/ferie
                List<Ferie> allFerie = ferieService.getAllFerie();
                List<Date> feries = new ArrayList<>();
                if (!allFerie.isEmpty()) {
                    for (Ferie f : allFerie) {
                        feries.add(f.getDate());
                    }
                }

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

                //save pret
                Pret p = new Pret(dt, null, prevuRetour, boky, user, param);
                pretService.savePret(p);

                return this.showReservation(model);
            } else {
                model.addAttribute("erreur", "L'utilisateur est penalise et ne peut pas emprunter");
                return "error";
            }
        } else {
            model.addAttribute("messageDisponibilite", "Livre non disponible pour traiter la reservation");
            return "reponsepret";
        }
    }
}
