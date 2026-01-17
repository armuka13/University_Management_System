package Tests.Integration_Tests;

import edu.university.main.model.Student;
import edu.university.main.model.Course;
import edu.university.main.repository.StudentRepository;
import edu.university.main.repository.CourseRepository;
import edu.university.main.service.EnrollmentService;
import edu.university.main.service.CourseManagementService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EndToEndIntegrationTest {

    @Test
    void testVerticalSlice_StudentRegistrationAndEnrollment() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService = new EnrollmentService(studentRepository, courseRepository);
        CourseManagementService courseManagementService = new CourseManagementService(courseRepository);

        Student student = new Student("S001", "Albi", "albi@edu.com", 20, "SWE");
        student.setGpa(3.5);
        boolean studentCreated = enrollmentService.createStudent(student);

        boolean courseCreated = courseManagementService.createCourse("SWE303", "Software Testing", "Mr. Gjerazi", 3, 30, "SWE");

        boolean enrolled = enrollmentService.enrollStudent("S001", "SWE303");

        assertTrue(studentCreated);
        assertTrue(courseCreated);
        assertTrue(enrolled);

        Student retrievedStudent = enrollmentService.getStudentById("S001").get();
        Course retrievedCourse = courseManagementService.getCourse("SWE303").get();

        assertEquals(1, retrievedStudent.getEnrolledCourseIds().size());
        assertEquals(3, retrievedStudent.getCreditHours());
        assertEquals(1, retrievedCourse.getCurrentEnrollment());
        assertTrue(retrievedCourse.getEnrolledStudentIds().contains("S001"));
    }

    @Test
    void testVerticalSlice_CourseCapacityAndEnrollmentRejection() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService = new EnrollmentService(studentRepository, courseRepository);
        CourseManagementService courseManagementService = new CourseManagementService(courseRepository);

        boolean courseCreated = courseManagementService.createCourse("SWE303", "Software Testing", "Mr. Gjerazi", 3, 2, "SWE");
        assertTrue(courseCreated);

        Student student1 = new Student("S001", "Albi", "albi@edu.com", 20, "SWE");
        student1.setGpa(3.5);
        Student student2 = new Student("S002", "Tomi", "tomi@edu.com", 21, "SWE");
        student2.setGpa(3.5);
        Student student3 = new Student("S003", "Monika", "monika@edu.com", 22, "SWE");
        student3.setGpa(3.5);

        enrollmentService.createStudent(student1);
        enrollmentService.createStudent(student2);
        enrollmentService.createStudent(student3);

        boolean enrolled1 = enrollmentService.enrollStudent("S001", "SWE303");
        boolean enrolled2 = enrollmentService.enrollStudent("S002", "SWE303");
        boolean enrolled3 = enrollmentService.enrollStudent("S003", "SWE303");

        assertTrue(enrolled1);
        assertTrue(enrolled2);
        assertFalse(enrolled3);
        assertEquals(2, courseManagementService.getCourse("SWE303").get().getCurrentEnrollment());
    }

    @Test
    void testVerticalSlice_DropCourseAndReEnroll() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService = new EnrollmentService(studentRepository, courseRepository);
        CourseManagementService courseManagementService = new CourseManagementService(courseRepository);

        Student student = new Student("S001", "Albi", "albi@edu.com", 20, "SWE");
        student.setGpa(3.5);
        enrollmentService.createStudent(student);

        courseManagementService.createCourse("SWE303", "Software Testing", "Mr. Gjerazi", 3, 30, "SWE");

        enrollmentService.enrollStudent("S001", "SWE303");
        assertEquals(3, enrollmentService.getStudentById("S001").get().getCreditHours());

        boolean dropped = enrollmentService.dropCourse("S001", "SWE303");
        assertTrue(dropped);
        assertEquals(0, enrollmentService.getStudentById("S001").get().getCreditHours());

        boolean reEnrolled = enrollmentService.enrollStudent("S001", "SWE303");
        assertTrue(reEnrolled);
        assertEquals(3, enrollmentService.getStudentById("S001").get().getCreditHours());
    }

    @Test
    void testVerticalSlice_CreditLimitEnforcement() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService = new EnrollmentService(studentRepository, courseRepository);
        CourseManagementService courseManagementService = new CourseManagementService(courseRepository);

        Student student = new Student("S001", "Albi", "albi@edu.com", 20, "SWE");
        student.setGpa(1.5);
        student.setCreditHours(9);
        enrollmentService.createStudent(student);

        courseManagementService.createCourse("SWE303", "Software Testing", "Mr. Gjerazi", 3, 30, "SWE");
        courseManagementService.createCourse("CS102", "Data Structures", "Dr. Skuka", 3, 30, "SWE");

        boolean enrolled1 = enrollmentService.enrollStudent("S001", "SWE303");
        assertTrue(enrolled1);

        boolean enrolled2 = enrollmentService.enrollStudent("S001", "CS102");
        assertFalse(enrolled2);

        assertEquals(1, enrollmentService.getStudentById("S001").get().getEnrolledCourseIds().size());
    }

    @Test
    void testVerticalSlice_StudentDeletionRemovesEnrollments() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService = new EnrollmentService(studentRepository, courseRepository);
        CourseManagementService courseManagementService = new CourseManagementService(courseRepository);

        Student student = new Student("S001", "Albi", "albi@edu.com", 20, "SWE");
        student.setGpa(3.5);
        enrollmentService.createStudent(student);

        courseManagementService.createCourse("SWE303", "Software Testing", "Mr. Gjerazi", 3, 30, "SWE");
        courseManagementService.createCourse("CS102", "Data Structures", "Dr. Skuka", 3, 30, "SWE");

        enrollmentService.enrollStudent("S001", "SWE303");
        enrollmentService.enrollStudent("S001", "CS102");

        assertEquals(2, enrollmentService.getStudentById("S001").get().getEnrolledCourseIds().size());

        boolean deleted = enrollmentService.deleteStudent("S001");

        assertTrue(deleted);
        assertFalse(enrollmentService.getStudentById("S001").isPresent());
        assertEquals(0, courseManagementService.getCourse("SWE303").get().getCurrentEnrollment());
        assertEquals(0, courseManagementService.getCourse("CS102").get().getCurrentEnrollment());
    }
}