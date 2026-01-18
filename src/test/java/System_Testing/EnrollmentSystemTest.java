package System_Testing;


import edu.university.main.model.*;
import edu.university.main.repository.*;
import edu.university.main.service.EnrollmentService;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FR-ES: Enrollment Service System Tests")
public class EnrollmentSystemTest {

    private EnrollmentService enrollmentService;
    private StudentRepository studentRepo;
    private CourseRepository courseRepo;
    private Student student;
    private Course course;

    @BeforeEach
    void setUp() {
        studentRepo = new StudentRepository();
        courseRepo = new CourseRepository();
        enrollmentService = new EnrollmentService(studentRepo, courseRepo);

        student = new Student("S001", "Alice Smith", "alice@edu.edu", 20, "CS");
        student.setGpa(3.5);
        student.setCreditHours(12);
        studentRepo.save(student);

        course = new Course("CS101", "Intro Programming", "Dr. Jones", 3, 30, "CS");
        courseRepo.save(course);
    }

    @Test
    @DisplayName("FR-ES-001: Student Enrollment - Success")
    void testStudentEnrollment_Success() {
        boolean enrolled = enrollmentService.enrollStudent("S001", "CS101");
        assertTrue(enrolled, "Enrollment should succeed");
        assertEquals(1, course.getCurrentEnrollment());
        assertEquals(15, student.getCreditHours());
        assertTrue(student.getEnrolledCourseIds().contains("CS101"));
    }

    @Test
    @DisplayName("FR-ES-001: Student Enrollment - Course Full")
    void testStudentEnrollment_CourseFull() {
        course.setCurrentEnrollment(30);
        boolean enrolled = enrollmentService.enrollStudent("S001", "CS101");
        assertFalse(enrolled, "Should fail when course is full");
    }

    @Test
    @DisplayName("FR-ES-001: Student Enrollment - Student Not Found")
    void testStudentEnrollment_StudentNotFound() {
        boolean enrolled = enrollmentService.enrollStudent("S999", "CS101");
        assertFalse(enrolled, "Should fail with invalid student");
    }

    @Test
    @DisplayName("FR-ES-001: Student Enrollment - Credit Limit Exceeded")
    void testStudentEnrollment_CreditLimitExceeded() {
        student.setCreditHours(16);
        student.setGpa(2.5);
        boolean enrolled = enrollmentService.enrollStudent("S001", "CS101");
        assertFalse(enrolled, "Should fail when exceeding credit limit");
    }

    @Test
    @DisplayName("FR-ES-002: Course Drop - Success")
    void testCourseDrop_Success() {
        enrollmentService.enrollStudent("S001", "CS101");
        boolean dropped = enrollmentService.dropCourse("S001", "CS101");
        assertTrue(dropped, "Drop should succeed");
        assertEquals(0, course.getCurrentEnrollment());
        assertEquals(12, student.getCreditHours());
        assertFalse(student.getEnrolledCourseIds().contains("CS101"));
    }

    @Test
    @DisplayName("FR-ES-002: Course Drop - Not Enrolled")
    void testCourseDrop_NotEnrolled() {
        boolean dropped = enrollmentService.dropCourse("S001", "CS101");
        assertFalse(dropped, "Should fail if not enrolled");
    }

    @Test
    @DisplayName("FR-ES-003: Enrollment Rate Calculation")
    void testEnrollmentRate() {
        double rate = enrollmentService.calculateEnrollmentRate(75, 100);
        assertEquals(75.0, rate, 0.01, "75/100 = 75%");
    }

    @Test
    @DisplayName("FR-ES-004: Enrollment Prediction - Positive Growth")
    void testEnrollmentPrediction_PositiveGrowth() {
        int predicted = enrollmentService.predictEnrollment(100, 10.0);
        assertEquals(110, predicted, "100 * 1.10 = 110");
    }

    @Test
    @DisplayName("FR-ES-004: Enrollment Prediction - Negative Growth")
    void testEnrollmentPrediction_NegativeGrowth() {
        int predicted = enrollmentService.predictEnrollment(100, -15.0);
        assertEquals(85, predicted, "100 * 0.85 = 85");
    }

    @Test
    @DisplayName("FR-ES-005: Waitlist Position - Not Full")
    void testWaitlistPosition_NotFull() {
        int position = enrollmentService.calculateWaitlistPosition(25, 30, 40);
        assertEquals(0, position, "Course not full, no waitlist");
    }

    @Test
    @DisplayName("FR-ES-005: Waitlist Position - Full Course")
    void testWaitlistPosition_FullCourse() {
        int position = enrollmentService.calculateWaitlistPosition(30, 30, 45);
        assertEquals(15, position, "45 - 30 = 15 on waitlist");
    }

    @Test
    @DisplayName("FR-ES-006: Prerequisites Check - All Met")
    void testPrerequisitesCheck_AllMet() {
        boolean eligible = enrollmentService.checkPrerequisites(3.0, 35, true);
        assertTrue(eligible, "All prerequisites met");
    }

    @Test
    @DisplayName("FR-ES-006: Prerequisites Check - Low GPA")
    void testPrerequisitesCheck_LowGPA() {
        boolean eligible = enrollmentService.checkPrerequisites(1.8, 35, true);
        assertFalse(eligible, "GPA below 2.0");
    }

    @Test
    @DisplayName("FR-ES-007: Retention Rate")
    void testRetentionRate() {
        double rate = enrollmentService.calculateRetentionRate(850, 1000);
        assertEquals(85.0, rate, 0.01, "850/1000 = 85%");
    }
}
