package Tests.CT;

import edu.university.main.model.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentCTEnrollTest {
    Student student = new Student("S001", "Test1", "test1@edu", 20, "CS");


    @Test
    void statementCoverageTests() {
        // Invalid input
        assertFalse(student.canEnroll(-1, 3, 3.0));
        assertFalse(student.canEnroll(12, -3, 3.0));
        assertFalse(student.canEnroll(12, 3, -1.0));
        assertFalse(student.canEnroll(12, 3, 4.5));

        // GPA >= 3.5, maxCredits = 21
        assertTrue(student.canEnroll(18, 3, 3.6)); // 18+3 <= 21
        assertFalse(student.canEnroll(19, 3, 3.6)); // 19+3 > 21

        // GPA < 2.0, maxCredits = 12
        assertTrue(student.canEnroll(10, 2, 1.5)); // 10+2 = 12
        assertFalse(student.canEnroll(11, 2, 1.5)); // 11+2 = 13 > 12

        // Normal GPA (2.0 - 3.5), maxCredits = 18
        assertTrue(student.canEnroll(15, 3, 3.0)); // 15+3 = 18
        assertFalse(student.canEnroll(16, 3, 3.0)); // 16+3 = 19 > 18
    }

    @Test
    void branchCoverageTests() {
        // Compound condition -> true
        assertFalse(student.canEnroll(-1, 0, 3.0));
        // Compound condition -> false
        assertTrue(student.canEnroll(12, 3, 3.0));

        // GPA >= 3.5 -> true
        assertTrue(student.canEnroll(18, 3, 3.6));
        // GPA >= 3.5 -> false
        assertTrue(student.canEnroll(12, 3, 3.2));

        // GPA < 2.0 ->true
        assertTrue(student.canEnroll(10, 2, 1.5));
        // GPA < 2.0 -> false
        assertTrue(student.canEnroll(12, 3, 3.0));

        // currentCredits + courseCredits > maxCredits-> true
        assertFalse(student.canEnroll(19, 3, 3.6));
        // currentCredits + courseCredits > maxCredits ->false
        assertTrue(student.canEnroll(15, 3, 3.0));
    }
    @Test
    void conditionCoverageTests() {
        // currentCredits < 0
        assertFalse(student.canEnroll(-1, 3, 3.0));
        // courseCredits < 0
        assertFalse(student.canEnroll(12, -3, 3.0));
        // gpa < 0
        assertFalse(student.canEnroll(12, 3, -1.0));
        // gpa > 4
        assertFalse(student.canEnroll(12, 3, 4.5));
        // all false
        assertTrue(student.canEnroll(12, 3, 3.0));
    }
    @Test
    void MCDCCoverageTests() {
        // Each condition independently triggers false
        assertFalse(student.canEnroll(-1, 3, 3.0)); // currentCredits < 0
        assertFalse(student.canEnroll(12, -3, 3.0)); // courseCredits < 0
        assertFalse(student.canEnroll(12, 3, -1.0)); // gpa < 0
        assertFalse(student.canEnroll(12, 3, 4.5)); // gpa > 4
        // all false → true
        assertTrue(student.canEnroll(12, 3, 3.0));
    }


}