package fr.eseo.e3e.devlogiciel.ui;

import fr.eseo.e3e.devlogiciel.formulaire.Formulaire;
import fr.eseo.e3e.devlogiciel.systeme.SystemeGestion;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

public class ConsoleUITest {

    private final InputStream systemInOriginal = System.in;
    private SystemeGestion systeme;

    @BeforeEach
    public void setUp() {
        systeme = new SystemeGestion();
    }

    @AfterEach
    public void restoreSystemInput() {
        System.setIn(systemInOriginal);
    }

    private void simulerEntreesUtilisateur(String donnees) {
        ByteArrayInputStream in = new ByteArrayInputStream(donnees.getBytes());
        System.setIn(in);
    }

    @Test
    public void testDemarrerEtQuitter() {
        simulerEntreesUtilisateur("6\n");
        ConsoleUI ui = new ConsoleUI(systeme);

        assertDoesNotThrow(ui::demarrer);
    }

    @Test
    public void testDemarrerOptionInvalidePuisQuitter() {
        simulerEntreesUtilisateur("9\n6\n");
        ConsoleUI ui = new ConsoleUI(systeme);

        assertDoesNotThrow(ui::demarrer);
    }

    @Test
    public void testSaisirNouveauFormulairePapier() {
        String inputs = "1\n" +
                "S06-POO\n" +
                "2026-06-15\n" +
                "10:30\n" +
                "90\n" +
                "1\n" +
                "Peloin\n" +
                "Titouan\n" +
                "444\n" +
                "3\n" +
                "A triché\n" +
                "Copie sur voisin\n" +
                "1\n" +
                "A4\n" +
                "oui\n" +
                "6\n";

        simulerEntreesUtilisateur(inputs);
        ConsoleUI ui = new ConsoleUI(systeme);
        ui.demarrer();

        assertEquals(1, systeme.getFormulaires().size());
    }

    @Test
    public void testSaisirNouveauFormulaireCalculatriceEtFormatsInvalides() {
        String inputs = "1\n" +
                "S06-POO\n" +
                "date-invalide\n" +
                "heure-invalide\n" +
                "duree-invalide\n" +
                "2\n" +
                "Martin\n" +
                "Alice\n" +
                "555\n" +
                "1\n" +
                "A triché\n" +
                "Formules stockées\n" +
                "2\n" +
                "Casio\n" +
                "AntiSecheV3\n" +
                "6\n";

        simulerEntreesUtilisateur(inputs);
        ConsoleUI ui = new ConsoleUI(systeme);
        ui.demarrer();

        assertEquals(1, systeme.getFormulaires().size());
    }

    @Test
    public void testSaisirNouveauFormulaireIAGConnecteeEtChoixParDefaut() {
        String inputs = "1\n" +
                "ECUE-TEST\n" +
                "\n" +
                "\n" +
                "\n" +
                "9\n" +
                "Nom\n" +
                "Prenom\n" +
                "777\n" +
                "9\n" +
                "Faits\n" +
                "Texte\n" +
                "3\n" +
                "ChatGPT\n" +
                "192.168.1.50\n" +
                "6\n";

        simulerEntreesUtilisateur(inputs);
        ConsoleUI ui = new ConsoleUI(systeme);
        ui.demarrer();

        assertEquals(1, systeme.getFormulaires().size());
    }

    @Test
    public void testToutesLesModalitesEtCursus() {
        String inputs = "1\nECUE-1\n\n\n\n3\nN\nP\n1\n2\nD\nC\n1\nA4\nnon\n" +
                "1\nECUE-2\n\n\n\n4\nN\nP\n1\n4\nD\nC\n1\nA4\nnon\n" +
                "1\nECUE-3\n\n\n\n5\nN\nP\n1\n5\nD\nC\n1\nA4\nnon\n" +
                "1\nECUE-4\n\n\n\n6\nN\nP\n1\n6\nD\nC\n1\nA4\nnon\n" +
                "6\n";

        simulerEntreesUtilisateur(inputs);
        ConsoleUI ui = new ConsoleUI(systeme);
        ui.demarrer();

        assertEquals(4, systeme.getFormulaires().size());
    }

    @Test
    public void testTricheInvalideEtEntreesVidesMenu() {
        String inputs = "\n" +
                "abc\n" +
                "1\n" +
                "ECUE-5\n" +
                "\n\n\n" +
                "1\n" +
                "N\nP\n1\n3\n" +
                "Desc\nContenu\n" +
                "4\n" +
                "A4\nnon\n" +
                "6\n";

        simulerEntreesUtilisateur(inputs);
        ConsoleUI ui = new ConsoleUI(systeme);
        ui.demarrer();

        assertEquals(1, systeme.getFormulaires().size());
    }

    @Test
    public void testActionSupprimerFormulaireSuccesEtEchec() {
        String creationInput = "1\nECUE\n\n\n\n1\nN\nP\n1\n1\nD\nC\n1\nA4\nnon\n6\n";
        simulerEntreesUtilisateur(creationInput);
        ConsoleUI uiCreation = new ConsoleUI(systeme);
        uiCreation.demarrer();

        int idDynamique = systeme.getFormulaires().get(0).getId();

        String suppressionInput = "2\n" + idDynamique + "\n" +
                "2\nabc\n" +
                "6\n";

        simulerEntreesUtilisateur(suppressionInput);
        ConsoleUI uiSuppression = new ConsoleUI(systeme);
        uiSuppression.demarrer();

        assertEquals(0, systeme.getFormulaires().size());
    }

    @Test
    public void testAfficherTousLesFormulairesVideEtPlein() {
        String inputs = "3\n" +
                "1\nECUE\n\n\n\n1\nN\nP\n1\n1\nD\nC\n1\nA4\nnon\n" +
                "3\n" +
                "6\n";

        simulerEntreesUtilisateur(inputs);
        ConsoleUI ui = new ConsoleUI(systeme);
        ui.demarrer();

        assertFalse(systeme.getFormulaires().isEmpty());
    }

    @Test
    public void testActionRechercheCroiseeResultat() {
        String inputs = "1\nECUE\n\n\n\n1\nN\nP\n1\n3\nD\nC\n1\nA4\nnon\n" +
                "4\n3\n1\n" +
                "4\n3\n2\n" +
                "4\n3\n3\n" +
                "4\n4\n4\n" +
                "6\n";

        simulerEntreesUtilisateur(inputs);
        ConsoleUI ui = new ConsoleUI(systeme);
        ui.demarrer();

        assertFalse(systeme.getFormulaires().isEmpty());
    }

    @Test
    public void testActionRechercheCroiseeSansResultat() {
        String inputs = "4\n1\n2\n" + // Cursus E1, Type Calculatrice sur base vide -> Vide (Déclenche res.isEmpty() -> true)
                "4\n2\n3\n" + // Cursus E2, Type IAG Connectee sur base vide -> Vide (Déclenche res.isEmpty() -> true)
                "6\n";

        simulerEntreesUtilisateur(inputs);
        ConsoleUI ui = new ConsoleUI(systeme);
        ui.demarrer();

        assertTrue(systeme.getFormulaires().isEmpty());
    }

    @Test
    public void testAfficherJournalHistoriqueVideEtPlein() {
        String inputs = "5\n" +
                "1\nECUE\n\n\n\n1\nN\nP\n1\n1\nD\nC\n1\nA4\nnon\n" +
                "5\n" +
                "6\n";

        simulerEntreesUtilisateur(inputs);
        ConsoleUI ui = new ConsoleUI(systeme);
        ui.demarrer();

        assertFalse(systeme.consulterHistorique().isEmpty());
    }

    @Test
    public void testAfficherTousLesFormulairesSansEtudiantNiFraude() {
        // Ajout d'un formulaire vide (créé manuellement, hors UI)
        systeme.enregistrerFormulaire(new Formulaire());

        // Simulation de l'appel au menu "3" (Afficher tous les formulaires) puis "6" (Quitter)
        String inputs = "3\n6\n";
        simulerEntreesUtilisateur(inputs);

        ConsoleUI ui = new ConsoleUI(systeme);
        ui.demarrer();

        // Si on arrive ici sans erreur, les branches "false" des if(!isEmpty()) ont été couvertes.
        assertEquals(1, systeme.getFormulaires().size());
    }
}