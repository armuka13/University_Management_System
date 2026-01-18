package Tests.Unit_Testing.RepositoryTest;

import edu.university.main.model.Grade;
import edu.university.main.repository.GradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GradeRepositoryTest {
    private GradeRepository repository;
    private Grade grade1;
    private Grade grade2;
    private Grade grade3;

    @BeforeEach
    void setUp() {
        repository = new GradeRepository();

        grade1 = new Grade("S001", "CS101", 85.0, 1, 2024);
        grade2 = new Grade("S001", "MATH201", 92.0, 1, 2024);
        grade3 = new Grade("S002", "CS101", 78.0, 1, 2024);
    }

    // Save Tests
    @Test
    @DisplayName("Should save a valid grade successfully")
    void testSaveValidGrade() {
        assertTrue(repository.save(grade1));
        assertEquals(1, repository.count());
    }

    @Test
    @DisplayName("Should not save null grade")
    void testSaveNullGrade() {
        assertFalse(repository.save(null));
        assertEquals(0, repository.count());
    }

    @Test
    @DisplayName("Should not save grade with null student ID")
    void testSaveGradeWithNullStudentId() {
        Grade invalid = new Grade(null, "CS101", 85.0, 1, 2024);
        assertFalse(repository.save(invalid));
    }

    @Test
    @DisplayName("Should not save grade with null course ID")
    void testSaveGradeWithNullCourseId() {
        Grade invalid = new Grade("S001", null, 85.0, 1, 2024);
        assertFalse(repository.save(invalid));
    }

    @Test
    @DisplayName("Should update existing grade when saved again")
    void testUpdateExistingGrade() {
        repository.save(grade1);
        assertEquals(1, repository.getTotalGradesRecorded());

        Grade updatedGrade = new Grade("S001", "CS101", 90.0, 1, 2024);
        repository.save(updatedGrade);

        assertEquals(1, repository.count());
        assertEquals(1, repository.getTotalGradesRecorded());
        assertEquals(90.0, repository.findByStudentAndCourse("S001", "CS101").getNumericGrade());
    }

    // FindByStudentAndCourse Tests
    @Test
    @DisplayName("Should find grade by student and course")
    void testFindByStudentAndCourse() {
        repository.save(grade1);

        Grade found = repository.findByStudentAndCourse("S001", "CS101");
        assertNotNull(found);
        assertEquals(85.0, found.getNumericGrade());
    }

    @Test
    @DisplayName("Should return null for non-existent combination")
    void testFindByStudentAndCourseNotFound() {
        Grade found = repository.findByStudentAndCourse("S999", "CS999");
        assertNull(found);
    }

    @Test
    @DisplayName("Should return null for null student ID")
    void testFindByStudentAndCourseNullStudentId() {
        Grade found = repository.findByStudentAndCourse(null, "CS101");
        assertNull(found);
    }

    @Test
    @DisplayName("Should return null for null course ID")
    void testFindByStudentAndCourseNullCourseId() {
        Grade found = repository.findByStudentAndCourse("S001", null);
        assertNull(found);
    }

    // FindByStudent Tests
    @Test
    @DisplayName("Should find all grades for a student")
    void testFindByStudent() {
        repository.save(grade1);
        repository.save(grade2);
        repository.save(grade3);

        List<Grade> grades = repository.findByStudent("S001");
        assertEquals(2, grades.size());
    }

    @Test
    @DisplayName("Should return empty list for null student ID")
    void testFindByStudentNull() {
        List<Grade> grades = repository.findByStudent(null);
        assertTrue(grades.isEmpty());
    }

    @Test
    @DisplayName("Should return empty list for empty student ID")
    void testFindByStudentEmpty() {
        List<Grade> grades = repository.findByStudent("");
        assertTrue(grades.isEmpty());
    }

    @Test
    @DisplayName("Should return empty list for non-existent student")
    void testFindByStudentNotFound() {
        repository.save(grade1);
        List<Grade> grades = repository.findByStudent("S999");
        assertTrue(grades.isEmpty());
    }

    // FindByCourse Tests
    @Test
    @DisplayName("Should find all grades for a course")
    void testFindByCourse() {
        repository.save(grade1);
        repository.save(grade2);
        repository.save(grade3);

        List<Grade> grades = repository.findByCourse("CS101");
        assertEquals(2, grades.size());
    }

    @Test
    @DisplayName("Should return empty list for null course ID")
    void testFindByCourseNull() {
        List<Grade> grades = repository.findByCourse(null);
        assertTrue(grades.isEmpty());
    }

    @Test
    @DisplayName("Should return empty list for empty course ID")
    void testFindByCourseEmpty() {
        List<Grade> grades = repository.findByCourse("");
        assertTrue(grades.isEmpty());
    }

    @Test
    @DisplayName("Should return empty list for non-existent course")
    void testFindByCourseNotFound() {
        repository.save(grade1);
        List<Grade> grades = repository.findByCourse("CS999");
        assertTrue(grades.isEmpty());
    }

    // FindByLetterGrade Tests
    @Test
    @DisplayName("Should find grades by letter grade")
    void testFindByLetterGrade() {
        repository.save(grade1); // 85.0 = B
        repository.save(grade2); // 92.0 = A
        repository.save(grade3); // 78.0 = C

        List<Grade> aGrades = repository.findByLetterGrade("A");
        assertEquals(1, aGrades.size());
    }

    @Test
    @DisplayName("Should return empty list for null letter grade")
    void testFindByLetterGradeNull() {
        List<Grade> grades = repository.findByLetterGrade(null);
        assertTrue(grades.isEmpty());
    }

    @Test
    @DisplayName("Should return empty list for empty letter grade")
    void testFindByLetterGradeEmpty() {
        List<Grade> grades = repository.findByLetterGrade("");
        assertTrue(grades.isEmpty());
    }

    // FindBySemester Tests
    @Test
    @DisplayName("Should find grades by semester and year")
    void testFindBySemester() {
        repository.save(grade1);
        repository.save(grade2);
        Grade grade4 = new Grade("S003", "CS201", 88.0, 2, 2024);
        repository.save(grade4);

        List<Grade> semester1 = repository.findBySemester(1, 2024);
        assertEquals(2, semester1.size());
    }

    @Test
    @DisplayName("Should return empty list for invalid semester (< 1)")
    void testFindBySemesterInvalidLow() {
        List<Grade> grades = repository.findBySemester(0, 2024);
        assertTrue(grades.isEmpty());
    }

    @Test
    @DisplayName("Should return empty list for invalid semester (> 3)")
    void testFindBySemesterInvalidHigh() {
        List<Grade> grades = repository.findBySemester(4, 2024);
        assertTrue(grades.isEmpty());
    }

    @Test
    @DisplayName("Should return empty list for invalid year (< 2000)")
    void testFindBySemesterInvalidYearLow() {
        List<Grade> grades = repository.findBySemester(1, 1999);
        assertTrue(grades.isEmpty());
    }

    @Test
    @DisplayName("Should return empty list for invalid year (> 2100)")
    void testFindBySemesterInvalidYearHigh() {
        List<Grade> grades = repository.findBySemester(1, 2101);
        assertTrue(grades.isEmpty());
    }

    // FindAll Tests
    @Test
    @DisplayName("Should return all grades")
    void testFindAll() {
        repository.save(grade1);
        repository.save(grade2);
        repository.save(grade3);

        List<Grade> all = repository.findAll();
        assertEquals(3, all.size());
    }

    @Test
    @DisplayName("Should return empty list when no grades exist")
    void testFindAllEmpty() {
        List<Grade> all = repository.findAll();
        assertTrue(all.isEmpty());
    }

    // Delete Tests
    @Test
    @DisplayName("Should delete existing grade")
    void testDeleteExistingGrade() {
        repository.save(grade1);
        assertTrue(repository.delete("S001", "CS101"));
        assertEquals(0, repository.count());
    }

    @Test
    @DisplayName("Should return false when deleting non-existent grade")
    void testDeleteNonExistentGrade() {
        assertFalse(repository.delete("S999", "CS999"));
    }

    @Test
    @DisplayName("Should return false when deleting with null student ID")
    void testDeleteNullStudentId() {
        assertFalse(repository.delete(null, "CS101"));
    }

    @Test
    @DisplayName("Should return false when deleting with null course ID")
    void testDeleteNullCourseId() {
        assertFalse(repository.delete("S001", null));
    }

    // Count Tests
    @Test
    @DisplayName("Should count grades correctly")
    void testCount() {
        assertEquals(0, repository.count());
        repository.save(grade1);
        assertEquals(1, repository.count());
        repository.save(grade2);
        assertEquals(2, repository.count());
    }

    @Test
    @DisplayName("Should track total grades recorded")
    void testGetTotalGradesRecorded() {
        repository.save(grade1);
        repository.save(grade2);
        assertEquals(2, repository.getTotalGradesRecorded());

        repository.delete("S001", "CS101");
        assertEquals(2, repository.getTotalGradesRecorded());
    }

    // CalculateAverageGrade Tests
    @Test
    @DisplayName("Should calculate average grade correctly")
    void testCalculateAverageGrade() {
        repository.save(grade1); // 85.0
        repository.save(grade2); // 92.0
        repository.save(grade3); // 78.0

        double average = repository.calculateAverageGrade();
        assertEquals(85.0, average, 0.01);
    }

    @Test
    @DisplayName("Should return 0.0 for average grade when no grades")
    void testCalculateAverageGradeEmpty() {
        assertEquals(0.0, repository.calculateAverageGrade());
    }

    // CountByLetterGrade Tests
    @Test
    @DisplayName("Should count grades by letter grade")
    void testCountByLetterGrade() {
        repository.save(grade1); // B
        repository.save(grade2); // A
        repository.save(grade3); // C

        assertEquals(1, repository.countByLetterGrade("A"));
        assertEquals(1, repository.countByLetterGrade("B"));
        assertEquals(1, repository.countByLetterGrade("C"));
    }

    @Test
    @DisplayName("Should return 0 for null letter grade")
    void testCountByLetterGradeNull() {
        assertEquals(0, repository.countByLetterGrade(null));
    }

    @Test
    @DisplayName("Should return 0 for empty letter grade")
    void testCountByLetterGradeEmpty() {
        assertEquals(0, repository.countByLetterGrade(""));
    }

    // Clear Tests
    @Test
    @DisplayName("Should clear all grades")
    void testClear() {
        repository.save(grade1);
        repository.save(grade2);
        repository.clear();
        assertEquals(0, repository.count());
    }

    // Update Tests
    @Test
    @DisplayName("Should update existing grade")
    void testUpdate() {
        repository.save(grade1);
        grade1.setNumericGrade(95.0);
        assertTrue(repository.update(grade1));
        assertEquals(95.0, repository.findByStudentAndCourse("S001", "CS101").getNumericGrade());
    }

    @Test
    @DisplayName("Should not update non-existent grade")
    void testUpdateNonExistent() {
        assertFalse(repository.update(grade1));
    }

    @Test
    @DisplayName("Should not update null grade")
    void testUpdateNull() {
        assertFalse(repository.update(null));
    }

    @Test
    @DisplayName("Should not update grade with null student ID")
    void testUpdateNullStudentId() {
        Grade invalid = new Grade(null, "CS101", 85.0, 1, 2024);
        assertFalse(repository.update(invalid));
    }

    @Test
    @DisplayName("Should not update grade with null course ID")
    void testUpdateNullCourseId() {
        Grade invalid = new Grade("S001", null, 85.0, 1, 2024);
        assertFalse(repository.update(invalid));
    }

    // DeleteById Tests
    @Test
    @DisplayName("Should delete by IDs")
    void testDeleteById() {
        repository.save(grade1);
        assertTrue(repository.deleteById("S001", "CS101"));
        assertEquals(0, repository.count());
    }

    // FindByScoreRange Tests
    @Test
    @DisplayName("Should find grades by score range")
    void testFindByScoreRange() {
        repository.save(grade1); // 85.0
        repository.save(grade2); // 92.0
        repository.save(grade3); // 78.0

        List<Grade> grades = repository.findByScoreRange(80.0, 90.0);
        assertEquals(1, grades.size());
        assertEquals(85.0, grades.get(0).getNumericGrade());
    }

    @Test
    @DisplayName("Should return empty list for invalid score range (min < 0)")
    void testFindByScoreRangeInvalidMin() {
        List<Grade> grades = repository.findByScoreRange(-10.0, 100.0);
        assertTrue(grades.isEmpty());
    }

    @Test
    @DisplayName("Should return empty list for invalid score range (max > 100)")
    void testFindByScoreRangeInvalidMax() {
        List<Grade> grades = repository.findByScoreRange(0.0, 110.0);
        assertTrue(grades.isEmpty());
    }

    @Test
    @DisplayName("Should return empty list when min > max")
    void testFindByScoreRangeMinGreaterThanMax() {
        List<Grade> grades = repository.findByScoreRange(90.0, 80.0);
        assertTrue(grades.isEmpty());
    }

    @Test
    @DisplayName("Should find grades at exact boundaries")
    void testFindByScoreRangeBoundaries() {
        repository.save(grade1); // 85.0

        List<Grade> grades = repository.findByScoreRange(85.0, 85.0);
        assertEquals(1, grades.size());
    }

    @Test
    @DisplayName("Should handle multiple students with same course")
    void testMultipleStudentsSameCourse() {
        repository.save(grade1); // S001, CS101
        repository.save(grade3); // S002, CS101

        List<Grade> courseGrades = repository.findByCourse("CS101");
        assertEquals(2, courseGrades.size());

        List<Grade> student1Grades = repository.findByStudent("S001");
        assertEquals(1, student1Grades.size());
    }

    @Test
    @DisplayName("Should handle same student with multiple courses")
    void testSameStudentMultipleCourses() {
        repository.save(grade1); // S001, CS101
        repository.save(grade2); // S001, MATH201

        List<Grade> studentGrades = repository.findByStudent("S001");
        assertEquals(2, studentGrades.size());
    }
}