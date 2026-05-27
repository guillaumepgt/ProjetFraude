package fr.eseo.e3e.devlogiciel.etudiant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static fr.eseo.e3e.devlogiciel.etudiant.Etudiant.Cursus.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Classe de test pour la classe Etudiant.
 */
public class EtudiantTest {

    private Etudiant etudiant;
    private Etudiant etudiant2;

    @BeforeEach
    void setUp() {
        etudiant = new Etudiant();
        etudiant2 = new Etudiant("Maude", "Marius", "2", E2);
    }

    /** Test du constructeur par défaut */
    @Test
    public void testConstructeur() {
        assertEquals("Titouan", etudiant.getPrenom());
        assertEquals("Peloin", etudiant.getNom());
        assertEquals("1", etudiant.getId());
        assertEquals(E3e, etudiant.getCursus());
    }

    /** Test du constructeur avec paramètres */
    @Test
    public void testConstructeur2() {
        assertEquals("Marius", etudiant2.getPrenom());
        assertEquals("Maude", etudiant2.getNom());
        assertEquals("2", etudiant2.getId());
        assertEquals(E2, etudiant2.getCursus());
    }
}
