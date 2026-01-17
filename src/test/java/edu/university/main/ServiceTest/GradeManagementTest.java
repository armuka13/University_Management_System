package edu.university.main.ServiceTest;

import edu.university.main.model.Grade;
import edu.university.main.repository.GradeRepository;
import edu.university.main.service.GradeManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GradeManagementTest {

    private GradeManagementService service;
    private FakeGradeRepository repository;

    @BeforeEach
    void setUp() {
        repository = new FakeGradeRepository();
        service = new GradeManagementService(repository);
    }

    @Test
    void testCreateGradeSuccess() {
        boolean result = service.createGrade("S001", "CS101", 85.0, 1, 2024);

        assertTrue(result);
        assertNotNull(repository.findByStudentAndCourse("S001", "CS101"));
    }

    @Test
    void testCreateGradeNullStudentId() {
        boolean result = service.createGrade(null, "CS101", 85.0, 1, 2024);

        assertFalse(result);
    }

    @Test
    void testCreateGradeNullCourseId() {
        boolean result = service.createGrade("S001", null, 85.0, 1, 2024);

        assertFalse(result);
    }

    @Test
    void testCreateGradeInvalidScore() {
        boolean result = service.createGrade("S001", "CS101", 105.0, 1, 2024);

        assertFalse(result);
    }

    @Test
    void testCreateGradeAlreadyExists() {
        repository.save(new Grade("S001", "CS101", 85.0, 1, 2024));

        boolean result = service.createGrade("S001", "CS101", 90.0, 1, 2024);

        assertFalse(result);
    }

    @Test
    void testGetGradeExists() {
        Grade grade = new Grade("S001", "CS101", 85.0, 1, 2024);
        repository.save(grade);

        Grade result = service.getGrade("S001", "CS101");

        assertNotNull(result);
        assertEquals(85.0, result.getNumericGrade());
    }

    @Test
    void testGetGradeNotExists() {
        Grade result = service.getGrade("S999", "CS999");

        assertNull(result);
    }

    @Test
    void testGetAllGrades() {
        repository.save(new Grade("S001", "CS101", 85.0, 1, 2024));
        repository.save(new Grade("S002", "CS101", 90.0, 1, 2024));

        List<Grade> grades = service.getAllGrades();

        assertEquals(2, grades.size());
    }

    @Test
    void testGetGradesByStudent() {
        repository.save(new Grade("S001", "CS101", 85.0, 1, 2024));
        repository.save(new Grade("S001", "MATH101", 90.0, 1, 2024));
        repository.save(new Grade("S002", "CS101", 88.0, 1, 2024));

        List<Grade> grades = service.getGradesByStudent("S001");

        assertEquals(2, grades.size());
    }

    @Test
    void testGetGradesByCourse() {
        repository.save(new Grade("S001", "CS101", 85.0, 1, 2024));
        repository.save(new Grade("S002", "CS101", 90.0, 1, 2024));
        repository.save(new Grade("S001", "MATH101", 88.0, 1, 2024));

        List<Grade> grades = service.getGradesByCourse("CS101");

        assertEquals(2, grades.size());
    }

    @Test
    void testGetGradesByLetterGrade() {
        repository.save(new Grade("S001", "CS101", 95.0, 1, 2024));
        repository.save(new Grade("S002", "CS101", 94.0, 1, 2024));
        repository.save(new Grade("S003", "CS101", 85.0, 1, 2024));

        List<Grade> grades = service.getGradesByLetterGrade("A");

        assertEquals(2, grades.size());
    }

    @Test
    void testGetGradesBySemester() {
        repository.save(new Grade("S001", "CS101", 85.0, 1, 2024));
        repository.save(new Grade("S002", "CS101", 90.0, 1, 2024));
        repository.save(new Grade("S003", "CS101", 88.0, 2, 2024));

        List<Grade> grades = service.getGradesBySemester(1, 2024);

        assertEquals(2, grades.size());
    }

    @Test
    void testUpdateGradeSuccess() {
        Grade grade = new Grade("S001", "CS101", 85.0, 1, 2024);
        repository.save(grade);
        grade.setNumericGrade(90.0);

        boolean result = service.updateGrade(grade);

        assertTrue(result);
        assertEquals(90.0, repository.findByStudentAndCourse("S001", "CS101").getNumericGrade());
    }

    @Test
    void testUpdateGradeNull() {
        boolean result = service.updateGrade(null);

        assertFalse(result);
    }

    @Test
    void testUpdateGradeNotExists() {
        Grade grade = new Grade("S999", "CS999", 85.0, 1, 2024);

        boolean result = service.updateGrade(grade);

        assertFalse(result);
    }

    @Test
    void testUpdateGradeScoreSuccess() {
        repository.save(new Grade("S001", "CS101", 85.0, 1, 2024));

        boolean result = service.updateGradeScore("S001", "CS101", 92.0);

        assertTrue(result);
        assertEquals(92.0, repository.findByStudentAndCourse("S001", "CS101").getNumericGrade());
    }

    @Test
    void testUpdateGradeScoreInvalidLow() {
        repository.save(new Grade("S001", "CS101", 85.0, 1, 2024));

        boolean result = service.updateGradeScore("S001", "CS101", -5.0);

        assertFalse(result);
    }

    @Test
    void testUpdateGradeScoreInvalidHigh() {
        repository.save(new Grade("S001", "CS101", 85.0, 1, 2024));

        boolean result = service.updateGradeScore("S001", "CS101", 105.0);

        assertFalse(result);
    }

    @Test
    void testUpdateGradeScoreNotExists() {
        boolean result = service.updateGradeScore("S999", "CS999", 90.0);

        assertFalse(result);
    }

    @Test
    void testDeleteGradeSuccess() {
        repository.save(new Grade("S001", "CS101", 85.0, 1, 2024));

        boolean result = service.deleteGrade("S001", "CS101");

        assertTrue(result);
        assertNull(repository.findByStudentAndCourse("S001", "CS101"));
    }

    @Test
    void testDeleteGradeNotExists() {
        boolean result = service.deleteGrade("S999", "CS999");

        assertFalse(result);
    }

    @Test
    void testDeleteGradesByStudent() {
        repository.save(new Grade("S001", "CS101", 85.0, 1, 2024));
        repository.save(new Grade("S001", "MATH101", 90.0, 1, 2024));
        repository.save(new Grade("S002", "CS101", 88.0, 1, 2024));

        int deleted = service.deleteGradesByStudent("S001");

        assertEquals(2, deleted);
        assertEquals(1, repository.count());
    }

    @Test
    void testDeleteGradesByCourse() {
        repository.save(new Grade("S001", "CS101", 85.0, 1, 2024));
        repository.save(new Grade("S002", "CS101", 90.0, 1, 2024));
        repository.save(new Grade("S001", "MATH101", 88.0, 1, 2024));

        int deleted = service.deleteGradesByCourse("CS101");

        assertEquals(2, deleted);
        assertEquals(1, repository.count());
    }

    @Test
    void testGetTotalGrades() {
        repository.save(new Grade("S001", "CS101", 85.0, 1, 2024));
        repository.save(new Grade("S002", "CS101", 90.0, 1, 2024));

        int total = service.getTotalGrades();

        assertEquals(2, total);
    }

    @Test
    void testGetAverageGrade() {
        repository.save(new Grade("S001", "CS101", 80.0, 1, 2024));
        repository.save(new Grade("S002", "CS101", 90.0, 1, 2024));

        double avg = service.getAverageGrade();

        assertEquals(85.0, avg);
    }

    @Test
    void testCountGradesByLetter() {
        repository.save(new Grade("S001", "CS101", 95.0, 1, 2024));
        repository.save(new Grade("S002", "CS101", 94.0, 1, 2024));
        repository.save(new Grade("S003", "CS101", 85.0, 1, 2024));

        int count = service.countGradesByLetter("A");

        assertEquals(2, count);
    }

    @Test
    void testHasGradesTrue() {
        repository.save(new Grade("S001", "CS101", 85.0, 1, 2024));

        boolean hasGrades = service.hasGrades();

        assertTrue(hasGrades);
    }

    @Test
    void testHasGradesFalse() {
        boolean hasGrades = service.hasGrades();

        assertFalse(hasGrades);
    }

    // Test Double
    private static class FakeGradeRepository extends GradeRepository {
        private Map<String, Grade> grades = new HashMap<>();

        private String makeKey(String studentId, String courseId) {
            return studentId + ":" + courseId;
        }

        @Override
        public boolean save(Grade grade) {
            String key = makeKey(grade.getStudentId(), grade.getCourseId());
            grades.put(key, grade);
            return true;
        }

        @Override
        public Grade findByStudentAndCourse(String studentId, String courseId) {
            return grades.get(makeKey(studentId, courseId));
        }

        @Override
        public List<Grade> findAll() {
            return new ArrayList<>(grades.values());
        }

        @Override
        public List<Grade> findByStudent(String studentId) {
            List<Grade> result = new ArrayList<>();
            for (Grade g : grades.values()) {
                if (g.getStudentId().equals(studentId)) {
                    result.add(g);
                }
            }
            return result;
        }

        @Override
        public List<Grade> findByCourse(String courseId) {
            List<Grade> result = new ArrayList<>();
            for (Grade g : grades.values()) {
                if (g.getCourseId().equals(courseId)) {
                    result.add(g);
                }
            }
            return result;
        }

        @Override
        public List<Grade> findByLetterGrade(String letterGrade) {
            List<Grade> result = new ArrayList<>();
            for (Grade g : grades.values()) {
                if (g.getLetterGrade().equals(letterGrade)) {
                    result.add(g);
                }
            }
            return result;
        }

        @Override
        public List<Grade> findBySemester(int semester, int year) {
            List<Grade> result = new ArrayList<>();
            for (Grade g : grades.values()) {
                if (g.getSemester() == semester && g.getYear() == year) {
                    result.add(g);
                }
            }
            return result;
        }

        @Override
        public boolean update(Grade grade) {
            String key = makeKey(grade.getStudentId(), grade.getCourseId());
            if (!grades.containsKey(key)) {
                return false;
            }
            grades.put(key, grade);
            return true;
        }

        @Override
        public boolean delete(String studentId, String courseId) {
            String key = makeKey(studentId, courseId);
            if (!grades.containsKey(key)) {
                return false;
            }
            grades.remove(key);
            return true;
        }

        @Override
        public int count() {
            return grades.size();
        }

        @Override
        public double calculateAverageGrade() {
            if (grades.isEmpty()) return 0.0;
            double sum = 0.0;
            for (Grade g : grades.values()) {
                sum += g.getNumericGrade();
            }
            return sum / grades.size();
        }

        @Override
        public int countByLetterGrade(String letterGrade) {
            int count = 0;
            for (Grade g : grades.values()) {
                if (g.getLetterGrade().equals(letterGrade)) {
                    count++;
                }
            }
            return count;
        }
    }
}