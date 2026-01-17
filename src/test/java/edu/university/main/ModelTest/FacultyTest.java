package edu.university.main.ModelTest;

import edu.university.main.model.Faculty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FacultyTest {

    private Faculty faculty;

    @BeforeEach
    void setUp() {
        faculty = new Faculty("F001", "Dr. Smith", "smith@university.edu",
                "Computer Science", "ASSISTANT_PROFESSOR", 75000.0);
    }

    @Test
    void testCalculateTeachingLoad() {
        double load = faculty.calculateTeachingLoad(3, 90, 2);

        assertEquals(23.0, load);
    }

    @Test
    void testCalculateTeachingLoadHeavyLoad() {
        double load = faculty.calculateTeachingLoad(4, 120, 3);

        assertEquals(31.5, load);
    }

    @Test
    void testCalculateTeachingLoadLightLoad() {
        double load = faculty.calculateTeachingLoad(2, 40, 1);

        assertEquals(12.5, load);
    }

    @Test
    void testCalculateTeachingLoadInvalidCourses() {
        double load = faculty.calculateTeachingLoad(-1, 90, 2);

        assertEquals(-1.0, load);
    }

    @Test
    void testCalculateTeachingLoadInvalidStudents() {
        double load = faculty.calculateTeachingLoad(3, -10, 2);

        assertEquals(-1.0, load);
    }

    @Test
    void testCalculateSalaryIncreaseHighPerformance() {
        double increase = faculty.calculateSalaryIncrease(80000.0, 92.0, 4);

        assertEquals(6400.0, increase);
    }

    @Test
    void testCalculateSalaryIncreaseGoodPerformance() {
        double increase = faculty.calculateSalaryIncrease(80000.0, 85.0, 2);

        assertEquals(4800.0, increase);
    }

    @Test
    void testCalculateSalaryIncreaseSatisfactoryPerformance() {
        double increase = faculty.calculateSalaryIncrease(80000.0, 75.0, 1);

        assertEquals(3200.0, increase);
    }

    @Test
    void testCalculateSalaryIncreaseBaseIncrease() {
        double increase = faculty.calculateSalaryIncrease(80000.0, 65.0, 1);

        assertEquals(2400.0, increase);
    }

    @Test
    void testCalculateSalaryIncreaseInvalidSalary() {
        double increase = faculty.calculateSalaryIncrease(-1000.0, 85.0, 2);

        assertEquals(-1.0, increase);
    }

    @Test
    void testCalculateSalaryIncreaseInvalidPerformance() {
        double increase = faculty.calculateSalaryIncrease(80000.0, 110.0, 2);

        assertEquals(-1.0, increase);
    }

    @Test
    void testIsEligibleForPromotionAssistantProfessor() {
        faculty.setRank("ASSISTANT_PROFESSOR");
        boolean eligible = faculty.isEligibleForPromotion(6, 12, 80.0, 75.0);

        assertTrue(eligible);
    }

    @Test
    void testIsEligibleForPromotionAssistantProfessorNotEnoughYears() {
        faculty.setRank("ASSISTANT_PROFESSOR");
        boolean eligible = faculty.isEligibleForPromotion(5, 12, 80.0, 75.0);

        assertFalse(eligible);
    }

    @Test
    void testIsEligibleForPromotionAssistantProfessorNotEnoughPublications() {
        faculty.setRank("ASSISTANT_PROFESSOR");
        boolean eligible = faculty.isEligibleForPromotion(6, 8, 80.0, 75.0);

        assertFalse(eligible);
    }

    @Test
    void testIsEligibleForPromotionAssociateProfessor() {
        faculty.setRank("ASSOCIATE_PROFESSOR");
        boolean eligible = faculty.isEligibleForPromotion(5, 22, 85.0, 80.0);

        assertTrue(eligible);
    }

    @Test
    void testIsEligibleForPromotionProfessor() {
        faculty.setRank("PROFESSOR");
        boolean eligible = faculty.isEligibleForPromotion(10, 50, 95.0, 90.0);

        assertFalse(eligible);
    }

    @Test
    void testIsEligibleForPromotionInvalidInput() {
        boolean eligible = faculty.isEligibleForPromotion(-1, 12, 80.0, 75.0);

        assertFalse(eligible);
    }

    @Test
    void testIsEligibleForTenureEligible() {
        boolean eligible = faculty.isEligibleForTenure(6, 18, 85.0, 80.0, true);

        assertTrue(eligible);
    }

    @Test
    void testIsEligibleForTenureNotEnoughYears() {
        boolean eligible = faculty.isEligibleForTenure(5, 18, 85.0, 80.0, true);

        assertFalse(eligible);
    }

    @Test
    void testIsEligibleForTenureNoGrantFunding() {
        boolean eligible = faculty.isEligibleForTenure(6, 18, 85.0, 80.0, false);

        assertFalse(eligible);
    }

    @Test
    void testIsEligibleForTenureLowPublications() {
        boolean eligible = faculty.isEligibleForTenure(6, 12, 85.0, 80.0, true);

        assertFalse(eligible);
    }

    @Test
    void testIsEligibleForTenureLowTeachingScore() {
        boolean eligible = faculty.isEligibleForTenure(6, 18, 75.0, 80.0, true);

        assertFalse(eligible);
    }

    @Test
    void testIsEligibleForTenureInvalidInput() {
        boolean eligible = faculty.isEligibleForTenure(-1, 18, 85.0, 80.0, true);

        assertFalse(eligible);
    }

    @Test
    void testCalculateResearchProductivity() {
        double productivity = faculty.calculateResearchProductivity(10, 50000.0, 5);

        assertEquals(21.0, productivity);
    }

    @Test
    void testCalculateResearchProductivityHighOutput() {
        double productivity = faculty.calculateResearchProductivity(25, 200000.0, 10);

        assertEquals(27.0, productivity);
    }

    @Test
    void testCalculateResearchProductivityNoGrants() {
        double productivity = faculty.calculateResearchProductivity(15, 0.0, 5);

        assertEquals(30.0, productivity);
    }

    @Test
    void testCalculateResearchProductivityInvalidPublications() {
        double productivity = faculty.calculateResearchProductivity(-5, 50000.0, 5);

        assertEquals(-1.0, productivity);
    }

    @Test
    void testCalculateResearchProductivityZeroYears() {
        double productivity = faculty.calculateResearchProductivity(10, 50000.0, 0);

        assertEquals(-1.0, productivity);
    }

    @Test
    void testDetermineWorkloadCategoryOverloaded() {
        String category = faculty.determineWorkloadCategory(40.0, 35.0, 30.0);

        assertEquals("OVERLOADED", category);
    }

    @Test
    void testDetermineWorkloadCategoryFullLoad() {
        String category = faculty.determineWorkloadCategory(30.0, 30.0, 30.0);

        assertEquals("FULL_LOAD", category);
    }

    @Test
    void testDetermineWorkloadCategoryModerateLoad() {
        String category = faculty.determineWorkloadCategory(25.0, 25.0, 20.0);

        assertEquals("MODERATE_LOAD", category);
    }

    @Test
    void testDetermineWorkloadCategoryLightLoad() {
        String category = faculty.determineWorkloadCategory(20.0, 15.0, 15.0);

        assertEquals("LIGHT_LOAD", category);
    }

    @Test
    void testDetermineWorkloadCategoryInvalid() {
        String category = faculty.determineWorkloadCategory(-10.0, 30.0, 30.0);

        assertEquals("INVALID", category);
    }

    @Test
    void testDetermineWorkloadCategoryBoundaryFullLoad() {
        String category = faculty.determineWorkloadCategory(30.0, 30.0, 20.0);

        assertEquals("FULL_LOAD", category);
    }

    @Test
    void testDetermineWorkloadCategoryBoundaryModerate() {
        String category = faculty.determineWorkloadCategory(20.0, 20.0, 20.0);

        assertEquals("MODERATE_LOAD", category);
    }

    @Test
    void testGettersAndSetters() {
        faculty.setName("Dr. Johnson");
        faculty.setEmail("johnson@university.edu");
        faculty.setDepartment("Mathematics");
        faculty.setRank("ASSOCIATE_PROFESSOR");
        faculty.setSalary(90000.0);
        faculty.setYearsOfExperience(10);
        faculty.setResearchScore(85.5);
        faculty.setNumberOfPublications(15);

        assertEquals("F001", faculty.getFacultyId());
        assertEquals("Dr. Johnson", faculty.getName());
        assertEquals("johnson@university.edu", faculty.getEmail());
        assertEquals("Mathematics", faculty.getDepartment());
        assertEquals("ASSOCIATE_PROFESSOR", faculty.getRank());
        assertEquals(90000.0, faculty.getSalary());
        assertEquals(10, faculty.getYearsOfExperience());
        assertEquals(85.5, faculty.getResearchScore());
        assertEquals(15, faculty.getNumberOfPublications());
        assertNotNull(faculty.getCourseIds());
    }
}