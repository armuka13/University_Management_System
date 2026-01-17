// FILE: src/main/java/edu/university/model/Course.java
package edu.university.main.model;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String courseId;
    private String courseName;
    private String instructor;
    private int credits;
    private int maxCapacity;
    private int currentEnrollment;
    private String department;
    private List<String> enrolledStudentIds;
    private List<String> prerequisites;
    private String courseLevel;
    private String semester;
    private int year;
    private String deliveryMode;

    public Course(String courseId, String courseName, String instructor, int credits, int maxCapacity, String department) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.instructor = instructor;
        this.credits = credits;
        this.maxCapacity = maxCapacity;
        this.currentEnrollment = 0;
        this.department = department;
        this.enrolledStudentIds = new ArrayList<>();
        this.prerequisites = new ArrayList<>();
        this.courseLevel = "UNDERGRADUATE";
        this.deliveryMode = "IN_PERSON";
    }

    public double calculateFillRate() {
        if (maxCapacity <= 0) {
            return -1.0;
        }

        double fillRate = (double) currentEnrollment / maxCapacity * 100.0;
        return Math.round(fillRate * 100.0) / 100.0;
    }

    public int calculateAvailableSeats() {
        if (maxCapacity < 0 || currentEnrollment < 0) {
            return -1;
        }

        int available = maxCapacity - currentEnrollment;
        return Math.max(available, 0);
    }

    public int calculateWaitlistSize(int totalDemand) {
        if (totalDemand < 0 || maxCapacity < 0) {
            return -1;
        }

        int waitlistSize = totalDemand - maxCapacity;
        return Math.max(waitlistSize, 0);
    }

    public boolean canAcceptEnrollment(int requestedSeats) {
        if (requestedSeats <= 0 || maxCapacity < 0 || currentEnrollment < 0) {
            return false;
        }

        if (currentEnrollment >= maxCapacity) {
            return false;
        }

        return (currentEnrollment + requestedSeats) <= maxCapacity;
    }

    public boolean hasWaitlist() {
        return currentEnrollment >= maxCapacity;
    }

    public String determineClassSize() {
        if (maxCapacity < 0) {
            return "INVALID";
        }

        if (maxCapacity <= 20) {
            return "SMALL";
        } else if (maxCapacity <= 50) {
            return "MEDIUM";
        } else if (maxCapacity <= 100) {
            return "LARGE";
        } else {
            return "AUDITORIUM";
        }
    }

    public String determineCoursePopularity() {
        if (maxCapacity <= 0) {
            return "INVALID";
        }

        double fillRate = calculateFillRate();

        if (fillRate >= 90.0) {
            return "HIGH_DEMAND";
        } else if (fillRate >= 70.0) {
            return "MODERATE_DEMAND";
        } else if (fillRate >= 40.0) {
            return "LOW_DEMAND";
        } else {
            return "VERY_LOW_DEMAND";
        }
    }

    public double calculateInstructorWorkload(int numberOfCourses, int totalStudents) {
        if (numberOfCourses <= 0 || totalStudents < 0) {
            return -1.0;
        }

        double baseWorkload = numberOfCourses * 10.0;
        double studentLoad = totalStudents * 0.5;
        double totalWorkload = baseWorkload + studentLoad;

        return Math.round(totalWorkload * 100.0) / 100.0;
    }

    public double calculateCourseCost(int credits, String deliveryMode, boolean hasLab) {
        if (credits < 0 || credits > 6) {
            return -1.0;
        }

        double baseCost = credits * 400.0;

        if (deliveryMode.equals("ONLINE")) {
            baseCost *= 0.90;
        } else if (deliveryMode.equals("HYBRID")) {
            baseCost *= 0.95;
        }

        if (hasLab) {
            baseCost += 150.0;
        }

        return Math.round(baseCost * 100.0) / 100.0;
    }

    public int calculateOptimalCapacity(double demandFactor, int baseCapacity) {
        if (demandFactor < 0.0 || demandFactor > 3.0 || baseCapacity <= 0) {
            return -1;
        }

        int optimalCapacity = (int) Math.ceil(baseCapacity * demandFactor);

        if (optimalCapacity > 200) {
            return 200;
        }

        return optimalCapacity;
    }

    // Getters and Setters
    public String getCourseId() { return courseId; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public String getInstructor() { return instructor; }
    public void setInstructor(String instructor) { this.instructor = instructor; }
    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }
    public int getMaxCapacity() { return maxCapacity; }
    public void setMaxCapacity(int maxCapacity) { this.maxCapacity = maxCapacity; }
    public int getCurrentEnrollment() { return currentEnrollment; }
    public void setCurrentEnrollment(int currentEnrollment) { this.currentEnrollment = currentEnrollment; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public List<String> getEnrolledStudentIds() { return enrolledStudentIds; }
    public List<String> getPrerequisites() { return prerequisites; }
    public String getCourseLevel() { return courseLevel; }
    public void setCourseLevel(String courseLevel) { this.courseLevel = courseLevel; }
    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public String getDeliveryMode() { return deliveryMode; }
    public void setDeliveryMode(String deliveryMode) { this.deliveryMode = deliveryMode; }


}