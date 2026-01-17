package Tests.Integration_Tests;

import edu.university.main.model.Student;
import edu.university.main.model.Course;
import edu.university.main.repository.StudentRepository;
import edu.university.main.repository.CourseRepository;
import edu.university.main.service.CourseManagementService;
import edu.university.main.service.EnrollmentService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceRepositoryIntegrationTest {

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

        Student student2 = new Student("S002", "Tomi", "tomi@edu.com", 20, "SWE");
        student2.setGpa(3.5);
        student2.setCreditHours(0);
        studentRepository.save(student2);

        Course course = new Course("SWE303", "Software Testing", "Mr. Gjerazi", 3, 1, "SWE");
        course.setCurrentEnrollment(1);
        courseRepository.save(course);

        enrollmentService.enrollStudent("S001", "SWE303");
        boolean enrolled = enrollmentService.enrollStudent("S002", "SWE303");


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
    void testCreateStudentThroughService() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService = new EnrollmentService(studentRepository, courseRepository);

        Student student = new Student("S001", "Albi", "albi@edu.com", 20, "SWE");
        boolean created = enrollmentService.createStudent(student);

        assertTrue(created);
        Optional<Student> retrieved = enrollmentService.getStudentById("S001");
        assertTrue(retrieved.isPresent());
        assertEquals("Albi", retrieved.get().getName());
    }

    @Test
    void testUpdateStudentThroughService() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService = new EnrollmentService(studentRepository, courseRepository);

        Student student = new Student("S001", "Albi", "albi@edu.com", 20, "SWE");
        enrollmentService.createStudent(student);

        student.setGpa(3.8);
        boolean updated = enrollmentService.updateStudent(student);

        assertTrue(updated);
        assertEquals(3.8, enrollmentService.getStudentById("S001").get().getGpa());
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
    
    
    

    @Test
    void testCourseCreationAndRetrieval() {
        CourseRepository courseRepository = new CourseRepository();
        CourseManagementService cms = new CourseManagementService(courseRepository);

        boolean created = cms.createCourse("SWE303", "SoftWare Testing", "Mr. Gjerazi", 3, 30, "SWE");
        assertTrue(created);
        assertTrue(courseRepository.exists("SWE303"));

        Course course = cms.getCourse("SWE303").get();
        assertEquals("SoftWare Testing", course.getCourseName());
        assertEquals("Mr. Gjerazi", course.getInstructor());
    }

    @Test
    void testCreateCourseValidation() {
        CourseRepository courseRepository = new CourseRepository();
        CourseManagementService courseManagementService = new CourseManagementService(courseRepository);

        boolean created = courseManagementService.createCourse("SWE303", "Intro to SWE", "Mr. Gjerazi", 3, 30, "SWE");

        assertTrue(created);
        assertTrue(courseRepository.exists("SWE303"));
    }

    @Test
    void testCreateCourseDuplicateFails() {
        CourseRepository courseRepository = new CourseRepository();
        CourseManagementService courseManagementService = new CourseManagementService(courseRepository);

        courseManagementService.createCourse("SWE303", "Intro to SWE", "Mr. Gjerazi", 3, 30, "SWE");
        boolean created = courseManagementService.createCourse("SWE303", "Duplicate", "Dr. Jones", 3, 30, "SWE");

        assertFalse(created);
    }

    @Test
    void testCreateCourseInvalidInput() {
        CourseRepository courseRepository = new CourseRepository();
        CourseManagementService courseManagementService = new CourseManagementService(courseRepository);

        boolean created = courseManagementService.createCourse("", "Intro to SWE", "Mr. Gjerazi", 3, 30, "SWE");

        assertFalse(created);
    }

    @Test
    void testUpdateCourseCapacity() {
        CourseRepository courseRepository = new CourseRepository();
        CourseManagementService courseManagementService = new CourseManagementService(courseRepository);

        courseManagementService.createCourse("SWE303", "Intro to SWE", "Mr. Gjerazi", 3, 30, "SWE");
        boolean updated = courseManagementService.updateCourseCapacity("SWE303", 50);

        assertTrue(updated);
        assertEquals(50, courseManagementService.getCourse("SWE303").get().getMaxCapacity());
    }

    @Test
    void testUpdateCourseCapacityBelowEnrollmentFails() {
        CourseRepository courseRepository = new CourseRepository();
        CourseManagementService courseManagementService = new CourseManagementService(courseRepository);

        courseManagementService.createCourse("SWE303", "Intro to SWE", "Mr. Gjerazi", 3, 30, "SWE");
        Course course = courseRepository.findById("SWE303").get();
        course.setCurrentEnrollment(40);
        courseRepository.update(course);

        boolean updated = courseManagementService.updateCourseCapacity("SWE303", 30);

        assertFalse(updated);
    }

    @Test
    void testUpdateCourseInstructor() {
        CourseRepository courseRepository = new CourseRepository();
        CourseManagementService courseManagementService = new CourseManagementService(courseRepository);

        courseManagementService.createCourse("SWE303", "Intro to SWE", "Mr. Gjerazi", 3, 30, "SWE");
        boolean updated = courseManagementService.updateCourseInstructor("SWE303", "Dr. Jones");

        assertTrue(updated);
        assertEquals("Dr. Jones", courseManagementService.getCourse("SWE303").get().getInstructor());
    }

    @Test
    void testGetCoursesByDepartment() {
        CourseRepository courseRepository = new CourseRepository();
        CourseManagementService courseManagementService = new CourseManagementService(courseRepository);

        courseManagementService.createCourse("SWE303", "Intro to SWE", "Mr. Gjerazi", 3, 30, "SWE");
        courseManagementService.createCourse("CS102", "Data Structures", "Dr. Jones", 3, 25, "SWE");
        courseManagementService.createCourse("MATH101", "Calculus", "Dr. Brown", 3, 35, "MATH");

        List<Course> csCourses = courseManagementService.getCoursesByDepartment("SWE");

        assertEquals(2, csCourses.size());
        assertTrue(csCourses.stream().allMatch(c -> "SWE".equals(c.getDepartment())));
    }

    @Test
    void testDeleteCourse() {
        CourseRepository courseRepository = new CourseRepository();
        CourseManagementService courseManagementService = new CourseManagementService(courseRepository);

        courseManagementService.createCourse("SWE303", "Intro to SWE", "Mr. Gjerazi", 3, 30, "SWE");
        boolean deleted = courseManagementService.deleteCourse("SWE303");

        assertTrue(deleted);
        assertFalse(courseRepository.exists("SWE303"));
    }

    @Test
    void testDeleteCoursesByDepartment() {
        CourseRepository courseRepository = new CourseRepository();
        CourseManagementService courseManagementService = new CourseManagementService(courseRepository);

        courseManagementService.createCourse("SWE303", "Intro to SWE", "Mr. Gjerazi", 3, 30, "SWE");
        courseManagementService.createCourse("CS102", "Data Structures", "Dr. Jones", 3, 25, "SWE");
        courseManagementService.createCourse("MATH101", "Calculus", "Dr. Brown", 3, 35, "MATH");

        int deleted = courseManagementService.deleteCoursesByDepartment("SWE");

        assertEquals(2, deleted);
        assertEquals(1, courseManagementService.getTotalCourses());
    }


    @Test
    void testGetAllStudents() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService = new EnrollmentService(studentRepository, courseRepository);

        enrollmentService.createStudent(new Student("S001", "Albi", "albi@edu.com", 20, "SWE"));

        List<Student> students = enrollmentService.getAllStudents();

        assertEquals(1, students.size());
    }

    @Test
    void testSearchStudents() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService = new EnrollmentService(studentRepository, courseRepository);

        enrollmentService.createStudent(new Student("S001", "Albi", "albi@edu.com", 20, "SWE"));
        enrollmentService.createStudent(new Student("S002", "Tomi", "tomi@edu.com", 21, "SWE"));

        List<Student> results = enrollmentService.searchStudents("Albi");

        assertEquals(1, results.size());
        assertEquals("Albi", results.get(0).getName());
    }

    @Test
    void testCompleteEnrollmentWorkflow() {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentService enrollmentService = new EnrollmentService(studentRepository, courseRepository);
        CourseManagementService courseManagementService = new CourseManagementService(courseRepository);

        Student student = new Student("S001", "Albi", "albi@edu.com", 20, "SWE");
        student.setGpa(3.5);
        enrollmentService.createStudent(student);

        courseManagementService.createCourse("SWE303", "Intro to SWE", "Mr. Gjerazi", 3, 30, "SWE");

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