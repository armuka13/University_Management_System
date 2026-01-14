package edu.university.main.view;



import javax.swing.*;
import java.awt.*;

public class CourseView extends JPanel {
    private JTextArea messageArea;
    private JLabel seatsLabel;
    private JLabel fillRateLabel;
    private JLabel popularityLabel;

    private JTextField courseIdField;
    private JTextField courseNameField;
    private JTextField instructorField;
    private JTextField creditsField;
    private JTextField capacityField;
    private JTextField departmentField;
    private JButton createButton;

    private JTextField searchCourseIdField;
    private JButton searchButton;

    public CourseView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new BorderLayout());

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

        JPanel formsPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        formsPanel.add(inputPanel);
        formsPanel.add(searchPanel);

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

    // Getters for form fields
    public JTextField getCourseIdField() { return courseIdField; }
    public JTextField getCourseNameField() { return courseNameField; }
    public JTextField getInstructorField() { return instructorField; }
    public JTextField getCreditsField() { return creditsField; }
    public JTextField getCapacityField() { return capacityField; }
    public JTextField getDepartmentField() { return departmentField; }
    public JButton getCreateButton() { return createButton; }
    public JTextField getSearchCourseIdField() { return searchCourseIdField; }
    public JButton getSearchButton() { return searchButton; }
}
