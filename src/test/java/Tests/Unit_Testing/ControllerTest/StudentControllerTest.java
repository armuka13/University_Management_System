package Tests.Unit_Testing.ControllerTest;

import edu.university.main.controller.StudentController;
import edu.university.main.model.Student;
import edu.university.main.repository.StudentRepository;
import edu.university.main.service.EnrollmentService;
import edu.university.main.service.FinancialService;
import edu.university.main.view.StudentView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class StudentControllerTest {

    private StudentController controller;
    private StubEnrollmentService enrollmentService;
    private StubFinancialService financialService;
    private SpyStudentView view;
    private FakeStudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        enrollmentService = new StubEnrollmentService();
        financialService = new StubFinancialService();
        view = new SpyStudentView();
        studentRepository = new FakeStudentRepository();
        enrollmentService.setStudentRepository(studentRepository);
        controller = new StudentController(enrollmentService, financialService, view);
    }

    @Test
    void testCreateStudent() {
        controller.createStudent("S001", "John Doe", "john@university.edu", 20, "Computer Science");

        assertTrue(enrollmentService.isStudentAdded());
        assertEquals("Student created successfully: John Doe", view.getLastMessage());
    }

    @Test
    void testEnrollStudentInCourseSuccess() {
        enrollmentService.setEnrollmentResult(true);

        controller.enrollStudentInCourse("S001", "CS101");

        assertEquals("S001", enrollmentService.getStudentIdForEnrollment());
        assertEquals("CS101", enrollmentService.getCourseIdForEnrollment());
        assertEquals("Enrollment successful", view.getLastMessage());
    }

    @Test
    void testEnrollStudentInCourseFailure() {
        enrollmentService.setEnrollmentResult(false);

        controller.enrollStudentInCourse("S001", "CS101");

        assertEquals("Enrollment failed - check capacity and eligibility", view.getLastMessage());
    }

    @Test
    void testCalculateStudentGPASuccess() {
        List<Double> grades = Arrays.asList(3.5, 4.0, 3.7);
        List<Integer> credits = Arrays.asList(3, 4, 3);

        FakeStudent student = new FakeStudent("S001", "John", "john@test.com", 20, "CS");
        student.setGpaToReturn(3.75);
        studentRepository.addStudent(student);

        controller.calculateStudentGPA("S001", grades, credits);

        assertEquals(3.75, student.getGpa());
        assertTrue(studentRepository.wasStudentSaved());
        assertEquals(3.75, view.getGpaDisplayed());
    }

    @Test
    void testCalculateStudentGPAStudentNotFound() {
        List<Double> grades = Arrays.asList(3.5, 4.0);
        List<Integer> credits = Arrays.asList(3, 4);

        controller.calculateStudentGPA("S999", grades, credits);

        assertEquals("Student not found", view.getLastMessage());
        assertEquals(0.0, view.getGpaDisplayed());
    }

    @Test
    void testCalculateStudentGPAInvalidData() {
        List<Double> grades = Arrays.asList(3.5, 4.0);
        List<Integer> credits = Arrays.asList(3, 4);

        FakeStudent student = new FakeStudent("S001", "John", "john@test.com", 20, "CS");
        student.setGpaToReturn(-1.0);
        studentRepository.addStudent(student);

        controller.calculateStudentGPA("S001", grades, credits);

        assertEquals("Invalid grades or credits", view.getLastMessage());
        assertFalse(studentRepository.wasStudentSaved());
    }

    @Test
    void testCheckAcademicStandingSuccess() {
        FakeStudent student = new FakeStudent("S001", "John", "john@test.com", 20, "CS");
        student.setGpa(3.5);
        student.setCreditHours(60);
        student.setAcademicStandingToReturn("Good Standing");
        studentRepository.addStudent(student);

        controller.checkAcademicStanding("S001");

        assertEquals("Good Standing", view.getAcademicStandingDisplayed());
    }

    @Test
    void testCheckAcademicStandingStudentNotFound() {
        controller.checkAcademicStanding("S999");

        assertEquals("Student not found", view.getLastMessage());
        assertNull(view.getAcademicStandingDisplayed());
    }

    @Test
    void testCalculateTuitionInStateWithScholarship() {
        FakeStudent student = new FakeStudent("S001", "John", "john@test.com", 20, "CS");
        student.setCreditHours(15);
        student.setTuitionToReturn(5000.0);
        studentRepository.addStudent(student);

        controller.calculateTuition("S001", true, true);

        assertEquals(5000.0, view.getTuitionDisplayed());
    }

    @Test
    void testCalculateTuitionOutOfStateNoScholarship() {
        FakeStudent student = new FakeStudent("S001", "John", "john@test.com", 20, "CS");
        student.setCreditHours(12);
        student.setTuitionToReturn(15000.0);
        studentRepository.addStudent(student);

        controller.calculateTuition("S001", false, false);

        assertEquals(15000.0, view.getTuitionDisplayed());
    }

    @Test
    void testCalculateTuitionStudentNotFound() {
        controller.calculateTuition("S999", true, false);

        assertEquals("Student not found", view.getLastMessage());
        assertEquals(0.0, view.getTuitionDisplayed());
    }

    // Test Doubles
    private static class StubEnrollmentService extends EnrollmentService {
        private boolean studentAdded = false;
        private boolean enrollmentResult;
        private String studentIdForEnrollment;
        private String courseIdForEnrollment;
        private StudentRepository studentRepository;

        public void setEnrollmentResult(boolean result) {
            this.enrollmentResult = result;
        }

        public void setStudentRepository(StudentRepository repo) {
            this.studentRepository = repo;
        }

        @Override
        public void addStudent(Student student) {
            this.studentAdded = true;
        }

        @Override
        public boolean enrollStudent(String studentId, String courseId) {
            this.studentIdForEnrollment = studentId;
            this.courseIdForEnrollment = courseId;
            return enrollmentResult;
        }

        @Override
        public StudentRepository getStudentRepository() {
            return studentRepository;
        }

        public boolean isStudentAdded() { return studentAdded; }
        public String getStudentIdForEnrollment() { return studentIdForEnrollment; }
        public String getCourseIdForEnrollment() { return courseIdForEnrollment; }
    }

    private static class StubFinancialService extends FinancialService {
        // No methods needed for current tests
    }

    private static class SpyStudentView extends StudentView {
        private String lastMessage;
        private double gpaDisplayed;
        private String academicStandingDisplayed;
        private double tuitionDisplayed;

        @Override
        public void displayMessage(String message) {
            this.lastMessage = message;
        }

        @Override
        public void displayGPA(double gpa) {
            this.gpaDisplayed = gpa;
        }

        @Override
        public void displayAcademicStanding(String standing) {
            this.academicStandingDisplayed = standing;
        }

        @Override
        public void displayTuition(double tuition) {
            this.tuitionDisplayed = tuition;
        }

        public String getLastMessage() { return lastMessage; }
        public double getGpaDisplayed() { return gpaDisplayed; }
        public String getAcademicStandingDisplayed() { return academicStandingDisplayed; }
        public double getTuitionDisplayed() { return tuitionDisplayed; }
    }

    private static class FakeStudentRepository extends StudentRepository {
        private Student student;
        private boolean studentSaved = false;

        public void addStudent(Student student) {
            this.student = student;
        }

        @Override
        public Optional<Student> findById(String id) {
            if (student != null && student.getStudentId().equals(id)) {
                return Optional.of(student);
            }
            return Optional.empty();
        }

        @Override
        public boolean save(Student student) {
            this.studentSaved = true;
            this.student = student;
            return false;
        }

        public boolean wasStudentSaved() { return studentSaved; }
    }

    private static class FakeStudent extends Student {
        private double gpaToReturn;
        private String academicStandingToReturn;
        private double tuitionToReturn;

        public FakeStudent(String id, String name, String email, int age, String major) {
            super(id, name, email, age, major);
        }

        public void setGpaToReturn(double gpa) {
            this.gpaToReturn = gpa;
        }

        public void setAcademicStandingToReturn(String standing) {
            this.academicStandingToReturn = standing;
        }

        public void setTuitionToReturn(double tuition) {
            this.tuitionToReturn = tuition;
        }

        @Override
        public double calculateGPA(List<Double> grades, List<Integer> credits) {
            return gpaToReturn;
        }

        @Override
        public String determineAcademicStanding(double gpa, int creditHours) {
            return academicStandingToReturn;
        }

        @Override
        public double calculateTuitionFees(int creditHours, boolean isInState, boolean hasScholarship) {
            return tuitionToReturn;
        }
    }
}