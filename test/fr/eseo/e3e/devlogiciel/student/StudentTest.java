package fr.eseo.e3e.devlogiciel.student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static fr.eseo.e3e.devlogiciel.student.Student.Cursus.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentTest {

    private Student student;
    private Student student2;

    @BeforeEach
    void setUp() {
        student = new Student();
        student2 = new Student("Maude", "Marius", "2", E2);
    }

    @Test
    public void testConstructeur() {
        assertEquals("Titouan", student.getPrenom());
        assertEquals("Peloin", student.getNom());
        assertEquals("1", student.getId());
        assertEquals(E3e, student.getCursus());
    }

    @Test
    public void testConstructeur2() {
        assertEquals("Marius", student2.getPrenom());
        assertEquals("Maude", student2.getNom());
        assertEquals("2", student2.getId());
        assertEquals(E2, student2.getCursus());
    }
}
