package fr.eseo.e3e.devlogiciel.formulaire;

import fr.eseo.e3e.devlogiciel.epreuve.Epreuve;
import fr.eseo.e3e.devlogiciel.etudiant.Etudiant;
import fr.eseo.e3e.devlogiciel.fraude.Fraude;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FormulaireTest {

    @Test
    void testConstructeurParDefaut() {
        Formulaire form = new Formulaire();

        assertTrue(form.getId() > 0);
        assertNotNull(form.getDateCreation());
        assertEquals(form.getDateCreation(), form.getDateDerniereModification());
        assertNotNull(form.getEpreuve());
        assertNotNull(form.getEtudiants());
        assertTrue(form.getEtudiants().isEmpty());
        assertNotNull(form.getFraudes());
        assertTrue(form.getFraudes().isEmpty());
    }

    @Test
    void testConstructeurParametre() {
        LocalDateTime dateC = LocalDateTime.of(2026, 5, 27, 10, 0);
        LocalDateTime dateM = LocalDateTime.of(2026, 5, 27, 12, 0);
        List<Epreuve> epreuves = new ArrayList<>();
        List<Etudiant> etudiants = new ArrayList<>();
        List<Fraude> fraudes = new ArrayList<>();

        Formulaire form = new Formulaire(10, dateC, dateM, epreuves, etudiants, fraudes);

        assertEquals(10, form.getId());
        assertEquals(dateC, form.getDateCreation());
        assertEquals(dateM, form.getDateDerniereModification());
        assertEquals(epreuves, form.getEpreuve());
        assertEquals(etudiants, form.getEtudiants());
        assertEquals(fraudes, form.getFraudes());
    }

    @Test
    void testAjouterEtudiant() throws InterruptedException {
        Formulaire form = new Formulaire();
        LocalDateTime dateAvant = form.getDateDerniereModification();

        Thread.sleep(10);

        Etudiant etudiant = new Etudiant();
        form.ajouterEtudiant(etudiant);

        assertEquals(1, form.getEtudiants().size());
        assertEquals(etudiant, form.getEtudiants().get(0));
        assertTrue(form.getDateDerniereModification().isAfter(dateAvant));
    }

    @Test
    void testAjouterFraude() throws InterruptedException {
        Formulaire form = new Formulaire();
        LocalDateTime dateAvant = form.getDateDerniereModification();

        Thread.sleep(10);

        Fraude fraude = new Fraude() {};
        form.ajouterFraude(fraude);

        assertEquals(1, form.getFraudes().size());
        assertEquals(fraude, form.getFraudes().get(0));
        assertTrue(form.getDateDerniereModification().isAfter(dateAvant));
    }
}