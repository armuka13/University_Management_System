package System_Testing;

import edu.university.main.model.Student;
import edu.university.main.repository.StudentRepository;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

@DisplayName("FR-SM: Student Management System Tests")
public class StudentManagementSystemTest {

    private Student student=new Student("S001", "John Doe", "john@university.edu", 20, "Computer Science");
    private StudentRepository repository= repository = new StudentRepository();


    @Test
    @DisplayName("FR-SM-001: Student Registration - Valid Inputs")
    void testStudentRegistration_ValidInputs() {
        boolean saved = repository.save(student);
        assertTrue(saved, "Student should be saved successfully");
        assertTrue(repository.exists("S001"), "Student should exist in repository");
        assertEquals(1, repository.count(), "Repository should contain 1 student");
    }

    @Test
    @DisplayName("FR-SM-001: Student Registration - Duplicate ID")
    void testStudentRegistration_DuplicateId() {
        repository.save(student);
        Student duplicate = new Student("S001", "Jane Doe", "jane@university.edu", 21, "Mathematics");
        boolean saved = repository.save(duplicate);
        assertTrue(saved, "Should update existing student");
        assertEquals(1, repository.count(), "Should still have 1 student");
    }

    @Test
    @DisplayName("FR-SM-001: Student Registration - Null Student")
    void testStudentRegistration_NullStudent() {
        boolean saved = repository.save(null);
        assertFalse(saved, "Null student should not be saved");
    }

    @Test
    @DisplayName("FR-SM-002: GPA Calculation - Valid Inputs")
    void testGPACalculation_ValidInputs() {
        List<Double> grades = Arrays.asList(4.0, 3.7, 3.3, 4.0);
        List<Integer> credits = Arrays.asList(3, 3, 4, 3);
        double gpa = student.calculateGPA(grades, credits);
        assertEquals(3.72, gpa, 0.01, "GPA should be 3.77");
    }

    @Test
    @DisplayName("FR-SM-002: GPA Calculation - Empty Lists")
    void testGPACalculation_EmptyLists() {
        List<Double> grades = Arrays.asList();
        List<Integer> credits = Arrays.asList();
        double gpa = student.calculateGPA(grades, credits);
        assertEquals(0.0, gpa, "Empty lists should return 0.0");
    }

    @Test
    @DisplayName("FR-SM-002: GPA Calculation - Mismatched Sizes")
    void testGPACalculation_MismatchedSizes() {
        List<Double> grades = Arrays.asList(4.0, 3.7);
        List<Integer> credits = Arrays.asList(3, 3, 4);
        double gpa = student.calculateGPA(grades, credits);
        assertEquals(-1.0, gpa, "Mismatched sizes should return -1.0");
    }

    @Test
    @DisplayName("FR-SM-002: GPA Calculation - Invalid Grade Range")
    void testGPACalculation_InvalidGrade() {
        List<Double> grades = Arrays.asList(4.5, 3.7);
        List<Integer> credits = Arrays.asList(3, 3);
        double gpa = student.calculateGPA(grades, credits);
        assertEquals(-1.0, gpa, "Grade > 4.0 should return -1.0");
    }

    @Test
    @DisplayName("FR-SM-002: GPA Calculation - Negative Credits")
    void testGPACalculation_NegativeCredits() {
        List<Double> grades = Arrays.asList(4.0, 3.7);
        List<Integer> credits = Arrays.asList(3, -2);
        double gpa = student.calculateGPA(grades, credits);
        assertEquals(-1.0, gpa, "Negative credits should return -1.0");
    }

    @Test
    @DisplayName("FR-SM-002: GPA Calculation - Zero Credits")
    void testGPACalculation_ZeroCredits() {
        List<Double> grades = Arrays.asList(4.0, 3.7);
        List<Integer> credits = Arrays.asList(0, 0);
        double gpa = student.calculateGPA(grades, credits);
        assertEquals(0.0, gpa, "Zero total credits should return 0.0");
    }

    @Test
    @DisplayName("FR-SM-003: Semester GPA - Excluding Pass/Fail")
    void testSemesterGPA_ExcludingPassFail() {
        List<Double> grades = Arrays.asList(4.0, 3.7, 3.0);
        List<Integer> credits = Arrays.asList(3, 3, 4);
        List<String> modes = Arrays.asList("LETTER", "LETTER", "PASS_FAIL");
        double semesterGPA = student.calculateSemesterGPA(grades, credits, modes);
        assertEquals(3.85, semesterGPA, 0.01, "Should exclude PASS_FAIL course");
    }

    @Test
    @DisplayName("FR-SM-003: Semester GPA - All Pass/Fail")
    void testSemesterGPA_AllPassFail() {
        List<Double> grades = Arrays.asList(4.0, 3.7);
        List<Integer> credits = Arrays.asList(3, 3);
        List<String> modes = Arrays.asList("PASS_FAIL", "PASS_FAIL");
        double semesterGPA = student.calculateSemesterGPA(grades, credits, modes);
        assertEquals(0.0, semesterGPA, "All PASS_FAIL should return 0.0");
    }

    @Test
    @DisplayName("FR-SM-004: Academic Standing - Dean's List")
    void testAcademicStanding_DeansList() {
        String standing = student.determineAcademicStanding(3.7, 16);
        assertEquals("DEAN_LIST", standing, "GPA 3.7 with 16 credits should be DEAN_LIST");
    }

    @Test
    @DisplayName("FR-SM-004: Academic Standing - Good Standing")
    void testAcademicStanding_GoodStanding() {
        String standing = student.determineAcademicStanding(3.2, 14);
        assertEquals("GOOD_STANDING", standing, "GPA 3.2 with 14 credits should be GOOD_STANDING");
    }

    @Test
    @DisplayName("FR-SM-004: Academic Standing - Satisfactory")
    void testAcademicStanding_Satisfactory() {
        String standing = student.determineAcademicStanding(2.5, 13);
        assertEquals("SATISFACTORY", standing, "GPA 2.5 with 13 credits should be SATISFACTORY");
    }

    @Test
    @DisplayName("FR-SM-004: Academic Standing - Probation")
    void testAcademicStanding_Probation() {
        String standing = student.determineAcademicStanding(1.8, 12);
        assertEquals("PROBATION", standing, "GPA 1.8 should be PROBATION");
    }

    @Test
    @DisplayName("FR-SM-004: Academic Standing - Academic Suspension")
    void testAcademicStanding_Suspension() {
        String standing = student.determineAcademicStanding(1.2, 12);
        assertEquals("ACADEMIC_SUSPENSION", standing, "GPA 1.2 should be ACADEMIC_SUSPENSION");
    }

    @Test
    @DisplayName("FR-SM-004: Academic Standing - Part Time")
    void testAcademicStanding_PartTime() {
        String standing = student.determineAcademicStanding(3.5, 9);
        assertEquals("PART_TIME", standing, "Less than 12 credits should be PART_TIME");
    }

    @Test
    @DisplayName("FR-SM-005: Academic Level - Freshman")
    void testAcademicLevel_Freshman() {
        String level = student.determineAcademicLevel(25);
        assertEquals("FRESHMAN", level, "25 credits should be FRESHMAN");
    }

    @Test
    @DisplayName("FR-SM-005: Academic Level - Sophomore")
    void testAcademicLevel_Sophomore() {
        String level = student.determineAcademicLevel(45);
        assertEquals("SOPHOMORE", level, "45 credits should be SOPHOMORE");
    }

    @Test
    @DisplayName("FR-SM-005: Academic Level - Junior")
    void testAcademicLevel_Junior() {
        String level = student.determineAcademicLevel(75);
        assertEquals("JUNIOR", level, "75 credits should be JUNIOR");
    }

    @Test
    @DisplayName("FR-SM-005: Academic Level - Senior")
    void testAcademicLevel_Senior() {
        String level = student.determineAcademicLevel(105);
        assertEquals("SENIOR", level, "105 credits should be SENIOR");
    }

    @Test
    @DisplayName("FR-SM-005: Academic Level - Graduate")
    void testAcademicLevel_Graduate() {
        String level = student.determineAcademicLevel(125);
        assertEquals("GRADUATE", level, "125 credits should be GRADUATE");
    }

    @Test
    @DisplayName("FR-SM-006: Enrollment Eligibility - High Performer")
    void testEnrollmentEligibility_HighPerformer() {
        boolean canEnroll = student.canEnroll(18, 3, 3.7);
        assertTrue(canEnroll, "High performer should enroll in 21 credits");
    }

    @Test
    @DisplayName("FR-SM-006: Enrollment Eligibility - Normal Student")
    void testEnrollmentEligibility_Normal() {
        boolean canEnroll = student.canEnroll(15, 3, 3.2);
        assertTrue(canEnroll, "Normal student should enroll up to 18 credits");
    }

    @Test
    @DisplayName("FR-SM-006: Enrollment Eligibility - At-Risk Student")
    void testEnrollmentEligibility_AtRisk() {
        boolean canEnroll = student.canEnroll(12, 3, 1.8);
        assertFalse(canEnroll, "At-risk student cannot exceed 12 credits");
    }

    @Test
    @DisplayName("FR-SM-007: Honors Course Eligibility - Eligible")
    void testHonorsCourseEligibility_Eligible() {
        boolean eligible = student.canEnrollInHonorsCourse(3.6, 35, true);
        assertTrue(eligible, "Should be eligible for honors course");
    }

    @Test
    @DisplayName("FR-SM-007: Honors Course Eligibility - Low GPA")
    void testHonorsCourseEligibility_LowGPA() {
        boolean eligible = student.canEnrollInHonorsCourse(3.2, 35, true);
        assertFalse(eligible, "GPA < 3.5 should not be eligible");
    }

    @Test
    @DisplayName("FR-SM-007: Honors Course Eligibility - Insufficient Credits")
    void testHonorsCourseEligibility_InsufficientCredits() {
        boolean eligible = student.canEnrollInHonorsCourse(3.6, 25, true);
        assertFalse(eligible, "Credits < 30 should not be eligible");
    }

    @Test
    @DisplayName("FR-SM-007: Honors Course Eligibility - No Prerequisites")
    void testHonorsCourseEligibility_NoPrerequisites() {
        boolean eligible = student.canEnrollInHonorsCourse(3.6, 35, false);
        assertFalse(eligible, "No prerequisites should not be eligible");
    }

    @Test
    @DisplayName("FR-SM-008: Tuition - In-State Without Scholarship")
    void testTuition_InStateNoScholarship() {
        double tuition = student.calculateTuitionFees(15, true, false);
        assertEquals(5750.0, tuition, 0.01, "In-state 15 credits = (15*350)+500");
    }

    @Test
    @DisplayName("FR-SM-008: Tuition - In-State With Scholarship")
    void testTuition_InStateWithScholarship() {
        double tuition = student.calculateTuitionFees(15, true, true);
        assertEquals(4437.5, tuition, 0.01, "With scholarship: 25% discount on tuition");
    }

    @Test
    @DisplayName("FR-SM-008: Tuition - Out-of-State Without Scholarship")
    void testTuition_OutOfStateNoScholarship() {
        double tuition = student.calculateTuitionFees(15, false, false);
        assertEquals(13250.0, tuition, 0.01, "Out-of-state 15 credits = (15*850)+500");
    }

    @Test
    @DisplayName("FR-SM-008: Tuition - Out-of-State With Scholarship")
    void testTuition_OutOfStateWithScholarship() {
        double tuition = student.calculateTuitionFees(15, false, true);
        assertEquals(10700.0, tuition, 0.01, "With scholarship: 20% discount on tuition");
    }

    @Test
    @DisplayName("FR-SM-008: Tuition - Scholarship Below 12 Credits")
    void testTuition_ScholarshipBelow12Credits() {
        double tuition = student.calculateTuitionFees(9, true, true);
        assertEquals(3650.0, tuition, 0.01, "Scholarship only applies if >= 12 credits");
    }

    @Test
    @DisplayName("FR-SM-009: International Fees - F1 Visa")
    void testInternationalFees_F1Visa() {
        double fees = student.calculateInternationalFees(15, true, "F1");
        assertEquals(19665.0, fees, 0.01, "F1 visa with 15 credits gets 5% discount");
    }

    @Test
    @DisplayName("FR-SM-009: International Fees - J1 Visa")
    void testInternationalFees_J1Visa() {
        double fees = student.calculateInternationalFees(12, true, "J1");
        assertEquals(17080.0, fees, 0.01, "J1 visa fee is $180");
    }

    @Test
    @DisplayName("FR-SM-009: International Fees - No Visa")
    void testInternationalFees_NoVisa() {
        double fees = student.calculateInternationalFees(15, false, "F1");
        assertEquals(-1.0, fees, "No visa should return -1.0");
    }

    @Test
    @DisplayName("FR-SM-010: Remaining Credits - Normal Case")
    void testRemainingCredits_NormalCase() {
        int remaining = student.calculateRemainingCredits(75, 120);
        assertEquals(45, remaining, "120 - 75 = 45 remaining");
    }

    @Test
    @DisplayName("FR-SM-010: Remaining Credits - Already Exceeded")
    void testRemainingCredits_AlreadyExceeded() {
        int remaining = student.calculateRemainingCredits(125, 120);
        assertEquals(0, remaining, "Should return 0 if already exceeded");
    }

    @Test
    @DisplayName("FR-SM-011: Semesters to Graduation - Normal Case")
    void testSemestersToGraduation_NormalCase() {
        int semesters = student.calculateSemestersToGraduation(45, 15);
        assertEquals(3, semesters, "45 credits at 15/semester = 3 semesters");
    }

    @Test
    @DisplayName("FR-SM-011: Semesters to Graduation - With Rounding Up")
    void testSemestersToGraduation_RoundingUp() {
        int semesters = student.calculateSemestersToGraduation(40, 15);
        assertEquals(3, semesters, "40 credits at 15/semester = ceil(2.67) = 3");
    }

    @Test
    @DisplayName("FR-SM-011: Semesters to Graduation - Zero Remaining")
    void testSemestersToGraduation_ZeroRemaining() {
        int semesters = student.calculateSemestersToGraduation(0, 15);
        assertEquals(0, semesters, "Zero remaining should return 0");
    }

    @Test
    @DisplayName("FR-SM-012: Predicted Graduation GPA")
    void testPredictedGraduationGPA() {
        double predicted = student.predictGraduationGPA(3.5, 90, 30, 3.8);
        assertEquals(3.58, predicted, 0.01, "Weighted average of current and expected");
    }

    @Test
    @DisplayName("FR-SM-013: Graduation Eligibility - Eligible")
    void testGraduationEligibility_Eligible() {
        boolean eligible = student.isEligibleForGraduation(125, 3.2, 65, 35);
        assertTrue(eligible, "Meets all graduation requirements");
    }

    @Test
    @DisplayName("FR-SM-013: Graduation Eligibility - Insufficient Credits")
    void testGraduationEligibility_InsufficientCredits() {
        boolean eligible = student.isEligibleForGraduation(115, 3.2, 65, 35);
        assertFalse(eligible, "Less than 120 credits");
    }

    @Test
    @DisplayName("FR-SM-013: Graduation Eligibility - Low GPA")
    void testGraduationEligibility_LowGPA() {
        boolean eligible = student.isEligibleForGraduation(125, 1.9, 65, 35);
        assertFalse(eligible, "GPA below 2.0");
    }
}

