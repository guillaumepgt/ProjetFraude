package fr.eseo.e3e.devlogiciel.fraude;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour la classe FraudeIAGConnectee.
 */
public class FraudeIAGConnecteeTest {

    private FraudeIAGConnectee fraude1;
    private FraudeIAGConnectee fraude2;

    @BeforeEach
    void setUp() {
        fraude1 = new FraudeIAGConnectee();
        fraude2 = new FraudeIAGConnectee(LocalDate.now(), "test", "test1", "test2", "192.168.0.1");
    }

    @Test
    void testConstructeur() {
        assertEquals("127.0.0.1", fraude1.getAdresseIP());
    }

    @Test
    void testConstructeur2() {
        assertEquals("192.168.0.1", fraude2.getAdresseIP());
    }
}
