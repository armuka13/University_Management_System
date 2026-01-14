package edu.university.main.service;



import java.util.List;

public class GradeCalculationService {

    public double calculateCourseAverage(List<Double> scores) {
        if (scores == null || scores.isEmpty()) {
            return -1.0;
        }

        double sum = 0.0;
        int validScores = 0;

        for (double score : scores) {
            if (score < 0.0 || score > 100.0) {
                continue;
            }
            sum += score;
            validScores++;
        }

        if (validScores == 0) {
            return -1.0;
        }

        double average = sum / validScores;
        return Math.round(average * 100.0) / 100.0;
    }

    public double calculateWeightedAverage(List<Double> scores, List<Double> weights) {
        if (scores == null || weights == null || scores.isEmpty() || weights.isEmpty()) {
            return -1.0;
        }

        if (scores.size() != weights.size()) {
            return -1.0;
        }

        double weightedSum = 0.0;
        double totalWeight = 0.0;

        for (int i = 0; i < scores.size(); i++) {
            double score = scores.get(i);
            double weight = weights.get(i);

            if (score < 0.0 || score > 100.0 || weight < 0.0) {
                return -1.0;
            }

            weightedSum += score * weight;
            totalWeight += weight;
        }

        if (totalWeight == 0.0) {
            return -1.0;
        }

        double average = weightedSum / totalWeight;
        return Math.round(average * 100.0) / 100.0;
    }

    public double applyBonusPoints(double baseScore, boolean hasAttendance, boolean hasParticipation) {
        if (baseScore < 0.0 || baseScore > 100.0) {
            return -1.0;
        }

        double finalScore = baseScore;

        if (hasAttendance && hasParticipation) {
            finalScore += 5.0;
        } else if (hasAttendance || hasParticipation) {
            finalScore += 2.0;
        }

        if (finalScore > 100.0) {
            finalScore = 100.0;
        }

        return Math.round(finalScore * 100.0) / 100.0;
    }

    public double calculateCurvedGrade(double rawScore, double classAverage, double targetAverage) {
        if (rawScore < 0.0 || rawScore > 100.0 || classAverage < 0.0 ||
                classAverage > 100.0 || targetAverage < 0.0 || targetAverage > 100.0) {
            return -1.0;
        }

        double curve = targetAverage - classAverage;
        double curvedScore = rawScore + curve;

        if (curvedScore > 100.0) {
            curvedScore = 100.0;
        } else if (curvedScore < 0.0) {
            curvedScore = 0.0;
        }

        return Math.round(curvedScore * 100.0) / 100.0;
    }

    public double calculateStandardDeviation(List<Double> scores) {
        if (scores == null || scores.isEmpty() || scores.size() < 2) {
            return -1.0;
        }

        double mean = calculateCourseAverage(scores);
        if (mean < 0) {
            return -1.0;
        }

        double sumSquaredDiff = 0.0;
        int validScores = 0;

        for (double score : scores) {
            if (score >= 0.0 && score <= 100.0) {
                double diff = score - mean;
                sumSquaredDiff += diff * diff;
                validScores++;
            }
        }

        if (validScores < 2) {
            return -1.0;
        }

        double variance = sumSquaredDiff / (validScores - 1);
        double stdDev = Math.sqrt(variance);

        return Math.round(stdDev * 100.0) / 100.0;
    }

    public int calculateClassRank(double gpa, int totalStudents) {
        if (gpa < 0.0 || gpa > 4.0 || totalStudents <= 0) {
            return -1;
        }

        int estimatedRank = 0;

        if (gpa >= 3.9) {
            estimatedRank = (int) Math.ceil(totalStudents * 0.05);
        } else if (gpa >= 3.7) {
            estimatedRank = (int) Math.ceil(totalStudents * 0.10);
        } else if (gpa >= 3.5) {
            estimatedRank = (int) Math.ceil(totalStudents * 0.20);
        } else if (gpa >= 3.0) {
            estimatedRank = (int) Math.ceil(totalStudents * 0.50);
        } else {
            estimatedRank = (int) Math.ceil(totalStudents * 0.75);
        }

        return Math.max(estimatedRank, 1);
    }

    public boolean isEligibleForHonors(double gpa, int completedCredits, int totalCredits) {
        if (gpa < 0.0 || gpa > 4.0 || completedCredits < 0 || totalCredits <= 0) {
            return false;
        }

        if (completedCredits < totalCredits) {
            return false;
        }

        if (gpa >= 3.9 && totalCredits >= 120) {
            return true;
        }

        if (gpa >= 3.7 && totalCredits >= 120) {
            return true;
        }

        return false;
    }

    public String determineHonorsLevel(double gpa, int totalCredits) {
        if (gpa < 0.0 || gpa > 4.0 || totalCredits < 120) {
            return "NONE";
        }

        if (gpa >= 3.9) {
            return "SUMMA_CUM_LAUDE";
        } else if (gpa >= 3.7) {
            return "MAGNA_CUM_LAUDE";
        } else if (gpa >= 3.5) {
            return "CUM_LAUDE";
        } else {
            return "NONE";
        }
    }

    public double calculateDroppedLowestScores(List<Double> scores, int numToDrop) {
        if (scores == null || scores.isEmpty() || numToDrop < 0 || numToDrop >= scores.size()) {
            return -1.0;
        }

        List<Double> sortedScores = new java.util.ArrayList<>(scores);
        java.util.Collections.sort(sortedScores);

        double sum = 0.0;
        int count = 0;

        for (int i = numToDrop; i < sortedScores.size(); i++) {
            if (sortedScores.get(i) >= 0.0 && sortedScores.get(i) <= 100.0) {
                sum += sortedScores.get(i);
                count++;
            }
        }

        if (count == 0) {
            return -1.0;
        }

        double average = sum / count;
        return Math.round(average * 100.0) / 100.0;
    }
}
