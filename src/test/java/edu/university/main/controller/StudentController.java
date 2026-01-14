package edu.university.main.controller;

import edu.university.main.model.Student;
import edu.university.main.service.EnrollmentService;
import edu.university.main.service.FinancialService;
import edu.university.main.view.StudentView;

import java.util.List;
import java.util.Optional;

public class StudentController {
    private final EnrollmentService enrollmentService;
    private final FinancialService financialService;
    private final StudentView view;

    public StudentController(EnrollmentService enrollmentService, FinancialService financialService, StudentView view) {
        this.enrollmentService = enrollmentService;
        this.financialService = financialService;
        this.view = view;
    }

    public void createStudent(String id, String name, String email, int age, String major) {
        Student student = new Student(id, name, email, age, major);
        enrollmentService.addStudent(student);
        view.displayMessage("Student created successfully: " + name);
    }

    public void enrollStudentInCourse(String studentId, String courseId) {
        boolean success = enrollmentService.enrollStudent(studentId, courseId);
        view.displayMessage(success ? "Enrollment successful" : "Enrollment failed - check capacity and eligibility");
    }

    public void calculateStudentGPA(String studentId, List<Double> grades, List<Integer> credits) {
        Optional<Student> opt = enrollmentService.getStudentRepository().findById(studentId);
        if (opt.isEmpty()) {
            view.displayMessage("Student not found");
            return;
        }

        Student student = opt.get();
        double gpa = student.calculateGPA(grades, credits);
        if (gpa < 0) {
            view.displayMessage("Invalid grades or credits");
            return;
        }

        student.setGpa(gpa);
        enrollmentService.getStudentRepository().save(student);
        view.displayGPA(gpa);
    }

    public void checkAcademicStanding(String studentId) {
        Optional<Student> opt = enrollmentService.getStudentRepository().findById(studentId);
        if (opt.isEmpty()) {
            view.displayMessage("Student not found");
            return;
        }
        Student student = opt.get();
        String standing = student.determineAcademicStanding(student.getGpa(), student.getCreditHours());
        view.displayAcademicStanding(standing);
    }

    public void calculateTuition(String studentId, boolean isInState, boolean hasScholarship) {
        Optional<Student> opt = enrollmentService.getStudentRepository().findById(studentId);
        if (opt.isEmpty()) {
            view.displayMessage("Student not found");
            return;
        }
        Student student = opt.get();
        double tuition = student.calculateTuitionFees(student.getCreditHours(), isInState, hasScholarship);
        view.displayTuition(tuition);
    }
}