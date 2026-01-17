package edu.university.main.ServiceTest;

import edu.university.main.service.GradeCalculationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GradeCalculationTest {

    private GradeCalculationService service;

    @BeforeEach
    void setUp() {
        service = new GradeCalculationService();
    }

    @Test
    void testCalculateCourseAverage() {
        List<Double> scores = Arrays.asList(85.0, 90.0, 88.0, 92.0);

        double average = service.calculateCourseAverage(scores);

        assertEquals(88.75, average);
    }

    @Test
    void testCalculateCourseAverageWithInvalidScores() {
        List<Double> scores = Arrays.asList(85.0, 105.0, 88.0, -5.0, 92.0);

        double average = service.calculateCourseAverage(scores);

        assertEquals(88.33, average);
    }

    @Test
    void testCalculateCourseAverageNull() {
        double average = service.calculateCourseAverage(null);

        assertEquals(-1.0, average);
    }

    @Test
    void testCalculateCourseAverageEmpty() {
        List<Double> scores = Arrays.asList();

        double average = service.calculateCourseAverage(scores);

        assertEquals(-1.0, average);
    }

    @Test
    void testCalculateCourseAverageAllInvalid() {
        List<Double> scores = Arrays.asList(-10.0, 105.0, 110.0);

        double average = service.calculateCourseAverage(scores);

        assertEquals(-1.0, average);
    }

    @Test
    void testCalculateWeightedAverage() {
        List<Double> scores = Arrays.asList(80.0, 90.0, 85.0);
        List<Double> weights = Arrays.asList(0.3, 0.4, 0.3);

        double average = service.calculateWeightedAverage(scores, weights);

        assertEquals(85.5, average);
    }

    @Test
    void testCalculateWeightedAverageUnequalWeights() {
        List<Double> scores = Arrays.asList(80.0, 95.0);
        List<Double> weights = Arrays.asList(1.0, 2.0);

        double average = service.calculateWeightedAverage(scores, weights);

        assertEquals(90.0, average);
    }

    @Test
    void testCalculateWeightedAverageNullScores() {
        List<Double> weights = Arrays.asList(0.5, 0.5);

        double average = service.calculateWeightedAverage(null, weights);

        assertEquals(-1.0, average);
    }

    @Test
    void testCalculateWeightedAverageMismatchedSizes() {
        List<Double> scores = Arrays.asList(80.0, 90.0);
        List<Double> weights = Arrays.asList(0.5);

        double average = service.calculateWeightedAverage(scores, weights);

        assertEquals(-1.0, average);
    }

    @Test
    void testCalculateWeightedAverageInvalidScore() {
        List<Double> scores = Arrays.asList(80.0, 105.0);
        List<Double> weights = Arrays.asList(0.5, 0.5);

        double average = service.calculateWeightedAverage(scores, weights);

        assertEquals(-1.0, average);
    }

    @Test
    void testApplyBonusPointsBoth() {
        double score = service.applyBonusPoints(85.0, true, true);

        assertEquals(90.0, score);
    }

    @Test
    void testApplyBonusPointsAttendanceOnly() {
        double score = service.applyBonusPoints(85.0, true, false);

        assertEquals(87.0, score);
    }

    @Test
    void testApplyBonusPointsParticipationOnly() {
        double score = service.applyBonusPoints(85.0, false, true);

        assertEquals(87.0, score);
    }

    @Test
    void testApplyBonusPointsNeither() {
        double score = service.applyBonusPoints(85.0, false, false);

        assertEquals(85.0, score);
    }

    @Test
    void testApplyBonusPointsCappedAtHundred() {
        double score = service.applyBonusPoints(98.0, true, true);

        assertEquals(100.0, score);
    }

    @Test
    void testApplyBonusPointsInvalidScore() {
        double score = service.applyBonusPoints(105.0, true, true);

        assertEquals(-1.0, score);
    }

    @Test
    void testCalculateCurvedGrade() {
        double curved = service.calculateCurvedGrade(75.0, 70.0, 80.0);

        assertEquals(85.0, curved);
    }

    @Test
    void testCalculateCurvedGradeNegativeCurve() {
        double curved = service.calculateCurvedGrade(85.0, 80.0, 75.0);

        assertEquals(80.0, curved);
    }

    @Test
    void testCalculateCurvedGradeCappedAtHundred() {
        double curved = service.calculateCurvedGrade(95.0, 70.0, 80.0);

        assertEquals(100.0, curved);
    }

    @Test
    void testCalculateCurvedGradeFlooredAtZero() {
        double curved = service.calculateCurvedGrade(10.0, 80.0, 70.0);

        assertEquals(0.0, curved);
    }

    @Test
    void testCalculateCurvedGradeInvalidScore() {
        double curved = service.calculateCurvedGrade(105.0, 70.0, 80.0);

        assertEquals(-1.0, curved);
    }

    @Test
    void testCalculateStandardDeviation() {
        List<Double> scores = Arrays.asList(80.0, 85.0, 90.0, 95.0, 100.0);

        double stdDev = service.calculateStandardDeviation(scores);

        assertEquals(7.91, stdDev);
    }

    @Test
    void testCalculateStandardDeviationTwoScores() {
        List<Double> scores = Arrays.asList(80.0, 90.0);

        double stdDev = service.calculateStandardDeviation(scores);

        assertEquals(7.07, stdDev);
    }

    @Test
    void testCalculateStandardDeviationNull() {
        double stdDev = service.calculateStandardDeviation(null);

        assertEquals(-1.0, stdDev);
    }

    @Test
    void testCalculateStandardDeviationOneScore() {
        List<Double> scores = Arrays.asList(85.0);

        double stdDev = service.calculateStandardDeviation(scores);

        assertEquals(-1.0, stdDev);
    }

    @Test
    void testCalculateClassRankTopFivePercent() {
        int rank = service.calculateClassRank(3.95, 100);

        assertEquals(5, rank);
    }

    @Test
    void testCalculateClassRankTopTenPercent() {
        int rank = service.calculateClassRank(3.75, 100);

        assertEquals(10, rank);
    }

    @Test
    void testCalculateClassRankTopTwentyPercent() {
        int rank = service.calculateClassRank(3.6, 100);

        assertEquals(20, rank);
    }

    @Test
    void testCalculateClassRankTopFiftyPercent() {
        int rank = service.calculateClassRank(3.2, 100);

        assertEquals(50, rank);
    }

    @Test
    void testCalculateClassRankBottomQuarter() {
        int rank = service.calculateClassRank(2.5, 100);

        assertEquals(75, rank);
    }

    @Test
    void testCalculateClassRankInvalidGPA() {
        int rank = service.calculateClassRank(5.0, 100);

        assertEquals(-1, rank);
    }

    @Test
    void testCalculateClassRankZeroStudents() {
        int rank = service.calculateClassRank(3.5, 0);

        assertEquals(-1, rank);
    }

    @Test
    void testIsEligibleForHonorsSummaCumLaude() {
        boolean eligible = service.isEligibleForHonors(3.95, 120, 120);

        assertTrue(eligible);
    }

    @Test
    void testIsEligibleForHonorsMagnaCumLaude() {
        boolean eligible = service.isEligibleForHonors(3.75, 120, 120);

        assertTrue(eligible);
    }

    @Test
    void testIsEligibleForHonorsNotEnoughCredits() {
        boolean eligible = service.isEligibleForHonors(3.95, 100, 120);

        assertFalse(eligible);
    }

    @Test
    void testIsEligibleForHonorsLowGPA() {
        boolean eligible = service.isEligibleForHonors(3.5, 120, 120);

        assertFalse(eligible);
    }

    @Test
    void testIsEligibleForHonorsInvalidGPA() {
        boolean eligible = service.isEligibleForHonors(5.0, 120, 120);

        assertFalse(eligible);
    }

    @Test
    void testDetermineHonorsLevelSumma() {
        String level = service.determineHonorsLevel(3.95, 120);

        assertEquals("SUMMA_CUM_LAUDE", level);
    }

    @Test
    void testDetermineHonorsLevelMagna() {
        String level = service.determineHonorsLevel(3.75, 120);

        assertEquals("MAGNA_CUM_LAUDE", level);
    }

    @Test
    void testDetermineHonorsLevelCum() {
        String level = service.determineHonorsLevel(3.6, 120);

        assertEquals("CUM_LAUDE", level);
    }

    @Test
    void testDetermineHonorsLevelNone() {
        String level = service.determineHonorsLevel(3.2, 120);

        assertEquals("NONE", level);
    }

    @Test
    void testDetermineHonorsLevelInsufficientCredits() {
        String level = service.determineHonorsLevel(3.95, 100);

        assertEquals("NONE", level);
    }

    @Test
    void testCalculateDroppedLowestScores() {
        List<Double> scores = Arrays.asList(70.0, 80.0, 85.0, 90.0, 95.0);

        double average = service.calculateDroppedLowestScores(scores, 2);

        assertEquals(90.0, average);
    }

    @Test
    void testCalculateDroppedLowestScoresDropOne() {
        List<Double> scores = Arrays.asList(60.0, 85.0, 90.0, 92.0);

        double average = service.calculateDroppedLowestScores(scores, 1);

        assertEquals(89.0, average);
    }

    @Test
    void testCalculateDroppedLowestScoresNull() {
        double average = service.calculateDroppedLowestScores(null, 1);

        assertEquals(-1.0, average);
    }

    @Test
    void testCalculateDroppedLowestScoresNegativeDrop() {
        List<Double> scores = Arrays.asList(70.0, 80.0, 90.0);

        double average = service.calculateDroppedLowestScores(scores, -1);

        assertEquals(-1.0, average);
    }

    @Test
    void testCalculateDroppedLowestScoresDropAll() {
        List<Double> scores = Arrays.asList(70.0, 80.0, 90.0);

        double average = service.calculateDroppedLowestScores(scores, 3);

        assertEquals(-1.0, average);
    }
}