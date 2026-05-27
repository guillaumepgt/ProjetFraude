package fr.eseo.e3e.devlogiciel.journalhistorique;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class EntreeHistoriqueTest {

    private EntreeHistorique entree;

    @BeforeEach
    void setUp() {
        entree = new EntreeHistorique("Connexion de l'utilisateur");
    }

    @Test
    void testConstructeur() {
        assertEquals("Connexion de l'utilisateur", entree.getAction());
        assertNotNull(entree.getHorodatage());
    }

    @Test
    void testSettersEtGetters() {
        LocalDateTime nouvelleDate = LocalDateTime.of(2026, 5, 27, 14, 30);

        entree.setHorodatage(nouvelleDate);
        entree.setAction("Déconnexion");

        assertEquals(nouvelleDate, entree.getHorodatage());
        assertEquals("Déconnexion", entree.getAction());
    }
}