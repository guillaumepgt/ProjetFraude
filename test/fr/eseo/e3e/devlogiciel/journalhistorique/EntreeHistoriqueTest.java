package fr.eseo.e3e.devlogiciel.journalhistorique;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class EntreeHistoriqueTest {

    private EntreeHistorique entree1;
    private EntreeHistorique entree2;

    @BeforeEach
    void setUp() {
        entree1 = new EntreeHistorique();
        entree2 = new EntreeHistorique("Connexion de l'utilisateur");
    }

    @Test
    void testConstructeur1() {
        assertEquals("Action non spécifiée", entree1.getAction());
        assertNotNull(entree1.getHorodatage());
    }

    @Test
    void testConstructeur2() {
        assertEquals("Connexion de l'utilisateur", entree2.getAction());
        assertNotNull(entree2.getHorodatage());
    }

    @Test
    void testSettersEtGetters() {
        LocalDateTime nouvelleDate = LocalDateTime.of(2026, 5, 27, 14, 30);

        entree2.setHorodatage(nouvelleDate);
        entree2.setAction("Déconnexion");

        assertEquals(nouvelleDate, entree2.getHorodatage());
        assertEquals("Déconnexion", entree2.getAction());
    }
}