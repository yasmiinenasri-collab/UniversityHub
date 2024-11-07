package tn.esprit.tpfoyer17;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.tpfoyer17.entities.Foyer;
import tn.esprit.tpfoyer17.entities.Universite;
import tn.esprit.tpfoyer17.services.interfaces.IFoyerService;
import tn.esprit.tpfoyer17.services.interfaces.IUniversiteService;
import tn.esprit.tpfoyer17.repositories.UniversiteRepository;

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
    @Autowired
    UniversiteRepository universiteRepository;
    @BeforeEach
    public void setUp() {
        // Assurez-vous de vider la base de données avant chaque test
        universiteRepository.deleteAll();
    }

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

        // Ajouter les universités
        universiteService.addUniversity(universite1);
        universiteService.addUniversity(universite2);

        // Récupérer toutes les universités
        List<Universite> universites = universiteService.retrieveAllUniversities();
        Assertions.assertFalse(universites.isEmpty(), "La liste des universités ne doit pas être vide");
        Assertions.assertEquals(2, universites.size(), "Il doit y avoir 2 universités récupérées");

        // Nettoyer les universités après le test
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
        // Créer et sauvegarder une université
        Universite universite = Universite.builder().nomUniversite("Université E").build();
        Universite savedUniversite = universiteService.addUniversity(universite);

        // Créer et sauvegarder un foyer
        Foyer foyer = Foyer.builder().nomFoyer("Foyer Test").build();
        foyerService.addFoyer(foyer);

        // Affecter le foyer à l'université
        universiteService.affecterFoyerAUniversite(foyer.getIdFoyer(), savedUniversite.getNomUniversite());

        // Récupérer l'université après l'association
        Universite updatedUniversite = universiteService.retrieveUniversity(savedUniversite.getIdUniversite());

        // Vérifier que le foyer est associé à l'université
        Assertions.assertNotNull(updatedUniversite.getFoyer(), "Le foyer doit être associé à l'université avant la dissociation");

        // Dissocier le foyer de l'université
        Universite universiteAfterDesaffectation = universiteService.desaffecterFoyerAUniversite(savedUniversite.getIdUniversite());

        // Vérifier que l'université n'a plus de foyer après la dissociation
        Assertions.assertNull(universiteAfterDesaffectation.getFoyer(), "Le foyer ne doit pas être associé à l'université après dissociation");

        // Sauvegarder les modifications dans la base de données
        universiteService.updateUniversity(universiteAfterDesaffectation);
    }


    // Test for affecterFoyerAUniversite(long idFoyer, String nomUniversite)
    @Test
    public void testAffecterFoyerAUniversite() {
        Foyer foyer = Foyer.builder().nomFoyer("Foyer 1").build();
        foyerService.addFoyer(foyer);

        Universite universite = Universite.builder().nomUniversite("Université Foyer Test").build();
        Universite savedUniversite = universiteService.addUniversity(universite);

        // Vérifier que l'université est bien récupérée
        Universite updatedUniversite = universiteService.affecterFoyerAUniversite(foyer.getIdFoyer(), savedUniversite.getNomUniversite());

        // Vérifier que le foyer est bien associé à l'université
        Assertions.assertNotNull(updatedUniversite.getFoyer(), "Le foyer doit être associé à l'université");
        Assertions.assertEquals(foyer.getIdFoyer(), updatedUniversite.getFoyer().getIdFoyer(), "L'ID du foyer associé doit correspondre à celui de l'université");

        // Sauvegarder les modifications dans la base de données
        universiteService.updateUniversity(updatedUniversite);
    }

}