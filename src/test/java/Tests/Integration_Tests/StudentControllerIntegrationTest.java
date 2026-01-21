package Tests.Integration_Tests;

import edu.university.main.controller.StudentController;
import edu.university.main.model.Student;
import edu.university.main.model.Course;
import edu.university.main.repository.StudentRepository;
import edu.university.main.repository.CourseRepository;
import edu.university.main.service.EnrollmentService;
import edu.university.main.service.FinancialService;
import edu.university.main.view.StudentView;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentControllerIntegrationTest {

    // ========== CREATE STUDENT TESTS ==========

    @Test
    void testCreateStudentSuccessfully() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService = new EnrollmentService(studentRepository, courseRepository);
        FinancialService financialService = new FinancialService();
        SpyStudentView view = new SpyStudentView();
        StudentController controller = new StudentController(enrollmentService, financialService, view);

        controller.createStudent("S001", "Albi", "albi@edu.com", 20, "SWE");

        assertTrue(studentRepository.exists("S001"));
        view.verifyDisplayMessageCalled("Student created successfully: Albi");
    }

    @Test
    void testCreateMultipleStudents() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService = new EnrollmentService(studentRepository, courseRepository);
        FinancialService financialService = new FinancialService();
        SpyStudentView view = new SpyStudentView();
        StudentController controller = new StudentController(enrollmentService, financialService, view);

        controller.createStudent("S001", "Albi", "albi@edu.com", 20, "SWE");
        controller.createStudent("S002", "Tomi", "tomi@edu.com", 21, "SWE");

        assertTrue(studentRepository.exists("S001"));
        assertTrue(studentRepository.exists("S002"));
        assertEquals(2, studentRepository.count());
    }

    // ========== ENROLLMENT TESTS ==========

    @Test
    void testEnrollStudentSuccessfully() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService = new EnrollmentService(studentRepository, courseRepository);
        FinancialService financialService = new FinancialService();
        SpyStudentView view = new SpyStudentView();
        StudentController controller = new StudentController(enrollmentService, financialService, view);

        Student student = new Student("S001", "Albi", "albi@edu.com", 20, "SWE");
        student.setGpa(3.5);
        studentRepository.save(student);

        Course course = new Course("SWE303", "Software Testing", "Mr. Gjerazi", 3, 30, "SWE");
        courseRepository.save(course);

        controller.enrollStudentInCourse("S001", "SWE303");

        view.verifyDisplayMessageCalled("Enrollment successful");
    }

    @Test
    void testEnrollStudentFailure() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService = new EnrollmentService(studentRepository, courseRepository);
        FinancialService financialService = new FinancialService();
        SpyStudentView view = new SpyStudentView();
        StudentController controller = new StudentController(enrollmentService, financialService, view);

        Course course = new Course("SWE303", "Software Testing", "Mr. Gjerazi", 3, 1, "SWE");
        course.setCurrentEnrollment(1);
        courseRepository.save(course);

        controller.enrollStudentInCourse("S001", "SWE303");

        view.verifyDisplayMessageCalled("Enrollment failed - check capacity and eligibility");
    }

    @Test
    void testEnrollNonexistentStudent() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService = new EnrollmentService(studentRepository, courseRepository);
        FinancialService financialService = new FinancialService();
        SpyStudentView view = new SpyStudentView();
        StudentController controller = new StudentController(enrollmentService, financialService, view);

        Course course = new Course("SWE303", "Software Testing", "Mr. Gjerazi", 3, 30, "SWE");
        courseRepository.save(course);

        controller.enrollStudentInCourse("NONEXISTENT", "SWE303");

        view.verifyDisplayMessageCalled("Enrollment failed - check capacity and eligibility");
    }

    // ========== GPA CALCULATION TESTS ==========

    @Test
    void testCalculateStudentGPA() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService = new EnrollmentService(studentRepository, courseRepository);
        FinancialService financialService = new FinancialService();
        SpyStudentView view = new SpyStudentView();
        StudentController controller = new StudentController(enrollmentService, financialService, view);

        Student student = new Student("S001", "Albi", "albi@edu.com", 20, "SWE");
        studentRepository.save(student);

        List<Double> grades = new ArrayList<>();
        grades.add(3.5);
        grades.add(4.0);
        grades.add(3.0);

        List<Integer> credits = new ArrayList<>();
        credits.add(3);
        credits.add(4);
        credits.add(3);

        controller.calculateStudentGPA("S001", grades, credits);

        view.verifyDisplayGPACalled(3.55);
    }

    @Test
    void testCalculateStudentGPANonexistentStudent() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService = new EnrollmentService(studentRepository, courseRepository);
        FinancialService financialService = new FinancialService();
        SpyStudentView view = new SpyStudentView();
        StudentController controller = new StudentController(enrollmentService, financialService, view);

        List<Double> grades = new ArrayList<>();
        grades.add(3.5);

        List<Integer> credits = new ArrayList<>();
        credits.add(3);

        controller.calculateStudentGPA("NONEXISTENT", grades, credits);

        view.verifyDisplayMessageCalled("Student not found");
    }

    @Test
    void testCalculateStudentGPAInvalidData() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService = new EnrollmentService(studentRepository, courseRepository);
        FinancialService financialService = new FinancialService();
        SpyStudentView view = new SpyStudentView();
        StudentController controller = new StudentController(enrollmentService, financialService, view);

        Student student = new Student("S001", "Albi", "albi@edu.com", 20, "SWE");
        studentRepository.save(student);

        List<Double> grades = new ArrayList<>();
        grades.add(3.5);
        grades.add(5.0); // Invalid grade

        List<Integer> credits = new ArrayList<>();
        credits.add(3);
        credits.add(3);

        controller.calculateStudentGPA("S001", grades, credits);

        view.verifyDisplayMessageCalled("Invalid grades or credits");
    }

    @Test
    void testCalculateStudentGPAMismatchedSizes() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService = new EnrollmentService(studentRepository, courseRepository);
        FinancialService financialService = new FinancialService();
        SpyStudentView view = new SpyStudentView();
        StudentController controller = new StudentController(enrollmentService, financialService, view);

        Student student = new Student("S001", "Albi", "albi@edu.com", 20, "SWE");
        studentRepository.save(student);

        List<Double> grades = new ArrayList<>();
        grades.add(3.5);

        List<Integer> credits = new ArrayList<>();
        credits.add(3);
        credits.add(4);

        controller.calculateStudentGPA("S001", grades, credits);

        view.verifyDisplayMessageCalled("Invalid grades or credits");
    }

    // ========== ACADEMIC STANDING TESTS ==========

    @Test
    void testCheckAcademicStandingGoodStanding() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService = new EnrollmentService(studentRepository, courseRepository);
        FinancialService financialService = new FinancialService();
        SpyStudentView view = new SpyStudentView();
        StudentController controller = new StudentController(enrollmentService, financialService, view);

        Student student = new Student("S001", "Albi", "albi@edu.com", 20, "SWE");
        student.setGpa(3.2);
        student.setCreditHours(15);
        studentRepository.save(student);

        controller.checkAcademicStanding("S001");

        view.verifyDisplayAcademicStandingCalled("GOOD_STANDING");
    }

    @Test
    void testCheckAcademicStandingDeansList() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService = new EnrollmentService(studentRepository, courseRepository);
        FinancialService financialService = new FinancialService();
        SpyStudentView view = new SpyStudentView();
        StudentController controller = new StudentController(enrollmentService, financialService, view);

        Student student = new Student("S001", "Albi", "albi@edu.com", 20, "SWE");
        student.setGpa(3.7);
        student.setCreditHours(15);
        studentRepository.save(student);

        controller.checkAcademicStanding("S001");

        view.verifyDisplayAcademicStandingCalled("DEAN_LIST");
    }

    @Test
    void testCheckAcademicStandingProbation() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService = new EnrollmentService(studentRepository, courseRepository);
        FinancialService financialService = new FinancialService();
        SpyStudentView view = new SpyStudentView();
        StudentController controller = new StudentController(enrollmentService, financialService, view);

        Student student = new Student("S001", "Albi", "albi@edu.com", 20, "SWE");
        student.setGpa(1.8);
        student.setCreditHours(12);
        studentRepository.save(student);

        controller.checkAcademicStanding("S001");

        view.verifyDisplayAcademicStandingCalled("PROBATION");
    }

    @Test
    void testCheckAcademicStandingNonexistentStudent() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService = new EnrollmentService(studentRepository, courseRepository);
        FinancialService financialService = new FinancialService();
        SpyStudentView view = new SpyStudentView();
        StudentController controller = new StudentController(enrollmentService, financialService, view);

        controller.checkAcademicStanding("NONEXISTENT");

        view.verifyDisplayMessageCalled("Student not found");
    }

    // ========== TUITION CALCULATION TESTS ==========

    @Test
    void testCalculateTuitionInState() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService = new EnrollmentService(studentRepository, courseRepository);
        FinancialService financialService = new FinancialService();
        SpyStudentView view = new SpyStudentView();
        StudentController controller = new StudentController(enrollmentService, financialService, view);

        Student student = new Student("S001", "Albi", "albi@edu.com", 20, "SWE");
        student.setCreditHours(15);
        studentRepository.save(student);

        controller.calculateTuition("S001", true, false);

        // 15 * 350 + 500 = 5750
        view.verifyDisplayTuitionCalled(5750.0);
    }

    @Test
    void testCalculateTuitionOutOfState() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService = new EnrollmentService(studentRepository, courseRepository);
        FinancialService financialService = new FinancialService();
        SpyStudentView view = new SpyStudentView();
        StudentController controller = new StudentController(enrollmentService, financialService, view);

        Student student = new Student("S001", "Albi", "albi@edu.com", 20, "SWE");
        student.setCreditHours(15);
        studentRepository.save(student);

        controller.calculateTuition("S001", false, false);

        // 15 * 850 + 500 = 13250
        view.verifyDisplayTuitionCalled(13250.0);
    }

    @Test
    void testCalculateTuitionWithScholarship() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService = new EnrollmentService(studentRepository, courseRepository);
        FinancialService financialService = new FinancialService();
        SpyStudentView view = new SpyStudentView();
        StudentController controller = new StudentController(enrollmentService, financialService, view);

        Student student = new Student("S001", "Albi", "albi@edu.com", 20, "SWE");
        student.setCreditHours(15);
        studentRepository.save(student);

        controller.calculateTuition("S001", true, true);

        view.verifyDisplayTuitionCalled(4437.5);
    }

    @Test
    void testCalculateTuitionNonexistentStudent() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService = new EnrollmentService(studentRepository, courseRepository);
        FinancialService financialService = new FinancialService();
        SpyStudentView view = new SpyStudentView();
        StudentController controller = new StudentController(enrollmentService, financialService, view);

        controller.calculateTuition("NONEXISTENT", true, false);

        view.verifyDisplayMessageCalled("Student not found");
    }

    // ========== SPY VIEW (Manual Test Double) ==========

    private static class SpyStudentView extends StudentView {
        private String lastMessage;
        private double lastGPA;
        private String lastAcademicStanding;
        private double lastTuition;
        private boolean displayMessageCalled = false;
        private boolean displayGPACalled = false;
        private boolean displayAcademicStandingCalled = false;
        private boolean displayTuitionCalled = false;

        @Override
        public void displayMessage(String message) {
            this.lastMessage = message;
            this.displayMessageCalled = true;
        }

        @Override
        public void displayGPA(double gpa) {
            this.lastGPA = gpa;
            this.displayGPACalled = true;
        }

        @Override
        public void displayAcademicStanding(String standing) {
            this.lastAcademicStanding = standing;
            this.displayAcademicStandingCalled = true;
        }

        @Override
        public void displayTuition(double tuition) {
            this.lastTuition = tuition;
            this.displayTuitionCalled = true;
        }


        public void verifyDisplayMessageCalled(String expectedMessage) {
            assertTrue(displayMessageCalled, "displayMessage() was not called");
            assertEquals(expectedMessage, lastMessage, "Message does not match");
        }

        public void verifyDisplayGPACalled(double expectedGPA) {
            assertTrue(displayGPACalled, "displayGPA() was not called");
            assertEquals(expectedGPA, lastGPA, 0.01, "GPA does not match");
        }

        public void verifyDisplayAcademicStandingCalled(String expectedStanding) {
            assertTrue(displayAcademicStandingCalled, "displayAcademicStanding() was not called");
            assertEquals(expectedStanding, lastAcademicStanding, "Academic standing does not match");
        }

        public void verifyDisplayTuitionCalled(double expectedTuition) {
            assertTrue(displayTuitionCalled, "displayTuition() was not called");
            assertEquals(expectedTuition, lastTuition, 0.01, "Tuition does not match");
        }
    }
}