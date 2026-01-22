package Tests.Integration_Testing;

import edu.university.main.model.Student;
import edu.university.main.repository.StudentRepository;
import edu.university.main.service.StudentManagementService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class StudentManagementServiceIntegrationTest {

    // ========== CREATE TESTS ==========

    @Test
    void testCreateStudentSuccessfully() {
        StudentRepository studentRepository = new StudentRepository();
        StudentManagementService studentManagementService = new StudentManagementService(studentRepository);

        boolean created = studentManagementService.createStudent("S001", "Alice Benson", "alice@edu.com", 20, "CS");

        assertTrue(created);
        assertTrue(studentRepository.exists("S001"));
    }

    @Test
    void testCreateStudentWithNullId() {
        StudentRepository studentRepository = new StudentRepository();
        StudentManagementService studentManagementService = new StudentManagementService(studentRepository);

        boolean created = studentManagementService.createStudent(null, "Alice", "alice@edu.com", 20, "CS");

        assertFalse(created);
    }

    @Test
    void testCreateStudentWithEmptyId() {
        StudentRepository studentRepository = new StudentRepository();
        StudentManagementService studentManagementService = new StudentManagementService(studentRepository);

        boolean created = studentManagementService.createStudent("", "Alice", "alice@edu.com", 20, "CS");

        assertFalse(created);
    }

    @Test
    void testCreateStudentWithNullName() {
        StudentRepository studentRepository = new StudentRepository();
        StudentManagementService studentManagementService = new StudentManagementService(studentRepository);

        boolean created = studentManagementService.createStudent("S001", null, "alice@edu.com", 20, "CS");

        assertFalse(created);
    }

    @Test
    void testCreateStudentWithEmptyName() {
        StudentRepository studentRepository = new StudentRepository();
        StudentManagementService studentManagementService = new StudentManagementService(studentRepository);

        boolean created = studentManagementService.createStudent("S001", "", "alice@edu.com", 20, "CS");

        assertFalse(created);
    }

    @Test
    void testCreateStudentDuplicateId() {
        StudentRepository studentRepository = new StudentRepository();
        StudentManagementService studentManagementService = new StudentManagementService(studentRepository);

        studentManagementService.createStudent("S001", "Alice", "alice@edu.com", 20, "CS");
        boolean created = studentManagementService.createStudent("S001", "Arjan", "Arjan@edu.com", 21, "CS");

        assertFalse(created);
    }

    @Test
    void testCreateMultipleStudents() {
        StudentRepository studentRepository = new StudentRepository();
        StudentManagementService studentManagementService = new StudentManagementService(studentRepository);

        boolean created1 = studentManagementService.createStudent("S001", "Alice", "alice@edu.com", 20, "CS");
        boolean created2 = studentManagementService.createStudent("S002", "Arjan", "Arjan@edu.com", 21, "CS");
        boolean created3 = studentManagementService.createStudent("S003", "Charlie", "charlie@edu.com", 22, "CS");

        assertTrue(created1);
        assertTrue(created2);
        assertTrue(created3);
        assertEquals(3, studentManagementService.getTotalStudents());
    }

    // ========== READ TESTS ==========

    @Test
    void testGetStudentById() {
        StudentRepository studentRepository = new StudentRepository();
        StudentManagementService studentManagementService = new StudentManagementService(studentRepository);

        studentManagementService.createStudent("S001", "Alice", "alice@edu.com", 20, "CS");

        Optional<Student> student = studentManagementService.getStudent("S001");

        assertTrue(student.isPresent());
        assertEquals("Alice", student.get().getName());
        assertEquals("alice@edu.com", student.get().getEmail());
    }

    @Test
    void testGetStudentNotFound() {
        StudentRepository studentRepository = new StudentRepository();
        StudentManagementService studentManagementService = new StudentManagementService(studentRepository);

        Optional<Student> student = studentManagementService.getStudent("NONEXISTENT");

        assertFalse(student.isPresent());
    }

    @Test
    void testGetAllStudents() {
        StudentRepository studentRepository = new StudentRepository();
        StudentManagementService studentManagementService = new StudentManagementService(studentRepository);

        studentManagementService.createStudent("S001", "Alice", "alice@edu.com", 20, "CS");
        studentManagementService.createStudent("S002", "Arjan", "Arjan@edu.com", 21, "CS");
        studentManagementService.createStudent("S003", "Charlie", "charlie@edu.com", 22, "CS");

        List<Student> students = studentManagementService.getAllStudents();

        assertEquals(3, students.size());
    }

    @Test
    void testGetAllStudentsEmpty() {
        StudentRepository studentRepository = new StudentRepository();
        StudentManagementService studentManagementService = new StudentManagementService(studentRepository);

        List<Student> students = studentManagementService.getAllStudents();

        assertEquals(0, students.size());
    }

    @Test
    void testGetStudentsByMajor() {
        StudentRepository studentRepository = new StudentRepository();
        StudentManagementService studentManagementService = new StudentManagementService(studentRepository);

        studentManagementService.createStudent("S001", "Alice", "alice@edu.com", 20, "CS");
        studentManagementService.createStudent("S002", "Arjan", "Arjan@edu.com", 21, "CS");
        studentManagementService.createStudent("S003", "Charlie", "charlie@edu.com", 22, "MATH");

        List<Student> csStudents = studentManagementService.getStudentsByMajor("CS");

        assertEquals(2, csStudents.size());
        assertTrue(csStudents.stream().allMatch(s -> "CS".equals(s.getMajor())));
    }

    @Test
    void testGetStudentsByMajorNoMatch() {
        StudentRepository studentRepository = new StudentRepository();
        StudentManagementService studentManagementService = new StudentManagementService(studentRepository);

        studentManagementService.createStudent("S001", "Alice", "alice@edu.com", 20, "CS");

        List<Student> students = studentManagementService.getStudentsByMajor("PHYSICS");

        assertEquals(0, students.size());
    }

    @Test
    void testSearchStudentsByName() {
        StudentRepository studentRepository = new StudentRepository();
        StudentManagementService studentManagementService = new StudentManagementService(studentRepository);

        studentManagementService.createStudent("S001", "Alice Smith", "alice@edu.com", 20, "CS");
        studentManagementService.createStudent("S002", "Arjan M", "Arjan@edu.com", 21, "CS");
        studentManagementService.createStudent("S003", "Charlie Smith", "charlie@edu.com", 22, "CS");

        List<Student> results = studentManagementService.searchStudentsByName("Smith");

        assertEquals(2, results.size());
        assertTrue(results.stream().allMatch(s -> s.getName().contains("Smith")));
    }

    @Test
    void testSearchStudentsByNameEmpty() {
        StudentRepository studentRepository = new StudentRepository();
        StudentManagementService studentManagementService = new StudentManagementService(studentRepository);

        studentManagementService.createStudent("S001", "Alice", "alice@edu.com", 20, "CS");

        List<Student> results = studentManagementService.searchStudentsByName("NonExistent");

        assertEquals(0, results.size());
    }

    // ========== UPDATE TESTS ==========

    @Test
    void testUpdateStudentSuccessfully() {
        StudentRepository studentRepository = new StudentRepository();
        StudentManagementService studentManagementService = new StudentManagementService(studentRepository);

        studentManagementService.createStudent("S001", "Alice", "alice@edu.com", 20, "CS");
        Optional<Student> studentOpt = studentManagementService.getStudent("S001");
        Student student = studentOpt.get();
        student.setName("Alice Updated");
        student.setGpa(3.8);

        boolean updated = studentManagementService.updateStudent(student);

        assertTrue(updated);
        assertEquals("Alice Updated", studentManagementService.getStudent("S001").get().getName());
        assertEquals(3.8, studentManagementService.getStudent("S001").get().getGpa());
    }

    @Test
    void testUpdateNonexistentStudent() {
        StudentRepository studentRepository = new StudentRepository();
        StudentManagementService studentManagementService = new StudentManagementService(studentRepository);

        Student student = new Student("NONEXISTENT", "Alice", "alice@edu.com", 20, "CS");

        boolean updated = studentManagementService.updateStudent(student);

        assertFalse(updated);
    }

    @Test
    void testUpdateStudentWithNull() {
        StudentRepository studentRepository = new StudentRepository();
        StudentManagementService studentManagementService = new StudentManagementService(studentRepository);

        boolean updated = studentManagementService.updateStudent(null);

        assertFalse(updated);
    }

    @Test
    void testUpdateStudentEmail() {
        StudentRepository studentRepository = new StudentRepository();
        StudentManagementService studentManagementService = new StudentManagementService(studentRepository);

        studentManagementService.createStudent("S001", "Alice", "alice@edu.com", 20, "CS");

        boolean updated = studentManagementService.updateStudentEmail("S001", "newemail@edu.com");

        assertTrue(updated);
        assertEquals("newemail@edu.com", studentManagementService.getStudent("S001").get().getEmail());
    }

    @Test
    void testUpdateStudentEmailNonexistent() {
        StudentRepository studentRepository = new StudentRepository();
        StudentManagementService studentManagementService = new StudentManagementService(studentRepository);

        boolean updated = studentManagementService.updateStudentEmail("NONEXISTENT", "newemail@edu.com");

        assertFalse(updated);
    }

    @Test
    void testUpdateStudentGPA() {
        StudentRepository studentRepository = new StudentRepository();
        StudentManagementService studentManagementService = new StudentManagementService(studentRepository);

        studentManagementService.createStudent("S001", "Alice", "alice@edu.com", 20, "CS");

        boolean updated = studentManagementService.updateStudentGPA("S001", 3.75);

        assertTrue(updated);
        assertEquals(3.75, studentManagementService.getStudent("S001").get().getGpa());
    }

    @Test
    void testUpdateStudentGPAInvalid() {
        StudentRepository studentRepository = new StudentRepository();
        StudentManagementService studentManagementService = new StudentManagementService(studentRepository);

        studentManagementService.createStudent("S001", "Alice", "alice@edu.com", 20, "CS");

        boolean updated = studentManagementService.updateStudentGPA("S001", 5.0);

        assertFalse(updated);
    }

    @Test
    void testUpdateStudentGPANegative() {
        StudentRepository studentRepository = new StudentRepository();
        StudentManagementService studentManagementService = new StudentManagementService(studentRepository);

        studentManagementService.createStudent("S001", "Alice", "alice@edu.com", 20, "CS");

        boolean updated = studentManagementService.updateStudentGPA("S001", -1.0);

        assertFalse(updated);
    }

    @Test
    void testUpdateStudentGPANonexistent() {
        StudentRepository studentRepository = new StudentRepository();
        StudentManagementService studentManagementService = new StudentManagementService(studentRepository);

        boolean updated = studentManagementService.updateStudentGPA("NONEXISTENT", 3.5);

        assertFalse(updated);
    }

    // ========== DELETE TESTS ==========

    @Test
    void testDeleteStudentSuccessfully() {
        StudentRepository studentRepository = new StudentRepository();
        StudentManagementService studentManagementService = new StudentManagementService(studentRepository);

        studentManagementService.createStudent("S001", "Alice", "alice@edu.com", 20, "CS");

        boolean deleted = studentManagementService.deleteStudent("S001");

        assertTrue(deleted);
        assertFalse(studentRepository.exists("S001"));
    }

    @Test
    void testDeleteNonexistentStudent() {
        StudentRepository studentRepository = new StudentRepository();
        StudentManagementService studentManagementService = new StudentManagementService(studentRepository);

        boolean deleted = studentManagementService.deleteStudent("NONEXISTENT");

        assertFalse(deleted);
    }

    @Test
    void testDeleteStudentsByMajor() {
        StudentRepository studentRepository = new StudentRepository();
        StudentManagementService studentManagementService = new StudentManagementService(studentRepository);

        studentManagementService.createStudent("S001", "Alice", "alice@edu.com", 20, "CS");
        studentManagementService.createStudent("S002", "Arjan", "Arjan@edu.com", 21, "CS");
        studentManagementService.createStudent("S003", "Charlie", "charlie@edu.com", 22, "MATH");

        int deleted = studentManagementService.deleteStudentsByMajor("CS");

        assertEquals(2, deleted);
        assertEquals(1, studentManagementService.getTotalStudents());
    }

    @Test
    void testDeleteStudentsByMajorNone() {
        StudentRepository studentRepository = new StudentRepository();
        StudentManagementService studentManagementService = new StudentManagementService(studentRepository);

        studentManagementService.createStudent("S001", "Alice", "alice@edu.com", 20, "CS");

        int deleted = studentManagementService.deleteStudentsByMajor("PHYSICS");

        assertEquals(0, deleted);
    }

    // ========== STATISTICS TESTS ==========

    @Test
    void testGetTotalStudents() {
        StudentRepository studentRepository = new StudentRepository();
        StudentManagementService studentManagementService = new StudentManagementService(studentRepository);

        studentManagementService.createStudent("S001", "Alice", "alice@edu.com", 20, "CS");
        studentManagementService.createStudent("S002", "Arjan", "Arjan@edu.com", 21, "CS");

        int total = studentManagementService.getTotalStudents();

        assertEquals(2, total);
    }

    @Test
    void testGetTotalStudentsEmpty() {
        StudentRepository studentRepository = new StudentRepository();
        StudentManagementService studentManagementService = new StudentManagementService(studentRepository);

        int total = studentManagementService.getTotalStudents();

        assertEquals(0, total);
    }

    @Test
    void testGetAverageGPA() {
        StudentRepository studentRepository = new StudentRepository();
        StudentManagementService studentManagementService = new StudentManagementService(studentRepository);

        studentManagementService.createStudent("S001", "Alice", "alice@edu.com", 20, "CS");
        Student s1 = studentManagementService.getStudent("S001").get();
        s1.setGpa(3.5);
        studentManagementService.updateStudent(s1);

        studentManagementService.createStudent("S002", "Arjan", "Arjan@edu.com", 21, "CS");
        Student s2 = studentManagementService.getStudent("S002").get();
        s2.setGpa(3.0);
        studentManagementService.updateStudent(s2);

        double avgGpa = studentManagementService.getAverageGPA();

        assertEquals(3.25, avgGpa, 0.01);
    }

    @Test
    void testGetAverageGPAEmpty() {
        StudentRepository studentRepository = new StudentRepository();
        StudentManagementService studentManagementService = new StudentManagementService(studentRepository);

        double avgGpa = studentManagementService.getAverageGPA();

        assertEquals(0.0, avgGpa);
    }

    @Test
    void testHasStudentsTrue() {
        StudentRepository studentRepository = new StudentRepository();
        StudentManagementService studentManagementService = new StudentManagementService(studentRepository);

        studentManagementService.createStudent("S001", "Alice", "alice@edu.com", 20, "CS");

        boolean hasStudents = studentManagementService.hasStudents();

        assertTrue(hasStudents);
    }

    @Test
    void testHasStudentsFalse() {
        StudentRepository studentRepository = new StudentRepository();
        StudentManagementService studentManagementService = new StudentManagementService(studentRepository);

        boolean hasStudents = studentManagementService.hasStudents();

        assertFalse(hasStudents);
    }
}