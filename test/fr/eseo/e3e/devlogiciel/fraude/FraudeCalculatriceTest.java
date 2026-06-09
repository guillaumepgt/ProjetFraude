package fr.eseo.e3e.devlogiciel.fraude;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class FraudeCalculatriceTest {

    @Test
    void testConstructeursEtGetters() {
        FraudeCalculatrice f1 = new FraudeCalculatrice();
        assertNull(f1.getMarque());
        assertNull(f1.getProgramme());
        assertNull(f1.getDateReleve());

        FraudeCalculatrice f2 = new FraudeCalculatrice(LocalDate.now(), "Affiche formule", "Matrices", "TI", "Stats");
        assertEquals("TI", f2.getMarque());
        assertEquals("Stats", f2.getProgramme());
        assertEquals("Affiche formule", f2.getDescription());
        assertEquals("Matrices", f2.getContenu());
    }
}