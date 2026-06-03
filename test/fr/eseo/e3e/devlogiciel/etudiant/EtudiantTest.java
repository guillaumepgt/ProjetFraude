package fr.eseo.e3e.devlogiciel.etudiant;

import org.junit.jupiter.api.Test;
import static fr.eseo.e3e.devlogiciel.etudiant.Etudiant.Cursus.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EtudiantTest {

    @Test
    public void testConstructeursEtGetters() {
        Etudiant e1 = new Etudiant();
        assertEquals("Titouan", e1.getPrenom());
        assertEquals("Peloin", e1.getNom());
        assertEquals("1", e1.getId());
        assertEquals(E3e, e1.getCursus());

        Etudiant e2 = new Etudiant("Dupont", "Jean", "2024", E2);
        assertEquals("Jean", e2.getPrenom());
        assertEquals("Dupont", e2.getNom());
        assertEquals("2024", e2.getId());
        assertEquals(E2, e2.getCursus());
    }
}