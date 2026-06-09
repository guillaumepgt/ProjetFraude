package fr.eseo.e3e.devlogiciel.etudiant;

import org.junit.jupiter.api.Test;
import static fr.eseo.e3e.devlogiciel.etudiant.Etudiant.Cursus.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class EtudiantTest {

    @Test
    public void testConstructeursEtGetters() {

        Etudiant e1 = new Etudiant();
        assertNull(e1.getPrenom(), "Échec : le prénom devrait être null par défaut.");
        assertNull(e1.getNom(), "Échec : le nom devrait être null par défaut.");
        assertNull(e1.getId(), "Échec : le numéro étudiant (ID) devrait être null par défaut.");
        assertNull(e1.getCursus(), "Échec : le cursus devrait être null par défaut.");

        Etudiant e2 = new Etudiant("Dupont", "Jean", "2024", E2);
        assertEquals("Jean", e2.getPrenom(), "Échec : le prénom n'a pas été correctement assigné.");
        assertEquals("Dupont", e2.getNom(), "Échec : le nom n'a pas été correctement assigné.");
        assertEquals("2024", e2.getId(), "Échec : l'ID n'a pas été correctement assigné.");
        assertEquals(E2, e2.getCursus(), "Échec : le cursus n'a pas été correctement assigné.");
    }
}