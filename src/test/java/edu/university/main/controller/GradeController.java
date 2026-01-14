package edu.university.main.controller;


import edu.university.main.service.GradeCalculationService;
import edu.university.main.view.GradeView;
import java.util.List;

public class GradeController {
    private GradeCalculationService gradeService;
    private GradeView view;

    public GradeController(GradeCalculationService gradeService, GradeView view) {
        this.gradeService = gradeService;
        this.view = view;
    }

    public void calculateCourseAverage(List<Double> scores) {
        double average = gradeService.calculateCourseAverage(scores);
        view.displayAverage(average);
    }

    public void applyBonus(double baseScore, boolean attendance, boolean participation) {
        double finalScore = gradeService.applyBonusPoints(baseScore, attendance, participation);
        view.displayFinalScore(finalScore);
    }

    public void calculateCurve(double raw, double classAvg, double targetAvg) {
        double curved = gradeService.calculateCurvedGrade(raw, classAvg, targetAvg);
        view.displayCurvedGrade(curved);
    }

    public void checkHonorsEligibility(double gpa, int completed, int total) {
        boolean eligible = gradeService.isEligibleForHonors(gpa, completed, total);
        view.displayHonorsEligibility(eligible);
    }

    public void determineHonorsLevel(double gpa, int credits) {
        String level = gradeService.determineHonorsLevel(gpa, credits);
        view.displayHonorsLevel(level);
    }
}
