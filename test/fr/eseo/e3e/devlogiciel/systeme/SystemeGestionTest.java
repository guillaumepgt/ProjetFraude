package fr.eseo.e3e.devlogiciel.systeme;

import fr.eseo.e3e.devlogiciel.etudiant.Etudiant;
import fr.eseo.e3e.devlogiciel.etudiant.Etudiant.Cursus;
import fr.eseo.e3e.devlogiciel.formulaire.Formulaire;
import fr.eseo.e3e.devlogiciel.fraude.FraudeCalculatrice;
import fr.eseo.e3e.devlogiciel.fraude.FraudePapier;
import fr.eseo.e3e.devlogiciel.journalhistorique.EntreeHistorique;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SystemeGestionTest {

    private SystemeGestion systeme;
    private static final String FICHIER_TEST = "sauvegarde_test.dat";

    @BeforeEach
    public void setUp() {
        systeme = new SystemeGestion();
        File file = new File(FICHIER_TEST);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testEnregistrerFormulaireValide() {
        Formulaire form = new Formulaire();
        int idAttendu = form.getId();

        systeme.enregistrerFormulaire(form);

        assertEquals(1, systeme.getFormulaires().size());
        assertEquals(form, systeme.getFormulaires().get(0));
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
    public void testSupprimerFormulaireSucces() {
        Formulaire form = new Formulaire();
        int idDossier = form.getId();
        systeme.enregistrerFormulaire(form);

        systeme.supprimerFormulaire(idDossier);

        assertEquals(0, systeme.getFormulaires().size());
        assertEquals(2, systeme.getJournal().getEntrees().size());
        assertEquals("Suppression du formulaire ID : " + idDossier, systeme.getJournal().getEntrees().get(1).getAction());
    }

    @Test
    public void testSupprimerFormulaireEchec() {
        Formulaire form = new Formulaire();
        systeme.enregistrerFormulaire(form);

        int mauvaisId = form.getId() + 999;
        systeme.supprimerFormulaire(mauvaisId);

        assertEquals(1, systeme.getFormulaires().size());
        assertEquals(2, systeme.getJournal().getEntrees().size());
        assertEquals("Tentative échouée de suppression du formulaire ID : " + mauvaisId, systeme.getJournal().getEntrees().get(1).getAction());
    }

    @Test
    public void testRechercheCroisee() {
        Formulaire form1 = new Formulaire();
        form1.ajouterEtudiant(new Etudiant("Doe", "John", "123", Cursus.E3e));
        form1.ajouterFraude(new FraudePapier(LocalDate.now(), "Triche", "Preuve", "A4", true));

        Formulaire form2 = new Formulaire();
        form2.ajouterEtudiant(new Etudiant("Smith", "Alice", "456", Cursus.E4));
        form2.ajouterFraude(new FraudePapier(LocalDate.now(), "Triche 2", "Preuve 2", "A5", false));

        Formulaire form3 = new Formulaire();
        form3.ajouterEtudiant(new Etudiant("Martin", "Bob", "789", Cursus.E3e));
        form3.ajouterFraude(new FraudeCalculatrice(LocalDate.now(), "Triche 3", "Preuve 3", "Casio", "Prog"));

        systeme.enregistrerFormulaire(form1);
        systeme.enregistrerFormulaire(form2);
        systeme.enregistrerFormulaire(form3);

        List<Formulaire> resultats = systeme.rechercheCroisee(Cursus.E3e, FraudePapier.class);

        assertEquals(1, resultats.size());
        assertEquals(form1, resultats.get(0));
        assertEquals(4, systeme.getJournal().getEntrees().size());
        assertEquals("Recherche croisée effectuée pour le cursus E3e et le type FraudePapier", systeme.getJournal().getEntrees().get(3).getAction());
    }

    @Test
    public void testConsulterHistorique() {
        systeme.enregistrerFormulaire(new Formulaire());

        List<EntreeHistorique> historique = systeme.consulterHistorique();

        assertNotNull(historique);
        assertEquals(1, historique.size());
    }

    @Test
    public void testSauvegarderEtChargerSucces() {
        Formulaire form = new Formulaire();
        systeme.enregistrerFormulaire(form);

        systeme.sauvegarder(FICHIER_TEST);

        File fichier = new File(FICHIER_TEST);
        assertTrue(fichier.exists());

        SystemeGestion systemeCharge = SystemeGestion.charger(FICHIER_TEST);

        assertNotNull(systemeCharge);
        assertEquals(1, systemeCharge.getFormulaires().size());
        assertEquals(form.getId(), systemeCharge.getFormulaires().get(0).getId());

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
    public void testChargerFichierCorrompu() throws java.io.IOException {
        File fichierCorrompu = new File("corrompu.dat");
        java.io.FileWriter fw = new java.io.FileWriter(fichierCorrompu);
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
}