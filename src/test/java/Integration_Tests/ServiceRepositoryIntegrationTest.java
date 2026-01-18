package Integration_Tests;
import edu.university.main.model.Student;
import edu.university.main.model.Course;
import edu.university.main.repository.StudentRepository;
import edu.university.main.repository.CourseRepository;
import edu.university.main.service.EnrollmentService;
import edu.university.main.service.CourseManagementService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceRepositoryIntegrationTest {

    // ========== ENROLLMENT TESTS ==========

    @Test
    void testEnrollStudentSuccessfully() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService = new EnrollmentService(studentRepository, courseRepository);

        Student student = new Student("S001", "Albi", "albi@edu.com", 20, "SWE");
        student.setGpa(3.5);
        student.setCreditHours(0);
        studentRepository.save(student);

        Course course = new Course("SWE303", "Software Testing", "Mr. Gjerazi", 3, 30, "SWE");
        courseRepository.save(course);

        boolean enrolled = enrollmentService.enrollStudent("S001", "SWE303");

        assertTrue(enrolled);
        assertEquals(1, student.getEnrolledCourseIds().size());
        assertEquals(3, student.getCreditHours());
        assertEquals(1, course.getCurrentEnrollment());
        assertTrue(course.getEnrolledStudentIds().contains("S001"));
    }

    @Test
    void testEnrollmentFailsWhenCourseFull() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService = new EnrollmentService(studentRepository, courseRepository);

        Student student = new Student("S001", "Albi", "albi@edu.com", 20, "SWE");
        student.setGpa(3.5);
        student.setCreditHours(0);
        studentRepository.save(student);

        Course course = new Course("SWE303", "Software Testing", "Mr. Gjerazi", 3, 1, "SWE");
        course.setCurrentEnrollment(1);
        courseRepository.save(course);

        boolean enrolled = enrollmentService.enrollStudent("S001", "SWE303");

        assertFalse(enrolled);
        assertEquals(0, student.getEnrolledCourseIds().size());
    }

    @Test
    void testEnrollmentFailsWhenCreditLimitExceeded() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService = new EnrollmentService(studentRepository, courseRepository);

        Student student = new Student("S001", "Albi", "albi@edu.com", 20, "SWE");
        student.setGpa(1.5);
        student.setCreditHours(12);
        studentRepository.save(student);

        Course course = new Course("SWE303", "Software Testing", "Mr. Gjerazi", 3, 30, "SWE");
        courseRepository.save(course);

        boolean enrolled = enrollmentService.enrollStudent("S001", "SWE303");

        assertFalse(enrolled);
        assertEquals(0, student.getEnrolledCourseIds().size());
    }

    @Test
    void testDropCourseSuccessfully() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService = new EnrollmentService(studentRepository, courseRepository);

        Student student = new Student("S001", "Albi", "albi@edu.com", 20, "SWE");
        student.setGpa(3.5);
        student.setCreditHours(3);
        student.getEnrolledCourseIds().add("SWE303");
        studentRepository.save(student);

        Course course = new Course("SWE303", "Software Testing", "Mr. Gjerazi", 3, 30, "SWE");
        course.setCurrentEnrollment(1);
        course.getEnrolledStudentIds().add("S001");
        courseRepository.save(course);

        boolean dropped = enrollmentService.dropCourse("S001", "SWE303");

        assertTrue(dropped);
        assertEquals(0, student.getEnrolledCourseIds().size());
        assertEquals(0, student.getCreditHours());
        assertEquals(0, course.getCurrentEnrollment());
    }

    @Test
    void testDropCourseFailsIfNotEnrolled() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService = new EnrollmentService(studentRepository, courseRepository);

        Student student = new Student("S001", "Albi", "albi@edu.com", 20, "SWE");
        student.setGpa(3.5);
        studentRepository.save(student);

        Course course = new Course("SWE303", "Software Testing", "Mr. Gjerazi", 3, 30, "SWE");
        courseRepository.save(course);

        boolean dropped = enrollmentService.dropCourse("S001", "SWE303");

        assertFalse(dropped);
    }

    @Test
    void testEnrollmentPersistenceAcrossRepositories() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService = new EnrollmentService(studentRepository, courseRepository);

        Student student = new Student("S001", "Albi", "albi@edu.com", 20, "SWE");
        student.setGpa(3.5);
        student.setCreditHours(0);
        studentRepository.save(student);

        Course course = new Course("SWE303", "Software Testing", "Mr. Gjerazi", 3, 30, "SWE");
        courseRepository.save(course);

        enrollmentService.enrollStudent("S001", "SWE303");

        Student retrievedStudent = studentRepository.findById("S001").get();
        Course retrievedCourse = courseRepository.findById("SWE303").get();

        assertEquals(1, retrievedStudent.getEnrolledCourseIds().size());
        assertEquals(3, retrievedStudent.getCreditHours());
        assertEquals(1, retrievedCourse.getCurrentEnrollment());
    }

    @Test
    void testMultipleEnrollmentsUpdateBothRepositories() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService = new EnrollmentService(studentRepository, courseRepository);

        Student student1 = new Student("S001", "Albi", "albi@edu.com", 20, "SWE");
        student1.setGpa(3.5);
        student1.setCreditHours(0);
        studentRepository.save(student1);

        Student student2 = new Student("S002", "Tomi", "tomi@edu.com", 21, "SWE");
        student2.setGpa(3.2);
        student2.setCreditHours(0);
        studentRepository.save(student2);

        Course course = new Course("SWE303", "Software Testing", "Mr. Gjerazi", 3, 30, "SWE");
        courseRepository.save(course);

        enrollmentService.enrollStudent("S001", "SWE303");
        enrollmentService.enrollStudent("S002", "SWE303");

        Course retrievedCourse = courseRepository.findById("SWE303").get();
        assertEquals(2, retrievedCourse.getCurrentEnrollment());
        assertEquals(2, retrievedCourse.getEnrolledStudentIds().size());
    }

    @Test
    void testDeleteStudentRemovesEnrollments() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService = new EnrollmentService(studentRepository, courseRepository);

        Student student = new Student("S001", "Albi", "albi@edu.com", 20, "SWE");
        student.setGpa(3.5);
        student.setCreditHours(3);
        student.getEnrolledCourseIds().add("SWE303");
        studentRepository.save(student);

        Course course = new Course("SWE303", "Software Testing", "Mr. Gjerazi", 3, 30, "SWE");
        course.setCurrentEnrollment(1);
        course.getEnrolledStudentIds().add("S001");
        courseRepository.save(course);

        boolean deleted = enrollmentService.deleteStudent("S001");

        assertTrue(deleted);
        assertFalse(studentRepository.exists("S001"));
        assertEquals(0, courseRepository.findById("SWE303").get().getCurrentEnrollment());
    }

    @Test
    void testEnrollmentFailsWithInvalidStudent() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService = new EnrollmentService(studentRepository, courseRepository);

        Course course = new Course("SWE303", "Software Testing", "Mr. Gjerazi", 3, 30, "SWE");
        courseRepository.save(course);

        boolean enrolled = enrollmentService.enrollStudent("INVALID", "SWE303");

        assertFalse(enrolled);
    }

    @Test
    void testEnrollmentFailsWithInvalidCourse() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService = new EnrollmentService(studentRepository, courseRepository);

        Student student = new Student("S001", "Albi", "albi@edu.com", 20, "SWE");
        student.setGpa(3.5);
        studentRepository.save(student);

        boolean enrolled = enrollmentService.enrollStudent("S001", "INVALID");

        assertFalse(enrolled);
    }

    @Test
    void testDropCourseFailsWithInvalidStudent() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService = new EnrollmentService(studentRepository, courseRepository);

        Course course = new Course("SWE303", "Software Testing", "Mr. Gjerazi", 3, 30, "SWE");
        courseRepository.save(course);

        boolean dropped = enrollmentService.dropCourse("INVALID", "SWE303");

        assertFalse(dropped);
    }

// ========== ENROLLMENT WORKFLOW TESTS ==========

    @Test
    void testCompleteEnrollmentWorkflow() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService = new EnrollmentService(studentRepository, courseRepository);
        CourseManagementService courseManagementService = new CourseManagementService(courseRepository);

        Student student = new Student("S001", "Albi", "albi@edu.com", 20, "SWE");
        student.setGpa(3.5);
        enrollmentService.createStudent(student);

        courseManagementService.createCourse("SWE303", "Software Testing", "Mr. Gjerazi", 3, 30, "SWE");

        boolean enrolled = enrollmentService.enrollStudent("S001", "SWE303");

        assertTrue(enrolled);
        Student updated = enrollmentService.getStudentById("S001").get();
        assertEquals(1, updated.getEnrolledCourseIds().size());
        assertEquals(3, updated.getCreditHours());
    }

    @Test
    void testCalculateEnrollmentRate() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService = new EnrollmentService(studentRepository, courseRepository);

        double rate = enrollmentService.calculateEnrollmentRate(15, 30);

        assertEquals(50.0, rate);
    }

    @Test
    void testCalculateEnrollmentRateInvalid() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService = new EnrollmentService(studentRepository, courseRepository);

        double rate = enrollmentService.calculateEnrollmentRate(40, 30);

        assertEquals(-1.0, rate);
    }
}