package edu.university.main.model;



import java.util.ArrayList;
import java.util.List;

public class Department {
    private String departmentId;
    private String departmentName;
    private String headOfDepartment;
    private int numberOfFaculty;
    private int numberOfStudents;
    private double budget;
    private List<String> courseIds;
    private int numberOfPrograms;
    private double researchFunding;

    public Department(String departmentId, String departmentName, String headOfDepartment, double budget) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.headOfDepartment = headOfDepartment;
        this.budget = budget;
        this.numberOfFaculty = 0;
        this.numberOfStudents = 0;
        this.courseIds = new ArrayList<>();
        this.numberOfPrograms = 0;
        this.researchFunding = 0.0;
    }

    public double calculateBudgetPerStudent() {
        if (numberOfStudents <= 0 || budget < 0) {
            return -1.0;
        }

        double perStudent = budget / numberOfStudents;
        return Math.round(perStudent * 100.0) / 100.0;
    }

    public double calculateBudgetPerFaculty() {
        if (numberOfFaculty <= 0 || budget < 0) {
            return -1.0;
        }

        double perFaculty = budget / numberOfFaculty;
        return Math.round(perFaculty * 100.0) / 100.0;
    }

    public double calculateFacultyToStudentRatio() {
        if (numberOfFaculty <= 0 || numberOfStudents < 0) {
            return -1.0;
        }

        double ratio = (double) numberOfStudents / numberOfFaculty;
        return Math.round(ratio * 100.0) / 100.0;
    }

    public double calculateAverageClassSize(int totalCourses) {
        if (totalCourses <= 0 || numberOfStudents < 0) {
            return -1.0;
        }

        double avgSize = (double) numberOfStudents / totalCourses;
        return Math.round(avgSize * 100.0) / 100.0;
    }

    public String determineDepartmentSize() {
        if (numberOfFaculty < 0 || numberOfStudents < 0) {
            return "INVALID";
        }

        if (numberOfFaculty <= 10 && numberOfStudents <= 100) {
            return "SMALL";
        } else if (numberOfFaculty <= 30 && numberOfStudents <= 500) {
            return "MEDIUM";
        } else if (numberOfFaculty <= 60 && numberOfStudents <= 1000) {
            return "LARGE";
        } else {
            return "VERY_LARGE";
        }
    }

    public String evaluateDepartmentPerformance(double researchOutput, double studentSatisfaction, double graduationRate) {
        if (researchOutput < 0 || researchOutput > 100 || studentSatisfaction < 0 ||
                studentSatisfaction > 100 || graduationRate < 0 || graduationRate > 100) {
            return "INVALID";
        }

        double overallScore = (researchOutput * 0.4) + (studentSatisfaction * 0.3) + (graduationRate * 0.3);

        if (overallScore >= 85.0) {
            return "EXCELLENT";
        } else if (overallScore >= 70.0) {
            return "GOOD";
        } else if (overallScore >= 55.0) {
            return "SATISFACTORY";
        } else {
            return "NEEDS_IMPROVEMENT";
        }
    }

    public double allocateBudget(double totalBudget, int numberOfDepartments, boolean isPriority) {
        if (totalBudget < 0 || numberOfDepartments <= 0) {
            return -1.0;
        }

        double baseBudget = totalBudget / numberOfDepartments;

        if (isPriority && numberOfStudents > 500) {
            baseBudget *= 1.25;
        } else if (isPriority) {
            baseBudget *= 1.15;
        }

        return Math.round(baseBudget * 100.0) / 100.0;
    }

    public double calculateResearchFundingPerFaculty() {
        if (numberOfFaculty <= 0 || researchFunding < 0) {
            return -1.0;
        }

        double perFaculty = researchFunding / numberOfFaculty;
        return Math.round(perFaculty * 100.0) / 100.0;
    }

    public int calculateRequiredFaculty(int totalStudents, int targetRatio) {
        if (totalStudents < 0 || targetRatio <= 0) {
            return -1;
        }

        int required = (int) Math.ceil((double) totalStudents / targetRatio);
        return required;
    }

    public boolean needsMoreFaculty(int currentFaculty, int currentStudents, int maxRatio) {
        if (currentFaculty <= 0 || currentStudents < 0 || maxRatio <= 0) {
            return false;
        }

        double currentRatio = (double) currentStudents / currentFaculty;
        return currentRatio > maxRatio;
    }

    // Getters and Setters
    public String getDepartmentId() { return departmentId; }
    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
    public String getHeadOfDepartment() { return headOfDepartment; }
    public void setHeadOfDepartment(String headOfDepartment) { this.headOfDepartment = headOfDepartment; }
    public int getNumberOfFaculty() { return numberOfFaculty; }
    public void setNumberOfFaculty(int numberOfFaculty) { this.numberOfFaculty = numberOfFaculty; }
    public int getNumberOfStudents() { return numberOfStudents; }
    public void setNumberOfStudents(int numberOfStudents) { this.numberOfStudents = numberOfStudents; }
    public double getBudget() { return budget; }
    public void setBudget(double budget) { this.budget = budget; }
    public List<String> getCourseIds() { return courseIds; }
    public int getNumberOfPrograms() { return numberOfPrograms; }
    public void setNumberOfPrograms(int numberOfPrograms) { this.numberOfPrograms = numberOfPrograms; }
    public double getResearchFunding() { return researchFunding; }
    public void setResearchFunding(double researchFunding) { this.researchFunding = researchFunding; }
}
