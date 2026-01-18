package Tests.Unit_Testing.RepositoryTest;

import edu.university.main.model.Department;
import edu.university.main.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for DepartmentRepository class
 */
class DepartmentRepositoryTest {
    private DepartmentRepository repository;
    private Department dept1;
    private Department dept2;
    private Department dept3;

    @BeforeEach
    void setUp() {
        repository = new DepartmentRepository();

        dept1 = new Department("CS", "Computer Science", "Dr. Smith", 500000);
        dept2 = new Department("MATH", "Mathematics", "Dr. Johnson", 400000);
        dept3 = new Department("PHYS", "Physics", "Dr. Brown", 350000);
    }

    // Save Tests
    @Test
    @DisplayName("Should save a valid department successfully")
    void testSaveValidDepartment() {
        assertTrue(repository.save(dept1));
        assertEquals(1, repository.count());
    }

    @Test
    @DisplayName("Should not save null department")
    void testSaveNullDepartment() {
        assertFalse(repository.save(null));
        assertEquals(0, repository.count());
    }

    @Test
    @DisplayName("Should not save department with null ID")
    void testSaveDepartmentWithNullId() {
        Department invalid = new Department(null, "Test", "Head", 100000);
        assertFalse(repository.save(invalid));
    }

    @Test
    @DisplayName("Should not save department with empty ID")
    void testSaveDepartmentWithEmptyId() {
        Department invalid = new Department("", "Test", "Head", 100000);
        assertFalse(repository.save(invalid));
    }

    @Test
    @DisplayName("Should update existing department when saved again")
    void testUpdateExistingDepartment() {
        repository.save(dept1);
        assertEquals(1, repository.getTotalDepartmentsCreated());

        dept1.setBudget(600000);
        repository.save(dept1);

        assertEquals(1, repository.count());
        assertEquals(1, repository.getTotalDepartmentsCreated());
        assertEquals(600000, repository.findById("CS").get().getBudget());
    }

    // FindById Tests
    @Test
    @DisplayName("Should find department by valid ID")
    void testFindByIdValid() {
        repository.save(dept1);
        Optional<Department> found = repository.findById("CS");

        assertTrue(found.isPresent());
        assertEquals("Computer Science", found.get().getDepartmentName());
    }

    @Test
    @DisplayName("Should return empty optional for non-existent ID")
    void testFindByIdNotFound() {
        Optional<Department> found = repository.findById("CHEM");
        assertFalse(found.isPresent());
    }

    @Test
    @DisplayName("Should return empty optional for null ID")
    void testFindByIdNull() {
        Optional<Department> found = repository.findById(null);
        assertFalse(found.isPresent());
    }

    @Test
    @DisplayName("Should return empty optional for empty ID")
    void testFindByIdEmpty() {
        Optional<Department> found = repository.findById("");
        assertFalse(found.isPresent());
    }

    // FindAll Tests
    @Test
    @DisplayName("Should return all departments")
    void testFindAll() {
        repository.save(dept1);
        repository.save(dept2);
        repository.save(dept3);

        Collection<Department> all = repository.findAll();
        assertEquals(3, all.size());
    }

    @Test
    @DisplayName("Should return empty collection when no departments exist")
    void testFindAllEmpty() {
        Collection<Department> all = repository.findAll();
        assertTrue(all.isEmpty());
    }

    // FindBySize Tests
    @Test
    @DisplayName("Should find departments by size")
    void testFindBySize() {
        repository.save(dept1); // LARGE (500 students)
        repository.save(dept2); // MEDIUM (300 students)
        repository.save(dept3); // SMALL (150 students)

        List<Department> large = repository.findBySize("LARGE");
        assertEquals(1, large.size());
    }

    @Test
    @DisplayName("Should return empty list for null size")
    void testFindBySizeNull() {
        List<Department> depts = repository.findBySize(null);
        assertTrue(depts.isEmpty());
    }

    @Test
    @DisplayName("Should return empty list for empty size")
    void testFindBySizeEmpty() {
        List<Department> depts = repository.findBySize("");
        assertTrue(depts.isEmpty());
    }

    // FindByBudgetRange Tests
    @Test
    @DisplayName("Should find departments by budget range")
    void testFindByBudgetRange() {
        repository.save(dept1); // 500000
        repository.save(dept2); // 400000
        repository.save(dept3); // 350000

        List<Department> depts = repository.findByBudgetRange(350000, 450000);
        assertEquals(2, depts.size());
    }

    @Test
    @DisplayName("Should return empty list for negative min budget")
    void testFindByBudgetRangeNegativeMin() {
        List<Department> depts = repository.findByBudgetRange(-100000, 500000);
        assertTrue(depts.isEmpty());
    }

    @Test
    @DisplayName("Should return empty list when min > max")
    void testFindByBudgetRangeMinGreaterThanMax() {
        List<Department> depts = repository.findByBudgetRange(500000, 300000);
        assertTrue(depts.isEmpty());
    }

    @Test
    @DisplayName("Should find departments at exact budget boundaries")
    void testFindByBudgetRangeBoundaries() {
        repository.save(dept1); // 500000

        List<Department> depts = repository.findByBudgetRange(500000, 500000);
        assertEquals(1, depts.size());
    }

    // Delete Tests
    @Test
    @DisplayName("Should delete existing department")
    void testDeleteExistingDepartment() {
        repository.save(dept1);
        assertTrue(repository.delete("CS"));
        assertEquals(0, repository.count());
    }

    @Test
    @DisplayName("Should return false when deleting non-existent department")
    void testDeleteNonExistentDepartment() {
        assertFalse(repository.delete("CHEM"));
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
    @DisplayName("Should return true for existing department")
    void testExistsTrue() {
        repository.save(dept1);
        assertTrue(repository.exists("CS"));
    }

    @Test
    @DisplayName("Should return false for non-existent department")
    void testExistsFalse() {
        assertFalse(repository.exists("CHEM"));
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
    @DisplayName("Should count departments correctly")
    void testCount() {
        assertEquals(0, repository.count());
        repository.save(dept1);
        assertEquals(1, repository.count());
        repository.save(dept2);
        assertEquals(2, repository.count());
    }

    @Test
    @DisplayName("Should track total departments created")
    void testGetTotalDepartmentsCreated() {
        repository.save(dept1);
        repository.save(dept2);
        assertEquals(2, repository.getTotalDepartmentsCreated());

        repository.delete("CS");
        assertEquals(2, repository.getTotalDepartmentsCreated());
    }

    // CalculateTotalBudget Tests
    @Test
    @DisplayName("Should calculate total budget correctly")
    void testCalculateTotalBudget() {
        repository.save(dept1); // 500000
        repository.save(dept2); // 400000
        repository.save(dept3); // 350000

        assertEquals(1250000, repository.calculateTotalBudget());
    }

    @Test
    @DisplayName("Should return 0.0 for total budget when no departments")
    void testCalculateTotalBudgetEmpty() {
        assertEquals(0.0, repository.calculateTotalBudget());
    }

    // CalculateTotalStudents Tests
    @Test
    @DisplayName("Should calculate total students correctly")
    void testCalculateTotalStudents() {
        repository.save(dept1); // 500
        repository.save(dept2); // 300
        repository.save(dept3); // 150

        assertEquals(950, repository.calculateTotalStudents());
    }

    @Test
    @DisplayName("Should return 0 for total students when no departments")
    void testCalculateTotalStudentsEmpty() {
        assertEquals(0, repository.calculateTotalStudents());
    }

    // CalculateTotalFaculty Tests
    @Test
    @DisplayName("Should calculate total faculty correctly")
    void testCalculateTotalFaculty() {
        repository.save(dept1); // 50
        repository.save(dept2); // 40
        repository.save(dept3); // 25

        assertEquals(115, repository.calculateTotalFaculty());
    }

    @Test
    @DisplayName("Should return 0 for total faculty when no departments")
    void testCalculateTotalFacultyEmpty() {
        assertEquals(0, repository.calculateTotalFaculty());
    }

    // Clear Tests
    @Test
    @DisplayName("Should clear all departments")
    void testClear() {
        repository.save(dept1);
        repository.save(dept2);
        repository.clear();
        assertEquals(0, repository.count());
    }

    // Update Tests
    @Test
    @DisplayName("Should update existing department")
    void testUpdate() {
        repository.save(dept1);
        dept1.setBudget(550000);
        assertTrue(repository.update(dept1));
        assertEquals(550000, repository.findById("CS").get().getBudget());
    }

    @Test
    @DisplayName("Should not update non-existent department")
    void testUpdateNonExistent() {
        assertFalse(repository.update(dept1));
    }

    @Test
    @DisplayName("Should not update null department")
    void testUpdateNull() {
        assertFalse(repository.update(null));
    }

    @Test
    @DisplayName("Should not update department with null ID")
    void testUpdateNullId() {
        Department invalid = new Department(null, "Test", "Head", 100000);
        assertFalse(repository.update(invalid));
    }

    // DeleteById Tests
    @Test
    @DisplayName("Should delete by ID")
    void testDeleteById() {
        repository.save(dept1);
        assertTrue(repository.deleteById("CS"));
        assertEquals(0, repository.count());
    }

    // SearchByName Tests
    @Test
    @DisplayName("Should search departments by name pattern")
    void testSearchByName() {
        repository.save(dept1);
        repository.save(dept2);
        repository.save(dept3);

        List<Department> results = repository.searchByName("Computer");
        assertEquals(1, results.size());
    }

    @Test
    @DisplayName("Should search case-insensitively")
    void testSearchByNameCaseInsensitive() {
        repository.save(dept1);

        List<Department> results = repository.searchByName("COMPUTER");
        assertEquals(1, results.size());
    }

    @Test
    @DisplayName("Should return empty list for null name pattern")
    void testSearchByNameNull() {
        List<Department> results = repository.searchByName(null);
        assertTrue(results.isEmpty());
    }

    @Test
    @DisplayName("Should return empty list for empty name pattern")
    void testSearchByNameEmpty() {
        List<Department> results = repository.searchByName("");
        assertTrue(results.isEmpty());
    }

    @Test
    @DisplayName("Should return empty list when no matches found")
    void testSearchByNameNoMatches() {
        repository.save(dept1);
        List<Department> results = repository.searchByName("xyz");
        assertTrue(results.isEmpty());
    }

    @Test
    @DisplayName("Should find partial name matches")
    void testSearchByNamePartialMatch() {
        repository.save(dept1);
        repository.save(dept2);

        List<Department> results = repository.searchByName("mat");
        assertEquals(1, results.size());
        assertEquals("Mathematics", results.get(0).getDepartmentName());
    }
}