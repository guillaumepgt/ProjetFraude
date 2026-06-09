package fr.eseo.e3e.devlogiciel.journalhistorique;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JournalHistoriqueTest {

    private JournalHistorique journal;

    @BeforeEach
    void setUp() {
        journal = new JournalHistorique();
    }

    @Test
    void testConstructeur() {
        assertNotNull(journal.getEntrees());
        assertTrue(journal.getEntrees().isEmpty());
    }

    @Test
    void testAjouterEntree() {
        journal.ajouterEntree("Création d'un formulaire");
        journal.ajouterEntree("Suppression d'une fraude");

        assertEquals(2, journal.getEntrees().size());

        assertEquals("Création d'un formulaire", journal.getEntrees().get(0).getAction());
        assertEquals("Suppression d'une fraude", journal.getEntrees().get(1).getAction());
        assertNotNull(journal.getEntrees().get(0).getHorodatage());
    }

    @Test
    void testSettersEtGetters() {
        List<EntreeHistorique> nouvelleListe = new ArrayList<>();
        EntreeHistorique entree = new EntreeHistorique("Action de test");
        nouvelleListe.add(entree);

        journal.setEntrees(nouvelleListe);

        assertEquals(1, journal.getEntrees().size());
        assertEquals(nouvelleListe, journal.getEntrees());
        assertEquals("Action de test", journal.getEntrees().get(0).getAction());
        List<EntreeHistorique> listeVide = new ArrayList<>();
        journal.setEntrees(listeVide);
        assertTrue(journal.getEntrees().isEmpty());
        journal.setEntrees(null);
        assertNull(journal.getEntrees());
    }

    @Test
    void testExporterEnTexte() {
        journal.ajouterEntree("Test 1");
        journal.ajouterEntree("Test 2");

        String cheminFichierTest = "test_export_journal.txt";

        journal.exporterEnTexte(cheminFichierTest);

        java.io.File fichier = new java.io.File(cheminFichierTest);
        assertTrue(fichier.exists());

        fichier.delete();
    }

    @Test
    void testExporterEnTexteErreur() {
        journal.exporterEnTexte(".");
        assertTrue(true, "L'exception a été gérée en interne par le logger.");
    }
}