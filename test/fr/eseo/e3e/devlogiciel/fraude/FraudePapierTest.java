package fr.eseo.e3e.devlogiciel.fraude;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class FraudePapierTest {

    @Test
    void testConstructeursEtGetters() {
        FraudePapier f1 = new FraudePapier();
        assertEquals("", f1.getDimensions());
        assertFalse(f1.getEstPlie());

        FraudePapier f2 = new FraudePapier(LocalDate.now(), "A cache un brouillon", "Formules", "A5", true);
        assertEquals("A5", f2.getDimensions());
        assertTrue(f2.getEstPlie());
    }
}