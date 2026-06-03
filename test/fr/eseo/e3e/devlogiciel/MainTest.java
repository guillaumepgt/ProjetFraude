package fr.eseo.e3e.devlogiciel;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class MainTest {

    private final InputStream systemInOriginal = System.in;
    private static final String FICHIER_DATA = "sauvegarde_systeme.dat";
    private static final String CHEMIN_LOGS = "historique_fraudes.txt";

    @BeforeEach
    public void setUp() {
        nettoyerFichiers();
    }

    @AfterEach
    public void tearDown() {
        System.setIn(systemInOriginal);
        nettoyerFichiers();
    }

    private void simulerEntreeMenu(String code) {
        ByteArrayInputStream in = new ByteArrayInputStream(code.getBytes());
        System.setIn(in);
    }

    private void nettoyerFichiers() {
        File data = new File(FICHIER_DATA);
        if (data.exists()) {
            data.delete();
        }
        File logs = new File(CHEMIN_LOGS);
        if (logs.exists()) {
            logs.delete();
        }
    }

    @Test
    public void testMainEtQuitterImmediat() {
        simulerEntreeMenu("6\n");

        assertDoesNotThrow(() -> Main.main(new String[]{}));
    }

    @Test
    public void testConstructeurMain() {
        assertDoesNotThrow(Main::new);
    }
}