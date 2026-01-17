package edu.university.main.service;
import java.util.List;

public class ReportingService {

    public double calculateGraduationRate(int graduated, int totalEnrolled) {
        if (graduated < 0 || totalEnrolled <= 0 || graduated > totalEnrolled) {
            return -1.0;
        }

        double rate = ((double) graduated / totalEnrolled) * 100.0;
        return Math.round(rate * 100.0) / 100.0;
    }

    public double calculateAverageTimeToGraduation(List<Integer> semestersToGraduate) {
        if (semestersToGraduate == null || semestersToGraduate.isEmpty()) {
            return -1.0;
        }

        int sum = 0;
        int count = 0;

        for (int semesters : semestersToGraduate) {
            if (semesters > 0 && semesters <= 20) {
                sum += semesters;
                count++;
            }
        }

        if (count == 0) {
            return -1.0;
        }

        double average = (double) sum / count;
        return Math.round(average * 100.0) / 100.0;
    }

    public double calculateDepartmentRevenue(int students, double tuitionPerStudent, double grantFunding) {
        if (students < 0 || tuitionPerStudent < 0 || grantFunding < 0) {
            return -1.0;
        }

        double tuitionRevenue = students * tuitionPerStudent;
        double totalRevenue = tuitionRevenue + grantFunding;

        return Math.round(totalRevenue * 100.0) / 100.0;
    }

    public String evaluateInstitutionalHealth(double financialStability, double studentSatisfaction, double academicQuality) {
        if (financialStability < 0 || financialStability > 100 ||
                studentSatisfaction < 0 || studentSatisfaction > 100 ||
                academicQuality < 0 || academicQuality > 100) {
            return "INVALID";
        }

        double overallScore = (financialStability * 0.35) + (studentSatisfaction * 0.30) + (academicQuality * 0.35);

        if (overallScore >= 85.0) {
            return "EXCELLENT";
        } else if (overallScore >= 70.0) {
            return "GOOD";
        } else if (overallScore >= 55.0) {
            return "FAIR";
        } else {
            return "POOR";
        }
    }

    public double calculateCostPerGraduate(double totalOperatingCost, int numberOfGraduates) {
        if (totalOperatingCost < 0 || numberOfGraduates <= 0) {
            return -1.0;
        }

        double costPerGrad = totalOperatingCost / numberOfGraduates;
        return Math.round(costPerGrad * 100.0) / 100.0;
    }
}
