package edu.university.main.view;



import javax.swing.*;
import java.awt.*;

public class FinancialView extends JPanel {
    private JTextArea messageArea;
    private JLabel scholarshipLabel;
    private JLabel loanLabel;
    private JLabel paymentLabel;
    private JLabel feeLabel;

    private JTextField scholarshipGpaField;
    private JTextField scholarshipCreditsField;
    private JTextField baseTuitionField;
    private JButton calculateScholarshipButton;

    private JTextField incomeField;
    private JTextField loanCreditsField;
    private JCheckBox dependentBox;
    private JButton calculateLoanButton;

    public FinancialView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new BorderLayout());

        JPanel infoPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Financial Information"));
        scholarshipLabel = new JLabel("Scholarship: N/A");
        loanLabel = new JLabel("Loan Eligibility: N/A");
        paymentLabel = new JLabel("Monthly Payment: N/A");
        feeLabel = new JLabel("Total with Fees: N/A");

        scholarshipLabel.setFont(new Font("Arial", Font.BOLD, 14));
        loanLabel.setFont(new Font("Arial", Font.BOLD, 14));
        paymentLabel.setFont(new Font("Arial", Font.BOLD, 14));
        feeLabel.setFont(new Font("Arial", Font.BOLD, 14));

        infoPanel.add(scholarshipLabel);
        infoPanel.add(loanLabel);
        infoPanel.add(paymentLabel);
        infoPanel.add(feeLabel);

        JPanel scholarshipPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        scholarshipPanel.setBorder(BorderFactory.createTitledBorder("Calculate Scholarship"));

        scholarshipPanel.add(new JLabel("GPA:"));
        scholarshipGpaField = new JTextField();
        scholarshipPanel.add(scholarshipGpaField);

        scholarshipPanel.add(new JLabel("Credit Hours:"));
        scholarshipCreditsField = new JTextField();
        scholarshipPanel.add(scholarshipCreditsField);

        scholarshipPanel.add(new JLabel("Base Tuition:"));
        baseTuitionField = new JTextField();
        scholarshipPanel.add(baseTuitionField);

        calculateScholarshipButton = new JButton("Calculate");
        calculateScholarshipButton.setBackground(new Color(70, 130, 180));
        calculateScholarshipButton.setForeground(Color.WHITE);
        calculateScholarshipButton.setFont(new Font("Arial", Font.BOLD, 12));
        scholarshipPanel.add(new JLabel());
        scholarshipPanel.add(calculateScholarshipButton);

        JPanel loanPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        loanPanel.setBorder(BorderFactory.createTitledBorder("Calculate Loan Eligibility"));

        loanPanel.add(new JLabel("Family Income:"));
        incomeField = new JTextField();
        loanPanel.add(incomeField);

        loanPanel.add(new JLabel("Credit Hours:"));
        loanCreditsField = new JTextField();
        loanPanel.add(loanCreditsField);

        loanPanel.add(new JLabel("Is Dependent:"));
        dependentBox = new JCheckBox();
        loanPanel.add(dependentBox);

        calculateLoanButton = new JButton("Calculate");
        calculateLoanButton.setBackground(new Color(60, 179, 113));
        calculateLoanButton.setForeground(Color.WHITE);
        calculateLoanButton.setFont(new Font("Arial", Font.BOLD, 12));
        loanPanel.add(new JLabel());
        loanPanel.add(calculateLoanButton);

        JPanel formsPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        formsPanel.add(scholarshipPanel);
        formsPanel.add(loanPanel);

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

    public void displayScholarshipAmount(double amount) {
        scholarshipLabel.setText("Scholarship: $" + String.format("%.2f", amount));
        displayMessage("Scholarship amount: $" + amount);
    }

    public void displayLoanEligibility(double amount) {
        loanLabel.setText("Loan Eligibility: $" + String.format("%.2f", amount));
        displayMessage("Loan eligibility: $" + amount);
    }

    public void displayMonthlyPayment(double payment) {
        paymentLabel.setText("Monthly Payment: $" + String.format("%.2f", payment));
        displayMessage("Monthly payment: $" + payment);
    }

    public void displayTotalWithFee(double total) {
        feeLabel.setText("Total with Fees: $" + String.format("%.2f", total));
        displayMessage("Total with fees: $" + total);
    }

    public void clearMessages() {
        messageArea.setText("");
    }

    // Getters
    public JTextField getScholarshipGpaField() { return scholarshipGpaField; }
    public JTextField getScholarshipCreditsField() { return scholarshipCreditsField; }
    public JTextField getBaseTuitionField() { return baseTuitionField; }
    public JButton getCalculateScholarshipButton() { return calculateScholarshipButton; }
    public JTextField getIncomeField() { return incomeField; }
    public JTextField getLoanCreditsField() { return loanCreditsField; }
    public JCheckBox getDependentBox() { return dependentBox; }
    public JButton getCalculateLoanButton() { return calculateLoanButton; }
}
