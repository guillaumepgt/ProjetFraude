package fr.eseo.e3e.devlogiciel.fraude;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class FraudeIAGConnecteeTest {

    @Test
    void testConstructeursEtGetters() {
        FraudeIAGConnectee f1 = new FraudeIAGConnectee();
        assertEquals("127.0.0.1", f1.getAdresseIP());

        FraudeIAGConnectee f2 = new FraudeIAGConnectee(LocalDate.now(), "Utilise IA", "Code genere", "ChatGPT", "192.168.1.1");
        assertEquals("192.168.1.1", f2.getAdresseIP());
        assertEquals("ChatGPT", f2.getNomService());
    }
}