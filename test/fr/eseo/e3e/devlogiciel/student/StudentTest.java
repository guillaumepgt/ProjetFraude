package fr.eseo.e3e.devlogiciel.student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentTest {

    private Student student;
    @BeforeEach
    void setUp() {
        student = new Student("Titouan", "Peloin", 1);
    }

    @Test
    public void testConstructeur() {
        assertEquals("Titouan", student.getForename());
        assertEquals("Peloin", student.getSurname());
        assertEquals(1, student.getId());
    }
}
