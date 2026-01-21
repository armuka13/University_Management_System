package Tests.BVT;

import edu.university.main.model.Student;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentGPATest {

    //  Minimum grade boundary (0.0)
    @Test
    public void testCalculateGPA_MinGradeBoundary() {
        Student student = new Student("S001", "Test1", "test1@edu", 20, "CS");

        List<Double> grades = Arrays.asList(0.0);
        List<Integer> credits = Arrays.asList(3);

        double result = student.calculateGPA(grades, credits);

        assertEquals(0.0, result, 0.01);
    }

    //  Maximum grade boundary (4.0)
    @Test
    public void testCalculateGPA_MaxGradeBoundary() {
        Student student = new Student("S002", "Test2", "test2@edu", 20, "CS");

        List<Double> grades = Arrays.asList(4.0);
        List<Integer> credits = Arrays.asList(3);

        double result = student.calculateGPA(grades, credits);

        assertEquals(4.0, result, 0.01);
    }

    //  Below minimum grade boundary (-0.1)
    @Test
    public void testCalculateGPA_BelowMinGrade() {
        Student student = new Student("S003", "Test3", "test3@edu", 20, "CS");

        List<Double> grades = Arrays.asList(-0.1);
        List<Integer> credits = Arrays.asList(3);

        double result = student.calculateGPA(grades, credits);

        assertEquals(-1.0, result, 0.01);
    }

    //  Above maximum grade boundary (4.1)
    @Test
    public void testCalculateGPA_AboveMaxGrade() {
        Student student = new Student("S004", "Test4", "test4@edu", 20, "CS");

        List<Double> grades = Arrays.asList(4.1);
        List<Integer> credits = Arrays.asList(3);

        double result = student.calculateGPA(grades, credits);

        assertEquals(-1.0, result, 0.01);
    }

    //  Credit boundary value (0 credits)
    @Test
    public void testCalculateGPA_ZeroCredits() {
        Student student = new Student("S005", "Test5", "test5@edu", 20, "CS");

        List<Double> grades = Arrays.asList(3.5);
        List<Integer> credits = Arrays.asList(0);

        double result = student.calculateGPA(grades, credits);

        assertEquals(0.0, result, 0.01);
    }

    //  Negative credit boundary (-1)
    @Test
    public void testCalculateGPA_NegativeCredits() {
        Student student = new Student("S006", "Test6", "test6@edu", 20, "CS");

        List<Double> grades = Arrays.asList(3.5);
        List<Integer> credits = Arrays.asList(-1);

        double result = student.calculateGPA(grades, credits);

        assertEquals(-1.0, result, 0.01);
    }

    // Size mismatch boundary
    @Test
    public void testCalculateGPA_SizeMismatch() {
        Student student = new Student("S007", "Test7", "test7@edu", 20, "CS");

        List<Double> grades = Arrays.asList(3.0, 4.0);
        List<Integer> credits = Arrays.asList(3);

        double result = student.calculateGPA(grades, credits);

        assertEquals(-1.0, result, 0.01);
    }

    // Minimum non-empty list boundary (size = 1)
    @Test
    public void testCalculateGPA_MinListSize() {
        Student student = new Student("S008", "Test8", "test8@edu", 20, "CS");

        List<Double> grades = Arrays.asList(3.0);
        List<Integer> credits = Arrays.asList(1);

        double result = student.calculateGPA(grades, credits);

        assertEquals(3.0, result, 0.01);
    }


}