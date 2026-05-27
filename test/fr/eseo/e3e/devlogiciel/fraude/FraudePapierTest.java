package fr.eseo.e3e.devlogiciel.fraude;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour la classe FraudePapier.
 */
public class FraudePapierTest {

    private FraudePapier fraude1;
    private FraudePapier fraude2;

    @BeforeEach
    void setUp() {
        fraude1 = new FraudePapier();
        fraude2 = new FraudePapier(LocalDate.now(), "test", "test1", "test2", true);
    }

    @Test
    void testConstructeur() {
        assertEquals("", fraude1.getDimensions());
        assertFalse(fraude1.getEstPlie());
    }

    @Test
    void testConstructeur2() {
        assertEquals("test2", fraude2.getDimensions());
        assertTrue(fraude2.getEstPlie());
    }

}
