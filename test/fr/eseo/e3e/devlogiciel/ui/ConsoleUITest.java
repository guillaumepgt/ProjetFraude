package fr.eseo.e3e.devlogiciel.ui;

import fr.eseo.e3e.devlogiciel.epreuve.Epreuve;
import fr.eseo.e3e.devlogiciel.etudiant.Etudiant;
import fr.eseo.e3e.devlogiciel.formulaire.Formulaire;
import fr.eseo.e3e.devlogiciel.systeme.SystemeGestion;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;

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
        String inputs = "1\nS06-POO\n2026-06-15\n10:30\n90\n1\nPeloin\nTitouan\n444\n3\nnon\nA triché\nCopie sur voisin\n1\nA4\noui\nnon\n10\n";
        simulerEntreesUtilisateur(inputs);
        ConsoleUI ui = new ConsoleUI(systeme);
        ui.demarrer();
        assertEquals(1, systeme.getFormulaires().size());
    }

    @Test
    public void testSaisirNouveauFormulaireCalculatriceEtFormatsInvalides() {
        String inputs = "1\nS06-POO\ndate-invalide\nheure-invalide\nduree-invalide\n2\nMartin\nAlice\n555\n1\nnon\nA triché\nFormules stockées\n2\nCasio\nAntiSecheV3\nnon\n10\n";
        simulerEntreesUtilisateur(inputs);
        ConsoleUI ui = new ConsoleUI(systeme);
        ui.demarrer();
        assertEquals(1, systeme.getFormulaires().size());
    }

    @Test
    public void testSaisirNouveauFormulaireIAGConnecteeEtChoixParDefaut() {
        String inputs = "1\nECUE-TEST\n\n\n\n9\nNom\nPrenom\n777\n9\nnon\nFaits\nTexte\n3\nChatGPT\n192.168.1.50\nnon\n10\n";
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
        String inputs = "\nabc\n1\nECUE-5\n\n\n\n1\nN\nP\n1\n3\nnon\nDesc\nContenu\n4\nA4\nnon\nnon\n10\n";
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
        String suppressionInput = "2\n" + idDynamique + "\n2\nabc\n10\n";
        simulerEntreesUtilisateur(suppressionInput);
        ConsoleUI uiSuppression = new ConsoleUI(systeme);
        uiSuppression.demarrer();
        assertEquals(0, systeme.getFormulaires().size());
    }

    @Test
    public void testAfficherTousLesFormulairesVideEtPlein() {
        String inputs = "3\n1\nECUE\n\n\n\n1\nN\nP\n1\n1\nnon\nD\nC\n1\nA4\nnon\nnon\n3\n10\n";
        simulerEntreesUtilisateur(inputs);
        ConsoleUI ui = new ConsoleUI(systeme);
        ui.demarrer();
        assertFalse(systeme.getFormulaires().isEmpty());
    }

    @Test
    public void testActionRechercheCroiseeResultat() {
        String inputs = "1\nECUE\n\n\n\n1\nN\nP\n1\n3\nnon\nD\nC\n1\nA4\nnon\nnon\n4\n3\n1\n4\n3\n2\n4\n3\n3\n4\n4\n4\n10\n";
        simulerEntreesUtilisateur(inputs);
        ConsoleUI ui = new ConsoleUI(systeme);
        ui.demarrer();
        assertFalse(systeme.getFormulaires().isEmpty());
    }

    @Test
    public void testActionRechercheCroiseeSansResultat() {
        String inputs = "4\n1\n2\n4\n2\n3\n10\n";
        simulerEntreesUtilisateur(inputs);
        ConsoleUI ui = new ConsoleUI(systeme);
        ui.demarrer();
        assertTrue(systeme.getFormulaires().isEmpty());
    }

    @Test
    public void testAfficherJournalHistoriqueVideEtPlein() {
        String inputs = "9\n1\nECUE\n\n\n\n1\nN\nP\n1\n1\nnon\nD\nC\n1\nA4\nnon\nnon\n9\n10\n";
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
        String inputs = "1\nS06-MATHS\n\n\n\n2\nNomTricheur1\nPrenom1\n111\n1\noui\nNomTricheur2\nPrenom2\n222\n3\nnon\nFaits\nPreuves\n1\nA4\nnon\nnon\n10\n";
        simulerEntreesUtilisateur(inputs);
        ConsoleUI ui = new ConsoleUI(systeme);
        ui.demarrer();
        assertEquals(1, systeme.getFormulaires().size());
        assertEquals(2, systeme.getFormulaires().get(0).getEtudiants().size());
    }

    @Test
    public void testScenarioReseauCompletAvecVraiCerveau() {
        String form1 = "1\nECUE-1\n\n\n\n1\nPeloin\nTitouan\n444\n3\noui\nPrigent\nGuillaume\n555\n3\nnon\nCopie TP\nPreuve1\n1\nA4\nnon\nnon\n";
        String form2 = "1\nECUE-2\n\n\n\n1\nPeloin\nTitouan\n444\n3\noui\nMorin\nBasile\n666\n3\nnon\nTriche exam\nPreuve2\n1\nA4\nnon\nnon\n";
        String actionsFinales = "8\n10\n";
        simulerEntreesUtilisateur(form1 + form2 + actionsFinales);
        ConsoleUI ui = new ConsoleUI(systeme);
        ui.demarrer();

        assertEquals(2, systeme.getFormulaires().size());
        Etudiant cerveau = systeme.trouverTricheurLePlusConnecte();
        assertNotNull(cerveau);
        assertEquals("444", cerveau.getId());
    }

    @Test
    public void testMenus5Et6Et7EtErreurs() {
        Etudiant e = new Etudiant("Stark", "Tony", "S123", Etudiant.Cursus.E4);
        Formulaire f = new Formulaire();
        f.ajouterEtudiant(e);
        f.setEpreuve(new Epreuve("DEV-101", LocalDate.now(), LocalTime.now(), 120, Epreuve.Modalite.SUR_ORDINATEUR));
        systeme.enregistrerFormulaire(f);

        String inputs = "5\nStark\n" +
                "5\nS123\n" +
                "5\nInconnu\n" +
                "6\n1\nS123\n" +
                "6\n2\nDEV-101\n" +
                "7\n" +
                "99\n" +
                "10\n";

        simulerEntreesUtilisateur(inputs);
        ConsoleUI ui = new ConsoleUI(systeme);
        assertDoesNotThrow(ui::demarrer);
    }
}