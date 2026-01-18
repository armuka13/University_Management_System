package Tests.Unit_Testing.RepositoryTest;

import edu.university.main.model.Course;
import edu.university.main.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CourseRepositoryTest {
    private CourseRepository repository;
    private Course course1;
    private Course course2;
    private Course course3;

    @BeforeEach
    void setUp() {
        repository = new CourseRepository();

        course1 = new Course("CS101", "Intro to Programming", "Dr. Smith", 3, 30, "Computer Science");
        course1.setCurrentEnrollment(25);

        course2 = new Course("MATH201", "Calculus I", "Dr. Johnson", 4, 40, "Mathematics");
        course2.setCurrentEnrollment(40);

        course3 = new Course("CS201", "Data Structures", "Dr. Smith", 3, 25, "Computer Science");
        course3.setCurrentEnrollment(10);
    }

    // Save Tests
    @Test
    @DisplayName("Should save a valid course successfully")
    void testSaveValidCourse() {
        assertTrue(repository.save(course1));
        assertEquals(1, repository.count());
    }

    @Test
    @DisplayName("Should not save null course")
    void testSaveNullCourse() {
        assertFalse(repository.save(null));
        assertEquals(0, repository.count());
    }

    @Test
    @DisplayName("Should not save course with null ID")
    void testSaveCourseWithNullId() {
        Course invalid = new Course(null, "Test", "Prof", 3, 30, "CS");
        assertFalse(repository.save(invalid));
    }

    @Test
    @DisplayName("Should not save course with empty ID")
    void testSaveCourseWithEmptyId() {
        Course invalid = new Course("", "Test", "Prof", 3, 30, "CS");
        assertFalse(repository.save(invalid));
    }

    @Test
    @DisplayName("Should update existing course when saved again")
    void testUpdateExistingCourse() {
        repository.save(course1);
        assertEquals(1, repository.getTotalCoursesCreated());

        course1.setCurrentEnrollment(28);
        repository.save(course1);

        assertEquals(1, repository.count());
        assertEquals(1, repository.getTotalCoursesCreated());
        assertEquals(28, repository.findById("CS101").get().getCurrentEnrollment());
    }

    // FindById Tests
    @Test
    @DisplayName("Should find course by valid ID")
    void testFindByIdValid() {
        repository.save(course1);
        Optional<Course> found = repository.findById("CS101");

        assertTrue(found.isPresent());
        assertEquals("Intro to Programming", found.get().getCourseName());
    }

    @Test
    @DisplayName("Should return empty optional for non-existent ID")
    void testFindByIdNotFound() {
        Optional<Course> found = repository.findById("CS999");
        assertFalse(found.isPresent());
    }

    @Test
    @DisplayName("Should return empty optional for null ID")
    void testFindByIdNull() {
        Optional<Course> found = repository.findById(null);
        assertFalse(found.isPresent());
    }

    @Test
    @DisplayName("Should return empty optional for empty ID")
    void testFindByIdEmpty() {
        Optional<Course> found = repository.findById("");
        assertFalse(found.isPresent());
    }

    // FindAll Tests
    @Test
    @DisplayName("Should return all courses")
    void testFindAll() {
        repository.save(course1);
        repository.save(course2);
        repository.save(course3);

        Collection<Course> all = repository.findAll();
        assertEquals(3, all.size());
    }

    @Test
    @DisplayName("Should return empty collection when no courses exist")
    void testFindAllEmpty() {
        Collection<Course> all = repository.findAll();
        assertTrue(all.isEmpty());
    }

    // FindByDepartment Tests
    @Test
    @DisplayName("Should find courses by department")
    void testFindByDepartment() {
        repository.save(course1);
        repository.save(course2);
        repository.save(course3);

        List<Course> csCourses = repository.findByDepartment("Computer Science");
        assertEquals(2, csCourses.size());
    }

    @Test
    @DisplayName("Should return empty list for null department")
    void testFindByDepartmentNull() {
        List<Course> courses = repository.findByDepartment(null);
        assertTrue(courses.isEmpty());
    }

    @Test
    @DisplayName("Should return empty list for empty department")
    void testFindByDepartmentEmpty() {
        List<Course> courses = repository.findByDepartment("");
        assertTrue(courses.isEmpty());
    }

    // FindByInstructor Tests
    @Test
    @DisplayName("Should find courses by instructor")
    void testFindByInstructor() {
        repository.save(course1);
        repository.save(course2);
        repository.save(course3);

        List<Course> courses = repository.findByInstructor("Dr. Smith");
        assertEquals(2, courses.size());
    }

    @Test
    @DisplayName("Should return empty list for null instructor")
    void testFindByInstructorNull() {
        List<Course> courses = repository.findByInstructor(null);
        assertTrue(courses.isEmpty());
    }

    @Test
    @DisplayName("Should return empty list for empty instructor")
    void testFindByInstructorEmpty() {
        List<Course> courses = repository.findByInstructor("");
        assertTrue(courses.isEmpty());
    }

    // FindByCredits Tests
    @Test
    @DisplayName("Should find courses by credits")
    void testFindByCredits() {
        repository.save(course1);
        repository.save(course2);
        repository.save(course3);

        List<Course> courses = repository.findByCredits(3);
        assertEquals(2, courses.size());
    }

    @Test
    @DisplayName("Should return empty list for negative credits")
    void testFindByCreditsNegative() {
        List<Course> courses = repository.findByCredits(-1);
        assertTrue(courses.isEmpty());
    }

    // FindAvailableCourses Tests
    @Test
    @DisplayName("Should find available courses")
    void testFindAvailableCourses() {
        repository.save(course1); // 25/30 - available
        repository.save(course2); // 40/40 - full
        repository.save(course3); // 10/25 - available

        List<Course> available = repository.findAvailableCourses();
        assertEquals(2, available.size());
    }

    @Test
    @DisplayName("Should return empty list when no available courses")
    void testFindAvailableCoursesEmpty() {
        course1.setCurrentEnrollment(30);
        repository.save(course1);

        List<Course> available = repository.findAvailableCourses();
        assertTrue(available.isEmpty());
    }

    // FindFullCourses Tests
    @Test
    @DisplayName("Should find full courses")
    void testFindFullCourses() {
        repository.save(course1); // 25/30 - not full
        repository.save(course2); // 40/40 - full
        repository.save(course3); // 10/25 - not full

        List<Course> full = repository.findFullCourses();
        assertEquals(1, full.size());
        assertEquals("MATH201", full.get(0).getCourseId());
    }

    @Test
    @DisplayName("Should return empty list when no full courses")
    void testFindFullCoursesEmpty() {
        repository.save(course1);
        repository.save(course3);

        List<Course> full = repository.findFullCourses();
        assertTrue(full.isEmpty());
    }

    // Delete Tests
    @Test
    @DisplayName("Should delete existing course")
    void testDeleteExistingCourse() {
        repository.save(course1);
        assertTrue(repository.delete("CS101"));
        assertEquals(0, repository.count());
    }

    @Test
    @DisplayName("Should return false when deleting non-existent course")
    void testDeleteNonExistentCourse() {
        assertFalse(repository.delete("CS999"));
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
    @DisplayName("Should return true for existing course")
    void testExistsTrue() {
        repository.save(course1);
        assertTrue(repository.exists("CS101"));
    }

    @Test
    @DisplayName("Should return false for non-existent course")
    void testExistsFalse() {
        assertFalse(repository.exists("CS999"));
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
    @DisplayName("Should count courses correctly")
    void testCount() {
        assertEquals(0, repository.count());
        repository.save(course1);
        assertEquals(1, repository.count());
        repository.save(course2);
        assertEquals(2, repository.count());
    }

    @Test
    @DisplayName("Should track total courses created")
    void testGetTotalCoursesCreated() {
        repository.save(course1);
        repository.save(course2);
        assertEquals(2, repository.getTotalCoursesCreated());

        repository.delete("CS101");
        assertEquals(2, repository.getTotalCoursesCreated());
    }

    // CalculateTotalEnrollment Tests
    @Test
    @DisplayName("Should calculate total enrollment correctly")
    void testCalculateTotalEnrollment() {
        repository.save(course1); // 25
        repository.save(course2); // 40
        repository.save(course3); // 10

        assertEquals(75, repository.calculateTotalEnrollment());
    }

    @Test
    @DisplayName("Should return 0 for total enrollment when no courses")
    void testCalculateTotalEnrollmentEmpty() {
        assertEquals(0, repository.calculateTotalEnrollment());
    }

    // CalculateAverageFillRate Tests
    @Test
    @DisplayName("Should calculate average fill rate correctly")
    void testCalculateAverageFillRate() {
        repository.save(course1); // 25/30 = 83.33%
        repository.save(course2); // 40/40 = 100%
        repository.save(course3); // 10/25 = 40%

        double avgFillRate = repository.calculateAverageFillRate();
        assertTrue(avgFillRate > 74.0 && avgFillRate < 75.0);
    }

    @Test
    @DisplayName("Should return 0.0 for average fill rate when no courses")
    void testCalculateAverageFillRateEmpty() {
        assertEquals(0.0, repository.calculateAverageFillRate());
    }

    // Clear Tests
    @Test
    @DisplayName("Should clear all courses")
    void testClear() {
        repository.save(course1);
        repository.save(course2);
        repository.clear();
        assertEquals(0, repository.count());
    }

    // Update Tests
    @Test
    @DisplayName("Should update existing course")
    void testUpdate() {
        repository.save(course1);
        course1.setCurrentEnrollment(29);
        assertTrue(repository.update(course1));
        assertEquals(29, repository.findById("CS101").get().getCurrentEnrollment());
    }

    @Test
    @DisplayName("Should not update non-existent course")
    void testUpdateNonExistent() {
        assertFalse(repository.update(course1));
    }

    @Test
    @DisplayName("Should not update null course")
    void testUpdateNull() {
        assertFalse(repository.update(null));
    }

    @Test
    @DisplayName("Should not update course with null ID")
    void testUpdateNullId() {
        Course invalid = new Course(null, "Test", "Prof", 3, 30, "CS");
        assertFalse(repository.update(invalid));
    }

    // DeleteById Tests
    @Test
    @DisplayName("Should delete by ID")
    void testDeleteById() {
        repository.save(course1);
        assertTrue(repository.deleteById("CS101"));
        assertEquals(0, repository.count());
    }

    // SearchByName Tests
    @Test
    @DisplayName("Should search courses by name pattern")
    void testSearchByName() {
        repository.save(course1);
        repository.save(course2);
        repository.save(course3);

        List<Course> results = repository.searchByName("Intro");
        assertEquals(1, results.size());
    }

    @Test
    @DisplayName("Should search case-insensitively")
    void testSearchByNameCaseInsensitive() {
        repository.save(course1);

        List<Course> results = repository.searchByName("INTRO");
        assertEquals(1, results.size());
    }

    @Test
    @DisplayName("Should return empty list for null name pattern")
    void testSearchByNameNull() {
        List<Course> results = repository.searchByName(null);
        assertTrue(results.isEmpty());
    }

    @Test
    @DisplayName("Should return empty list for empty name pattern")
    void testSearchByNameEmpty() {
        List<Course> results = repository.searchByName("");
        assertTrue(results.isEmpty());
    }

    @Test
    @DisplayName("Should return empty list when no matches found")
    void testSearchByNameNoMatches() {
        repository.save(course1);
        List<Course> results = repository.searchByName("xyz");
        assertTrue(results.isEmpty());
    }
}