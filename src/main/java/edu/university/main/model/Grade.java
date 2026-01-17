package edu.university.main.model;



public class Grade {
    private String studentId;
    private String courseId;
    private double numericGrade;
    private String letterGrade;
    private int semester;
    private int year;
    private double midtermScore;
    private double finalScore;
    private double assignmentScore;
    private double participationScore;

    public Grade(String studentId, String courseId, double numericGrade, int semester, int year) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.numericGrade = numericGrade;
        this.semester = semester;
        this.year = year;
        this.letterGrade = calculateLetterGrade(numericGrade);
        this.midtermScore = 0.0;
        this.finalScore = 0.0;
        this.assignmentScore = 0.0;
        this.participationScore = 0.0;
    }

    public String calculateLetterGrade(double score) {
        if (score < 0.0 || score > 100.0) {
            return "INVALID";
        }

        if (score >= 93.0) {
            return "A";
        } else if (score >= 90.0) {
            return "A-";
        } else if (score >= 87.0) {
            return "B+";
        } else if (score >= 83.0) {
            return "B";
        } else if (score >= 80.0) {
            return "B-";
        } else if (score >= 77.0) {
            return "C+";
        } else if (score >= 73.0) {
            return "C";
        } else if (score >= 70.0) {
            return "C-";
        } else if (score >= 67.0) {
            return "D+";
        } else if (score >= 63.0) {
            return "D";
        } else if (score >= 60.0) {
            return "D-";
        } else {
            return "F";
        }
    }

    public double convertLetterToGPA(String letter) {
        if (letter == null || letter.isEmpty()) {
            return -1.0;
        }

        switch (letter.toUpperCase()) {
            case "A": return 4.0;
            case "A-": return 3.7;
            case "B+": return 3.3;
            case "B": return 3.0;
            case "B-": return 2.7;
            case "C+": return 2.3;
            case "C": return 2.0;
            case "C-": return 1.7;
            case "D+": return 1.3;
            case "D": return 1.0;
            case "D-": return 0.7;
            case "F": return 0.0;
            default: return -1.0;
        }
    }

    public double convertPercentageToGPA(double percentage) {
        if (percentage < 0.0 || percentage > 100.0) {
            return -1.0;
        }

        String letterGrade = calculateLetterGrade(percentage);
        return convertLetterToGPA(letterGrade);
    }

    public boolean isPassing(double score, boolean isGraduateCourse) {
        if (score < 0.0 || score > 100.0) {
            return false;
        }

        double passingThreshold = isGraduateCourse ? 70.0 : 60.0;
        return score >= passingThreshold;
    }

    public boolean isPassingWithHonors(double score, boolean isHonorsCourse) {
        if (score < 0.0 || score > 100.0) {
            return false;
        }

        double honorsThreshold = isHonorsCourse ? 87.0 : 93.0;
        return score >= honorsThreshold;
    }

    public double calculateWeightedScore(double midterm, double finalExam, double assignments,
                                         double midtermWeight, double finalWeight, double assignmentWeight) {
        if (midterm < 0 || midterm > 100 || finalExam < 0 || finalExam > 100 ||
                assignments < 0 || assignments > 100) {
            return -1.0;
        }

        if (midtermWeight < 0 || finalWeight < 0 || assignmentWeight < 0) {
            return -1.0;
        }

        double totalWeight = midtermWeight + finalWeight + assignmentWeight;
        if (Math.abs(totalWeight - 100.0) > 0.01 && Math.abs(totalWeight - 1.0) > 0.01) {
            return -1.0;
        }

        double normalizedMidterm = (totalWeight > 1.5) ? midtermWeight / 100.0 : midtermWeight;
        double normalizedFinal = (totalWeight > 1.5) ? finalWeight / 100.0 : finalWeight;
        double normalizedAssign = (totalWeight > 1.5) ? assignmentWeight / 100.0 : assignmentWeight;

        double weighted = (midterm * normalizedMidterm) + (finalExam * normalizedFinal) +
                (assignments * normalizedAssign);

        return Math.round(weighted * 100.0) / 100.0;
    }

    public double calculateComprehensiveScore(double midterm, double finalExam, double assignments,
                                              double participation, double attendance) {
        if (midterm < 0 || midterm > 100 || finalExam < 0 || finalExam > 100 ||
                assignments < 0 || assignments > 100 || participation < 0 || participation > 100 ||
                attendance < 0 || attendance > 100) {
            return -1.0;
        }

        double score = (midterm * 0.25) + (finalExam * 0.35) + (assignments * 0.25) +
                (participation * 0.10) + (attendance * 0.05);

        return Math.round(score * 100.0) / 100.0;
    }

    public double applyExtraCreditBonus(double baseScore, double extraCreditPoints, double maxExtraCredit) {
        if (baseScore < 0 || baseScore > 100 || extraCreditPoints < 0 || maxExtraCredit < 0) {
            return -1.0;
        }

        double bonusToApply = Math.min(extraCreditPoints, maxExtraCredit);
        double finalScore = baseScore + bonusToApply;

        if (finalScore > 100.0) {
            finalScore = 100.0;
        }

        return Math.round(finalScore * 100.0) / 100.0;
    }

    // Getters and Setters
    public String getStudentId() { return studentId; }
    public String getCourseId() { return courseId; }
    public double getNumericGrade() { return numericGrade; }
    public void setNumericGrade(double numericGrade) {
        this.numericGrade = numericGrade;
        this.letterGrade = calculateLetterGrade(numericGrade);
    }
    public String getLetterGrade() { return letterGrade; }
    public int getSemester() { return semester; }
    public int getYear() { return year; }
    public double getMidtermScore() { return midtermScore; }
    public void setMidtermScore(double midtermScore) { this.midtermScore = midtermScore; }
    public double getFinalScore() { return finalScore; }
    public void setFinalScore(double finalScore) { this.finalScore = finalScore; }
    public double getAssignmentScore() { return assignmentScore; }
    public void setAssignmentScore(double assignmentScore) { this.assignmentScore = assignmentScore; }
    public double getParticipationScore() { return participationScore; }
    public void setParticipationScore(double participationScore) { this.participationScore = participationScore; }
}
