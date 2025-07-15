package com.example.demo.services;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.example.demo.entities.Exemplaire;
import com.example.demo.repositories.ExemplaireRepository;

@Service
public class ExemplaireService {

    private ExemplaireRepository ExemplaireRepo;

    public ExemplaireService(ExemplaireRepository ur) {
        this.ExemplaireRepo = ur;
    }

    public List<Exemplaire> getAllExemplaire() {
        return this.ExemplaireRepo.findAll();
    }

    public Optional<Exemplaire> getExemplaireById(Integer id) {
        return this.ExemplaireRepo.findById(id);
    }

    public void deleteExemplaire(Integer id) {
        this.ExemplaireRepo.deleteById(id);
    }

    //avoir le nb d'exemplaire restants pour un livre
    public int getStockByExemplaire(Integer idLivre) {
        Integer stock = ExemplaireRepo.getStockByExemplaire(idLivre);
        if (stock == null) {
            throw new RuntimeException("Aucun exemplaire trouvé pour ce livre");
        }
        return stock;
    }

    //avoir les details (stock et nb ) pour un livre
    public Exemplaire getExemplaireByLivre(Integer idLivre) {
        Exemplaire exemplaire = ExemplaireRepo.findByIdLivre(idLivre);
        if (exemplaire == null) {
            throw new RuntimeException("Aucun exemplaire trouvé pour ce livre");
        }
        return exemplaire;
    }

    //check s'il reste un exemplaire
    public boolean checkExemplaire(Integer idLivre) {
        Integer stock = ExemplaireRepo.getStockByExemplaire(idLivre);
        if (stock == null || stock == 0) {
            return false;
        }
        return true;
    }

    public void updateStock(Integer idlivre) {
        Integer stock = ExemplaireRepo.getStockByExemplaire(idlivre);
        this.ExemplaireRepo.updateExemplaire(idlivre, stock - 1);
    }

    public void updateStockUp(Integer idLivre) {
        Integer stock = ExemplaireRepo.getStockByExemplaire(idLivre);
        this.ExemplaireRepo.updateExemplaire(idLivre, stock + 1);
    }

}
