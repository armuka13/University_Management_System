package System_Testing;


import edu.university.main.model.Faculty;
import edu.university.main.repository.FacultyRepository;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FR-FM: Faculty Management System Tests")
public class FacultyManagementSystemTest {

    private Faculty faculty;
    private FacultyRepository repository;

    @BeforeEach
    void setUp() {
        faculty = new Faculty("F001", "Dr. Smith", "smith@edu.edu", "CS", "ASSISTANT_PROFESSOR", 75000.0);
        faculty.setYearsOfExperience(4);
        faculty.setNumberOfPublications(8);
        repository = new FacultyRepository();
    }

    @Test
    @DisplayName("FR-FM-001: Teaching Load Calculation")
    void testTeachingLoadCalculation() {
        double load = faculty.calculateTeachingLoad(4, 120, 3);
        assertEquals(27.5, load, 0.01, "(4*3) + (120*0.1) + (3*2.5)");
    }

    @Test
    @DisplayName("FR-FM-002: Salary Increase - High Performance Long Tenure")
    void testSalaryIncrease_HighPerformanceLongTenure() {
        double increase = faculty.calculateSalaryIncrease(75000.0, 92.0, 4);
        assertEquals(6000.0, increase, 0.01, "8% for perf >= 90, years >= 3");
    }

    @Test
    @DisplayName("FR-FM-002: Salary Increase - Good Performance")
    void testSalaryIncrease_GoodPerformance() {
        double increase = faculty.calculateSalaryIncrease(75000.0, 85.0, 3);
        assertEquals(4500.0, increase, 0.01, "6% for perf >= 80, years >= 2");
    }

    @Test
    @DisplayName("FR-FM-002: Salary Increase - Satisfactory Performance")
    void testSalaryIncrease_SatisfactoryPerformance() {
        double increase = faculty.calculateSalaryIncrease(75000.0, 72.0, 1);
        assertEquals(3000.0, increase, 0.01, "4% for perf >= 70");
    }

    @Test
    @DisplayName("FR-FM-002: Salary Increase - Base Increase")
    void testSalaryIncrease_BaseIncrease() {
        double increase = faculty.calculateSalaryIncrease(75000.0, 65.0, 1);
        assertEquals(2250.0, increase, 0.01, "3% base increase");
    }

    @Test
    @DisplayName("FR-FM-003: Promotion Eligibility - Assistant to Associate (Eligible)")
    void testPromotionEligibility_AssistantToAssociate_Eligible() {
        boolean eligible = faculty.isEligibleForPromotion(6, 12, 80.0, 75.0);
        assertTrue(eligible, "Meets all criteria for Associate Professor");
    }

    @Test
    @DisplayName("FR-FM-003: Promotion Eligibility - Assistant to Associate (Not Enough Years)")
    void testPromotionEligibility_AssistantToAssociate_NotEnoughYears() {
        boolean eligible = faculty.isEligibleForPromotion(5, 12, 80.0, 75.0);
        assertFalse(eligible, "Need 6 years at rank");
    }

    @Test
    @DisplayName("FR-FM-003: Promotion Eligibility - Associate to Full (Eligible)")
    void testPromotionEligibility_AssociateToFull_Eligible() {
        faculty.setRank("ASSOCIATE_PROFESSOR");
        boolean eligible = faculty.isEligibleForPromotion(5, 22, 85.0, 78.0);
        assertTrue(eligible, "Meets all criteria for Full Professor");
    }

    @Test
    @DisplayName("FR-FM-003: Promotion Eligibility - Full Professor")
    void testPromotionEligibility_FullProfessor() {
        faculty.setRank("PROFESSOR");
        boolean eligible = faculty.isEligibleForPromotion(10, 40, 90.0, 85.0);
        assertFalse(eligible, "Already at highest rank");
    }

    @Test
    @DisplayName("FR-FM-004: Tenure Eligibility - Eligible")
    void testTenureEligibility_Eligible() {
        boolean eligible = faculty.isEligibleForTenure(6, 18, 85.0, 80.0, true);
        assertTrue(eligible, "Meets all tenure requirements");
    }

    @Test
    @DisplayName("FR-FM-004: Tenure Eligibility - Insufficient Publications")
    void testTenureEligibility_InsufficientPublications() {
        boolean eligible = faculty.isEligibleForTenure(6, 12, 85.0, 80.0, true);
        assertFalse(eligible, "Need 15 publications");
    }

    @Test
    @DisplayName("FR-FM-004: Tenure Eligibility - No Grant Funding")
    void testTenureEligibility_NoGrantFunding() {
        boolean eligible = faculty.isEligibleForTenure(6, 18, 85.0, 80.0, false);
        assertFalse(eligible, "Need grant funding");
    }

    @Test
    @DisplayName("FR-FM-005: Research Productivity")
    void testResearchProductivity() {
        double productivity = faculty.calculateResearchProductivity(20, 500000.0, 5);
        assertEquals(50.0, productivity, 0.01, "((20*10) + (500000/10000)) / 5");
    }

    @Test
    @DisplayName("FR-FM-006: Workload Category - Overloaded")
    void testWorkloadCategory_Overloaded() {
        String category = faculty.determineWorkloadCategory(45.0, 40.0, 20.0);
        assertEquals("OVERLOADED", category, "Total >= 100");
    }

    @Test
    @DisplayName("FR-FM-006: Workload Category - Full Load")
    void testWorkloadCategory_FullLoad() {
        String category = faculty.determineWorkloadCategory(35.0, 35.0, 20.0);
        assertEquals("FULL_LOAD", category, "Total 80-99");
    }

    @Test
    @DisplayName("FR-FM-006: Workload Category - Moderate Load")
    void testWorkloadCategory_ModerateLoad() {
        String category = faculty.determineWorkloadCategory(25.0, 25.0, 15.0);
        assertEquals("MODERATE_LOAD", category, "Total 60-79");
    }

    @Test
    @DisplayName("FR-FM-006: Workload Category - Light Load")
    void testWorkloadCategory_LightLoad() {
        String category = faculty.determineWorkloadCategory(20.0, 20.0, 10.0);
        assertEquals("LIGHT_LOAD", category, "Total < 60");
    }
}