package Tests.Unit_Testing;

import edu.university.main.model.Student;
import edu.university.main.model.Course;
import edu.university.main.model.Grade;
import edu.university.main.repository.CourseRepository;
import edu.university.main.repository.GradeRepository;
import edu.university.main.repository.Stub.StudentRepositoryStub;
import edu.university.main.repository.Stub.CourseRepositoryStub;
import edu.university.main.repository.Stub.GradeRepositoryStub;
import edu.university.main.repository.StudentRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RepositoryUnitTest {

    @Test
    void testStudentRepositorySaveAndRetrieve() {
        StudentRepository studentRepository = new StudentRepositoryStub();
        Student student = new Student("S001", "Albi", "albi@edu.com", 20, "SWE");
        student.setGpa(3.5);
        student.setCreditHours(12);

        boolean saved = studentRepository.save(student);
        Student retrieved = studentRepository.findById("S001").orElse(null);

        assertTrue(saved);
        assertNotNull(retrieved);
        assertEquals("Albi", retrieved.getName());
        assertEquals(3.5, retrieved.getGpa());
    }

    @Test
    void testCourseRepositorySaveAndRetrieve() {
        CourseRepository courseRepository = new CourseRepositoryStub();
        Course course = new Course("SWE303", "Software Testing", "Mr. Gjerazi", 3, 30, "SWE");

        boolean saved = courseRepository.save(course);
        Course retrieved = courseRepository.findById("SWE303").orElse(null);

        assertTrue(saved);
        assertNotNull(retrieved);
        assertEquals("Software Testing", retrieved.getCourseName());
        assertEquals(30, retrieved.getMaxCapacity());
    }

    @Test
    void testGradeRepositorySaveAndRetrieve() {
        GradeRepository gradeRepository = new GradeRepositoryStub();
        Grade grade = new Grade("S001", "SWE303", 85.5, 3, 2025);

        boolean saved = gradeRepository.save(grade);
        Grade retrieved = gradeRepository.findByStudentAndCourse("S001", "SWE303");

        assertTrue(saved);
        assertNotNull(retrieved);
        assertEquals(85.5, retrieved.getNumericGrade());
        assertEquals("B", grade.calculateLetterGrade(85.5));
    }

    @Test
    void testFindStudentsByGpaRange() {
        StudentRepository studentRepository = new StudentRepositoryStub();
        Student student1 = new Student("S001", "Arlin", "Arlin@edu.com", 20, "SWE");
        student1.setGpa(3.5);

        studentRepository.save(student1);

        var result = studentRepository.findByGpaRange(3.0, 3.7);

        assertEquals(1, result.size());
        assertTrue(result.stream().anyMatch(s -> s.getStudentId().equals("S001")));
    }

    @Test
    void testFindCoursesByDepartment() {
        CourseRepository courseRepository = new CourseRepositoryStub();
        Course course1 = new Course("SWE303", "Software Testing", "Mr. Gjerazi", 3, 30, "SWE");
        Course course2 = new Course("CS201", "Data Structures", "Dr. Skuka", 3, 25, "SWE");

        courseRepository.save(course1);
        courseRepository.save(course2);

        var result = courseRepository.findByDepartment("SWE");

        assertEquals(2, result.size());
    }

    @Test
    void testUpdateStudent() {
        StudentRepository studentRepository = new StudentRepositoryStub();
        Student student = new Student("S001", "Arlin", "Arlin@edu.com", 20, "SWE");
        studentRepository.save(student);

        student.setGpa(3.8);
        boolean updated = studentRepository.update(student);

        assertTrue(updated);
    }

    @Test
    void testDeleteStudent() {
        StudentRepository studentRepository = new StudentRepositoryStub();
        Student student = new Student("S001", "Arlin", "Arlin@edu.com", 20, "SWE");
        studentRepository.save(student);

        boolean deleted = studentRepository.delete("S001");

        assertTrue(deleted);
    }

    @Test
    void testStudentNotFound() {
        StudentRepository studentRepository = new StudentRepositoryStub();

        Student result = studentRepository.findById("INVALID").orElse(null);

        assertNull(result);
    }
}