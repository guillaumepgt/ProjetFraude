package fr.eseo.e3e.devlogiciel.fraude;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FraudeCalculatriceTest {

    private FraudeCalculatrice fraude1;
    private FraudeCalculatrice fraude2;

    @BeforeEach
    void setUp() {
        fraude1 = new FraudeCalculatrice();
        fraude2 = new FraudeCalculatrice(LocalDate.now(), "test", "test1", "casio", "Nouveau");
    }

    @Test
    void testConstructeur() {
        assertEquals("Casio", fraude1.getMarque());
        assertEquals("", fraude1.getProgramme());
        assertEquals(LocalDate.now(), fraude1.getDateReleve());
        assertEquals("", fraude1.getContenu());
        assertEquals("", fraude1.getDescription());
    }

    @Test
    void testConstructeur2() {
        assertEquals("casio", fraude2.getMarque());
        assertEquals("Nouveau", fraude2.getProgramme());
        assertEquals(LocalDate.now(), fraude2.getDateReleve());
        assertEquals("test", fraude2.getDescription());
        assertEquals("test1", fraude2.getContenu());
    }
}