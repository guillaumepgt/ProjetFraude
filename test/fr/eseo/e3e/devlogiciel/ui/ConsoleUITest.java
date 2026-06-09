package fr.eseo.e3e.devlogiciel.ui;

import fr.eseo.e3e.devlogiciel.etudiant.Etudiant;
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
        simulerEntreesUtilisateur("10\n");
        ConsoleUI ui = new ConsoleUI(systeme);

        assertDoesNotThrow(ui::demarrer);
    }

    @Test
    public void testDemarrerOptionInvalidePuisQuitter() {
        simulerEntreesUtilisateur("15\n10\n");
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
                "non\n"+
                "A triché\n" +
                "Copie sur voisin\n" +
                "1\n" +
                "A4\n" +
                "oui\n" +
                "non\n" +
                "10\n";

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
                "non\n"+
                "A triché\n" +
                "Formules stockées\n" +
                "2\n" +
                "Casio\n" +
                "AntiSecheV3\n" +
                "non\n" +
                "10\n";

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
                "non\n"+
                "Faits\n" +
                "Texte\n" +
                "3\n" +
                "ChatGPT\n" +
                "192.168.1.50\n" +
                "non\n" +
                "10\n";

        simulerEntreesUtilisateur(inputs);
        ConsoleUI ui = new ConsoleUI(systeme);
        ui.demarrer();

        assertEquals(1, systeme.getFormulaires().size());
    }

    @Test
    public void testToutesLesModalitesEtCursus() {
        String inputs = "1\nECUE-1\n\n\n\n3\nN\nP\n1\n2\nnon\nD\nC\n1\nA4\nnon\nnon\n" +
                "1\nECUE-2\n\n\n\n4\nN\nP\n1\n4\nnon\nD\nC\n1\nA4\nnon\nnon\n" +
                "1\nECUE-3\n\n\n\n5\nN\nP\n1\n5\nnon\nD\nC\n1\nA4\nnon\nnon\n" +
                "1\nECUE-4\n\n\n\n6\nN\nP\n1\n6\nnon\nD\nC\n1\nA4\nnon\nnon\n" +
                "10\n";

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
                "non\n"+
                "Desc\nContenu\n" +
                "4\n" +
                "A4\nnon\n" +
                "non\n" +
                "10\n";

        simulerEntreesUtilisateur(inputs);
        ConsoleUI ui = new ConsoleUI(systeme);
        ui.demarrer();

        assertEquals(1, systeme.getFormulaires().size());
    }

    @Test
    public void testActionSupprimerFormulaireSuccesEtEchec() {
        String creationInput = "1\nECUE\n\n\n\n1\nN\nP\n1\n1\nnon\nD\nC\n1\nA4\nnon\nnon\n10\n";
        simulerEntreesUtilisateur(creationInput);
        ConsoleUI uiCreation = new ConsoleUI(systeme);
        uiCreation.demarrer();

        int idDynamique = systeme.getFormulaires().get(0).getId();

        String suppressionInput = "2\n" + idDynamique + "\n" +
                "2\nabc\n" +
                "10\n";

        simulerEntreesUtilisateur(suppressionInput);
        ConsoleUI uiSuppression = new ConsoleUI(systeme);
        uiSuppression.demarrer();

        assertEquals(0, systeme.getFormulaires().size());
    }

    @Test
    public void testAfficherTousLesFormulairesVideEtPlein() {
        String inputs = "3\n" +
                "1\nECUE\n\n\n\n1\nN\nP\n1\n1\nnon\nD\nC\n1\nA4\nnon\nnon\n" +
                "3\n" +
                "10\n";

        simulerEntreesUtilisateur(inputs);
        ConsoleUI ui = new ConsoleUI(systeme);
        ui.demarrer();

        assertFalse(systeme.getFormulaires().isEmpty());
    }

    @Test
    public void testActionRechercheCroiseeResultat() {
        String inputs = "1\nECUE\n\n\n\n1\nN\nP\n1\n3\nnon\nD\nC\n1\nA4\nnon\nnon\n" +
                "4\n3\n1\n" +
                "4\n3\n2\n" +
                "4\n3\n3\n" +
                "4\n4\n4\n" +
                "10\n";

        simulerEntreesUtilisateur(inputs);
        ConsoleUI ui = new ConsoleUI(systeme);
        ui.demarrer();

        assertFalse(systeme.getFormulaires().isEmpty());
    }

    @Test
    public void testActionRechercheCroiseeSansResultat() {
        String inputs = "4\n1\n2\n" +
                "4\n2\n3\n" +
                "10\n";

        simulerEntreesUtilisateur(inputs);
        ConsoleUI ui = new ConsoleUI(systeme);
        ui.demarrer();

        assertTrue(systeme.getFormulaires().isEmpty());
    }

    @Test
    public void testAfficherJournalHistoriqueVideEtPlein() {
        String inputs = "9\n" +
                "1\nECUE\n\n\n\n1\nN\nP\n1\n1\nnon\nD\nC\n1\nA4\nnon\nnon\n" +
                "9\n" +
                "10\n";

        simulerEntreesUtilisateur(inputs);
        ConsoleUI ui = new ConsoleUI(systeme);
        ui.demarrer();

        assertFalse(systeme.consulterHistorique().isEmpty());
    }

    @Test
    public void testAfficherTousLesFormulairesSansEtudiantNiFraude() {
        systeme.enregistrerFormulaire(new Formulaire());

        String inputs = "3\n10\n";
        simulerEntreesUtilisateur(inputs);

        ConsoleUI ui = new ConsoleUI(systeme);
        ui.demarrer();

        assertEquals(1, systeme.getFormulaires().size());
    }

    @Test
    public void testActionAnalyserReseauVide() {
        String inputs = "8\n10\n";
        simulerEntreesUtilisateur(inputs);

        ConsoleUI ui = new ConsoleUI(systeme);
        assertDoesNotThrow(ui::demarrer);
    }

    @Test
    public void testActionAnalyserReseauAvecSuspect() {
        Etudiant t = new Etudiant("Peloin", "Titouan", "111", Etudiant.Cursus.E3e);
        Etudiant b = new Etudiant("Prigent", "Guillaume", "222", Etudiant.Cursus.E3e);

        Formulaire formTriche = new Formulaire();
        formTriche.ajouterEtudiant(t);
        formTriche.ajouterEtudiant(b);
        systeme.enregistrerFormulaire(formTriche);

        String inputs = "8\n10\n";
        simulerEntreesUtilisateur(inputs);

        ConsoleUI ui = new ConsoleUI(systeme);
        assertDoesNotThrow(ui::demarrer);
    }

    @Test
    public void testSaisirFormulairePlusieursEtudiantsEtOptionsManquantes() {
        String inputs = "1\n" +             // Menu : Nouveau formulaire
                "S06-MATHS\n" +             // Code ECUE
                "\n" +                      // Date par défaut
                "\n" +                      // Heure par défaut
                "\n" +                      // Durée par défaut
                "2\n" +                     // MODALITÉ 2 (ORAL) -> Couvre le switch manquant !
                "NomTricheur1\n" +
                "Prenom1\n" +
                "111\n" +
                "1\n" +                     // CURSUS 1 (E1) -> Couvre le switch manquant !
                "oui\n" +                   // UN COMPLICE ? OUI ! -> Couvre la branche manquante de la boucle !
                "NomTricheur2\n" +
                "Prenom2\n" +
                "222\n" +
                "3\n" +                     // Cursus par défaut pour le complice
                "non\n" +                   // On arrête d'ajouter des étudiants
                "Faits\n" +
                "Preuves\n" +
                "1\n" +                     // Type : Papier
                "A4\n" +
                "non\n" +
                "non\n" +                   // On arrête d'ajouter des fraudes
                "10\n";                     // Menu : Quitter

        simulerEntreesUtilisateur(inputs);
        ConsoleUI ui = new ConsoleUI(systeme);
        ui.demarrer();

        // On vérifie que le formulaire a bien été créé et qu'il contient bien NOS DEUX étudiants
        assertEquals(1, systeme.getFormulaires().size());
        assertEquals(2, systeme.getFormulaires().get(0).getEtudiants().size());
    }

    @Test
    public void testScenarioReseauCompletAvecVraiCerveau() {
        String form1 = "1\n" +
                "ECUE-1\n\n\n\n1\n" +
                "Peloin\nTitouan\n444\n3\n" +
                "oui\n" +
                "Prigent\nGuillaume\n555\n3\n" +
                "non\n" +
                "Copie TP\nPreuve1\n" +
                "1\nA4\nnon\nnon\n";

        String form2 = "1\n" +
                "ECUE-2\n\n\n\n1\n" +
                "Peloin\nTitouan\n444\n3\n" +
                "oui\n" +
                "Collaborateur\nBasile\n666\n3\n" +
                "non\n" +
                "Triche exam\nPreuve2\n" +
                "1\nA4\nnon\nnon\n";

        String actionsFinales = "8\n10\n";

        simulerEntreesUtilisateur(form1 + form2 + actionsFinales);

        ConsoleUI ui = new ConsoleUI(systeme);
        ui.demarrer();


        assertEquals(2, systeme.getFormulaires().size());

        assertEquals(2, systeme.getFormulaires().get(0).getEtudiants().size());
        assertEquals(2, systeme.getFormulaires().get(1).getEtudiants().size());

        Etudiant cerveau = systeme.trouverTricheurLePlusConnecte();
        assertNotNull(cerveau, "Le graphe n'a pas trouvé de cerveau !");
        assertEquals("444", cerveau.getId(), "Le cerveau trouvé n'est pas le bon !");
        assertEquals("Titouan", cerveau.getPrenom());
    }
}
