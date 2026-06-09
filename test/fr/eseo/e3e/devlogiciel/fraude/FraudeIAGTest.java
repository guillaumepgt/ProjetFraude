package fr.eseo.e3e.devlogiciel.fraude;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Classe de test pour la classe FraudeIAG.
 */
public class FraudeIAGTest {
    private FraudeIAG fraude1;
    private FraudeIAG fraude2;

    @BeforeEach
    void setUp() {
        fraude1 = new FraudeIAG() {};
        fraude2 = new FraudeIAG(LocalDate.now(), "test", "test1", "test2") {};
    }

    @Test
    void testConstructeur() {
        assertNull(fraude1.getNomService());
    }

    @Test
    void testConstructeur2() {
        assertEquals("test2", fraude2.getNomService());
    }
}
