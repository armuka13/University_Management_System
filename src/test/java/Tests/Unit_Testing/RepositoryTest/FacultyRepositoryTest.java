package Tests.Unit_Testing.RepositoryTest;

import edu.university.main.model.Faculty;
import edu.university.main.repository.FacultyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FacultyRepositoryTest {
    private FacultyRepository repository;
    private Faculty faculty1;
    private Faculty faculty2;
    private Faculty faculty3;

    @BeforeEach
    void setUp() {
        repository = new FacultyRepository();

        faculty1 = new Faculty("F001", "Dr. John Smith", "john.smith@university.edu",  "Computer Science", "Professor", 95000);

        faculty2 = new Faculty("F002", "Dr. Jane Doe", "jane.doe@university.edu",
                 "Mathematics", "Associate Professor", 75000);

        faculty3 = new Faculty("F003", "Dr. Bob Johnson", "bob.johnson@university.edu",
                 "Computer Science", "Assistant Professor", 65000);
    }

    // Save Tests
    @Test
    @DisplayName("Should save a valid faculty successfully")
    void testSaveValidFaculty() {
        assertTrue(repository.save(faculty1));
        assertEquals(1, repository.count());
    }

    @Test
    @DisplayName("Should not save null faculty")
    void testSaveNullFaculty() {
        assertFalse(repository.save(null));
        assertEquals(0, repository.count());
    }

    @Test
    @DisplayName("Should not save faculty with null ID")
    void testSaveFacultyWithNullId() {
        Faculty invalid = new Faculty(null, "Test", "test@test.com", "CS", "Professor", 80000);
        assertFalse(repository.save(invalid));
    }

    @Test
    @DisplayName("Should not save faculty with empty ID")
    void testSaveFacultyWithEmptyId() {
        Faculty invalid = new Faculty("", "Test", "test@test.com", "CS", "Professor", 80000);
        assertFalse(repository.save(invalid));
    }

    @Test
    @DisplayName("Should update existing faculty when saved again")
    void testUpdateExistingFaculty() {
        repository.save(faculty1);
        assertEquals(1, repository.getTotalFacultyCreated());

        faculty1.setSalary(100000);
        repository.save(faculty1);

        assertEquals(1, repository.count());
        assertEquals(1, repository.getTotalFacultyCreated());
        assertEquals(100000, repository.findById("F001").get().getSalary());
    }

    // FindById Tests
    @Test
    @DisplayName("Should find faculty by valid ID")
    void testFindByIdValid() {
        repository.save(faculty1);
        Optional<Faculty> found = repository.findById("F001");

        assertTrue(found.isPresent());
        assertEquals("Dr. John Smith", found.get().getName());
    }

    @Test
    @DisplayName("Should return empty optional for non-existent ID")
    void testFindByIdNotFound() {
        Optional<Faculty> found = repository.findById("F999");
        assertFalse(found.isPresent());
    }

    @Test
    @DisplayName("Should return empty optional for null ID")
    void testFindByIdNull() {
        Optional<Faculty> found = repository.findById(null);
        assertFalse(found.isPresent());
    }

    @Test
    @DisplayName("Should return empty optional for empty ID")
    void testFindByIdEmpty() {
        Optional<Faculty> found = repository.findById("");
        assertFalse(found.isPresent());
    }

    // FindAll Tests
    @Test
    @DisplayName("Should return all faculty members")
    void testFindAll() {
        repository.save(faculty1);
        repository.save(faculty2);
        repository.save(faculty3);

        Collection<Faculty> all = repository.findAll();
        assertEquals(3, all.size());
    }

    @Test
    @DisplayName("Should return empty collection when no faculty exist")
    void testFindAllEmpty() {
        Collection<Faculty> all = repository.findAll();
        assertTrue(all.isEmpty());
    }

    // FindByDepartment Tests
    @Test
    @DisplayName("Should find faculty by department")
    void testFindByDepartment() {
        repository.save(faculty1);
        repository.save(faculty2);
        repository.save(faculty3);

        List<Faculty> csFaculty = repository.findByDepartment("Computer Science");
        assertEquals(2, csFaculty.size());
    }

    @Test
    @DisplayName("Should return empty list for null department")
    void testFindByDepartmentNull() {
        List<Faculty> faculty = repository.findByDepartment(null);
        assertTrue(faculty.isEmpty());
    }

    @Test
    @DisplayName("Should return empty list for empty department")
    void testFindByDepartmentEmpty() {
        List<Faculty> faculty = repository.findByDepartment("");
        assertTrue(faculty.isEmpty());
    }

    @Test
    @DisplayName("Should return empty list for non-existent department")
    void testFindByDepartmentNotFound() {
        repository.save(faculty1);
        List<Faculty> faculty = repository.findByDepartment("Physics");
        assertTrue(faculty.isEmpty());
    }

    // FindByRank Tests
    @Test
    @DisplayName("Should find faculty by rank")
    void testFindByRank() {
        repository.save(faculty1);
        repository.save(faculty2);
        repository.save(faculty3);

        List<Faculty> professors = repository.findByRank("Professor");
        assertEquals(1, professors.size());
    }

    @Test
    @DisplayName("Should return empty list for null rank")
    void testFindByRankNull() {
        List<Faculty> faculty = repository.findByRank(null);
        assertTrue(faculty.isEmpty());
    }

    @Test
    @DisplayName("Should return empty list for empty rank")
    void testFindByRankEmpty() {
        List<Faculty> faculty = repository.findByRank("");
        assertTrue(faculty.isEmpty());
    }

    // FindBySalaryRange Tests
    @Test
    @DisplayName("Should find faculty by salary range")
    void testFindBySalaryRange() {
        repository.save(faculty1); // 95000
        repository.save(faculty2); // 75000
        repository.save(faculty3); // 65000

        List<Faculty> faculty = repository.findBySalaryRange(70000, 80000);
        assertEquals(1, faculty.size());
        assertEquals("Dr. Jane Doe", faculty.get(0).getName());
    }

    @Test
    @DisplayName("Should return empty list for negative min salary")
    void testFindBySalaryRangeNegativeMin() {
        List<Faculty> faculty = repository.findBySalaryRange(-1000, 100000);
        assertTrue(faculty.isEmpty());
    }

    @Test
    @DisplayName("Should return empty list when min > max")
    void testFindBySalaryRangeMinGreaterThanMax() {
        List<Faculty> faculty = repository.findBySalaryRange(100000, 50000);
        assertTrue(faculty.isEmpty());
    }

    @Test
    @DisplayName("Should find faculty at exact salary boundaries")
    void testFindBySalaryRangeBoundaries() {
        repository.save(faculty1); // 95000

        List<Faculty> faculty = repository.findBySalaryRange(95000, 95000);
        assertEquals(1, faculty.size());
    }

    // Delete Tests
    @Test
    @DisplayName("Should delete existing faculty")
    void testDeleteExistingFaculty() {
        repository.save(faculty1);
        assertTrue(repository.delete("F001"));
        assertEquals(0, repository.count());
    }

    @Test
    @DisplayName("Should return false when deleting non-existent faculty")
    void testDeleteNonExistentFaculty() {
        assertFalse(repository.delete("F999"));
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
    @DisplayName("Should return true for existing faculty")
    void testExistsTrue() {
        repository.save(faculty1);
        assertTrue(repository.exists("F001"));
    }

    @Test
    @DisplayName("Should return false for non-existent faculty")
    void testExistsFalse() {
        assertFalse(repository.exists("F999"));
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
    @DisplayName("Should count faculty correctly")
    void testCount() {
        assertEquals(0, repository.count());
        repository.save(faculty1);
        assertEquals(1, repository.count());
        repository.save(faculty2);
        assertEquals(2, repository.count());
    }

    @Test
    @DisplayName("Should track total faculty created")
    void testGetTotalFacultyCreated() {
        repository.save(faculty1);
        repository.save(faculty2);
        assertEquals(2, repository.getTotalFacultyCreated());

        repository.delete("F001");
        assertEquals(2, repository.getTotalFacultyCreated());
    }

    // CalculateAverageSalary Tests
    @Test
    @DisplayName("Should calculate average salary correctly")
    void testCalculateAverageSalary() {
        repository.save(faculty1); // 95000
        repository.save(faculty2); // 75000
        repository.save(faculty3); // 65000

        double average = repository.calculateAverageSalary();
        assertEquals(78333.33, average, 0.01);
    }

    @Test
    @DisplayName("Should return 0.0 for average salary when no faculty")
    void testCalculateAverageSalaryEmpty() {
        assertEquals(0.0, repository.calculateAverageSalary());
    }

    // CountByDepartment Tests
    @Test
    @DisplayName("Should count faculty by department")
    void testCountByDepartment() {
        repository.save(faculty1);
        repository.save(faculty2);
        repository.save(faculty3);

        assertEquals(2, repository.countByDepartment("Computer Science"));
        assertEquals(1, repository.countByDepartment("Mathematics"));
    }

    @Test
    @DisplayName("Should return 0 for null department")
    void testCountByDepartmentNull() {
        assertEquals(0, repository.countByDepartment(null));
    }

    @Test
    @DisplayName("Should return 0 for empty department")
    void testCountByDepartmentEmpty() {
        assertEquals(0, repository.countByDepartment(""));
    }

    @Test
    @DisplayName("Should return 0 for non-existent department")
    void testCountByDepartmentNotFound() {
        repository.save(faculty1);
        assertEquals(0, repository.countByDepartment("Physics"));
    }

    // Clear Tests
    @Test
    @DisplayName("Should clear all faculty")
    void testClear() {
        repository.save(faculty1);
        repository.save(faculty2);
        repository.clear();
        assertEquals(0, repository.count());
    }

    // Update Tests
    @Test
    @DisplayName("Should update existing faculty")
    void testUpdate() {
        repository.save(faculty1);
        faculty1.setSalary(105000);
        assertTrue(repository.update(faculty1));
        assertEquals(105000, repository.findById("F001").get().getSalary());
    }

    @Test
    @DisplayName("Should not update non-existent faculty")
    void testUpdateNonExistent() {
        assertFalse(repository.update(faculty1));
    }

    @Test
    @DisplayName("Should not update null faculty")
    void testUpdateNull() {
        assertFalse(repository.update(null));
    }

    @Test
    @DisplayName("Should not update faculty with null ID")
    void testUpdateNullId() {
        Faculty invalid = new Faculty(null, "Test", "test@test.com", "CS", "Professor", 80000);
        assertFalse(repository.update(invalid));
    }

    // DeleteById Tests
    @Test
    @DisplayName("Should delete by ID")
    void testDeleteById() {
        repository.save(faculty1);
        assertTrue(repository.deleteById("F001"));
        assertEquals(0, repository.count());
    }

    // SearchByName Tests
    @Test
    @DisplayName("Should search faculty by name pattern")
    void testSearchByName() {
        repository.save(faculty1);
        repository.save(faculty2);
        repository.save(faculty3);

        List<Faculty> results = repository.searchByName("John");
        assertEquals(2, results.size()); // John Smith and Bob Johnson
    }

    @Test
    @DisplayName("Should search case-insensitively")
    void testSearchByNameCaseInsensitive() {
        repository.save(faculty1);

        List<Faculty> results = repository.searchByName("JOHN");
        assertEquals(1, results.size());
    }

    @Test
    @DisplayName("Should return empty list for null name pattern")
    void testSearchByNameNull() {
        List<Faculty> results = repository.searchByName(null);
        assertTrue(results.isEmpty());
    }

    @Test
    @DisplayName("Should return empty list for empty name pattern")
    void testSearchByNameEmpty() {
        List<Faculty> results = repository.searchByName("");
        assertTrue(results.isEmpty());
    }

    @Test
    @DisplayName("Should return empty list when no matches found")
    void testSearchByNameNoMatches() {
        repository.save(faculty1);
        List<Faculty> results = repository.searchByName("xyz");
        assertTrue(results.isEmpty());
    }

    @Test
    @DisplayName("Should find partial name matches")
    void testSearchByNamePartialMatch() {
        repository.save(faculty1);
        repository.save(faculty2);

        List<Faculty> results = repository.searchByName("Dr.");
        assertEquals(2, results.size());
    }
}