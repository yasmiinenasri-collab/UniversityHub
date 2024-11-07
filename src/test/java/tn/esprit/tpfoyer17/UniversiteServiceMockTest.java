package tn.esprit.tpfoyer17;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer17.entities.Foyer;
import tn.esprit.tpfoyer17.entities.Universite;
import tn.esprit.tpfoyer17.repositories.FoyerRepository;
import tn.esprit.tpfoyer17.repositories.UniversiteRepository;
import  tn.esprit.tpfoyer17.services.impementations.UniversiteService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UniversiteServiceMockTest {

    @Mock
    UniversiteRepository universiteRepository;

    @Mock
    FoyerRepository foyerRepository;

    @InjectMocks
    UniversiteService universiteService;

    // Test for addUniversity()
    @Test
    void testAddUniversity() {
        Universite universite = Universite.builder().nomUniversite("ESPRIT").build();

        // Mock the save method
        when(universiteRepository.save(universite)).thenReturn(universite);

        Universite savedUniversite = universiteService.addUniversity(universite);

        assertNotNull(savedUniversite, "The saved university should not be null");
        assertEquals("ESPRIT", savedUniversite.getNomUniversite(), "The university name should match");
        verify(universiteRepository, times(1)).save(universite); // Verify save was called once
    }

    // Test for retrieveAllUniversities()
    @Test
    void testRetrieveAllUniversities() {
        Universite uni1 = Universite.builder().nomUniversite("ESPRIT").build();
        Universite uni2 = Universite.builder().nomUniversite("Tunisia University").build();

        // Mock the findAll method
        when(universiteRepository.findAll()).thenReturn(Arrays.asList(uni1, uni2));

        List<Universite> universities = universiteService.retrieveAllUniversities();

        assertNotNull(universities, "The university list should not be null");
        assertEquals(2, universities.size(), "There should be 2 universities retrieved");

        verify(universiteRepository, times(1)).findAll(); // Verify findAll was called once
    }

    // Test for updateUniversity()
    @Test
    void testUpdateUniversity() {
        Universite universite = Universite.builder().idUniversite(1L).nomUniversite("ESPRIT").build();

        // Mock the save method
        when(universiteRepository.save(universite)).thenReturn(universite);

        universite.setNomUniversite("Updated ESPRIT"); // Update university name
        Universite updatedUniversite = universiteService.updateUniversity(universite);

        assertEquals("Updated ESPRIT", updatedUniversite.getNomUniversite(), "The university name should be updated");
        verify(universiteRepository, times(1)).save(universite); // Verify save was called once
    }

    // Test for retrieveUniversity(long idUniversity)
    @Test
    void testRetrieveUniversity() {
        Universite universite = Universite.builder().idUniversite(1L).nomUniversite("ESPRIT").build();

        // Mock the findById method
        when(universiteRepository.findById(1L)).thenReturn(Optional.of(universite));

        Universite retrievedUniversite = universiteService.retrieveUniversity(1L);

        assertNotNull(retrievedUniversite, "The retrieved university should not be null");
        assertEquals(1L, retrievedUniversite.getIdUniversite(), "The university ID should match");
        verify(universiteRepository, times(1)).findById(1L); // Verify findById was called once
    }

    // Test for affecterFoyerAUniversite()
    // Test pour affecter un foyer à une université
    @Test
    void testAffecterFoyerAUniversite() {
        // Création d'un foyer
        Foyer foyer = Foyer.builder()
                .idFoyer(1L)
                .nomFoyer("Foyer Central")
                .build();

        // Création d'une université
        Universite universite = Universite.builder()
                .idUniversite(1L)
                .nomUniversite("Université de l'Esprit")
                .adresse("Av. des Universités")
                .build();

        // Simulation du retour des méthodes findById et findByNomUniversiteLike
        Mockito.when(foyerRepository.findById(1L)).thenReturn(Optional.ofNullable(foyer));
        Mockito.when(universiteRepository.findByNomUniversiteLike("Université de l'Esprit")).thenReturn(universite);
        Mockito.when(universiteRepository.save(Mockito.any(Universite.class))).thenReturn(universite);

        // Appel de la méthode à tester
        Universite result = universiteService.affecterFoyerAUniversite(1L, "Université de l'Esprit");

        // Vérification des résultats
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Université de l'Esprit", result.getNomUniversite());
        Assertions.assertEquals(foyer, result.getFoyer());

        // Vérification de l'appel aux méthodes save et findById
        verify(foyerRepository).findById(1L);
        verify(universiteRepository).findByNomUniversiteLike("Université de l'Esprit");
        verify(universiteRepository).save(Mockito.any(Universite.class));
    }

    // Test for desaffecterFoyerAUniversite()
    @Test
    void testDesaffecterFoyerAUniversite() {
        Universite universite = Universite.builder().idUniversite(1L).nomUniversite("ESPRIT").build();

        // Mock the findById method
        when(universiteRepository.findById(1L)).thenReturn(Optional.of(universite));

        // Mock the save method to return the updated Universite
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        // Call the service method
        Universite updatedUniversite = universiteService.desaffecterFoyerAUniversite(1L);

        // Ensure that the updatedUniversite is not null
        assertNotNull(updatedUniversite, "The universite should not be null");

        // Assert that the Foyer has been removed (null)
        assertNull(updatedUniversite.getFoyer(), "The university should not have a foyer assigned");

        // Verify interactions with the repository
        verify(universiteRepository, times(1)).findById(1L); // Verify findById was called once
        verify(universiteRepository, times(1)).findById(1L); // Verify findById was called once
        verify(universiteRepository, times(1)).save(updatedUniversite); // Verify save was called
    }

}
