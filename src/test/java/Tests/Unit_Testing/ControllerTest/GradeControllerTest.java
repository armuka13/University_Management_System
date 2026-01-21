package Tests.Unit_Testing.ControllerTest;

import edu.university.main.controller.GradeController;
import edu.university.main.service.GradeCalculationService;
import edu.university.main.view.GradeView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GradeControllerTest {

    private GradeController controller;
    private StubGradeCalculationService gradeService;
    private SpyGradeView view;

    @BeforeEach
    void setUp() {
        gradeService = new StubGradeCalculationService();
        view = new SpyGradeView();
        controller = new GradeController(gradeService, view);
    }

    @Test
    void testCalculateCourseAverage() {
        List<Double> scores = Arrays.asList(85.0, 90.0, 78.0, 92.0);
        gradeService.setAverageToReturn(86.25);

        controller.calculateCourseAverage(scores);

        assertEquals(scores, gradeService.getScoresReceived());
        assertEquals(86.25, view.getAverageDisplayed());
    }

    @Test
    void testCalculateCourseAverageWithEmptyList() {
        List<Double> scores = Arrays.asList();
        gradeService.setAverageToReturn(0.0);

        controller.calculateCourseAverage(scores);

        assertEquals(scores, gradeService.getScoresReceived());
        assertEquals(0.0, view.getAverageDisplayed());
    }

    @Test
    void testApplyBonusWithAttendanceAndParticipation() {
        gradeService.setFinalScoreToReturn(90.0);

        controller.applyBonus(85.0, true, true);

        assertEquals(85.0, gradeService.getBaseScoreReceived());
        assertTrue(gradeService.getAttendanceReceived());
        assertTrue(gradeService.getParticipationReceived());
        assertEquals(90.0, view.getFinalScoreDisplayed());
    }

    @Test
    void testApplyBonusWithNoBonus() {
        gradeService.setFinalScoreToReturn(85.0);

        controller.applyBonus(85.0, false, false);

        assertEquals(85.0, gradeService.getBaseScoreReceived());
        assertFalse(gradeService.getAttendanceReceived());
        assertFalse(gradeService.getParticipationReceived());
        assertEquals(85.0, view.getFinalScoreDisplayed());
    }

    @Test
    void testCalculateCurve() {
        gradeService.setCurvedGradeToReturn(85.0);

        controller.calculateCurve(75.0, 70.0, 80.0);

        assertEquals(75.0, gradeService.getRawScoreReceived());
        assertEquals(70.0, gradeService.getClassAvgReceived());
        assertEquals(80.0, gradeService.getTargetAvgReceived());
        assertEquals(85.0, view.getCurvedGradeDisplayed());
    }

    @Test
    void testCheckHonorsEligibilityWhenEligible() {
        gradeService.setEligibleForHonors(true);

        controller.checkHonorsEligibility(3.8, 90, 120);

        assertEquals(3.8, gradeService.getGpaReceived());
        assertEquals(90, gradeService.getCompletedReceived());
        assertEquals(120, gradeService.getTotalReceived());
        assertTrue(view.getHonorsEligibilityDisplayed());
    }

    @Test
    void testCheckHonorsEligibilityWhenNotEligible() {
        gradeService.setEligibleForHonors(false);

        controller.checkHonorsEligibility(2.5, 30, 120);

        assertFalse(view.getHonorsEligibilityDisplayed());
    }

    @Test
    void testDetermineHonorsLevelSumma() {
        gradeService.setHonorsLevelToReturn("With High Honors");

        controller.determineHonorsLevel(3.9, 120);

        assertEquals(3.9, gradeService.getGpaForHonorsLevel());
        assertEquals(120, gradeService.getCreditsForHonorsLevel());
        assertEquals("With High Honors", view.getHonorsLevelDisplayed());
    }

    @Test
    void testDetermineHonorsLevelMagna() {
        gradeService.setHonorsLevelToReturn("With Honors");

        controller.determineHonorsLevel(3.7, 120);

        assertEquals("With Honors", view.getHonorsLevelDisplayed());
    }

    @Test
    void testDetermineHonorsLevelNone() {
        gradeService.setHonorsLevelToReturn("None");

        controller.determineHonorsLevel(2.8, 120);

        assertEquals("None", view.getHonorsLevelDisplayed());
    }

    // Test Doubles
    private static class StubGradeCalculationService extends GradeCalculationService {
        private double averageToReturn;
        private double finalScoreToReturn;
        private double curvedGradeToReturn;
        private boolean eligibleForHonors;
        private String honorsLevelToReturn;

        private List<Double> scoresReceived;
        private double baseScoreReceived;
        private boolean attendanceReceived;
        private boolean participationReceived;
        private double rawScoreReceived;
        private double classAvgReceived;
        private double targetAvgReceived;
        private double gpaReceived;
        private int completedReceived;
        private int totalReceived;
        private double gpaForHonorsLevel;
        private int creditsForHonorsLevel;

        public void setAverageToReturn(double average) {
            this.averageToReturn = average;
        }

        public void setFinalScoreToReturn(double score) {
            this.finalScoreToReturn = score;
        }

        public void setCurvedGradeToReturn(double curved) {
            this.curvedGradeToReturn = curved;
        }

        public void setEligibleForHonors(boolean eligible) {
            this.eligibleForHonors = eligible;
        }

        public void setHonorsLevelToReturn(String level) {
            this.honorsLevelToReturn = level;
        }

        @Override
        public double calculateCourseAverage(List<Double> scores) {
            this.scoresReceived = scores;
            return averageToReturn;
        }

        @Override
        public double applyBonusPoints(double baseScore, boolean attendance, boolean participation) {
            this.baseScoreReceived = baseScore;
            this.attendanceReceived = attendance;
            this.participationReceived = participation;
            return finalScoreToReturn;
        }

        @Override
        public double calculateCurvedGrade(double raw, double classAvg, double targetAvg) {
            this.rawScoreReceived = raw;
            this.classAvgReceived = classAvg;
            this.targetAvgReceived = targetAvg;
            return curvedGradeToReturn;
        }

        @Override
        public boolean isEligibleForHonors(double gpa, int completed, int total) {
            this.gpaReceived = gpa;
            this.completedReceived = completed;
            this.totalReceived = total;
            return eligibleForHonors;
        }

        @Override
        public String determineHonorsLevel(double gpa, int credits) {
            this.gpaForHonorsLevel = gpa;
            this.creditsForHonorsLevel = credits;
            return honorsLevelToReturn;
        }

        public List<Double> getScoresReceived() { return scoresReceived; }
        public double getBaseScoreReceived() { return baseScoreReceived; }
        public boolean getAttendanceReceived() { return attendanceReceived; }
        public boolean getParticipationReceived() { return participationReceived; }
        public double getRawScoreReceived() { return rawScoreReceived; }
        public double getClassAvgReceived() { return classAvgReceived; }
        public double getTargetAvgReceived() { return targetAvgReceived; }
        public double getGpaReceived() { return gpaReceived; }
        public int getCompletedReceived() { return completedReceived; }
        public int getTotalReceived() { return totalReceived; }
        public double getGpaForHonorsLevel() { return gpaForHonorsLevel; }
        public int getCreditsForHonorsLevel() { return creditsForHonorsLevel; }
    }

    private static class SpyGradeView extends GradeView {
        private double averageDisplayed;
        private double finalScoreDisplayed;
        private double curvedGradeDisplayed;
        private boolean honorsEligibilityDisplayed;
        private String honorsLevelDisplayed;

        @Override
        public void displayAverage(double average) {
            this.averageDisplayed = average;
        }

        @Override
        public void displayFinalScore(double finalScore) {
            this.finalScoreDisplayed = finalScore;
        }

        @Override
        public void displayCurvedGrade(double curved) {
            this.curvedGradeDisplayed = curved;
        }

        @Override
        public void displayHonorsEligibility(boolean eligible) {
            this.honorsEligibilityDisplayed = eligible;
        }

        @Override
        public void displayHonorsLevel(String level) {
            this.honorsLevelDisplayed = level;
        }

        public double getAverageDisplayed() { return averageDisplayed; }
        public double getFinalScoreDisplayed() { return finalScoreDisplayed; }
        public double getCurvedGradeDisplayed() { return curvedGradeDisplayed; }
        public boolean getHonorsEligibilityDisplayed() { return honorsEligibilityDisplayed; }
        public String getHonorsLevelDisplayed() { return honorsLevelDisplayed; }
    }
}