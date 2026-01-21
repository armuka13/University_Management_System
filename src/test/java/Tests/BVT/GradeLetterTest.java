package Tests.BVT;

import edu.university.main.model.Grade;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GradeLetterTest {
    Grade gradeCalc = new Grade("S001", "C001", 0.0, 1, 2026);

    @Test
    public void testInvalidBelowZero() {
        assertEquals("INVALID", gradeCalc.calculateLetterGrade(-0.1));
    }

    @Test
    public void testMinF() {
        assertEquals("F", gradeCalc.calculateLetterGrade(0.0));
    }

    @Test
    public void testMaxF() {
        assertEquals("F", gradeCalc.calculateLetterGrade(59.99));
    }

    @Test
    public void testMinDMinus() {
        assertEquals("D-", gradeCalc.calculateLetterGrade(60.0));
    }

    @Test
    public void testMaxDMinus() {
        assertEquals("D-", gradeCalc.calculateLetterGrade(62.99));
    }

    @Test
    public void testMinD() {
        assertEquals("D", gradeCalc.calculateLetterGrade(63.0));
    }

    @Test
    public void testMinDPlus() {
        assertEquals("D+", gradeCalc.calculateLetterGrade(67.0));
    }

    @Test
    public void testMinCMinus() {
        assertEquals("C-", gradeCalc.calculateLetterGrade(70.0));
    }

    @Test
    public void testMinC() {
        assertEquals("C", gradeCalc.calculateLetterGrade(73.0));
    }

    @Test
    public void testMinCPlus() {
        assertEquals("C+", gradeCalc.calculateLetterGrade(77.0));
    }

    @Test
    public void testMinBMinus() {
        assertEquals("B-", gradeCalc.calculateLetterGrade(80.0));
    }

    @Test
    public void testMinB() {
        assertEquals("B", gradeCalc.calculateLetterGrade(83.0));
    }

    @Test
    public void testMinBPlus() {
        assertEquals("B+", gradeCalc.calculateLetterGrade(87.0));
    }

    @Test
    public void testMinAMinus() {
        assertEquals("A-", gradeCalc.calculateLetterGrade(90.0));
    }

    @Test
    public void testMinA() {
        assertEquals("A", gradeCalc.calculateLetterGrade(93.0));
    }

    @Test
    public void testInvalidAboveMax() {
        assertEquals("INVALID", gradeCalc.calculateLetterGrade(100.1));
    }

}