package BVT;

import edu.university.main.model.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTuitonTest {

    Student student = new Student("S001", "Test", "test@edu", 20, "CS");

    // Below minimum credit hours (-1)
    @Test
    public void testTuition_BelowMinCredit() {
        double result = student.calculateTuitionFees(-1, true, false);
        assertEquals(-1.0, result, 0.01);
    }

    //  Minimum credit hours (0)
    @Test
    public void testTuition_MinCredit() {
        double result = student.calculateTuitionFees(0, true, false);
        // 0*350 + 500 = 500
        assertEquals(500.0, result, 0.01);
    }

    // Just above minimum credit hours (1)
    @Test
    public void testTuition_JustAboveMinCredit() {
        double result = student.calculateTuitionFees(1, true, false);
        // 1*350 + 500 = 850
        assertEquals(850.0, result, 0.01);
    }

    //  Just below maximum credit hours  (11)
    @Test
    public void testTuition_JustBelowScholarship() {
        double result = student.calculateTuitionFees(11, true, true);

        double expected = 11*350 + 500;
        // 11*350 = 3850 + 500 = 4350
        assertEquals(4350.0, result, 0.01);
    }

    //  Scholarship boundary for in-state (12)
    @Test
    public void testTuition_Scholarship_InState() {
        double result = student.calculateTuitionFees(12, true, true);

        double expected = 12*350*0.75 + 500;
        assertEquals(expected, result, 0.01); // 12*350=4200*0.75=3150+500=3650
    }

    //  Scholarship boundary for out-of-state (12)
    @Test
    public void testTuition_Scholarship_OutOfState() {
        double result = student.calculateTuitionFees(12, false, true);

        double expected = 12*850*0.80 + 500; // 10200*0.80=8160+500=8660
        assertEquals(expected, result, 0.01);
    }

    //  Maximum credit hours (24)
    @Test
    public void testTuition_MaxCredit() {
        double result = student.calculateTuitionFees(24, true, false);
        // 24*350 + 500 = 8400 + 500 = 8900
        assertEquals(8900.0, result, 0.01);
    }

    //  Above maximum credit hours (25)
    @Test
    public void testTuition_AboveMaxCredit() {
        double result = student.calculateTuitionFees(25, true, false);
        assertEquals(-1.0, result, 0.01);
    }

}