package edu.university.main.model;

public class Scholarship {
    private String scholarshipId;
    private String name;
    private double amount;
    private String type;
    private double minGPA;
    private int minCredits;
    private boolean needsBased;
    private String department;

    public Scholarship(String scholarshipId, String name, double amount, String type) {
        this.scholarshipId = scholarshipId;
        this.name = name;
        this.amount = amount;
        this.type = type;
        this.minGPA = 0.0;
        this.minCredits = 0;
        this.needsBased = false;
        this.department = "GENERAL";
    }

    public double calculateAwardAmount(double baseAmount, double gpa, int creditHours) {
        if (baseAmount < 0 || gpa < 0.0 || gpa > 4.0 || creditHours < 0) {
            return -1.0;
        }

        double awardAmount = baseAmount;

        if (gpa >= 3.9 && creditHours >= 15) {
            awardAmount = baseAmount * 1.0;
        } else if (gpa >= 3.7 && creditHours >= 15) {
            awardAmount = baseAmount * 0.75;
        } else if (gpa >= 3.5 && creditHours >= 12) {
            awardAmount = baseAmount * 0.50;
        } else if (gpa >= 3.0 && creditHours >= 12) {
            awardAmount = baseAmount * 0.25;
        } else {
            return 0.0;
        }

        return Math.round(awardAmount * 100.0) / 100.0;
    }

    public boolean isEligible(double gpa, int creditHours, double familyIncome, String major) {
        if (gpa < 0.0 || gpa > 4.0 || creditHours < 0 || familyIncome < 0) {
            return false;
        }

        if (gpa < minGPA || creditHours < minCredits) {
            return false;
        }

        if (needsBased && familyIncome > 60000) {
            return false;
        }

        if (!department.equals("GENERAL") && !department.equals(major)) {
            return false;
        }

        return true;
    }

    public double calculateRenewalAmount(double originalAmount, double currentGPA, double previousGPA) {
        if (originalAmount < 0 || currentGPA < 0.0 || currentGPA > 4.0 ||
                previousGPA < 0.0 || previousGPA > 4.0) {
            return -1.0;
        }

        if (currentGPA >= minGPA && currentGPA >= previousGPA) {
            return originalAmount;
        } else if (currentGPA >= minGPA && currentGPA >= (previousGPA - 0.2)) {
            return originalAmount * 0.75;
        } else if (currentGPA >= (minGPA - 0.3)) {
            return originalAmount * 0.50;
        } else {
            return 0.0;
        }
    }

    // Getters and Setters
    public String getScholarshipId() { return scholarshipId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public double getMinGPA() { return minGPA; }
    public void setMinGPA(double minGPA) { this.minGPA = minGPA; }
    public int getMinCredits() { return minCredits; }
    public void setMinCredits(int minCredits) { this.minCredits = minCredits; }
    public boolean isNeedsBased() { return needsBased; }
    public void setNeedsBased(boolean needsBased) { this.needsBased = needsBased; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
}