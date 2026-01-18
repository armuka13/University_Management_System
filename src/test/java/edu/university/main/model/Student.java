
package edu.university.main.model;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String studentId;
    private String name;
    private String email;
    private int age;
    private String major;
    private double gpa;
    private List<String> enrolledCourseIds;
    private int creditHours;
    private String academicLevel;
    private boolean isInternational;
    private String residencyStatus;

    public Student(String studentId, String name, String email, int age, String major) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.age = age;
        this.major = major;
        this.gpa = 0.0;
        this.enrolledCourseIds = new ArrayList<>();
        this.creditHours = 0;
        this.academicLevel = "FRESHMAN";
        this.isInternational = false;
        this.residencyStatus = "IN_STATE";
    }

    public double calculateGPA(List<Double> grades, List<Integer> credits) {
        if (grades == null || credits == null || grades.isEmpty() || credits.isEmpty()) {
            return 0.0;
        }

        if (grades.size() != credits.size()) {
            return -1.0;
        }

        double totalPoints = 0.0;
        int totalCredits = 0;

        for (int i = 0; i < grades.size(); i++) {
            double grade = grades.get(i);
            int credit = credits.get(i);

            if (grade < 0.0 || grade > 4.0 || credit < 0) {
                return -1.0;
            }

            totalPoints += grade * credit;
            totalCredits += credit;
        }

        if (totalCredits == 0) {
            return 0.0;
        }

        return Math.round((totalPoints / totalCredits) * 100.0) / 100.0;
    }

    public double calculateSemesterGPA(List<Double> grades, List<Integer> credits, List<String> gradeModes) {
        if (grades == null || credits == null || gradeModes == null) {
            return -1.0;
        }

        if (grades.size() != credits.size() || grades.size() != gradeModes.size()) {
            return -1.0;
        }

        double totalPoints = 0.0;
        int totalCredits = 0;

        for (int i = 0; i < grades.size(); i++) {
            String mode = gradeModes.get(i);

            if (mode.equals("PASS_FAIL")) {
                continue;
            }

            double grade = grades.get(i);
            int credit = credits.get(i);

            if (grade < 0.0 || grade > 4.0 || credit < 0) {
                return -1.0;
            }

            totalPoints += grade * credit;
            totalCredits += credit;
        }

        if (totalCredits == 0) {
            return 0.0;
        }

        return Math.round((totalPoints / totalCredits) * 100.0) / 100.0;
    }

    public String determineAcademicStanding(double gpa, int creditHours) {
        if (gpa < 0.0 || gpa > 4.0 || creditHours < 0) {
            return "INVALID";
        }

        if (creditHours < 12) {
            return "PART_TIME";
        }

        if (gpa >= 3.5 && creditHours >= 15) {
            return "DEAN_LIST";
        } else if (gpa >= 3.0 && creditHours >= 12) {
            return "GOOD_STANDING";
        } else if (gpa >= 2.0 && creditHours >= 12) {
            return "SATISFACTORY";
        } else if (gpa >= 1.5 && gpa < 2.0) {
            return "PROBATION";
        } else {
            return "ACADEMIC_SUSPENSION";
        }
    }

    public String determineAcademicLevel(int completedCredits) {
        if (completedCredits < 0) {
            return "INVALID";
        }

        if (completedCredits < 30) {
            return "FRESHMAN";
        } else if (completedCredits < 60) {
            return "SOPHOMORE";
        } else if (completedCredits < 90) {
            return "JUNIOR";
        } else if (completedCredits < 120) {
            return "SENIOR";
        } else {
            return "GRADUATE";
        }
    }

    public boolean canEnroll(int currentCredits, int courseCredits, double gpa) {
        if (currentCredits < 0 || courseCredits < 0 || gpa < 0.0 || gpa > 4.0) {
            return false;
        }

        int maxCredits = 18;

        if (gpa >= 3.5) {
            maxCredits = 21;
        } else if (gpa < 2.0) {
            maxCredits = 12;
        }

        if (currentCredits + courseCredits > maxCredits) {
            return false;
        }

        return true;
    }

    public boolean canEnrollInHonorsCourse(double gpa, int completedCredits, boolean hasPrerequisites) {
        if (gpa < 0.0 || gpa > 4.0 || completedCredits < 0) {
            return false;
        }

        if (!hasPrerequisites) {
            return false;
        }

        if (gpa >= 3.5 && completedCredits >= 30) {
            return true;
        }

        return false;
    }

    public double calculateTuitionFees(int creditHours, boolean isInState, boolean hasScholarship) {
        if (creditHours < 0 || creditHours > 24) {
            return -1.0;
        }

        double costPerCredit = isInState ? 350.0 : 850.0;
        double totalCost = creditHours * costPerCredit;

        if (hasScholarship && creditHours >= 12) {
            if (isInState) {
                totalCost *= 0.75;
            } else {
                totalCost *= 0.80;
            }
        }

        double flatFee = 500.0;
        totalCost += flatFee;

        return Math.round(totalCost * 100.0) / 100.0;
    }

    public double calculateInternationalFees(int creditHours, boolean hasVisa, String visaType) {
        if (creditHours < 0 || creditHours > 24 || !hasVisa) {
            return -1.0;
        }

        double baseTuition = 1200.0 * creditHours;
        double internationalFee = 2500.0;
        double visaFee = 0.0;

        if (visaType.equals("F1")) {
            visaFee = 200.0;
        } else if (visaType.equals("J1")) {
            visaFee = 180.0;
        } else if (visaType.equals("M1")) {
            visaFee = 220.0;
        }

        double totalCost = baseTuition + internationalFee + visaFee;

        if (creditHours >= 15) {
            totalCost *= 0.95;
        }

        return Math.round(totalCost * 100.0) / 100.0;
    }

    public int calculateRemainingCredits(int currentCredits, int requiredCredits) {
        if (currentCredits < 0 || requiredCredits < 0 || requiredCredits > 200) {
            return -1;
        }

        int remaining = requiredCredits - currentCredits;
        return Math.max(remaining, 0);
    }

    public int calculateSemestersToGraduation(int remainingCredits, int creditsPerSemester) {
        if (remainingCredits < 0 || creditsPerSemester <= 0) {
            return -1;
        }

        if (remainingCredits == 0) {
            return 0;
        }

        return (int) Math.ceil((double) remainingCredits / creditsPerSemester);
    }

    public double predictGraduationGPA(double currentGPA, int currentCredits, int remainingCredits, double expectedGPA) {
        if (currentGPA < 0.0 || currentGPA > 4.0 || expectedGPA < 0.0 || expectedGPA > 4.0) {
            return -1.0;
        }

        if (currentCredits < 0 || remainingCredits < 0) {
            return -1.0;
        }

        if (currentCredits == 0 && remainingCredits == 0) {
            return 0.0;
        }

        double currentPoints = currentGPA * currentCredits;
        double expectedPoints = expectedGPA * remainingCredits;
        double totalCredits = currentCredits + remainingCredits;

        if (totalCredits == 0) {
            return 0.0;
        }

        double predictedGPA = (currentPoints + expectedPoints) / totalCredits;
        return Math.round(predictedGPA * 100.0) / 100.0;
    }

    public boolean isEligibleForGraduation(int completedCredits, double gpa, int majorCredits, int electiveCredits) {
        if (completedCredits < 0 || gpa < 0.0 || gpa > 4.0 || majorCredits < 0 || electiveCredits < 0) {
            return false;
        }

        if (completedCredits >= 120 && gpa >= 2.0 && majorCredits >= 60 && electiveCredits >= 30) {
            return true;
        }

        return false;
    }

    // Getters and Setters
    public String getStudentId() { return studentId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }
    public double getGpa() { return gpa; }
    public void setGpa(double gpa) { this.gpa = gpa; }
    public int getCreditHours() { return creditHours; }
    public void setCreditHours(int creditHours) { this.creditHours = creditHours; }
    public List<String> getEnrolledCourseIds() { return enrolledCourseIds; }
    public String getAcademicLevel() { return academicLevel; }
    public void setAcademicLevel(String academicLevel) { this.academicLevel = academicLevel; }
    public boolean isInternational() { return isInternational; }
    public void setInternational(boolean international) { isInternational = international; }
    public String getResidencyStatus() { return residencyStatus; }
    public void setResidencyStatus(String residencyStatus) { this.residencyStatus = residencyStatus; }

    public List<String> getEnrolledCourses() {
        return enrolledCourseIds;
    }
    public int getEnrolledCredits() {
        return creditHours;
    }
}