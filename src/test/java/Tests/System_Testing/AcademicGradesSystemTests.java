package Tests.System_Testing;

import edu.university.main.model.Course;
import edu.university.main.model.Student;
import edu.university.main.service.GradeCalculationService;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AcademicGradesSystemTests extends BaseSystemTest {

    @Test
    @DisplayName("ST-002: Academic Progress Tracking")
    void testST002_AcademicProgressTracking() {
        System.out.println("\nST-002: Academic Progress Tracking");

        Student student = new Student("S002", "Eglis Braho", "eglis@university.edu", 21, "Mathematics");
        studentRepo.addStudent(student);
        System.out.println(" Step 2: Student S002 created successfully");

        Course[] courses = {
                new Course("MATH101", "Calculus I", "Dr. Ani", 3, 30,"CS"),
                new Course("MATH102", "Calculus II", "Dr. Eri", 3, 30,"CS"),
                new Course("MATH201", "Linear Algebra", "Dr. Andi", 3, 30,"CS"),
                new Course("MATH202", "Discrete Math", "Dr. Deivi", 3, 30,"CS"),
                new Course("MATH301", "Abstract Algebra", "Dr. Arjan", 3, 30,"CS")
        };
        for (Course course : courses) {
            courseRepo.addCourse(course);
        }

        enrollmentService.enrollStudent("S002", "MATH101");
        enrollmentService.enrollStudent("S002", "MATH102");
        enrollmentService.enrollStudent("S002", "MATH201");
        enrollmentService.enrollStudent("S002", "MATH202");
        assertEquals(12, student.getEnrolledCredits(), "Student should have 12 enrolled credits");
        System.out.println("Step 3: All 4 enrollments successful (12 credits)");

        System.out.println("Step 4: Grades tab displayed");


        Map<String, Double> gradeMap = new HashMap<>();
        gradeMap.put("MATH101", 4.0);
        gradeMap.put("MATH102", 3.3);
        gradeMap.put("MATH201", 3.0);
        gradeMap.put("MATH202", 3.7);

        List<Double> grades = new ArrayList<>();
        List<Integer> credits = new ArrayList<>();

        for (String courseId : gradeMap.keySet()) {
            grades.add(gradeMap.get(courseId));
            credits.add(3);
        }

        double calculatedGPA = student.calculateGPA(grades, credits);
        student.setGpa(calculatedGPA);
        student.setCreditHours(12);

        System.out.println(" Step 5: Grades entered and GPA calculated: " + calculatedGPA);
        assertEquals(3.5, calculatedGPA, 0.05, "System should calculate GPA = 3.5");

        String standing = student.determineAcademicStanding(calculatedGPA, student.getCreditHours());
        student.setAcademicLevel(standing);
        System.out.println(" Step 6: Academic standing: " + standing);
        assertEquals("GOOD_STANDING", standing, "System should display GOOD_STANDING");

        enrollmentService.enrollStudent("S002", "MATH301");
        assertEquals(15, student.getEnrolledCredits(), "Student should have 15 enrolled credits");
        System.out.println(" Step 7: Enrolled in 5th course, total credits = 15");

        grades.add(4.0);
        credits.add(3);

        double newGPA = student.calculateGPA(grades, credits);
        student.setGpa(newGPA);
        student.setCreditHours(15);
        System.out.println(" Step 8: New GPA calculated: " + newGPA);
        assertTrue(newGPA > calculatedGPA, "New GPA should be higher");

        String newStanding = student.determineAcademicStanding(newGPA, student.getCreditHours());
        student.setAcademicLevel(newStanding);
        System.out.println(" Step 9: Academic standing updated: " + newStanding);
        assertEquals("DEAN_LIST", newStanding, "System should display DEAN_LIST");

        System.out.println(" ST-002: PASSED - Academic progress tracking completed\n");
    }

    @Test

    @DisplayName("ST-005: Grade Calculation and Honors Determination")
    void testST005_GradeCalculationAndHonors() {
        System.out.println("\n ST-005: Grade Calculation and Honors Determination ");

        System.out.println(" Step 1: Grades tab displayed");

        GradeCalculationService gradeCalc = new GradeCalculationService();

        double score1 = gradeCalc.applyBonusPoints(85, true, true);
        assertEquals(90.00, score1, 0.01, "Final score should display 90.00");
        System.out.println("✓ Step 6: Score 85 + both bonuses = " + String.format("%.2f", score1));


        assertTrue(score1 >= 90, "Score should be shown in green");
        System.out.println(" Step 7: Score color: green (≥90)");


        double score2 = gradeCalc.applyBonusPoints(85, true, false);
        assertEquals(87.00, score2, 0.01, "Final score should display 87.00");
        System.out.println(" Step 10: Score 85 + attendance only = " + String.format("%.2f", score2));


        double score3 = gradeCalc.applyBonusPoints(98, true, true);
        assertEquals(100.00, score3, 0.01, "Final score should be capped at 100.00");
        System.out.println(" Step 14: Score 98 + bonuses capped at " + String.format("%.2f", score3));


        double gpa1 = 3.85;
        int completedCredits = 125;
        boolean eligible1 = gradeCalc.isEligibleForHonors(gpa1, completedCredits, completedCredits);
        String honorsLevel1 = gradeCalc.determineHonorsLevel(gpa1, completedCredits);

        assertTrue(eligible1, "Eligibility should show 'Yes'");
        assertEquals("MAGNA_CUM_LAUDE", honorsLevel1, "Level should display correctly");
        System.out.println(" Steps 18-19: GPA 3.85, 125 credits = MAGNA_CUM_LAUDE (Eligible)");


        double gpa2 = 3.95;
        String honorsLevel2 = gradeCalc.determineHonorsLevel(gpa2, completedCredits);
        assertEquals("SUMMA_CUM_LAUDE", honorsLevel2, "Level should be updated to highest tier");
        System.out.println(" Step 22: GPA 3.95, 125 credits = SUMMA_CUM_LAUDE");


        double gpa3 = 3.4;
        boolean eligible3 = gradeCalc.isEligibleForHonors(gpa3, completedCredits, completedCredits);
        assertFalse(eligible3, "Eligibility should show 'No'");
        System.out.println("✓ Step 25: GPA 3.4, 125 credits = Not eligible");

        System.out.println(" ST-005: PASSED - Grade calculation and honors completed\n");
    }

    @Test

    @DisplayName("ST-007: Graduation Eligibility Workflow")
    void testST007_GraduationEligibility() {
        System.out.println("\n ST-007: Graduation Eligibility Workflow ");


        System.out.println(" Step 1: Students tab displayed");


        Student s011 = new Student("S011", "Senior Student", "senior@edu.edu", 22, "CS");
        s011.setCreditHours(125);
        s011.setGpa(3.2);
        int majorCreditsS011 = 65;
        int electiveCreditsS011 = 35;
        studentRepo.addStudent(s011);
        System.out.println(" Steps 2-4: S011 created (125 credits, GPA 3.2, Major 65, Electives 35)");


        boolean eligibleS011 = s011.isEligibleForGraduation(s011.getCreditHours(), s011.getGpa(),
                majorCreditsS011, electiveCreditsS011);
        assertTrue(eligibleS011, "Status should show ELIGIBLE in green");
        System.out.println(" Step 6: Graduation status: ELIGIBLE (green)");


        Student s012 = new Student("S012", "Junior Student", "junior@edu.edu", 21, "CS");
        s012.setCreditHours(115);
        s012.setGpa(3.5);
        int majorCreditsS012 = 65;
        int electiveCreditsS012 = 35;
        studentRepo.addStudent(s012);
        System.out.println(" Step 8: S012 created (115 credits, GPA 3.5, Major 65, Electives 35)");


        boolean eligibleS012 = s012.isEligibleForGraduation(s012.getCreditHours(), s012.getGpa(),
                majorCreditsS012, electiveCreditsS012);
        assertFalse(eligibleS012, "Status should show NOT ELIGIBLE");
        System.out.println(" Step 10: Graduation status: NOT ELIGIBLE");


        int remainingCreditsS012 = s012.calculateRemainingCredits(s012.getCreditHours(), 120);
        assertEquals(5, remainingCreditsS012, "Remaining credits should be 5");
        System.out.println(" Step 11: Reason: Insufficient total credits (remaining 5)");


        int remainingS011 = s011.calculateRemainingCredits(s011.getCreditHours(), 120);
        assertEquals(0, remainingS011, "Remaining should be 0 (already exceeded 120)");
        int semestersS011 = s011.calculateSemestersToGraduation(remainingS011, 15);
        assertEquals(0, semestersS011, "Semesters should be 0");
        System.out.println(" Steps 12-13: S011 remaining credits: 0, semesters: 0");


        int semestersS012 = s012.calculateSemestersToGraduation(remainingCreditsS012, 15);
        assertEquals(1, semestersS012, "Semesters should be 1");
        System.out.println(" Steps 14-15: S012 remaining credits: 5, semesters: 1");


        double predictedGPA = s012.predictGraduationGPA(s012.getGpa(), s012.getCreditHours(),
                remainingCreditsS012, 3.8);
        assertEquals(3.51, predictedGPA, 0.02, "Calculation should be correct");
        System.out.println(" Step 17: Predicted GPA ≈ " + String.format("%.2f", predictedGPA));


        String levelS011 = s011.determineAcademicLevel(s011.getCreditHours());
        String levelS012 = s012.determineAcademicLevel(s012.getCreditHours());
        assertEquals("GRADUATE", levelS011, "Level should be GRADUATE");
        assertEquals("SENIOR", levelS012, "Level should be SENIOR");
        System.out.println(" Steps 18-19: S011=GRADUATE, S012=SENIOR");

        System.out.println(" ST-007: PASSED - Graduation eligibility completed\n");
    }


}
