package tn.esprit.tpfoyer17.services.impementations;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer17.entities.Foyer;
import tn.esprit.tpfoyer17.entities.Universite;
import tn.esprit.tpfoyer17.repositories.FoyerRepository;
import tn.esprit.tpfoyer17.repositories.UniversiteRepository;
import tn.esprit.tpfoyer17.services.interfaces.IUniversiteService;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UniversiteService implements IUniversiteService {

    UniversiteRepository universiteRepository;
    FoyerRepository foyerRepository;

    @Override
    public List<Universite> retrieveAllUniversities() {
        log.debug("Retrieving all universities.");
        return (List<Universite>) universiteRepository.findAll();
    }

    @Override
    public Universite addUniversity(Universite u) {
        log.info("Adding a new university: {}", u.getNomUniversite());
        return universiteRepository.save(u);
    }

    @Override
    public Universite updateUniversity(Universite u) {
        log.info("Updating university with ID: {}", u.getIdUniversite());
        return universiteRepository.save(u);
    }

    @Override
    public Universite retrieveUniversity(long idUniversity) {
        log.debug("Retrieving university with ID: {}", idUniversity);
        return universiteRepository.findById(idUniversity).orElse(null);
    }

    @Override
    public Universite desaffecterFoyerAUniversite(long idUniversite) {
        log.debug("Desaffecting foyer from university with ID: {}", idUniversite);
        Universite universite = universiteRepository.findById(idUniversite).orElse(null);
        if (universite == null) {
            throw new RuntimeException("University not found with ID: " + idUniversite);
        }
        universite.setFoyer(null);
        return universiteRepository.save(universite);
    }

    @Override
    public Universite affecterFoyerAUniversite(long idFoyer, String nomUniversite) {
        log.debug("Assigning foyer with ID: {} to university: {}", idFoyer, nomUniversite);
        Foyer foyer = foyerRepository.findById(idFoyer).orElse(null);
        if (foyer == null) {
            throw new RuntimeException("Foyer not found for ID: " + idFoyer);
        }

        Universite universite = universiteRepository.findByNomUniversiteLike(nomUniversite);
        if (universite == null) {
            throw new RuntimeException("University not found with name: " + nomUniversite);
        }

        universite.setFoyer(foyer);
        return universiteRepository.save(universite);
    }
}
