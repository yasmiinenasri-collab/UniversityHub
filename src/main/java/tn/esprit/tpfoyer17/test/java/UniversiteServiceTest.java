package tn.esprit.tpfoyer17.test.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.tpfoyer17.entities.Foyer;
import tn.esprit.tpfoyer17.entities.Universite;
import tn.esprit.tpfoyer17.services.impementations.UniversiteService;
import tn.esprit.tpfoyer17.services.interfaces.IFoyerService;
import tn.esprit.tpfoyer17.services.interfaces.IUniversiteService;

import java.util.List;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@ActiveProfiles("test")
public class UniversiteServiceTest {

    @Autowired
    IUniversiteService universiteService;

    @Autowired
    IFoyerService foyerService;

    // Test for addUniversity()
    @Test
    public void testAddUniversity() {
        Universite universite = Universite.builder().nomUniversite("Université Test").build();
        Universite savedUniversite = universiteService.addUniversity(universite);

        Assertions.assertNotNull(savedUniversite.getIdUniversite(), "L'ID de l'université ne doit pas être null après la sauvegarde");
        Assertions.assertEquals("Université Test", savedUniversite.getNomUniversite(), "Le nom de l'université doit être 'Université Test'");

        universiteService.retrieveUniversity(savedUniversite.getIdUniversite());
        Assertions.assertNotNull(savedUniversite, "L'université ne doit pas être null après récupération");
    }

    // Test for retrieveAllUniversities()
    @Test
    public void testRetrieveAllUniversities() {
        Universite universite1 = Universite.builder().nomUniversite("Université A").build();
        Universite universite2 = Universite.builder().nomUniversite("Université B").build();
        universiteService.addUniversity(universite1);
        universiteService.addUniversity(universite2);

        List<Universite> universites = universiteService.retrieveAllUniversities();
        Assertions.assertFalse(universites.isEmpty(), "La liste des universités ne doit pas être vide");
        Assertions.assertEquals(2, universites.size(), "Il doit y avoir 2 universités récupérées");

        // Nettoyage
        universiteService.retrieveUniversity(universite1.getIdUniversite());
        universiteService.retrieveUniversity(universite2.getIdUniversite());
    }

    // Test for updateUniversity()
    @Test
    public void testUpdateUniversity() {
        Universite universite = Universite.builder().nomUniversite("Université C").build();
        Universite savedUniversite = universiteService.addUniversity(universite);

        savedUniversite.setNomUniversite("Université C Modifiée");
        Universite updatedUniversite = universiteService.updateUniversity(savedUniversite);

        Assertions.assertEquals("Université C Modifiée", updatedUniversite.getNomUniversite(), "Le nom de l'université doit être mis à jour");

        universiteService.retrieveUniversity(savedUniversite.getIdUniversite());
    }

    // Test for retrieveUniversity(long idUniversity)
    @Test
    public void testRetrieveUniversity() {
        Universite universite = Universite.builder().nomUniversite("Université D").build();
        Universite savedUniversite = universiteService.addUniversity(universite);

        Universite retrievedUniversite = universiteService.retrieveUniversity(savedUniversite.getIdUniversite());
        Assertions.assertNotNull(retrievedUniversite, "L'université récupérée ne doit pas être null");
        Assertions.assertEquals(savedUniversite.getIdUniversite(), retrievedUniversite.getIdUniversite(), "L'ID de l'université récupérée doit correspondre à celui de l'université ajoutée");
        Assertions.assertEquals("Université D", retrievedUniversite.getNomUniversite(), "Le nom de l'université doit être 'Université D'");

        universiteService.retrieveUniversity(savedUniversite.getIdUniversite());
    }

    // Test for desaffecterFoyerAUniversite(long idUniversite)
    @Test
    public void testDesaffecterFoyerAUniversite() {
        Universite universite = Universite.builder().nomUniversite("Université E").build();
        Universite savedUniversite = universiteService.addUniversity(universite);

        Foyer foyer = Foyer.builder().nomFoyer("Foyer Test").build();
        foyerService.addFoyer(foyer);

        // Affecter un foyer à l'université
        universiteService.affecterFoyerAUniversite(foyer.getIdFoyer(), savedUniversite.getNomUniversite());
        Universite updatedUniversite = universiteService.desaffecterFoyerAUniversite(savedUniversite.getIdUniversite());

        Assertions.assertNull(updatedUniversite.getFoyer(), "Le foyer ne doit pas être associé à l'université après désaffectation");
    }

    // Test for affecterFoyerAUniversite(long idFoyer, String nomUniversite)
    @Test
    public void testAffecterFoyerAUniversite() {
        Foyer foyer = Foyer.builder().nomFoyer("Foyer 1").build();
        foyerService.addFoyer(foyer);

        Universite universite = Universite.builder().nomUniversite("Université Foyer Test").build();
        Universite savedUniversite = universiteService.addUniversity(universite);

        Universite updatedUniversite = universiteService.affecterFoyerAUniversite(foyer.getIdFoyer(), savedUniversite.getNomUniversite());

        Assertions.assertNotNull(updatedUniversite.getFoyer(), "Le foyer doit être associé à l'université");
        Assertions.assertEquals(foyer.getIdFoyer(), updatedUniversite.getFoyer().getIdFoyer(), "L'ID du foyer associé doit correspondre à celui de l'université");
    }
}
