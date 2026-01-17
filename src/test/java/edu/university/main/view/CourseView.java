package edu.university.main.view;




import javax.swing.*;
import java.awt.*;

public class CourseView extends JPanel {
    private JTextArea messageArea;
    private JLabel seatsLabel;
    private JLabel fillRateLabel;
    private JLabel popularityLabel;

    // Create Course Panel
    private JTextField courseIdField;
    private JTextField courseNameField;
    private JTextField instructorField;
    private JTextField creditsField;
    private JTextField capacityField;
    private JTextField departmentField;
    private JButton createButton;

    // Search Panel
    private JTextField searchCourseIdField;
    private JButton searchButton;

    // Update/Delete Panel (NEW)
    private JTextField updateCourseIdField;
    private JTextField updateCourseNameField;
    private JTextField updateInstructorField;
    private JTextField updateCreditsField;
    private JTextField updateCapacityField;
    private JTextField updateDepartmentField;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton loadCourseButton;

    public CourseView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new BorderLayout());

        // Info Panel
        JPanel infoPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Course Statistics"));
        seatsLabel = new JLabel("Available Seats: N/A");
        fillRateLabel = new JLabel("Fill Rate: N/A");
        popularityLabel = new JLabel("Popularity: N/A");

        seatsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        fillRateLabel.setFont(new Font("Arial", Font.BOLD, 14));
        popularityLabel.setFont(new Font("Arial", Font.BOLD, 14));

        infoPanel.add(seatsLabel);
        infoPanel.add(fillRateLabel);
        infoPanel.add(popularityLabel);

        // Create Course Panel
        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Create New Course"));

        inputPanel.add(new JLabel("Course ID:"));
        courseIdField = new JTextField();
        inputPanel.add(courseIdField);

        inputPanel.add(new JLabel("Course Name:"));
        courseNameField = new JTextField();
        inputPanel.add(courseNameField);

        inputPanel.add(new JLabel("Instructor:"));
        instructorField = new JTextField();
        inputPanel.add(instructorField);

        inputPanel.add(new JLabel("Credits:"));
        creditsField = new JTextField();
        inputPanel.add(creditsField);

        inputPanel.add(new JLabel("Max Capacity:"));
        capacityField = new JTextField();
        inputPanel.add(capacityField);

        inputPanel.add(new JLabel("Department:"));
        departmentField = new JTextField();
        inputPanel.add(departmentField);

        createButton = new JButton("Create Course");
        createButton.setBackground(new Color(70, 130, 180));
        createButton.setForeground(Color.WHITE);
        createButton.setFont(new Font("Arial", Font.BOLD, 12));
        inputPanel.add(new JLabel());
        inputPanel.add(createButton);

        // Update/Delete Course Panel (NEW)
        JPanel updatePanel = new JPanel(new GridLayout(8, 2, 5, 5));
        updatePanel.setBorder(BorderFactory.createTitledBorder("Update/Delete Course"));

        updatePanel.add(new JLabel("Course ID:"));
        JPanel idPanel = new JPanel(new BorderLayout(5, 0));
        updateCourseIdField = new JTextField();
        loadCourseButton = new JButton("Load");
        loadCourseButton.setBackground(new Color(100, 149, 237));
        loadCourseButton.setForeground(Color.WHITE);
        idPanel.add(updateCourseIdField, BorderLayout.CENTER);
        idPanel.add(loadCourseButton, BorderLayout.EAST);
        updatePanel.add(idPanel);

        updatePanel.add(new JLabel("Course Name:"));
        updateCourseNameField = new JTextField();
        updatePanel.add(updateCourseNameField);

        updatePanel.add(new JLabel("Instructor:"));
        updateInstructorField = new JTextField();
        updatePanel.add(updateInstructorField);

        updatePanel.add(new JLabel("Credits:"));
        updateCreditsField = new JTextField();
        updatePanel.add(updateCreditsField);

        updatePanel.add(new JLabel("Max Capacity:"));
        updateCapacityField = new JTextField();
        updatePanel.add(updateCapacityField);

        updatePanel.add(new JLabel("Department:"));
        updateDepartmentField = new JTextField();
        updatePanel.add(updateDepartmentField);

        updateButton = new JButton("Update Course");
        updateButton.setBackground(new Color(255, 165, 0));
        updateButton.setForeground(Color.WHITE);
        updateButton.setFont(new Font("Arial", Font.BOLD, 12));
        updatePanel.add(updateButton);

        deleteButton = new JButton("Delete Course");
        deleteButton.setBackground(new Color(220, 20, 60));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFont(new Font("Arial", Font.BOLD, 12));
        updatePanel.add(deleteButton);

        // Search Panel
        JPanel searchPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Search Course"));

        searchPanel.add(new JLabel("Course ID:"));
        searchCourseIdField = new JTextField();
        searchPanel.add(searchCourseIdField);

        searchButton = new JButton("Search");
        searchButton.setBackground(new Color(60, 179, 113));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFont(new Font("Arial", Font.BOLD, 12));
        searchPanel.add(new JLabel());
        searchPanel.add(searchButton);

        // Combine panels
        JPanel formsPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        formsPanel.add(inputPanel);
        formsPanel.add(updatePanel);
        formsPanel.add(searchPanel);

        topPanel.add(infoPanel, BorderLayout.NORTH);
        topPanel.add(formsPanel, BorderLayout.CENTER);

        messageArea = new JTextArea(10, 50);
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

    public void displayAvailableSeats(int seats) {
        seatsLabel.setText("Available Seats: " + seats);
        seatsLabel.setForeground(seats > 10 ? new Color(0, 128, 0) : (seats > 0 ? Color.ORANGE : Color.RED));
        displayMessage("Available seats: " + seats);
    }

    public void displayFillRate(double rate) {
        fillRateLabel.setText("Fill Rate: " + String.format("%.2f", rate) + "%");
        fillRateLabel.setForeground(rate >= 90 ? Color.RED : (rate >= 70 ? Color.ORANGE : new Color(0, 128, 0)));
        displayMessage("Fill rate: " + rate + "%");
    }

    public void displayPopularity(String popularity) {
        popularityLabel.setText("Popularity: " + popularity.replace("_", " "));
        displayMessage("Course popularity: " + popularity);
    }

    public void clearMessages() {
        messageArea.setText("");
    }

    // Getters for Create form
    public JTextField getCourseIdField() { return courseIdField; }
    public JTextField getCourseNameField() { return courseNameField; }
    public JTextField getInstructorField() { return instructorField; }
    public JTextField getCreditsField() { return creditsField; }
    public JTextField getCapacityField() { return capacityField; }
    public JTextField getDepartmentField() { return departmentField; }
    public JButton getCreateButton() { return createButton; }

    // Getters for Search form
    public JTextField getSearchCourseIdField() { return searchCourseIdField; }
    public JButton getSearchButton() { return searchButton; }

    // Getters for Update/Delete form (NEW)
    public JTextField getUpdateCourseIdField() { return updateCourseIdField; }
    public JTextField getUpdateCourseNameField() { return updateCourseNameField; }
    public JTextField getUpdateInstructorField() { return updateInstructorField; }
    public JTextField getUpdateCreditsField() { return updateCreditsField; }
    public JTextField getUpdateCapacityField() { return updateCapacityField; }
    public JTextField getUpdateDepartmentField() { return updateDepartmentField; }
    public JButton getUpdateButton() { return updateButton; }
    public JButton getDeleteButton() { return deleteButton; }
    public JButton getLoadCourseButton() { return loadCourseButton; }
}
