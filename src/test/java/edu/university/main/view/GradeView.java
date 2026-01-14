package edu.university.main.view;



import javax.swing.*;
import java.awt.*;

public class GradeView extends JPanel {
    private JTextArea messageArea;
    private JLabel averageLabel;
    private JLabel scoreLabel;
    private JLabel eligibilityLabel;
    private JLabel honorsLabel;

    private JTextField scoreField;
    private JCheckBox attendanceBox;
    private JCheckBox participationBox;
    private JButton calculateButton;

    private JTextField gpaField;
    private JTextField creditsField;
    private JButton checkHonorsButton;

    public GradeView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new BorderLayout());

        JPanel infoPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Grade Information"));
        averageLabel = new JLabel("Average: N/A");
        scoreLabel = new JLabel("Score: N/A");
        eligibilityLabel = new JLabel("Honors Eligible: N/A");
        honorsLabel = new JLabel("Honors Level: N/A");

        averageLabel.setFont(new Font("Arial", Font.BOLD, 14));
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 14));
        eligibilityLabel.setFont(new Font("Arial", Font.BOLD, 14));
        honorsLabel.setFont(new Font("Arial", Font.BOLD, 14));

        infoPanel.add(averageLabel);
        infoPanel.add(scoreLabel);
        infoPanel.add(eligibilityLabel);
        infoPanel.add(honorsLabel);

        JPanel bonusPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        bonusPanel.setBorder(BorderFactory.createTitledBorder("Calculate Grade with Bonus"));

        bonusPanel.add(new JLabel("Base Score:"));
        scoreField = new JTextField();
        bonusPanel.add(scoreField);

        bonusPanel.add(new JLabel("Has Attendance:"));
        attendanceBox = new JCheckBox();
        bonusPanel.add(attendanceBox);

        bonusPanel.add(new JLabel("Has Participation:"));
        participationBox = new JCheckBox();
        bonusPanel.add(participationBox);

        calculateButton = new JButton("Calculate Final Score");
        calculateButton.setBackground(new Color(70, 130, 180));
        calculateButton.setForeground(Color.WHITE);
        calculateButton.setFont(new Font("Arial", Font.BOLD, 12));
        bonusPanel.add(new JLabel());
        bonusPanel.add(calculateButton);

        JPanel honorsPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        honorsPanel.setBorder(BorderFactory.createTitledBorder("Check Honors Eligibility"));

        honorsPanel.add(new JLabel("GPA:"));
        gpaField = new JTextField();
        honorsPanel.add(gpaField);

        honorsPanel.add(new JLabel("Credits:"));
        creditsField = new JTextField();
        honorsPanel.add(creditsField);

        checkHonorsButton = new JButton("Check Honors");
        checkHonorsButton.setBackground(new Color(60, 179, 113));
        checkHonorsButton.setForeground(Color.WHITE);
        checkHonorsButton.setFont(new Font("Arial", Font.BOLD, 12));
        honorsPanel.add(new JLabel());
        honorsPanel.add(checkHonorsButton);

        JPanel formsPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        formsPanel.add(bonusPanel);
        formsPanel.add(honorsPanel);

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

    public void displayAverage(double average) {
        averageLabel.setText("Average: " + String.format("%.2f", average));
        displayMessage("Course average: " + average);
    }

    public void displayFinalScore(double score) {
        scoreLabel.setText("Score: " + String.format("%.2f", score));
        scoreLabel.setForeground(score >= 90 ? new Color(0, 128, 0) : (score >= 70 ? Color.ORANGE : Color.RED));
        displayMessage("Final score: " + score);
    }

    public void displayCurvedGrade(double curved) {
        displayMessage("Curved grade: " + curved);
    }

    public void displayHonorsEligibility(boolean eligible) {
        eligibilityLabel.setText("Honors Eligible: " + (eligible ? "Yes" : "No"));
        eligibilityLabel.setForeground(eligible ? new Color(0, 128, 0) : Color.RED);
        displayMessage("Honors eligibility: " + eligible);
    }

    public void displayHonorsLevel(String level) {
        honorsLabel.setText("Honors Level: " + level.replace("_", " "));
        displayMessage("Honors level: " + level);
    }

    public void clearMessages() {
        messageArea.setText("");
    }

    // Getters
    public JTextField getScoreField() { return scoreField; }
    public JCheckBox getAttendanceBox() { return attendanceBox; }
    public JCheckBox getParticipationBox() { return participationBox; }
    public JButton getCalculateButton() { return calculateButton; }
    public JTextField getGpaField() { return gpaField; }
    public JTextField getCreditsField() { return creditsField; }
    public JButton getCheckHonorsButton() { return checkHonorsButton; }
}
