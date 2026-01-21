package Tests.Unit_Testing.RepositoryTest;

import edu.university.main.model.Student;
import edu.university.main.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class StudentRepositoryTest {
    private StudentRepository repository;
    private Student student1;
    private Student student2;
    private Student student3;

    @BeforeEach
    void setUp() {
        repository = new StudentRepository();

        student1 = new Student("S001", "ArlinB", "Arlin@university.edu", 20, "Computer Science");
        student1.setGpa(3.5);
        student1.setCreditHours(60);

        student2 = new Student("S002", "Eglis B", "Eglis@university.edu", 21, "Mathematics");
        student2.setGpa(3.8);
        student2.setCreditHours(75);

        student3 = new Student("S003", "Arjan M", "Arjan@university.edu", 19, "Computer Science");
        student3.setGpa(2.9);
        student3.setCreditHours(45);
    }

    // Save Tests
    @Test
    @DisplayName("Should save a valid student successfully")
    void testSaveValidStudent() {
        assertTrue(repository.save(student1));
        assertEquals(1, repository.count());
    }

    @Test
    @DisplayName("Should not save null student")
    void testSaveNullStudent() {
        assertFalse(repository.save(null));
        assertEquals(0, repository.count());
    }

    @Test
    @DisplayName("Should not save student with null ID")
    void testSaveStudentWithNullId() {
        Student invalidStudent = new Student(null, "Test", "test@test.com", 20, "CS");
        assertFalse(repository.save(invalidStudent));
    }

    @Test
    @DisplayName("Should not save student with empty ID")
    void testSaveStudentWithEmptyId() {
        Student invalidStudent = new Student("", "Test", "test@test.com", 20, "CS");
        assertFalse(repository.save(invalidStudent));
    }

    @Test
    @DisplayName("Should update existing student when saved again")
    void testUpdateExistingStudent() {
        repository.save(student1);
        assertEquals(1, repository.getTotalStudentsCreated());

        student1.setGpa(3.9);
        repository.save(student1);

        assertEquals(1, repository.count());
        assertEquals(1, repository.getTotalStudentsCreated());
        assertEquals(3.9, repository.findById("S001").get().getGpa());
    }

    // FindById Tests
    @Test
    @DisplayName("Should find student by valid ID")
    void testFindByIdValid() {
        repository.save(student1);
        Optional<Student> found = repository.findById("S001");

        assertTrue(found.isPresent());
        assertEquals("ArlinB", found.get().getName());
    }

    @Test
    @DisplayName("Should return empty optional for non-existent ID")
    void testFindByIdNotFound() {
        Optional<Student> found = repository.findById("S999");
        assertFalse(found.isPresent());
    }

    @Test
    @DisplayName("Should return empty optional for null ID")
    void testFindByIdNull() {
        Optional<Student> found = repository.findById(null);
        assertFalse(found.isPresent());
    }

    @Test
    @DisplayName("Should return empty optional for empty ID")
    void testFindByIdEmpty() {
        Optional<Student> found = repository.findById("");
        assertFalse(found.isPresent());
    }

    // FindAll Tests
    @Test
    @DisplayName("Should return all students")
    void testFindAll() {
        repository.save(student1);
        repository.save(student2);
        repository.save(student3);

        Collection<Student> all = repository.findAll();
        assertEquals(3, all.size());
    }

    @Test
    @DisplayName("Should return empty collection when no students exist")
    void testFindAllEmpty() {
        Collection<Student> all = repository.findAll();
        assertTrue(all.isEmpty());
    }

    // FindByMajor Tests
    @Test
    @DisplayName("Should find students by major")
    void testFindByMajor() {
        repository.save(student1);
        repository.save(student2);
        repository.save(student3);

        List<Student> csStudents = repository.findByMajor("Computer Science");
        assertEquals(2, csStudents.size());
    }

    @Test
    @DisplayName("Should return empty list for null major")
    void testFindByMajorNull() {
        List<Student> students = repository.findByMajor(null);
        assertTrue(students.isEmpty());
    }

    @Test
    @DisplayName("Should return empty list for empty major")
    void testFindByMajorEmpty() {
        List<Student> students = repository.findByMajor("");
        assertTrue(students.isEmpty());
    }

    @Test
    @DisplayName("Should return empty list for non-existent major")
    void testFindByMajorNotFound() {
        repository.save(student1);
        List<Student> students = repository.findByMajor("Physics");
        assertTrue(students.isEmpty());
    }

    // FindByGpaRange Tests
    @Test
    @DisplayName("Should find students by GPA range")
    void testFindByGpaRange() {
        repository.save(student1);
        repository.save(student2);
        repository.save(student3);

        List<Student> students = repository.findByGpaRange(3.0, 4.0);
        assertEquals(2, students.size());
    }

    @Test
    @DisplayName("Should return empty list for invalid GPA range (min < 0)")
    void testFindByGpaRangeInvalidMin() {
        List<Student> students = repository.findByGpaRange(-1.0, 4.0);
        assertTrue(students.isEmpty());
    }

    @Test
    @DisplayName("Should return empty list for invalid GPA range (max > 4)")
    void testFindByGpaRangeInvalidMax() {
        List<Student> students = repository.findByGpaRange(0.0, 5.0);
        assertTrue(students.isEmpty());
    }

    @Test
    @DisplayName("Should return empty list when min > max")
    void testFindByGpaRangeMinGreaterThanMax() {
        List<Student> students = repository.findByGpaRange(3.5, 2.0);
        assertTrue(students.isEmpty());
    }

    // FindByAcademicLevel Tests
    @Test
    @DisplayName("Should find students by academic level")
    void testFindByAcademicLevel() {
        student1.setCreditHours(60);
        student2.setCreditHours(90);
        repository.save(student1);
        repository.save(student2);

        List<Student> students = repository.findByAcademicLevel("JUNIOR");
        assertFalse(students.isEmpty());
    }

    @Test
    @DisplayName("Should return empty list for null academic level")
    void testFindByAcademicLevelNull() {
        List<Student> students = repository.findByAcademicLevel(null);
        assertTrue(students.isEmpty());
    }

    @Test
    @DisplayName("Should return empty list for empty academic level")
    void testFindByAcademicLevelEmpty() {
        List<Student> students = repository.findByAcademicLevel("");
        assertTrue(students.isEmpty());
    }

    // Delete Tests
    @Test
    @DisplayName("Should delete existing student")
    void testDeleteExistingStudent() {
        repository.save(student1);
        assertTrue(repository.delete("S001"));
        assertEquals(0, repository.count());
    }

    @Test
    @DisplayName("Should return false when deleting non-existent student")
    void testDeleteNonExistentStudent() {
        assertFalse(repository.delete("S999"));
    }

    @Test
    @DisplayName("Should return false when deleting with null ID")
    void testDeleteNullId() {
        assertFalse(repository.delete(null));
    }

    @Test
    @DisplayName("Should return false when deleting with empty ID")
    void testDeleteEmptyId() {
        assertFalse(repository.delete(""));
    }

    // Exists Tests
    @Test
    @DisplayName("Should return true for existing student")
    void testExistsTrue() {
        repository.save(student1);
        assertTrue(repository.exists("S001"));
    }

    @Test
    @DisplayName("Should return false for non-existent student")
    void testExistsFalse() {
        assertFalse(repository.exists("S999"));
    }

    @Test
    @DisplayName("Should return false for null ID")
    void testExistsNull() {
        assertFalse(repository.exists(null));
    }

    @Test
    @DisplayName("Should return false for empty ID")
    void testExistsEmpty() {
        assertFalse(repository.exists(""));
    }

    // Count Tests
    @Test
    @DisplayName("Should count students correctly")
    void testCount() {
        assertEquals(0, repository.count());
        repository.save(student1);
        assertEquals(1, repository.count());
        repository.save(student2);
        assertEquals(2, repository.count());
    }

    @Test
    @DisplayName("Should track total students created")
    void testGetTotalStudentsCreated() {
        repository.save(student1);
        repository.save(student2);
        assertEquals(2, repository.getTotalStudentsCreated());

        repository.delete("S001");
        assertEquals(2, repository.getTotalStudentsCreated());
    }

    // CalculateAverageGpa Tests
    @Test
    @DisplayName("Should calculate average GPA correctly")
    void testCalculateAverageGpa() {
        repository.save(student1); // 3.5
        repository.save(student2); // 3.8
        repository.save(student3); // 2.9

        double average = repository.calculateAverageGpa();
        assertEquals(3.4, average, 0.01);
    }

    @Test
    @DisplayName("Should return 0.0 for average GPA when no students")
    void testCalculateAverageGpaEmpty() {
        assertEquals(0.0, repository.calculateAverageGpa());
    }

    // Clear Tests
    @Test
    @DisplayName("Should clear all students")
    void testClear() {
        repository.save(student1);
        repository.save(student2);
        repository.clear();
        assertEquals(0, repository.count());
    }

    // Update Tests
    @Test
    @DisplayName("Should update existing student")
    void testUpdate() {
        repository.save(student1);
        student1.setGpa(4.0);
        assertTrue(repository.update(student1));
        assertEquals(4.0, repository.findById("S001").get().getGpa());
    }

    @Test
    @DisplayName("Should not update non-existent student")
    void testUpdateNonExistent() {
        assertFalse(repository.update(student1));
    }

    @Test
    @DisplayName("Should not update null student")
    void testUpdateNull() {
        assertFalse(repository.update(null));
    }

    @Test
    @DisplayName("Should not update student with null ID")
    void testUpdateNullId() {
        Student invalid = new Student(null, "Test", "test@test.com", 20, "CS");
        assertFalse(repository.update(invalid));
    }

    // DeleteById Tests
    @Test
    @DisplayName("Should delete by ID")
    void testDeleteById() {
        repository.save(student1);
        assertTrue(repository.deleteById("S001"));
        assertEquals(0, repository.count());
    }

    // SearchByName Tests
    @Test
    @DisplayName("Should search students by name pattern")
    void testSearchByName() {
        repository.save(student1);
        repository.save(student2);
        repository.save(student3);

        List<Student> results = repository.searchByName("Arlin");
        assertEquals(2, results.size()); // ArlinB and Arjan M
    }

    @Test
    @DisplayName("Should search case-insensitively")
    void testSearchByNameCaseInsensitive() {
        repository.save(student1);

        List<Student> results = repository.searchByName("Arlin");
        assertEquals(1, results.size());
    }

    @Test
    @DisplayName("Should return empty list for null name pattern")
    void testSearchByNameNull() {
        List<Student> results = repository.searchByName(null);
        assertTrue(results.isEmpty());
    }

    @Test
    @DisplayName("Should return empty list for empty name pattern")
    void testSearchByNameEmpty() {
        List<Student> results = repository.searchByName("");
        assertTrue(results.isEmpty());
    }

    @Test
    @DisplayName("Should return empty list when no matches found")
    void testSearchByNameNoMatches() {
        repository.save(student1);
        List<Student> results = repository.searchByName("xyz");
        assertTrue(results.isEmpty());
    }
}