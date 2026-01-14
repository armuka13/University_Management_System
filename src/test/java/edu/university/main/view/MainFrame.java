package edu.university.main.view;



import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private StudentView studentView;
    private CourseView courseView;
    private GradeView gradeView;
    private FinancialView financialView;
    private JTabbedPane tabbedPane;
    private JLabel statusBar;

    public MainFrame() {
        setTitle("University Management System - Advanced Edition");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create views
        studentView = new StudentView();
        courseView = new CourseView();
        gradeView = new GradeView();
        financialView = new FinancialView();

        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));

        tabbedPane.addTab("Students", new ImageIcon(), studentView, "Manage Students");
        tabbedPane.addTab("Courses", new ImageIcon(), courseView, "Manage Courses");
        tabbedPane.addTab("Grades", new ImageIcon(), gradeView, "Grade Calculations");
        tabbedPane.addTab("Financial", new ImageIcon(), financialView, "Financial Services");

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "University Management System v2.0\n" +
                            "A comprehensive academic management solution\n\n" +
                            "Features:\n" +
                            "- Student Management\n" +
                            "- Course Administration\n" +
                            "- Grade Calculations\n" +
                            "- Financial Services\n\n" +
                            "Built with Java Swing MVC Architecture",
                    "About",
                    JOptionPane.INFORMATION_MESSAGE);
        });
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);

        // Create status bar
        statusBar = new JLabel(" Ready");
        statusBar.setBorder(BorderFactory.createEtchedBorder());
        statusBar.setFont(new Font("Arial", Font.PLAIN, 12));

        // Add components
        add(tabbedPane, BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);

        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public StudentView getStudentView() { return studentView; }
    public CourseView getCourseView() { return courseView; }
    public GradeView getGradeView() { return gradeView; }
    public FinancialView getFinancialView() { return financialView; }
    public JLabel getStatusBar() { return statusBar; }

    public void updateStatus(String message) {
        statusBar.setText(" " + message);
    }
}
