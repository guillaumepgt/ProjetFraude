package fr.eseo.e3e.devlogiciel.epreuve;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class EpreuveTest {

    @Test
    void testConstructeurParDefaut() {
        Epreuve epreuve = new Epreuve();
        assertNull(epreuve.getCodeECUE());
        assertNull(epreuve.getDate());
        assertNull(epreuve.getHeurePassage());
        assertEquals(0, epreuve.getDuree());
        assertNull(epreuve.getModalite());
    }

    @Test
    void testConstructeurParametre() {
        LocalDate dateTest = LocalDate.of(2026, 6, 15);
        LocalTime heureTest = LocalTime.of(14, 0);
        Epreuve epreuve = new Epreuve("UE-DEVLO", dateTest, heureTest, 120, Epreuve.Modalite.EXAMEN_ECRIT);

        assertEquals("UE-DEVLO", epreuve.getCodeECUE());
        assertEquals(dateTest, epreuve.getDate());
        assertEquals(heureTest, epreuve.getHeurePassage());
        assertEquals(120, epreuve.getDuree());
        assertEquals(Epreuve.Modalite.EXAMEN_ECRIT, epreuve.getModalite());
    }

    @Test
    void testSettersAndGetters() {
        Epreuve epreuve = new Epreuve();
        LocalDate dateTest = LocalDate.of(2026, 1, 10);
        LocalTime heureTest = LocalTime.of(8, 30);

        epreuve.setCodeECUE("UE-MATHS");
        epreuve.setDate(dateTest);
        epreuve.setHeurePassage(heureTest);
        epreuve.setDuree(90);
        epreuve.setModalite(Epreuve.Modalite.QCM);

        assertEquals("UE-MATHS", epreuve.getCodeECUE());
        assertEquals(dateTest, epreuve.getDate());
        assertEquals(heureTest, epreuve.getHeurePassage());
        assertEquals(90, epreuve.getDuree());
        assertEquals(Epreuve.Modalite.QCM, epreuve.getModalite());
    }

    @Test
    void testSettersConditionsInvalides() {
        Epreuve epreuve = new Epreuve();

        epreuve.setCodeECUE(null);
        assertNull(epreuve.getCodeECUE());

        epreuve.setCodeECUE("   ");
        assertEquals("   ", epreuve.getCodeECUE());

        epreuve.setDuree(0);
        assertEquals(0, epreuve.getDuree());

        epreuve.setDuree(-10);
        assertEquals(-10, epreuve.getDuree());
    }

    @Test
    void testModaliteEnum() {
        assertEquals(6, Epreuve.Modalite.values().length);
        assertEquals(Epreuve.Modalite.ORAL, Epreuve.Modalite.valueOf("ORAL"));
        assertEquals(Epreuve.Modalite.SUR_ORDINATEUR, Epreuve.Modalite.valueOf("SUR_ORDINATEUR"));
        assertEquals(Epreuve.Modalite.PROJET, Epreuve.Modalite.valueOf("PROJET"));
        assertEquals(Epreuve.Modalite.TP, Epreuve.Modalite.valueOf("TP"));
    }
}