package System_Testing1;


import edu.university.main.model.Grade;
import edu.university.main.service.GradeCalculationService;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

@DisplayName("FR-GC: Grade Calculation System Tests")
public class GradeCalculationSystemTest {

    private GradeCalculationService gradeService;
    private Grade grade;

    @BeforeEach
    void setUp() {
        gradeService = new GradeCalculationService();
        grade = new Grade("S001", "CS101", 87.5, 1, 2024);
    }

    @Test
    @DisplayName("FR-GC-001: Course Average - Valid Scores")
    void testCourseAverage_ValidScores() {
        List<Double> scores = Arrays.asList(85.0, 90.0, 78.0, 92.0);
        double average = gradeService.calculateCourseAverage(scores);
        assertEquals(86.25, average, 0.01, "Average of scores");
    }

    @Test
    @DisplayName("FR-GC-001: Course Average - With Invalid Scores")
    void testCourseAverage_WithInvalidScores() {
        List<Double> scores = Arrays.asList(85.0, 110.0, 78.0, -5.0, 92.0);
        double average = gradeService.calculateCourseAverage(scores);
        assertEquals(85.0, average, 0.01, "Should skip invalid scores");
    }

    @Test
    @DisplayName("FR-GC-002: Weighted Average")
    void testWeightedAverage() {
        List<Double> scores = Arrays.asList(85.0, 90.0, 88.0);
        List<Double> weights = Arrays.asList(0.3, 0.5, 0.2);
        double weighted = gradeService.calculateWeightedAverage(scores, weights);
        assertEquals(88.1, weighted, 0.01, "Weighted average");
    }

    @Test
    @DisplayName("FR-GC-003: Bonus Points - Both Bonuses")
    void testBonusPoints_Both() {
        double finalScore = gradeService.applyBonusPoints(85.0, true, true);
        assertEquals(90.0, finalScore, 0.01, "85 + 5 = 90");
    }

    @Test
    @DisplayName("FR-GC-003: Bonus Points - One Bonus")
    void testBonusPoints_One() {
        double finalScore = gradeService.applyBonusPoints(85.0, true, false);
        assertEquals(87.0, finalScore, 0.01, "85 + 2 = 87");
    }

    @Test
    @DisplayName("FR-GC-003: Bonus Points - Capped at 100")
    void testBonusPoints_CappedAt100() {
        double finalScore = gradeService.applyBonusPoints(98.0, true, true);
        assertEquals(100.0, finalScore, 0.01, "Should cap at 100");
    }

    @Test
    @DisplayName("FR-GC-004: Grade Curve - Positive Curve")
    void testGradeCurve_Positive() {
        double curved = gradeService.calculateCurvedGrade(75.0, 70.0, 75.0);
        assertEquals(80.0, curved, 0.01, "75 + (75-70) = 80");
    }

    @Test
    @DisplayName("FR-GC-004: Grade Curve - Negative Curve")
    void testGradeCurve_Negative() {
        double curved = gradeService.calculateCurvedGrade(85.0, 80.0, 75.0);
        assertEquals(80.0, curved, 0.01, "85 + (75-80) = 80");
    }

    @Test
    @DisplayName("FR-GC-005: Standard Deviation")
    void testStandardDeviation() {
        List<Double> scores = Arrays.asList(80.0, 85.0, 90.0, 75.0, 95.0);
        double stdDev = gradeService.calculateStandardDeviation(scores);
        assertTrue(stdDev > 7.0 && stdDev < 8.0, "Std dev should be ~7.9");
    }

    @Test
    @DisplayName("FR-GC-006: Class Rank - Top 5%")
    void testClassRank_Top5Percent() {
        int rank = gradeService.calculateClassRank(3.95, 200);
        assertEquals(10, rank, "Top 5% of 200 = rank 10");
    }

    @Test
    @DisplayName("FR-GC-006: Class Rank - Top 10%")
    void testClassRank_Top10Percent() {
        int rank = gradeService.calculateClassRank(3.75, 200);
        assertEquals(20, rank, "Top 10% of 200 = rank 20");
    }

    @Test
    @DisplayName("FR-GC-007: Honors Eligibility - Eligible")
    void testHonorsEligibility_Eligible() {
        boolean eligible = gradeService.isEligibleForHonors(3.8, 120, 120);
        assertTrue(eligible, "GPA >= 3.7 with enough credits");
    }

    @Test
    @DisplayName("FR-GC-008: Honors Level - Summa Cum Laude")
    void testHonorsLevel_SummaCumLaude() {
        String level = gradeService.determineHonorsLevel(3.92, 120);
        assertEquals("SUMMA_CUM_LAUDE", level, "GPA >= 3.9");
    }

    @Test
    @DisplayName("FR-GC-008: Honors Level - Magna Cum Laude")
    void testHonorsLevel_MagnaCumLaude() {
        String level = gradeService.determineHonorsLevel(3.75, 120);
        assertEquals("MAGNA_CUM_LAUDE", level, "GPA >= 3.7");
    }

    @Test
    @DisplayName("FR-GC-008: Honors Level - Cum Laude")
    void testHonorsLevel_CumLaude() {
        String level = gradeService.determineHonorsLevel(3.55, 120);
        assertEquals("CUM_LAUDE", level, "GPA >= 3.5");
    }

    @Test
    @DisplayName("FR-GC-009: Dropped Scores Average")
    void testDroppedScoresAverage() {
        List<Double> scores = Arrays.asList(60.0, 75.0, 85.0, 90.0, 95.0);
        double average = gradeService.calculateDroppedLowestScores(scores, 2);
        assertEquals(90.0, average, 0.01, "Drop 60 and 75, average of rest");
    }

    @Test
    @DisplayName("FR-GC-010: Letter Grade Conversion - A")
    void testLetterGradeConversion_A() {
        String letter = grade.calculateLetterGrade(94.0);
        assertEquals("A", letter, "94 is A");
    }

    @Test
    @DisplayName("FR-GC-010: Letter Grade Conversion - B+")
    void testLetterGradeConversion_BPlus() {
        String letter = grade.calculateLetterGrade(88.0);
        assertEquals("B+", letter, "88 is B+");
    }

    @Test
    @DisplayName("FR-GC-010: Letter Grade Conversion - F")
    void testLetterGradeConversion_F() {
        String letter = grade.calculateLetterGrade(55.0);
        assertEquals("F", letter, "55 is F");
    }

    @Test
    @DisplayName("FR-GC-011: GPA Conversion - A")
    void testGPAConversion_A() {
        double gpa = grade.convertLetterToGPA("A");
        assertEquals(4.0, gpa, 0.01, "A = 4.0");
    }

    @Test
    @DisplayName("FR-GC-011: GPA Conversion - B+")
    void testGPAConversion_BPlus() {
        double gpa = grade.convertLetterToGPA("B+");
        assertEquals(3.3, gpa, 0.01, "B+ = 3.3");
    }

    @Test
    @DisplayName("FR-GC-012: Percentage to GPA")
    void testPercentageToGPA() {
        double gpa = grade.convertPercentageToGPA(88.0);
        assertEquals(3.3, gpa, 0.01, "88% = B+ = 3.3");
    }

    @Test
    @DisplayName("FR-GC-013: Passing Grade - Undergraduate")
    void testPassingGrade_Undergraduate() {
        boolean passing = grade.isPassing(65.0, false);
        assertTrue(passing, "65 passes for undergrad");
    }

    @Test
    @DisplayName("FR-GC-013: Passing Grade - Graduate")
    void testPassingGrade_Graduate() {
        boolean passing = grade.isPassing(65.0, true);
        assertFalse(passing, "65 fails for graduate (need 70)");
    }

    @Test
    @DisplayName("FR-GC-014: Honors Passing")
    void testHonorsPassing() {
        boolean passing = grade.isPassingWithHonors(90.0, true);
        assertTrue(passing, "90 passes honors course");
    }

    @Test
    @DisplayName("FR-GC-015: Weighted Score")
    void testWeightedScore() {
        double weighted = grade.calculateWeightedScore(85.0, 90.0, 88.0, 30.0, 50.0, 20.0);
        assertEquals(87.6, weighted, 0.01, "Weighted score");
    }

    @Test
    @DisplayName("FR-GC-016: Comprehensive Score")
    void testComprehensiveScore() {
        double score = grade.calculateComprehensiveScore(85.0, 90.0, 88.0, 95.0, 100.0);
        assertEquals(88.75, score, 0.01, "Comprehensive score");
    }

    @Test
    @DisplayName("FR-GC-017: Extra Credit Bonus")
    void testExtraCreditBonus() {
        double finalScore = grade.applyExtraCreditBonus(85.0, 7.0, 5.0);
        assertEquals(90.0, finalScore, 0.01, "85 + min(7,5) = 90");
    }
}
