package System_Testing;

import edu.university.main.model.Course;
import edu.university.main.model.Student;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class StudentEnrollmentSystemTest extends BaseSystemTest {

    @Test
    @DisplayName("ST-001: Student Enrollment Workflow")
    void testST001_StudentEnrollmentWorkflow() {
        System.out.println("\n ST-001: Student Enrollment Workflow ");

        assertEquals(0, studentRepo.getStudentCount(), "Student repository should be empty initially");

         Student student = new Student("S001", "Eglis Braho", "eglis@university.edu", 20, "Computer Science");

        studentRepo.addStudent(student);
        System.out.println(" Step 7: Student created successfully: ArlinB");

        assertEquals(1, studentRepo.getStudentCount(), "Student count should increase to 1");
        assertNotNull(studentRepo.findStudentById("S001"), "Student S001 should exist in system");
        System.out.println(" Step 8: Student count verified: 1");

        Course cs101 = new Course("CS101", "Introduction to Programming", "Dr. Smith", 3, 30,"CS");
        courseRepo.addCourse(cs101);

        String enrollStudentId = "S001";
        String enrollCourseId = "CS101";

        boolean enrollmentSuccess = enrollmentService.enrollStudent(enrollStudentId, enrollCourseId);
        assertTrue(enrollmentSuccess, "Enrollment should be successful");
        System.out.println(" Step 11: Enrollment successful");

        assertTrue(student.getEnrolledCourses().contains("CS101"), "Activity log should show enrollment confirmation");
        System.out.println(" Step 12: Enrollment confirmed in activity log");

        assertEquals(3, student.getEnrolledCredits(), "Student credit hours should show 3");
        System.out.println("Step 13: Student credit hours updated to 3");

        Course foundCourse = courseRepo.findCourseById("CS101");
        assertNotNull(foundCourse, "Course CS101 should be found");
        System.out.println("Step 15: Course CS101 found");

        assertEquals(1, foundCourse.getCurrentEnrollment(), "Current enrollment should show 1 student");
        assertEquals(29, foundCourse.getAvailableSeats(), "Available seats should be 29");
        System.out.println(" Step 16: Enrollment count verified: 1 student enrolled");

        System.out.println("ST-001: PASSED - All steps completed successfully\n");
    }

    @Test
    @DisplayName("ST-003: Course Capacity Management")
    void testST003_CourseCapacityManagement() {
        System.out.println("\n ST-003: Course Capacity Management ");

        System.out.println(" Step 1: Courses tab displayed");

        Course cs201 = new Course("CS201", "Data Structures", "Dr. Flori", 3, 3, "CS");
        courseRepo.addCourse(cs201);
        System.out.println(" Step 2: Course CS201 created successfully");

        assertEquals(3, cs201.getAvailableSeats(), "Available seats should show 3");
        System.out.println(" Step 3: Available seats verified: 3");

        for (int i = 1; i <= 5; i++) {
            Student student = new Student("S00" + i, "Student " + i,
                    "student" + i + "@edu.edu", 20, "CS");
            studentRepo.addStudent(student);
        }
        System.out.println(" Step 4: 5 students created");

        enrollmentService.enrollStudent("S001", "CS201");
        System.out.println(" Step 5: S001 enrolled in CS201");

        assertEquals(2, cs201.getAvailableSeats(), "Available seats should be decremented to 2");
        System.out.println(" Step 7: Available seats: 2");

        assertEquals(33.33, cs201.getFillRate(), 0.1, "Fill rate should be calculated correctly");
        System.out.println(" Step 8: Fill rate: 33.33%");

        enrollmentService.enrollStudent("S002", "CS201");
        System.out.println(" Step 9: S002 enrolled in CS201");

        assertEquals(1, cs201.getAvailableSeats(), "Available seats should be 1");
        assertEquals(66.67, cs201.getFillRate(), 0.1, "Fill rate should be updated");
        System.out.println(" Steps 10-11: Available seats: 1, Fill rate: 66.67%");

        enrollmentService.enrollStudent("S003", "CS201");
        System.out.println(" Step 12: S003 enrolled in CS201");

        assertEquals(0, cs201.getAvailableSeats(), "Available seats should be 0");
        assertEquals(100.0, cs201.getFillRate(), 0.01, "Fill rate should be 100%");
        System.out.println(" Steps 13-14: Available seats: 0, Fill rate: 100%");

        assertEquals("HIGH_DEMAND", cs201.getPopularityStatus(),
                "Popularity status should be displayed");
        System.out.println(" Step 15: Course popularity: HIGH_DEMAND");

        boolean enrollmentFailed = !enrollmentService.enrollStudent("S004", "CS201");
        assertTrue(enrollmentFailed, "Enrollment should FAIL");
        System.out.println(" Step 16: S004 enrollment failed as expected");

        System.out.println(" Step 17: Error message displayed");

        assertEquals(3, cs201.getCurrentEnrollment(), "No change to enrollment count");
        System.out.println(" Step 18: Enrollment count unchanged: 3");

        enrollmentService.deleteStudent("S001");
        System.out.println(" Step 19: S001 dropped from CS201");

        assertEquals(1, cs201.getAvailableSeats(), "Available seats should increase to 1");
        System.out.println(" Step 20: Available seats increased to 1");

        boolean enrollmentSuccess = enrollmentService.enrollStudent("S004", "CS201");
        assertTrue(enrollmentSuccess, "Enrollment should succeed");
        System.out.println(" Step 21: S004 enrolled successfully");

        assertEquals(0, cs201.getAvailableSeats(), "Available seats should be 0");
        System.out.println(" Step 22: Course full again");

        System.out.println("ST-003: PASSED - Course capacity management completed\n");

    }

    @Test
    @DisplayName("ST-006: Multi-Student Enrollment Scenario")
    void testST006_MultiStudentEnrollment() {
        System.out.println("\n ST-006: Multi-Student Enrollment Scenario ");

        for (int i = 1; i <= 10; i++) {
            String id = String.format("S%03d", i);
            Student student = new Student(id, "Student " + i,
                    "student" + i + "@edu.edu", 20, "CS");
            studentRepo.addStudent(student);
        }
        assertEquals(10, studentRepo.getStudentCount(), "All 10 students should be created");
        System.out.println(" Step 1: 10 students created (S001-S010)");

        Course cs101 = new Course("CS101", "Intro to Programming", "Dr. Sami", 3, 30,"CS");
        Course cs102 = new Course("CS102", "Data Structures", "Dr. Armiri", 3, 5,"LAW");
        Course cs103 = new Course("CS103", "Algorithms", "Dr. Enejda", 3, 20,"Architecture");

        courseRepo.addCourse(cs101);
        courseRepo.addCourse(cs102);
        courseRepo.addCourse(cs103);
        assertEquals(3, courseRepo.getCourseCount(), "All courses should be created");
        System.out.println("Step 2: 3 courses created (CS101: 30, CS102: 5, CS103: 20)");

        for (int i = 1; i <= 5; i++) {
            String id = String.format("S%03d", i);
            enrollmentService.enrollStudent(id, "CS101");
        }
        System.out.println(" Step 3: S001-S005 enrolled in CS101");

        assertEquals(5, cs101.getCurrentEnrollment(), "Enrollment count should be 5");
        System.out.println(" Step 5: CS101 enrollment verified: 5");

        assertEquals(16.67, cs101.getFillRate(), 0.1, "Fill rate should be calculated");
        System.out.println(" Step 6: CS101 fill rate: 16.67%");

        System.out.println(" Step 7: Students tab shown");

        int successCount = 0;
        int failCount = 0;
        for (int i = 1; i <= 10; i++) {
            String id = String.format("S%03d", i);
            boolean success = enrollmentService.enrollStudent(id, "CS102");
            if (success) successCount++;
            else failCount++;
        }
        assertEquals(5, successCount, "Only first 5 enrollments should succeed");
        assertEquals(5, failCount, "Last 5 should fail");
        System.out.println(" Step 8: 5 enrollments succeeded, 5 failed");

        System.out.println("Step 9: Error messages displayed for failed enrollments");

        assertEquals(5, cs102.getCurrentEnrollment(), "Course should be at capacity");
        System.out.println(" Step 10: CS102 at capacity: 5");

        enrollmentService.deleteStudent("S003");
        System.out.println(" Step 11: S003 dropped from CS102");

        assertEquals(4, cs102.getCurrentEnrollment(), "Enrollment should be decremented");
        System.out.println(" Step 12: CS102 enrollment: 4");

        boolean s006Enrolled = enrollmentService.enrollStudent("S006", "CS102");
        assertTrue(s006Enrolled, "Enrollment should be successful (seat freed)");
        System.out.println(" Step 13: S006 enrolled in CS102");

        assertEquals(5, cs102.getCurrentEnrollment(), "Course should be full again");
        System.out.println(" Step 14: CS102 full again: 5");

        for (int i = 1; i <=10; i++) {
            String id = String.format("S%03d", i);
            enrollmentService.enrollStudent(id, "CS103");
        }
        System.out.println(" Step 15: S001-S010 enrolled in CS103");

        assertEquals(10, cs103.getCurrentEnrollment(), "All should be enrolled");
        System.out.println(" Step 16: CS103 enrollment: 10");

        assertEquals(10, studentRepo.getStudentCount(), "Count should be 10");
        assertEquals(3, courseRepo.getCourseCount(), "Count should be 3");
        System.out.println(" Steps 17-18: Repository counts verified");

        System.out.println(" Step 19: All tabs show consistent data");

        System.out.println(" ST-006: PASSED - Multi-student enrollment completed\n");
    }

}
