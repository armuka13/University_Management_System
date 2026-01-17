package edu.university.main.ModelTest;

import edu.university.main.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student("S001", "John Doe", "john@test.com", 20, "Computer Science");
    }

    @Test
    void testCalculateGPAValidInput() {
        List<Double> grades = Arrays.asList(3.5, 4.0, 3.7);
        List<Integer> credits = Arrays.asList(3, 4, 3);

        double gpa = student.calculateGPA(grades, credits);

        assertEquals(3.73, gpa);
    }

    @Test
    void testCalculateGPAEmptyLists() {
        List<Double> grades = Arrays.asList();
        List<Integer> credits = Arrays.asList();

        double gpa = student.calculateGPA(grades, credits);

        assertEquals(0.0, gpa);
    }

    @Test
    void testCalculateGPANullInput() {
        double gpa = student.calculateGPA(null, null);

        assertEquals(0.0, gpa);
    }

    @Test
    void testCalculateGPAMismatchedSizes() {
        List<Double> grades = Arrays.asList(3.5, 4.0);
        List<Integer> credits = Arrays.asList(3, 4, 3);

        double gpa = student.calculateGPA(grades, credits);

        assertEquals(-1.0, gpa);
    }

    @Test
    void testCalculateGPAInvalidGrade() {
        List<Double> grades = Arrays.asList(3.5, 5.0);
        List<Integer> credits = Arrays.asList(3, 4);

        double gpa = student.calculateGPA(grades, credits);

        assertEquals(-1.0, gpa);
    }

    @Test
    void testCalculateGPANegativeCredit() {
        List<Double> grades = Arrays.asList(3.5, 4.0);
        List<Integer> credits = Arrays.asList(3, -1);

        double gpa = student.calculateGPA(grades, credits);

        assertEquals(-1.0, gpa);
    }

    @Test
    void testCalculateSemesterGPAWithPassFail() {
        List<Double> grades = Arrays.asList(3.5, 4.0, 3.0);
        List<Integer> credits = Arrays.asList(3, 4, 3);
        List<String> gradeModes = Arrays.asList("LETTER", "LETTER", "PASS_FAIL");

        double gpa = student.calculateSemesterGPA(grades, credits, gradeModes);

        assertEquals(3.79, gpa);
    }

    @Test
    void testCalculateSemesterGPAAllPassFail() {
        List<Double> grades = Arrays.asList(3.5, 4.0);
        List<Integer> credits = Arrays.asList(3, 4);
        List<String> gradeModes = Arrays.asList("PASS_FAIL", "PASS_FAIL");

        double gpa = student.calculateSemesterGPA(grades, credits, gradeModes);

        assertEquals(0.0, gpa);
    }

    @Test
    void testCalculateSemesterGPANullInput() {
        double gpa = student.calculateSemesterGPA(null, null, null);

        assertEquals(-1.0, gpa);
    }

    @Test
    void testCalculateSemesterGPAMismatchedSizes() {
        List<Double> grades = Arrays.asList(3.5, 4.0);
        List<Integer> credits = Arrays.asList(3, 4);
        List<String> gradeModes = Arrays.asList("LETTER");

        double gpa = student.calculateSemesterGPA(grades, credits, gradeModes);

        assertEquals(-1.0, gpa);
    }

    @Test
    void testDetermineAcademicStandingDeansList() {
        String standing = student.determineAcademicStanding(3.5, 15);

        assertEquals("DEAN_LIST", standing);
    }

    @Test
    void testDetermineAcademicStandingGoodStanding() {
        String standing = student.determineAcademicStanding(3.2, 12);

        assertEquals("GOOD_STANDING", standing);
    }

    @Test
    void testDetermineAcademicStandingSatisfactory() {
        String standing = student.determineAcademicStanding(2.5, 12);

        assertEquals("SATISFACTORY", standing);
    }

    @Test
    void testDetermineAcademicStandingProbation() {
        String standing = student.determineAcademicStanding(1.8, 12);

        assertEquals("PROBATION", standing);
    }

    @Test
    void testDetermineAcademicStandingSuspension() {
        String standing = student.determineAcademicStanding(1.2, 12);

        assertEquals("ACADEMIC_SUSPENSION", standing);
    }

    @Test
    void testDetermineAcademicStandingPartTime() {
        String standing = student.determineAcademicStanding(3.5, 9);

        assertEquals("PART_TIME", standing);
    }

    @Test
    void testDetermineAcademicStandingInvalid() {
        String standing = student.determineAcademicStanding(-1.0, 12);

        assertEquals("INVALID", standing);
    }

    @Test
    void testDetermineAcademicLevelFreshman() {
        String level = student.determineAcademicLevel(25);

        assertEquals("FRESHMAN", level);
    }

    @Test
    void testDetermineAcademicLevelSophomore() {
        String level = student.determineAcademicLevel(45);

        assertEquals("SOPHOMORE", level);
    }

    @Test
    void testDetermineAcademicLevelJunior() {
        String level = student.determineAcademicLevel(75);

        assertEquals("JUNIOR", level);
    }

    @Test
    void testDetermineAcademicLevelSenior() {
        String level = student.determineAcademicLevel(100);

        assertEquals("SENIOR", level);
    }

    @Test
    void testDetermineAcademicLevelGraduate() {
        String level = student.determineAcademicLevel(130);

        assertEquals("GRADUATE", level);
    }

    @Test
    void testDetermineAcademicLevelInvalid() {
        String level = student.determineAcademicLevel(-5);

        assertEquals("INVALID", level);
    }

    @Test
    void testCanEnrollHighGPA() {
        boolean canEnroll = student.canEnroll(15, 6, 3.7);

        assertTrue(canEnroll);
    }

    @Test
    void testCanEnrollNormalGPA() {
        boolean canEnroll = student.canEnroll(12, 4, 3.0);

        assertTrue(canEnroll);
    }

    @Test
    void testCanEnrollLowGPA() {
        boolean canEnroll = student.canEnroll(10, 3, 1.8);

        assertFalse(canEnroll);
    }

    @Test
    void testCanEnrollExceedsMaxCredits() {
        boolean canEnroll = student.canEnroll(15, 5, 3.0);

        assertFalse(canEnroll);
    }

    @Test
    void testCanEnrollInvalidInput() {
        boolean canEnroll = student.canEnroll(-5, 3, 3.0);

        assertFalse(canEnroll);
    }

    @Test
    void testCanEnrollInHonorsCourseEligible() {
        boolean canEnroll = student.canEnrollInHonorsCourse(3.6, 35, true);

        assertTrue(canEnroll);
    }

    @Test
    void testCanEnrollInHonorsCourseNoPrerequisites() {
        boolean canEnroll = student.canEnrollInHonorsCourse(3.6, 35, false);

        assertFalse(canEnroll);
    }

    @Test
    void testCanEnrollInHonorsCourseLowGPA() {
        boolean canEnroll = student.canEnrollInHonorsCourse(3.2, 35, true);

        assertFalse(canEnroll);
    }

    @Test
    void testCanEnrollInHonorsCourseInsufficientCredits() {
        boolean canEnroll = student.canEnrollInHonorsCourse(3.6, 25, true);

        assertFalse(canEnroll);
    }

    @Test
    void testCalculateTuitionFeesInStateNoScholarship() {
        double tuition = student.calculateTuitionFees(15, true, false);

        assertEquals(5750.0, tuition);
    }

    @Test
    void testCalculateTuitionFeesInStateWithScholarship() {
        double tuition = student.calculateTuitionFees(15, true, true);

        assertEquals(4437.5, tuition);
    }

    @Test
    void testCalculateTuitionFeesOutOfStateNoScholarship() {
        double tuition = student.calculateTuitionFees(12, false, false);

        assertEquals(10700.0, tuition);
    }

    @Test
    void testCalculateTuitionFeesOutOfStateWithScholarship() {
        double tuition = student.calculateTuitionFees(12, false, true);

        assertEquals(8680.0, tuition);
    }

    @Test
    void testCalculateTuitionFeesInvalidCredits() {
        double tuition = student.calculateTuitionFees(-5, true, false);

        assertEquals(-1.0, tuition);
    }

    @Test
    void testCalculateTuitionFeesScholarshipNotAppliedBelowMinCredits() {
        double tuition = student.calculateTuitionFees(9, true, true);

        assertEquals(3650.0, tuition);
    }

    @Test
    void testCalculateInternationalFeesF1Visa() {
        double fees = student.calculateInternationalFees(15, true, "F1");

        assertEquals(19522.5, fees);
    }

    @Test
    void testCalculateInternationalFeesJ1Visa() {
        double fees = student.calculateInternationalFees(12, true, "J1");

        assertEquals(17080.0, fees);
    }

    @Test
    void testCalculateInternationalFeesM1Visa() {
        double fees = student.calculateInternationalFees(15, true, "M1");

        assertEquals(19542.5, fees);
    }

    @Test
    void testCalculateInternationalFeesNoVisa() {
        double fees = student.calculateInternationalFees(12, false, "F1");

        assertEquals(-1.0, fees);
    }

    @Test
    void testCalculateInternationalFeesInvalidCredits() {
        double fees = student.calculateInternationalFees(-5, true, "F1");

        assertEquals(-1.0, fees);
    }

    @Test
    void testCalculateRemainingCredits() {
        int remaining = student.calculateRemainingCredits(60, 120);

        assertEquals(60, remaining);
    }

    @Test
    void testCalculateRemainingCreditsAlreadyMet() {
        int remaining = student.calculateRemainingCredits(130, 120);

        assertEquals(0, remaining);
    }

    @Test
    void testCalculateRemainingCreditsInvalid() {
        int remaining = student.calculateRemainingCredits(-5, 120);

        assertEquals(-1, remaining);
    }

    @Test
    void testCalculateSemestersToGraduation() {
        int semesters = student.calculateSemestersToGraduation(30, 15);

        assertEquals(2, semesters);
    }

    @Test
    void testCalculateSemestersToGraduationRoundsUp() {
        int semesters = student.calculateSemestersToGraduation(35, 15);

        assertEquals(3, semesters);
    }

    @Test
    void testCalculateSemestersToGraduationZeroRemaining() {
        int semesters = student.calculateSemestersToGraduation(0, 15);

        assertEquals(0, semesters);
    }

    @Test
    void testCalculateSemestersToGraduationInvalid() {
        int semesters = student.calculateSemestersToGraduation(30, -5);

        assertEquals(-1, semesters);
    }

    @Test
    void testPredictGraduationGPA() {
        double predicted = student.predictGraduationGPA(3.5, 60, 60, 3.8);

        assertEquals(3.65, predicted);
    }

    @Test
    void testPredictGraduationGPAZeroCredits() {
        double predicted = student.predictGraduationGPA(0.0, 0, 0, 0.0);

        assertEquals(0.0, predicted);
    }

    @Test
    void testPredictGraduationGPAInvalidGPA() {
        double predicted = student.predictGraduationGPA(5.0, 60, 60, 3.8);

        assertEquals(-1.0, predicted);
    }

    @Test
    void testIsEligibleForGraduationEligible() {
        boolean eligible = student.isEligibleForGraduation(120, 3.0, 60, 30);

        assertTrue(eligible);
    }

    @Test
    void testIsEligibleForGraduationInsufficientCredits() {
        boolean eligible = student.isEligibleForGraduation(100, 3.0, 60, 30);

        assertFalse(eligible);
    }

    @Test
    void testIsEligibleForGraduationLowGPA() {
        boolean eligible = student.isEligibleForGraduation(120, 1.8, 60, 30);

        assertFalse(eligible);
    }

    @Test
    void testIsEligibleForGraduationInsufficientMajorCredits() {
        boolean eligible = student.isEligibleForGraduation(120, 3.0, 50, 30);

        assertFalse(eligible);
    }

    @Test
    void testIsEligibleForGraduationInsufficientElectives() {
        boolean eligible = student.isEligibleForGraduation(120, 3.0, 60, 20);

        assertFalse(eligible);
    }

    @Test
    void testGettersAndSetters() {
        student.setName("Jane Doe");
        student.setEmail("jane@test.com");
        student.setAge(21);
        student.setMajor("Mathematics");
        student.setGpa(3.75);
        student.setCreditHours(45);
        student.setAcademicLevel("SOPHOMORE");
        student.setInternational(true);
        student.setResidencyStatus("OUT_OF_STATE");

        assertEquals("S001", student.getStudentId());
        assertEquals("Jane Doe", student.getName());
        assertEquals("jane@test.com", student.getEmail());
        assertEquals(21, student.getAge());
        assertEquals("Mathematics", student.getMajor());
        assertEquals(3.75, student.getGpa());
        assertEquals(45, student.getCreditHours());
        assertEquals("SOPHOMORE", student.getAcademicLevel());
        assertTrue(student.isInternational());
        assertEquals("OUT_OF_STATE", student.getResidencyStatus());
        assertNotNull(student.getEnrolledCourseIds());
    }
}