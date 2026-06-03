package fr.eseo.e3e.devlogiciel.fraude;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FraudeTest {

    @Test
    void testSettersClasseMere() {
        Fraude f = new FraudePapier();

        f.setDateReleve(LocalDate.of(2026, 12, 25));
        f.setDescription("Nouvelle description");
        f.setContenu("Nouveau contenu");

        assertEquals(LocalDate.of(2026, 12, 25), f.getDateReleve());
        assertEquals("Nouvelle description", f.getDescription());
        assertEquals("Nouveau contenu", f.getContenu());
    }
}
