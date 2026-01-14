package edu.university.main;


import edu.university.main.repository.*;
import edu.university.main.service.*;
import edu.university.main.controller.*;
import edu.university.main.view.MainFrame;


import javax.swing.SwingUtilities;

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

            // Wire up Student View event handlers
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

            mainFrame.getStudentView().getEnrollButton().addActionListener(e -> {
                String studentId = mainFrame.getStudentView().getEnrollStudentIdField().getText();
                String courseId = mainFrame.getStudentView().getEnrollCourseIdField().getText();

                studentController.enrollStudentInCourse(studentId, courseId);
                mainFrame.updateStatus("Enrollment processed");

                // Clear fields
                mainFrame.getStudentView().getEnrollStudentIdField().setText("");
                mainFrame.getStudentView().getEnrollCourseIdField().setText("");
            });

            // Wire up Course View event handlers
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

            mainFrame.getCourseView().getSearchButton().addActionListener(e -> {
                String courseId = mainFrame.getCourseView().getSearchCourseIdField().getText();
                courseController.checkCourseAvailability(courseId);
                courseController.showCourseFillRate(courseId);
                courseController.showCoursePopularity(courseId);
                mainFrame.updateStatus("Course information displayed");
            });

            // Wire up Grade View event handlers
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

            // Wire up Financial View event handlers
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