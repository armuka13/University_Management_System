package edu.university.main.view;

import javax.swing.*;
import java.awt.*;

public class StudentView extends JPanel {
    private JTextArea messageArea;
    private JLabel gpaLabel;
    private JLabel standingLabel;
    private JLabel tuitionLabel;
    private JLabel graduationLabel;

    private JTextField studentIdField;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField ageField;
    private JTextField majorField;
    private JButton createButton;

    private JTextField enrollStudentIdField;
    private JTextField enrollCourseIdField;
    private JButton enrollButton;

    public StudentView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new BorderLayout());

        JPanel infoPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Student Information"));
        gpaLabel = new JLabel("GPA: N/A");
        standingLabel = new JLabel("Standing: N/A");
        tuitionLabel = new JLabel("Tuition: N/A");
        graduationLabel = new JLabel("Graduation: N/A");

        gpaLabel.setFont(new Font("Arial", Font.BOLD, 14));
        standingLabel.setFont(new Font("Arial", Font.BOLD, 14));
        tuitionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        graduationLabel.setFont(new Font("Arial", Font.BOLD, 14));

        infoPanel.add(gpaLabel);
        infoPanel.add(standingLabel);
        infoPanel.add(tuitionLabel);
        infoPanel.add(graduationLabel);

        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Create New Student"));

        inputPanel.add(new JLabel("Student ID:"));
        studentIdField = new JTextField();
        inputPanel.add(studentIdField);

        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        inputPanel.add(emailField);

        inputPanel.add(new JLabel("Age:"));
        ageField = new JTextField();
        inputPanel.add(ageField);

        inputPanel.add(new JLabel("Major:"));
        majorField = new JTextField();
        inputPanel.add(majorField);

        createButton = new JButton("Create Student");
        createButton.setBackground(new Color(70, 130, 180));
        createButton.setForeground(Color.WHITE);
        createButton.setFont(new Font("Arial", Font.BOLD, 12));
        inputPanel.add(new JLabel());
        inputPanel.add(createButton);

        JPanel enrollPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        enrollPanel.setBorder(BorderFactory.createTitledBorder("Enroll in Course"));

        enrollPanel.add(new JLabel("Student ID:"));
        enrollStudentIdField = new JTextField();
        enrollPanel.add(enrollStudentIdField);

        enrollPanel.add(new JLabel("Course ID:"));
        enrollCourseIdField = new JTextField();
        enrollPanel.add(enrollCourseIdField);

        enrollButton = new JButton("Enroll");
        enrollButton.setBackground(new Color(60, 179, 113));
        enrollButton.setForeground(Color.WHITE);
        enrollButton.setFont(new Font("Arial", Font.BOLD, 12));
        enrollPanel.add(new JLabel());
        enrollPanel.add(enrollButton);

        JPanel formsPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        formsPanel.add(inputPanel);
        formsPanel.add(enrollPanel);

        topPanel.add(infoPanel, BorderLayout.NORTH);
        topPanel.add(formsPanel, BorderLayout.CENTER);

        messageArea = new JTextArea(12, 50);
        messageArea.setEditable(false);
        messageArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(messageArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Activity Log"));

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void displayMessage(String message) {
        messageArea.append("[" + java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss")) + "] " + message + "\n");
        messageArea.setCaretPosition(messageArea.getDocument().getLength());
    }

    public void displayGPA(double gpa) {
        gpaLabel.setText("GPA: " + String.format("%.2f", gpa));
        gpaLabel.setForeground(gpa >= 3.5 ? new Color(0, 128, 0) : (gpa >= 2.0 ? Color.BLACK : Color.RED));
        displayMessage("GPA calculated: " + gpa);
    }

    public void displayAcademicStanding(String standing) {
        standingLabel.setText("Standing: " + standing.replace("_", " "));
        displayMessage("Academic Standing: " + standing);
    }

    public void displayTuition(double tuition) {
        tuitionLabel.setText("Tuition: $" + String.format("%.2f", tuition));
        displayMessage("Tuition calculated: $" + tuition);
    }

    public void displayGraduationStatus(boolean eligible) {
        graduationLabel.setText("Graduation: " + (eligible ? "ELIGIBLE" : "NOT ELIGIBLE"));
        graduationLabel.setForeground(eligible ? new Color(0, 128, 0) : Color.RED);
        displayMessage("Graduation eligibility: " + eligible);
    }

    public void clearMessages() {
        messageArea.setText("");
    }

    // Getters for form fields
    public JTextField getStudentIdField() { return studentIdField; }
    public JTextField getNameField() { return nameField; }
    public JTextField getEmailField() { return emailField; }
    public JTextField getAgeField() { return ageField; }
    public JTextField getMajorField() { return majorField; }
    public JButton getCreateButton() { return createButton; }
    public JTextField getEnrollStudentIdField() { return enrollStudentIdField; }
    public JTextField getEnrollCourseIdField() { return enrollCourseIdField; }
    public JButton getEnrollButton() { return enrollButton; }
}

