package fr.eseo.e3e.devlogiciel.etudiant;

import org.junit.jupiter.api.Test;
import static fr.eseo.e3e.devlogiciel.etudiant.Etudiant.Cursus.*;
import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void testEqualsEtHashCode() {
        Etudiant e1 = new Etudiant("Peloin", "Titouan", "111", E3e);
        Etudiant e2 = new Etudiant("Clone", "Clone", "111", E2);
        Etudiant e3 = new Etudiant("Peloin", "Titouan", "222", E3e);

        assertEquals(e1, e2, "Deux étudiants avec le même ID doivent être égaux.");
        assertEquals(e1.hashCode(), e2.hashCode(), "Le hashcode doit être identique pour le même ID.");
        assertNotEquals(e1, e3, "Deux étudiants avec des ID différents ne sont pas égaux.");
    }

    @Test
    public void testEqualsEtHashCodeBranchesEdgeCases() {
        Etudiant e1 = new Etudiant("Test", "Test", "123", E1);
        Etudiant eNullId = new Etudiant("Test", "Test", null, E1);

        assertTrue(e1.equals(e1));
        assertFalse(e1.equals(null));
        assertFalse(e1.equals(new Object()));
        assertEquals(0, eNullId.hashCode());
    }
}