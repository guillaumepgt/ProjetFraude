package fr.eseo.e3e.devlogiciel.systeme;

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
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
        assertEquals(1, systeme.getJournal().getEntrees().size());
    }

    @Test
    public void testRechercheCroisee() {
        Formulaire f1 = new Formulaire();
        f1.ajouterEtudiant(new Etudiant("D", "J", "1", Cursus.E3e));
        f1.ajouterFraude(new FraudePapier(LocalDate.now(), "T", "P", "A", true));
        systeme.enregistrerFormulaire(f1);
        List<Formulaire> res = systeme.rechercheCroisee(Cursus.E3e, FraudePapier.class);
        assertEquals(1, res.size());
    }

    @Test
    public void testRechercheEtudiant() {
        Etudiant e1 = new Etudiant("Durand", "Jean", "E001", Cursus.E1);
        Formulaire f = new Formulaire();
        f.ajouterEtudiant(e1);
        systeme.enregistrerFormulaire(f);
        assertEquals(e1, systeme.trouverEtudiantParId("E001"));
        assertFalse(systeme.rechercherEtudiantsParNomPrenom("Durand").isEmpty());
    }

    @Test
    public void testTrouverFormulairesParEtudiantEtEpreuve() {
        Etudiant e1 = new Etudiant("Durand", "Jean", "E001", Cursus.E1);
        fr.eseo.e3e.devlogiciel.epreuve.Epreuve ep = new fr.eseo.e3e.devlogiciel.epreuve.Epreuve();
        ep.setCodeECUE("MA101");
        Formulaire f = new Formulaire();
        f.ajouterEtudiant(e1);
        f.setEpreuve(ep);
        systeme.enregistrerFormulaire(f);
        assertEquals(1, systeme.trouverFormulairesParEtudiant("E001").size());
        assertEquals(1, systeme.trouverFormulairesParEpreuve("MA101").size());
    }

    @Test
    public void testStatistiques() {
        Formulaire f1 = new Formulaire();
        f1.ajouterFraude(new FraudePapier(LocalDate.now(), "D1", "C1", "A4", true));
        Formulaire f2 = new Formulaire();
        f2.ajouterFraude(new FraudePapier(LocalDate.now(), "D2", "C2", "A4", true));
        f2.ajouterFraude(new FraudePapier(LocalDate.now(), "D3", "C3", "A4", true));
        systeme.enregistrerFormulaire(f1);
        systeme.enregistrerFormulaire(f2);
        assertEquals(2, systeme.getNombreTotalFormulaires());
        assertEquals(3, systeme.getNombreTotalFraudes());
        assertEquals(1.5, systeme.getMoyenneFraudesParFormulaire(), 0.001);
        assertEquals(0.5, systeme.getEcartTypeFraudesParFormulaire(), 0.001);
    }

    @Test
    public void testSauvegarderEtCharger() {
        Formulaire form = new Formulaire();
        systeme.enregistrerFormulaire(form);
        systeme.sauvegarder(FICHIER_TEST);
        SystemeGestion charge = SystemeGestion.charger(FICHIER_TEST);
        assertEquals(1, charge.getFormulaires().size());
        new File(FICHIER_TEST).delete();
    }
}
