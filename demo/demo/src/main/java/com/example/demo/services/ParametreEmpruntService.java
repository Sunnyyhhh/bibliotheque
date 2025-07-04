package com.example.demo.services;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.example.demo.entities.ParametreEmprunt;
import com.example.demo.repositories.ParametreEmpruntRepository;

@Service
public class ParametreEmpruntService {

    private ParametreEmpruntRepository ParametreEmpruntRepo;

    public ParametreEmpruntService(ParametreEmpruntRepository ur) {
        this.ParametreEmpruntRepo = ur;
    }

    public List<ParametreEmprunt> getAllParametreEmprunt() {
        return this.ParametreEmpruntRepo.findAll();
    }

    public Optional<ParametreEmprunt> getParametreEmpruntById(Integer id) {
        return this.ParametreEmpruntRepo.findById(id);
    }

    public void deleteParametreEmprunt(Integer id) {
        this.ParametreEmpruntRepo.deleteById(id);
    }

    public boolean isAllowedHome(Integer idAdherent, Integer idlivre) {
        ParametreEmprunt param = this.ParametreEmpruntRepo.getParametreEmpruntMaison(idAdherent, idlivre);

        if (param.getEmpruntDomicile() == true) {
            return true;
        } else {
            return false;
        }
    }

    public ParametreEmprunt getParametreByAdherent(Integer id,Integer idlivre) {
        return this.ParametreEmpruntRepo.getParametreEmpruntMaison(id,idlivre);
    }

    public ParametreEmprunt getParametreByAdherentPlace(Integer id) {
        return this.ParametreEmpruntRepo.getParametreEmpruntPlace(id);
    }

}
