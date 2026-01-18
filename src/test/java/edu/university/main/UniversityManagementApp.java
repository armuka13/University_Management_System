package edu.university.main;

import edu.university.main.model.Student;
import edu.university.main.controller.*;
import edu.university.main.service.*;
import edu.university.main.view.*;
import edu.university.main.repository.*;
import edu.university.main.model.*;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;
import java.util.Optional;

public class UniversityManagementApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Initialize repositories
            StudentRepository studentRepository = new StudentRepository();
            CourseRepository courseRepository = new CourseRepository();
            GradeRepository gradeRepository = new GradeRepository();
            DepartmentRepository departmentRepository = new DepartmentRepository();
            FacultyRepository facultyRepository = new FacultyRepository();

            // Initialize services
            EnrollmentService enrollmentService = new EnrollmentService(studentRepository, courseRepository);
            GradeCalculationService gradeService = new GradeCalculationService();
            FinancialService financialService = new FinancialService();

            // Create main frame
            MainFrame mainFrame = new MainFrame();

            // Initialize controllers
            StudentController studentController = new StudentController(
                    enrollmentService, financialService, mainFrame.getStudentView()
            );

            CourseController courseController = new CourseController(
                    enrollmentService, mainFrame.getCourseView()
            );

            GradeController gradeController = new GradeController(
                    gradeService, mainFrame.getGradeView()
            );

            FinancialController financialController = new FinancialController(
                    financialService, mainFrame.getFinancialView()
            );

            // ================================================================
            // STUDENT VIEW EVENT HANDLERS
            // ================================================================

            // Create Student Button
            mainFrame.getStudentView().getCreateButton().addActionListener(e -> {
                try {
                    String id = mainFrame.getStudentView().getStudentIdField().getText();
                    String name = mainFrame.getStudentView().getNameField().getText();
                    String email = mainFrame.getStudentView().getEmailField().getText();
                    int age = Integer.parseInt(mainFrame.getStudentView().getAgeField().getText());
                    String major = mainFrame.getStudentView().getMajorField().getText();

                    studentController.createStudent(id, name, email, age, major);
                    mainFrame.updateStatus("Student created: " + name);

                    // Clear fields
                    mainFrame.getStudentView().getStudentIdField().setText("");
                    mainFrame.getStudentView().getNameField().setText("");
                    mainFrame.getStudentView().getEmailField().setText("");
                    mainFrame.getStudentView().getAgeField().setText("");
                    mainFrame.getStudentView().getMajorField().setText("");
                } catch (NumberFormatException ex) {
                    mainFrame.getStudentView().displayMessage("Error: Invalid age format");
                }
            });

            // Load Student Button (NEW)
            mainFrame.getStudentView().getLoadButton().addActionListener(e -> {
                String studentId = mainFrame.getStudentView().getUpdateStudentIdField().getText();

                if (studentId.isEmpty()) {
                    mainFrame.getStudentView().displayMessage("Error: Please enter Student ID");
                    return;
                }

                Optional<Student> studentOpt = enrollmentService.getStudentRepository().findById(studentId);

                if (studentOpt.isPresent()) {
                    Student student = studentOpt.get();
                    mainFrame.getStudentView().getUpdateNameField().setText(student.getName());
                    mainFrame.getStudentView().getUpdateEmailField().setText(student.getEmail());
                    mainFrame.getStudentView().getUpdateAgeField().setText(String.valueOf(student.getAge()));
                    mainFrame.getStudentView().getUpdateMajorField().setText(student.getMajor());
                    mainFrame.getStudentView().displayMessage("Student loaded: " + student.getName());
                    mainFrame.updateStatus("Student loaded");
                } else {
                    mainFrame.getStudentView().displayMessage("Error: Student not found");
                }
            });

            // Update Student Button (NEW)
            mainFrame.getStudentView().getUpdateButton().addActionListener(e -> {
                try {
                    String studentId = mainFrame.getStudentView().getUpdateStudentIdField().getText();

                    if (studentId.isEmpty()) {
                        mainFrame.getStudentView().displayMessage("Error: Please enter Student ID");
                        return;
                    }

                    Optional<Student> studentOpt = enrollmentService.getStudentRepository().findById(studentId);

                    if (studentOpt.isPresent()) {
                        Student student = studentOpt.get();

                        // Update fields
                        student.setName(mainFrame.getStudentView().getUpdateNameField().getText());
                        student.setEmail(mainFrame.getStudentView().getUpdateEmailField().getText());
                        student.setAge(Integer.parseInt(mainFrame.getStudentView().getUpdateAgeField().getText()));
                        student.setMajor(mainFrame.getStudentView().getUpdateMajorField().getText());

                        // Save
                        boolean success = enrollmentService.updateStudent(student);

                        if (success) {
                            mainFrame.getStudentView().displayMessage("Student updated successfully: " + student.getName());
                            mainFrame.updateStatus("Student updated");
                        } else {
                            mainFrame.getStudentView().displayMessage("Error: Update failed");
                        }
                    } else {
                        mainFrame.getStudentView().displayMessage("Error: Student not found");
                    }
                } catch (NumberFormatException ex) {
                    mainFrame.getStudentView().displayMessage("Error: Invalid age format");
                }
            });

            // Delete Student Button (NEW)
            mainFrame.getStudentView().getDeleteButton().addActionListener(e -> {
                String studentId = mainFrame.getStudentView().getUpdateStudentIdField().getText();

                if (studentId.isEmpty()) {
                    mainFrame.getStudentView().displayMessage("Error: Please enter Student ID");
                    return;
                }

                Optional<Student> studentOpt = enrollmentService.getStudentRepository().findById(studentId);

                if (studentOpt.isPresent()) {
                    Student student = studentOpt.get();

                    // Confirm deletion
                    int confirm = JOptionPane.showConfirmDialog(
                            mainFrame,
                            "Are you sure you want to delete student: " + student.getName() + "?",
                            "Confirm Deletion",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );

                    if (confirm == JOptionPane.YES_OPTION) {
                        boolean success = enrollmentService.deleteStudent(studentId);

                        if (success) {
                            mainFrame.getStudentView().displayMessage("Student deleted: " + student.getName());
                            mainFrame.updateStatus("Student deleted");

                            // Clear update fields
                            mainFrame.getStudentView().getUpdateStudentIdField().setText("");
                            mainFrame.getStudentView().getUpdateNameField().setText("");
                            mainFrame.getStudentView().getUpdateEmailField().setText("");
                            mainFrame.getStudentView().getUpdateAgeField().setText("");
                            mainFrame.getStudentView().getUpdateMajorField().setText("");
                        } else {
                            mainFrame.getStudentView().displayMessage("Error: Delete failed");
                        }
                    }
                } else {
                    mainFrame.getStudentView().displayMessage("Error: Student not found");
                }
            });

            // Enroll Student Button
            mainFrame.getStudentView().getEnrollButton().addActionListener(e -> {
                String studentId = mainFrame.getStudentView().getEnrollStudentIdField().getText();
                String courseId = mainFrame.getStudentView().getEnrollCourseIdField().getText();

                studentController.enrollStudentInCourse(studentId, courseId);
                mainFrame.updateStatus("Enrollment processed");

                // Clear fields
                mainFrame.getStudentView().getEnrollStudentIdField().setText("");
                mainFrame.getStudentView().getEnrollCourseIdField().setText("");
            });

            // ================================================================
            // COURSE VIEW EVENT HANDLERS
            // ================================================================

            // Create Course Button
            mainFrame.getCourseView().getCreateButton().addActionListener(e -> {
                try {
                    String id = mainFrame.getCourseView().getCourseIdField().getText();
                    String name = mainFrame.getCourseView().getCourseNameField().getText();
                    String instructor = mainFrame.getCourseView().getInstructorField().getText();
                    int credits = Integer.parseInt(mainFrame.getCourseView().getCreditsField().getText());
                    int capacity = Integer.parseInt(mainFrame.getCourseView().getCapacityField().getText());
                    String dept = mainFrame.getCourseView().getDepartmentField().getText();

                    courseController.createCourse(id, name, instructor, credits, capacity, dept);
                    mainFrame.updateStatus("Course created: " + name);

                    // Clear fields
                    mainFrame.getCourseView().getCourseIdField().setText("");
                    mainFrame.getCourseView().getCourseNameField().setText("");
                    mainFrame.getCourseView().getInstructorField().setText("");
                    mainFrame.getCourseView().getCreditsField().setText("");
                    mainFrame.getCourseView().getCapacityField().setText("");
                    mainFrame.getCourseView().getDepartmentField().setText("");
                } catch (NumberFormatException ex) {
                    mainFrame.getCourseView().displayMessage("Error: Invalid number format");
                }
            });

            // Load Course Button (NEW)
            mainFrame.getCourseView().getLoadCourseButton().addActionListener(e -> {
                String courseId = mainFrame.getCourseView().getUpdateCourseIdField().getText();

                if (courseId.isEmpty()) {
                    mainFrame.getCourseView().displayMessage("Error: Please enter Course ID");
                    return;
                }

                Optional<Course> courseOpt = enrollmentService.getCourseRepository().findById(courseId);

                if (courseOpt.isPresent()) {
                    Course course = courseOpt.get();
                    mainFrame.getCourseView().getUpdateCourseNameField().setText(course.getCourseName());
                    mainFrame.getCourseView().getUpdateInstructorField().setText(course.getInstructor());
                    mainFrame.getCourseView().getUpdateCreditsField().setText(String.valueOf(course.getCredits()));
                    mainFrame.getCourseView().getUpdateCapacityField().setText(String.valueOf(course.getMaxCapacity()));
                    mainFrame.getCourseView().getUpdateDepartmentField().setText(course.getDepartment());
                    mainFrame.getCourseView().displayMessage("Course loaded: " + course.getCourseName());
                    mainFrame.updateStatus("Course loaded");
                } else {
                    mainFrame.getCourseView().displayMessage("Error: Course not found");
                }
            });

            // Update Course Button (NEW)
            mainFrame.getCourseView().getUpdateButton().addActionListener(e -> {
                try {
                    String courseId = mainFrame.getCourseView().getUpdateCourseIdField().getText();

                    if (courseId.isEmpty()) {
                        mainFrame.getCourseView().displayMessage("Error: Please enter Course ID");
                        return;
                    }

                    Optional<Course> courseOpt = enrollmentService.getCourseRepository().findById(courseId);

                    if (courseOpt.isPresent()) {
                        Course course = courseOpt.get();

                        // Update fields
                        course.setCourseName(mainFrame.getCourseView().getUpdateCourseNameField().getText());
                        course.setInstructor(mainFrame.getCourseView().getUpdateInstructorField().getText());
                        course.setCredits(Integer.parseInt(mainFrame.getCourseView().getUpdateCreditsField().getText()));
                        course.setMaxCapacity(Integer.parseInt(mainFrame.getCourseView().getUpdateCapacityField().getText()));
                        course.setDepartment(mainFrame.getCourseView().getUpdateDepartmentField().getText());

                        // Save
                        boolean success = enrollmentService.updateCourse(course);

                        if (success) {
                            mainFrame.getCourseView().displayMessage("Course updated successfully: " + course.getCourseName());
                            mainFrame.updateStatus("Course updated");
                        } else {
                            mainFrame.getCourseView().displayMessage("Error: Update failed");
                        }
                    } else {
                        mainFrame.getCourseView().displayMessage("Error: Course not found");
                    }
                } catch (NumberFormatException ex) {
                    mainFrame.getCourseView().displayMessage("Error: Invalid number format");
                }
            });

            // Delete Course Button (NEW)
            mainFrame.getCourseView().getDeleteButton().addActionListener(e -> {
                String courseId = mainFrame.getCourseView().getUpdateCourseIdField().getText();

                if (courseId.isEmpty()) {
                    mainFrame.getCourseView().displayMessage("Error: Please enter Course ID");
                    return;
                }

                Optional<Course> courseOpt = enrollmentService.getCourseRepository().findById(courseId);

                if (courseOpt.isPresent()) {
                    Course course = courseOpt.get();

                    // Confirm deletion
                    int confirm = JOptionPane.showConfirmDialog(
                            mainFrame,
                            "Are you sure you want to delete course: " + course.getCourseName() + "?",
                            "Confirm Deletion",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );

                    if (confirm == JOptionPane.YES_OPTION) {
                        boolean success = enrollmentService.deleteCourse(courseId);

                        if (success) {
                            mainFrame.getCourseView().displayMessage("Course deleted: " + course.getCourseName());
                            mainFrame.updateStatus("Course deleted");

                            // Clear update fields
                            mainFrame.getCourseView().getUpdateCourseIdField().setText("");
                            mainFrame.getCourseView().getUpdateCourseNameField().setText("");
                            mainFrame.getCourseView().getUpdateInstructorField().setText("");
                            mainFrame.getCourseView().getUpdateCreditsField().setText("");
                            mainFrame.getCourseView().getUpdateCapacityField().setText("");
                            mainFrame.getCourseView().getUpdateDepartmentField().setText("");
                        } else {
                            mainFrame.getCourseView().displayMessage("Error: Delete failed");
                        }
                    }
                } else {
                    mainFrame.getCourseView().displayMessage("Error: Course not found");
                }
            });

            // Search Course Button
            mainFrame.getCourseView().getSearchButton().addActionListener(e -> {
                String courseId = mainFrame.getCourseView().getSearchCourseIdField().getText();
                courseController.checkCourseAvailability(courseId);
                courseController.showCourseFillRate(courseId);
                courseController.showCoursePopularity(courseId);
                mainFrame.updateStatus("Course information displayed");
            });

            // ================================================================
            // GRADE VIEW EVENT HANDLERS (Keep existing)
            // ================================================================

            mainFrame.getGradeView().getCalculateButton().addActionListener(e -> {
                try {
                    double score = Double.parseDouble(mainFrame.getGradeView().getScoreField().getText());
                    boolean attendance = mainFrame.getGradeView().getAttendanceBox().isSelected();
                    boolean participation = mainFrame.getGradeView().getParticipationBox().isSelected();

                    gradeController.applyBonus(score, attendance, participation);
                    mainFrame.updateStatus("Bonus points applied");
                } catch (NumberFormatException ex) {
                    mainFrame.getGradeView().displayMessage("Error: Invalid score format");
                }
            });

            mainFrame.getGradeView().getCheckHonorsButton().addActionListener(e -> {
                try {
                    double gpa = Double.parseDouble(mainFrame.getGradeView().getGpaField().getText());
                    int credits = Integer.parseInt(mainFrame.getGradeView().getCreditsField().getText());

                    gradeController.checkHonorsEligibility(gpa, credits, 120);
                    gradeController.determineHonorsLevel(gpa, credits);
                    mainFrame.updateStatus("Honors eligibility checked");
                } catch (NumberFormatException ex) {
                    mainFrame.getGradeView().displayMessage("Error: Invalid number format");
                }
            });

            // ================================================================
            // FINANCIAL VIEW EVENT HANDLERS (Keep existing)
            // ================================================================

            mainFrame.getFinancialView().getCalculateScholarshipButton().addActionListener(e -> {
                try {
                    double gpa = Double.parseDouble(mainFrame.getFinancialView().getScholarshipGpaField().getText());
                    int credits = Integer.parseInt(mainFrame.getFinancialView().getScholarshipCreditsField().getText());
                    double tuition = Double.parseDouble(mainFrame.getFinancialView().getBaseTuitionField().getText());

                    financialController.calculateScholarship(gpa, credits, tuition);
                    mainFrame.updateStatus("Scholarship calculated");
                } catch (NumberFormatException ex) {
                    mainFrame.getFinancialView().displayMessage("Error: Invalid number format");
                }
            });

            mainFrame.getFinancialView().getCalculateLoanButton().addActionListener(e -> {
                try {
                    double income = Double.parseDouble(mainFrame.getFinancialView().getIncomeField().getText());
                    int credits = Integer.parseInt(mainFrame.getFinancialView().getLoanCreditsField().getText());
                    boolean dependent = mainFrame.getFinancialView().getDependentBox().isSelected();

                    financialController.calculateLoan(income, credits, dependent);
                    mainFrame.updateStatus("Loan eligibility calculated");
                } catch (NumberFormatException ex) {
                    mainFrame.getFinancialView().displayMessage("Error: Invalid number format");
                }
            });

            // Show the application
            mainFrame.setVisible(true);
            mainFrame.updateStatus("System initialized - Ready to use");
        });
    }
}