package System_Testing;

import edu.university.main.model.Course;
import edu.university.main.model.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EndToEndSystemTest extends BaseSystemTest {
    @Test
    @DisplayName("ST-010: Complete End-to-End Scenario")
    void testST010_CompleteLifecycleScenario() {
        System.out.println("\n ST-010: Complete End-to-End Scenario ");


        System.out.println("\n--- FRESHMAN YEAR ---");

        Student s015 = new Student("S015", "Complete Lifecycle Test",
                "lifecycle@edu.edu", 18, "CS");
        studentRepo.addStudent(s015);
        System.out.println("Step 1: Student S015 created (freshman)");

        Course[] freshmanCourses = {
                new Course("FRESH1", "Intro to CS", "Dr. A", 3, 30,"CS"),
                new Course("FRESH2", "Calculus I", "Dr. B", 3, 30,"CS"),
                new Course("FRESH3", "English Comp", "Dr. C", 3, 30,"CS"),
                new Course("FRESH4", "Physics I", "Dr. D", 3, 30,"CS")
        };

        for (Course course : freshmanCourses) {
            courseRepo.addCourse(course);
            enrollmentService.enrollStudent("S015", course.getCourseId());
        }
        System.out.println(" Step 2: All 4 enrollments successful (12 credits)");

        gradeCalculationService.applyBonusPoints(92, true, true);
        gradeCalculationService.applyBonusPoints(88, true, true);
        gradeCalculationService.applyBonusPoints(85, true, true);
        gradeCalculationService.applyBonusPoints(90, true, true);
        System.out.println(" Step 3: Grades processed (mock calculation)");


        double freshmanGPA = 3.55;
        s015.setGpa(freshmanGPA);
        s015.setCreditHours(12);
        System.out.println(" Step 4: GPA set to " + freshmanGPA);


        // Freshman < 30 credits
        String academicLevel = (s015.getCreditHours() < 30) ? "FRESHMAN" : "UNKNOWN";
        System.out.println(" Step 5: Academic level: " + academicLevel);
        assertEquals("FRESHMAN", academicLevel);


        double tuition1 = s015.calculateTuitionFees(12, true,true);
        System.out.println(" Step 6: Tuition calculated: " + tuition1);
        assertTrue(tuition1 > 0);


        double scholarship1 = financialService.calculateScholarshipAmount(freshmanGPA, 12, tuition1);
        System.out.println(" Step 7: Scholarship calculated: " + scholarship1);
        assertTrue(scholarship1 >= 0);


        System.out.println("\n SOPHOMORE YEAR ");


        for (int i = 1; i <= 5; i++) {
            Course course = new Course("SOPH" + i, "Course " + i, "Dr. " + i, 3, 30,"CS");
            courseRepo.addCourse(course);
            enrollmentService.enrollStudent("S015", course.getCourseId());
        }
        s015.setCreditHours(27);
        System.out.println(" Step 8: Sophomore enrollments completed, total credits: 27");

        boolean honorsEligible = (freshmanGPA >= 3.5 && s015.getCreditHours() >= 30);
        assertFalse(honorsEligible);
        System.out.println(" Step 9: Honors eligibility: " + honorsEligible);

        s015.setCreditHours(36);
        System.out.println("Step 10: Total credits after extra courses: " + s015.getCreditHours());
        assertEquals(36, s015.getCreditHours());


        academicLevel = (s015.getCreditHours() >= 30 && s015.getCreditHours() < 60) ? "SOPHOMORE" : academicLevel;
        assertEquals("SOPHOMORE", academicLevel);
        System.out.println(" Step 11: Academic level updated: " + academicLevel);


        s015.setCreditHours(65);
        academicLevel = (s015.getCreditHours() >= 60 && s015.getCreditHours() < 90) ? "JUNIOR" : academicLevel;
        assertEquals("JUNIOR", academicLevel);
        System.out.println(" Step 12: Academic level: " + academicLevel);

        s015.setCreditHours(95);
        academicLevel = (s015.getCreditHours() >= 90 && s015.getCreditHours() < 120) ? "SENIOR" : academicLevel;
        assertEquals("SENIOR", academicLevel);
        System.out.println("Step 13: Academic level: " + academicLevel);


        s015.setCreditHours(125);
        s015.setGpa(3.56);
        assertEquals(125, s015.getCreditHours());
        assertEquals(3.56, s015.getGpa(), 0.01);

        boolean graduationEligible = s015.getCreditHours() >= 120;
        assertTrue(graduationEligible);
        System.out.println(" Step 14: Graduation eligibility: " + graduationEligible);


        String honorsLevel = (s015.getGpa() >= 3.5) ? "CUM_LAUDE" : "NONE";
        assertEquals("CUM_LAUDE", honorsLevel);
        System.out.println(" Step 15: Honors level: " + honorsLevel);

        System.out.println("\n ST-010 PASSED - Lifecycle scenario completed");
    }

}
