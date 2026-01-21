package Tests.ECT;

import edu.university.main.model.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentECTAcademicStandingTest {


    Student student = new Student("S001", "Test", "test@edu", 20, "SWE");


    @Test
    public void testInvalidGPA_LessThanZero() {
        assertEquals("INVALID", student.determineAcademicStanding(-0.5, 10));
    }

    @Test
    public void testInvalidGPA_GreaterThanFour() {
        assertEquals("INVALID", student.determineAcademicStanding(4.5, 10));
    }

    @Test
    public void testInvalidCreditHours_Negative() {
        assertEquals("INVALID", student.determineAcademicStanding(2.5, -5));
    }

    @Test
    public void testPartTimeStudent() {
        assertEquals("PART_TIME", student.determineAcademicStanding(2.5, 10));
    }

    @Test
    public void testDeansList() {
        assertEquals("DEAN_LIST", student.determineAcademicStanding(3.6, 16));
    }

    @Test
    public void testGoodStanding() {
        assertEquals("GOOD_STANDING", student.determineAcademicStanding(3.2, 12));
    }

    @Test
    public void testSatisfactory() {
        assertEquals("SATISFACTORY", student.determineAcademicStanding(2.5, 12));
    }

    @Test
    public void testProbation() {
        assertEquals("PROBATION", student.determineAcademicStanding(1.6, 12));
    }

    @Test
    public void testAcademicSuspension() {
        assertEquals("ACADEMIC_SUSPENSION", student.determineAcademicStanding(1.2, 12));
    }

    @Test
    public void testHighGPA_LowCreditHours() {
        assertEquals("GOOD_STANDING", student.determineAcademicStanding(3.8, 12));
    }

}