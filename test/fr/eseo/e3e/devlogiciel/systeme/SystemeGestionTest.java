package fr.eseo.e3e.devlogiciel.systeme;

import fr.eseo.e3e.devlogiciel.epreuve.Epreuve;
import fr.eseo.e3e.devlogiciel.etudiant.Etudiant;
import fr.eseo.e3e.devlogiciel.etudiant.Etudiant.Cursus;
import fr.eseo.e3e.devlogiciel.formulaire.Formulaire;
import fr.eseo.e3e.devlogiciel.fraude.FraudeCalculatrice;
import fr.eseo.e3e.devlogiciel.fraude.FraudePapier;
import fr.eseo.e3e.devlogiciel.journalhistorique.EntreeHistorique;
import fr.eseo.e3e.devlogiciel.utils.FraudeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class SystemeGestionTest {

    private SystemeGestion systeme;
    private static final String FICHIER_TEST = "sauvegarde_test.dat";

    private Formulaire form1;
    private Formulaire form2;
    private Etudiant etudiant1;
    private Etudiant etudiant2;
    private Etudiant etudiant3;

    @BeforeEach
    public void setUp() {
        systeme = new SystemeGestion();
        File file = new File(FICHIER_TEST);
        if (file.exists()) {
            file.delete();
        }

        form1 = new Formulaire();
        form2 = new Formulaire();
        etudiant1 = new Etudiant("Durand", "Jean", "E001", Cursus.E3e);
        etudiant2 = new Etudiant("Smith", "Alice", "E002", Cursus.E4);
        etudiant3 = new Etudiant("Martin", "Bob", "E003", Cursus.E3e);
    }

    @Test
    public void testEnregistrerFormulaireValide() {
        int idAttendu = form1.getId();
        systeme.enregistrerFormulaire(form1);

        assertEquals(1, systeme.getFormulaires().size());
        assertEquals(form1, systeme.getFormulaires().get(0));
        assertEquals(1, systeme.getJournal().getEntrees().size());
        assertEquals("Enregistrement du formulaire ID : " + idAttendu, systeme.getJournal().getEntrees().get(0).getAction());
    }

    @Test
    public void testEnregistrerFormulaireNull() {
        systeme.enregistrerFormulaire(null);
        assertEquals(0, systeme.getFormulaires().size());
        assertEquals(0, systeme.getJournal().getEntrees().size());
    }

    @Test
    public void testSupprimerFormulaireSucces() throws FraudeException {
        int idDossier = form1.getId();
        systeme.enregistrerFormulaire(form1);
        systeme.supprimerFormulaire(idDossier);

        assertEquals(0, systeme.getFormulaires().size());
        assertEquals(2, systeme.getJournal().getEntrees().size());
        assertEquals("Suppression du formulaire ID : " + idDossier, systeme.getJournal().getEntrees().get(1).getAction());
    }

    @Test
    public void testSupprimerFormulaireEchec() {
        systeme.enregistrerFormulaire(form1);
        int mauvaisId = form1.getId() + 999;

        assertThrows(FraudeException.class, () -> systeme.supprimerFormulaire(mauvaisId));
        assertEquals(1, systeme.getFormulaires().size());
    }

    @Test
    public void testRechercheCroisee() {
        form1.ajouterEtudiant(etudiant1);
        form1.ajouterFraude(new FraudePapier(LocalDate.now(), "Triche", "Preuve", "A4", true));

        form2.ajouterEtudiant(etudiant2);
        form2.ajouterFraude(new FraudePapier(LocalDate.now(), "Triche 2", "Preuve 2", "A5", false));

        Formulaire form3 = new Formulaire();
        form3.ajouterEtudiant(etudiant3);
        form3.ajouterFraude(new FraudeCalculatrice(LocalDate.now(), "Triche 3", "Preuve 3", "Casio", "Prog"));

        systeme.enregistrerFormulaire(form1);
        systeme.enregistrerFormulaire(form2);
        systeme.enregistrerFormulaire(form3);

        List<Formulaire> resultats = systeme.rechercheCroisee(Cursus.E3e, FraudePapier.class);

        assertEquals(1, resultats.size());
        assertEquals(form1, resultats.get(0));
    }

    @Test
    public void testConsulterHistorique() {
        systeme.enregistrerFormulaire(form1);
        List<EntreeHistorique> historique = systeme.consulterHistorique();
        assertNotNull(historique);
        assertEquals(1, historique.size());
    }

    @Test
    public void testSauvegarderEtChargerSucces() {
        systeme.enregistrerFormulaire(form1);
        systeme.sauvegarder(FICHIER_TEST);
        File fichier = new File(FICHIER_TEST);
        assertTrue(fichier.exists());

        SystemeGestion systemeCharge = SystemeGestion.charger(FICHIER_TEST);
        assertNotNull(systemeCharge);
        assertEquals(1, systemeCharge.getFormulaires().size());
        assertEquals(form1.getId(), systemeCharge.getFormulaires().get(0).getId());

        fichier.delete();
    }

    @Test
    public void testSauvegarderErreur() {
        systeme.sauvegarder("/");
        assertTrue(true);
    }

    @Test
    public void testChargerFichierInexistant() {
        SystemeGestion systemeVide = SystemeGestion.charger("fichier_fantome.dat");
        assertNotNull(systemeVide);
        assertEquals(0, systemeVide.getFormulaires().size());
    }

    @Test
    public void testChargerFichierCorrompu() throws IOException {
        File fichierCorrompu = new File("corrompu.dat");
        FileWriter fw = new FileWriter(fichierCorrompu);
        fw.write("Pas un objet sérialisé");
        fw.close();

        SystemeGestion systemeVide = SystemeGestion.charger("corrompu.dat");
        assertNotNull(systemeVide);
        assertEquals(0, systemeVide.getFormulaires().size());

        fichierCorrompu.delete();
    }

    @Test
    public void testGetters() {
        assertNotNull(systeme.getFormulaires());
        assertNotNull(systeme.getEtudiants());
        assertNotNull(systeme.getJournal());
    }

    @Test
    void testGenererGrapheEtTrouverCerveau() {
        form1.ajouterEtudiant(etudiant1);
        form1.ajouterEtudiant(etudiant2);
        form2.ajouterEtudiant(etudiant1);
        form2.ajouterEtudiant(etudiant3);
        systeme.enregistrerFormulaire(form1);
        systeme.enregistrerFormulaire(form2);

        Map<Etudiant, List<Etudiant>> graphe = systeme.genererGrapheTricheurs();
        assertNotNull(graphe);
        assertEquals(2, graphe.get(etudiant1).size());
        assertEquals(1, graphe.get(etudiant2).size());
        assertEquals(etudiant1, systeme.trouverTricheurLePlusConnecte());
    }

    @Test
    public void testTrouverCerveauSystemeVide() {
        assertNull(systeme.trouverTricheurLePlusConnecte());
    }

    @Test
    public void testGrapheTricheursDoublonsRenvoyes() {
        form1.ajouterEtudiant(etudiant1);
        form1.ajouterEtudiant(etudiant2);
        form2.ajouterEtudiant(etudiant1);
        form2.ajouterEtudiant(etudiant2);
        systeme.enregistrerFormulaire(form1);
        systeme.enregistrerFormulaire(form2);

        Map<Etudiant, List<Etudiant>> graphe = systeme.genererGrapheTricheurs();
        assertEquals(1, graphe.get(etudiant1).size());
        assertTrue(graphe.get(etudiant1).contains(etudiant2));
    }

    @Test
    public void testRechercheEtudiant() {
        form1.ajouterEtudiant(etudiant1);
        systeme.enregistrerFormulaire(form1);
        assertEquals(etudiant1, systeme.trouverEtudiantParId("E001"));
    }

    @Test
    public void testTrouverFormulairesParEtudiantEtEpreuve() {
        Epreuve ep = new Epreuve();
        ep.setCodeECUE("MA101");
        form1.ajouterEtudiant(etudiant1);
        form1.setEpreuve(ep);
        systeme.enregistrerFormulaire(form1);

        assertEquals(1, systeme.trouverFormulairesParEtudiant("E001").size());
        assertEquals(1, systeme.trouverFormulairesParEpreuve("MA101").size());
    }

    @Test
    public void testStatistiques() {
        form1.ajouterFraude(new FraudePapier(LocalDate.now(), "D1", "C1", "A4", true));
        form2.ajouterFraude(new FraudePapier(LocalDate.now(), "D2", "C2", "A4", true));
        form2.ajouterFraude(new FraudePapier(LocalDate.now(), "D3", "C3", "A4", true));

        systeme.enregistrerFormulaire(form1);
        systeme.enregistrerFormulaire(form2);

        assertEquals(2, systeme.getNombreTotalFormulaires());
        assertEquals(3, systeme.getNombreTotalFraudes());
        assertEquals(1.5, systeme.getMoyenneFraudesParFormulaire(), 0.001);
        assertEquals(0.5, systeme.getEcartTypeFraudesParFormulaire(), 0.001);
    }

    @Test
    public void testStatistiquesVides() {
        assertEquals(0, systeme.getNombreTotalFormulaires());
        assertEquals(0, systeme.getNombreTotalFraudes());
        assertEquals(0, systeme.getMoyenneFraudesParFormulaire(), 0.001);
        assertEquals(0, systeme.getEcartTypeFraudesParFormulaire(), 0.001);
    }

    @Test
    public void testGetNombreEtudiantsDistincts() {
        form1.ajouterEtudiant(etudiant1);
        systeme.enregistrerFormulaire(form1);
        assertEquals(1, systeme.getNombreEtudiantsDistincts());
    }

    @Test
    public void testEnregistrerFormulaireEtudiantExistant() {
        form1.ajouterEtudiant(etudiant1);
        systeme.enregistrerFormulaire(form1);
        form2.ajouterEtudiant(etudiant1);
        systeme.enregistrerFormulaire(form2);
        assertEquals(1, systeme.getEtudiants().size());
    }

    @Test
    public void testRechercheEtudiantIntrouvable() {
        assertNull(systeme.trouverEtudiantParId("ID_FANTOME"));
        assertTrue(systeme.rechercherEtudiantsParNomPrenom("Inconnu").isEmpty());
    }

    @Test
    public void testTrouverFormulairesParEpreuveNulle() {
        form1.setEpreuve(null);
        systeme.enregistrerFormulaire(form1);
        assertTrue(systeme.trouverFormulairesParEpreuve("TEST").isEmpty());
    }
}