package Integration_Tests;

import edu.university.main.controller.GradeController;
import edu.university.main.service.GradeCalculationService;
import edu.university.main.view.GradeView;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GradeControllerIntegrationTest {

    @Test
    void testCalculateCourseAverage() {
        GradeCalculationService service = new GradeCalculationService();
        SpyGradeView view = new SpyGradeView();
        GradeController controller = new GradeController(service, view);

        controller.calculateCourseAverage(List.of(80.0, 90.0, 85.0));

        view.verifyAverage(85.0);
    }

    @Test
    void testApplyBonus() {
        GradeCalculationService service = new GradeCalculationService();
        SpyGradeView view = new SpyGradeView();
        GradeController controller = new GradeController(service, view);

        controller.applyBonus(80, true, true);

        assertTrue(view.finalScore > 80);
    }

    @Test
    void testCalculateCurve() {
        GradeCalculationService service = new GradeCalculationService();
        SpyGradeView view = new SpyGradeView();
        GradeController controller = new GradeController(service, view);

        controller.calculateCurve(70, 75, 80);

        assertTrue(view.curvedGrade >= 70);
    }

    @Test
    void testCheckHonorsEligibility() {
        GradeCalculationService service = new GradeCalculationService();
        SpyGradeView view = new SpyGradeView();
        GradeController controller = new GradeController(service, view);

        controller.checkHonorsEligibility(3.9, 120, 120);

        assertTrue(view.honorsEligible);
    }

    // ===== SPY VIEW =====
    private static class SpyGradeView extends GradeView {
        double average;
        double finalScore;
        double curvedGrade;
        boolean honorsEligible;

        @Override
        public void displayAverage(double avg) {
            average = avg;
        }

        @Override
        public void displayFinalScore(double score) {
            finalScore = score;
        }

        @Override
        public void displayCurvedGrade(double grade) {
            curvedGrade = grade;
        }

        @Override
        public void displayHonorsEligibility(boolean eligible) {
            honorsEligible = eligible;
        }

        void verifyAverage(double expected) {
            assertEquals(expected, average, 0.01);
        }
    }
}
