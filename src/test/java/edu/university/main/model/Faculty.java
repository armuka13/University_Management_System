package edu.university.main.model;



import java.util.ArrayList;
import java.util.List;

public class Faculty {
    private String facultyId;
    private String name;
    private String email;
    private String department;
    private String rank;
    private double salary;
    private List<String> courseIds;
    private int yearsOfExperience;
    private double researchScore;
    private int numberOfPublications;

    public Faculty(String facultyId, String name, String email, String department, String rank, double salary) {
        this.facultyId = facultyId;
        this.name = name;
        this.email = email;
        this.department = department;
        this.rank = rank;
        this.salary = salary;
        this.courseIds = new ArrayList<>();
        this.yearsOfExperience = 0;
        this.researchScore = 0.0;
        this.numberOfPublications = 0;
    }

    public double calculateTeachingLoad(int numberOfCourses, int totalStudents, int numberOfPreps) {
        if (numberOfCourses < 0 || totalStudents < 0 || numberOfPreps < 0) {
            return -1.0;
        }

        double courseLoad = numberOfCourses * 3.0;
        double studentLoad = totalStudents * 0.1;
        double prepLoad = numberOfPreps * 2.5;

        double totalLoad = courseLoad + studentLoad + prepLoad;
        return Math.round(totalLoad * 100.0) / 100.0;
    }

    public double calculateSalaryIncrease(double currentSalary, double performanceScore, int yearsAtRank) {
        if (currentSalary < 0 || performanceScore < 0 || performanceScore > 100 || yearsAtRank < 0) {
            return -1.0;
        }

        double baseIncrease = currentSalary * 0.03;

        if (performanceScore >= 90.0 && yearsAtRank >= 3) {
            baseIncrease = currentSalary * 0.08;
        } else if (performanceScore >= 80.0 && yearsAtRank >= 2) {
            baseIncrease = currentSalary * 0.06;
        } else if (performanceScore >= 70.0) {
            baseIncrease = currentSalary * 0.04;
        }

        return Math.round(baseIncrease * 100.0) / 100.0;
    }

    public boolean isEligibleForPromotion(int yearsAtCurrentRank, int publications, double teachingScore, double serviceScore) {
        if (yearsAtCurrentRank < 0 || publications < 0 || teachingScore < 0 ||
                teachingScore > 100 || serviceScore < 0 || serviceScore > 100) {
            return false;
        }

        if (rank.equals("ASSISTANT_PROFESSOR")) {
            return yearsAtCurrentRank >= 6 && publications >= 10 && teachingScore >= 75.0 && serviceScore >= 70.0;
        } else if (rank.equals("ASSOCIATE_PROFESSOR")) {
            return yearsAtCurrentRank >= 5 && publications >= 20 && teachingScore >= 80.0 && serviceScore >= 75.0;
        } else if (rank.equals("PROFESSOR")) {
            return false;
        }

        return false;
    }

    public boolean isEligibleForTenure(int yearsInPosition, int publications, double teachingScore, double serviceScore, boolean hasGrantFunding) {
        if (yearsInPosition < 0 || publications < 0 || teachingScore < 0 ||
                teachingScore > 100 || serviceScore < 0 || serviceScore > 100) {
            return false;
        }

        if (yearsInPosition >= 6 && publications >= 15 && teachingScore >= 80.0 &&
                serviceScore >= 75.0 && hasGrantFunding) {
            return true;
        }

        return false;
    }

    public double calculateResearchProductivity(int publications, double grantAmount, int yearsActive) {
        if (publications < 0 || grantAmount < 0 || yearsActive <= 0) {
            return -1.0;
        }

        double publicationScore = publications * 10.0;
        double grantScore = grantAmount / 10000.0;
        double annualScore = (publicationScore + grantScore) / yearsActive;

        return Math.round(annualScore * 100.0) / 100.0;
    }

    public String determineWorkloadCategory(double teachingLoad, double researchLoad, double serviceLoad) {
        if (teachingLoad < 0 || researchLoad < 0 || serviceLoad < 0) {
            return "INVALID";
        }

        double totalLoad = teachingLoad + researchLoad + serviceLoad;

        if (totalLoad >= 100.0) {
            return "OVERLOADED";
        } else if (totalLoad >= 80.0 && totalLoad < 100.0) {
            return "FULL_LOAD";
        } else if (totalLoad >= 60.0 && totalLoad < 80.0) {
            return "MODERATE_LOAD";
        } else {
            return "LIGHT_LOAD";
        }
    }

    // Getters and Setters
    public String getFacultyId() { return facultyId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getRank() { return rank; }
    public void setRank(String rank) { this.rank = rank; }
    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }
    public List<String> getCourseIds() { return courseIds; }
    public int getYearsOfExperience() { return yearsOfExperience; }
    public void setYearsOfExperience(int yearsOfExperience) { this.yearsOfExperience = yearsOfExperience; }
    public double getResearchScore() { return researchScore; }
    public void setResearchScore(double researchScore) { this.researchScore = researchScore; }
    public int getNumberOfPublications() { return numberOfPublications; }
    public void setNumberOfPublications(int numberOfPublications) { this.numberOfPublications = numberOfPublications; }
}
