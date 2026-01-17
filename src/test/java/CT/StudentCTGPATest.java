package CT;

import edu.university.main.model.Student;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentCTGPATest {

    Student student = new Student("S001", "Test1", "test1@edu", 20, "CS");


     // Ensures every executable statement in calculateGPA runs at least once.

    @Test
    void statementCoverageTest() {
        // Null or empty lists → early return
        assertEquals(0.0, student.calculateGPA(null, List.of(3)), 0.01);
        assertEquals(0.0, student.calculateGPA(new ArrayList<>(), List.of(3)), 0.01);

        // Size mismatch → return -1.0
        assertEquals(-1.0, student.calculateGPA(List.of(3.0), List.of(3, 4)), 0.01);

        // Invalid grade/credit → return -1.0
        assertEquals(-1.0, student.calculateGPA(List.of(-1.0), List.of(3)), 0.01);
        assertEquals(-1.0, student.calculateGPA(List.of(3.0), List.of(-1)), 0.01);

        // Valid calculation → return GPA
        assertEquals(3.5, student.calculateGPA(List.of(3.0, 4.0), List.of(3, 3)), 0.01);

        // totalCredits == 0 → return 0.0
        assertEquals(0.0, student.calculateGPA(List.of(3.0), List.of(0)), 0.01);
    }


     //Ensures each decision is evaluated both true and false.

    @Test
    void branchCoverageTest() {
        // Null / empty → true
        assertEquals(0.0, student.calculateGPA(null, List.of(3)), 0.01);

        // Size mismatch → true
        assertEquals(-1.0, student.calculateGPA(List.of(3.0), List.of(3, 4)), 0.01);

        // Invalid grade → true
        assertEquals(-1.0, student.calculateGPA(List.of(-1.0), List.of(3)), 0.01);

        // Invalid credit → true
        assertEquals(-1.0, student.calculateGPA(List.of(3.0), List.of(-1)), 0.01);

        // totalCredits == 0 → true
        assertEquals(0.0, student.calculateGPA(List.of(3.0), List.of(0)), 0.01);

        // All decisions false → normal GPA calculation
        assertEquals(3.5, student.calculateGPA(List.of(3.0, 4.0), List.of(3, 3)), 0.01);
    }


     //Ensures each  boolean condition is evaluated true and false.

    @Test
    void conditionCoverageTest() {
        // grades == null → true
        assertEquals(0.0, student.calculateGPA(null, List.of(3)), 0.01);

        // credits == null → true
        assertEquals(0.0, student.calculateGPA(List.of(3.0), null), 0.01);

        // grades.isEmpty() → true
        assertEquals(0.0, student.calculateGPA(new ArrayList<>(), List.of(3)), 0.01);

        // credits.isEmpty() → true
        assertEquals(0.0, student.calculateGPA(List.of(3.0), new ArrayList<>()), 0.01);

        // grade < 0 → true
        assertEquals(-1.0, student.calculateGPA(List.of(-1.0), List.of(3)), 0.01);

        // grade > 4 → true
        assertEquals(-1.0, student.calculateGPA(List.of(4.5), List.of(3)), 0.01);

        // credit < 0 → true
        assertEquals(-1.0, student.calculateGPA(List.of(3.0), List.of(-1)), 0.01);

        // Each condition also false at least once → normal GPA
        assertEquals(3.5, student.calculateGPA(List.of(3.0, 4.0), List.of(3, 3)), 0.01);
    }


     // Ensures each condition independently affects the decision outcome.

    @Test
    void mcDCCoverageTest() {
        // Early return conditions:
        // grades == null independently flips decision
        assertEquals(0.0, student.calculateGPA(null, List.of(3)), 0.01);

        // credits == null independently flips decision
        assertEquals(0.0, student.calculateGPA(List.of(3.0), null), 0.01);

        // In-loop invalid grades/credits independently flip decision
        assertEquals(-1.0, student.calculateGPA(List.of(-1.0), List.of(3)), 0.01); // grade < 0
        assertEquals(-1.0, student.calculateGPA(List.of(4.5), List.of(3)), 0.01);  // grade > 4
        assertEquals(-1.0, student.calculateGPA(List.of(3.0), List.of(-1)), 0.01); // credit < 0

        // totalCredits == 0 independently flips decision
        assertEquals(0.0, student.calculateGPA(List.of(3.0), List.of(0)), 0.01);

        // Normal GPA → all conditions false
        assertEquals(3.5, student.calculateGPA(List.of(3.0, 4.0), List.of(3, 3)), 0.01);
    }
}