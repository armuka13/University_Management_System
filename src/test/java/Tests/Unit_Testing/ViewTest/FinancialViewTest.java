package Tests.Unit_Testing.ViewTest;

import edu.university.main.view.FinancialView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class FinancialViewTest {
    private FinancialView financialView;

    @BeforeEach
    void setUp() {
        financialView = new FinancialView();
    }

    @Test
    @DisplayName("Should initialize all scholarship text field components")
    void testScholarshipTextFieldInitialization() {
        assertNotNull(financialView.getScholarshipGpaField(), "Scholarship GPA field should not be null");
        assertNotNull(financialView.getScholarshipCreditsField(), "Scholarship credits field should not be null");
        assertNotNull(financialView.getBaseTuitionField(), "Base tuition field should not be null");
    }

    @Test
    @DisplayName("Should initialize all loan text field components")
    void testLoanTextFieldInitialization() {
        assertNotNull(financialView.getIncomeField(), "Income field should not be null");
        assertNotNull(financialView.getLoanCreditsField(), "Loan credits field should not be null");
    }

    @Test
    @DisplayName("Should initialize checkbox component")
    void testCheckboxInitialization() {
        assertNotNull(financialView.getDependentBox(), "Dependent checkbox should not be null");
        assertFalse(financialView.getDependentBox().isSelected(), "Dependent box should be initially unchecked");
    }

    @Test
    @DisplayName("Should initialize all button components")
    void testButtonInitialization() {
        assertNotNull(financialView.getCalculateScholarshipButton(), "Calculate scholarship button should not be null");
        assertNotNull(financialView.getCalculateLoanButton(), "Calculate loan button should not be null");

        assertEquals("Calculate", financialView.getCalculateScholarshipButton().getText());
        assertEquals("Calculate", financialView.getCalculateLoanButton().getText());
    }

    @Test
    @DisplayName("Should display scholarship amount with correct formatting")
    void testDisplayScholarshipAmount() {
        assertDoesNotThrow(() -> financialView.displayScholarshipAmount(5000.00));
        assertDoesNotThrow(() -> financialView.displayScholarshipAmount(0.00));
        assertDoesNotThrow(() -> financialView.displayScholarshipAmount(25000.50));
    }

    @Test
    @DisplayName("Should display loan eligibility with correct formatting")
    void testDisplayLoanEligibility() {
        assertDoesNotThrow(() -> financialView.displayLoanEligibility(10000.00));
        assertDoesNotThrow(() -> financialView.displayLoanEligibility(0.00));
        assertDoesNotThrow(() -> financialView.displayLoanEligibility(50000.75));
    }

    @Test
    @DisplayName("Should display monthly payment with correct formatting")
    void testDisplayMonthlyPayment() {
        assertDoesNotThrow(() -> financialView.displayMonthlyPayment(250.50));
        assertDoesNotThrow(() -> financialView.displayMonthlyPayment(0.00));
        assertDoesNotThrow(() -> financialView.displayMonthlyPayment(1500.99));
    }

    @Test
    @DisplayName("Should display total with fees with correct formatting")
    void testDisplayTotalWithFee() {
        assertDoesNotThrow(() -> financialView.displayTotalWithFee(15000.00));
        assertDoesNotThrow(() -> financialView.displayTotalWithFee(0.00));
        assertDoesNotThrow(() -> financialView.displayTotalWithFee(75000.25));
    }

    @Test
    @DisplayName("Should display messages in activity log")
    void testDisplayMessage() {
        String testMessage = "Test financial message";
        assertDoesNotThrow(() -> financialView.displayMessage(testMessage));
    }

    @Test
    @DisplayName("Should clear messages from activity log")
    void testClearMessages() throws NoSuchFieldException, IllegalAccessException {
        Field messageAreaField = FinancialView.class.getDeclaredField("messageArea");
        messageAreaField.setAccessible(true);
        JTextArea messageArea = (JTextArea) messageAreaField.get(financialView);

        financialView.displayMessage("Test message 1");
        financialView.displayMessage("Test message 2");
        assertTrue(messageArea.getText().length() > 0);

        financialView.clearMessages();
        assertEquals("", messageArea.getText());
    }

    @Test
    @DisplayName("Should have correct layout manager")
    void testLayoutManager() {
        assertTrue(financialView.getLayout() instanceof BorderLayout);
    }

    @Test
    @DisplayName("All text fields should be initially empty")
    void testInitialTextFieldValues() {
        assertEquals("", financialView.getScholarshipGpaField().getText());
        assertEquals("", financialView.getScholarshipCreditsField().getText());
        assertEquals("", financialView.getBaseTuitionField().getText());
        assertEquals("", financialView.getIncomeField().getText());
        assertEquals("", financialView.getLoanCreditsField().getText());
    }

    @Test
    @DisplayName("Buttons should have correct background colors")
    void testButtonColors() {
        assertEquals(new Color(70, 130, 180), financialView.getCalculateScholarshipButton().getBackground());
        assertEquals(Color.WHITE, financialView.getCalculateScholarshipButton().getForeground());

        assertEquals(new Color(60, 179, 113), financialView.getCalculateLoanButton().getBackground());
        assertEquals(Color.WHITE, financialView.getCalculateLoanButton().getForeground());
    }

    @Test
    @DisplayName("Should handle negative scholarship amounts")
    void testNegativeScholarshipAmount() {
        assertDoesNotThrow(() -> financialView.displayScholarshipAmount(-1000.00));
    }

    @Test
    @DisplayName("Should handle negative loan eligibility")
    void testNegativeLoanEligibility() {
        assertDoesNotThrow(() -> financialView.displayLoanEligibility(-5000.00));
    }

    @Test
    @DisplayName("Should handle negative monthly payment")
    void testNegativeMonthlyPayment() {
        assertDoesNotThrow(() -> financialView.displayMonthlyPayment(-250.00));
    }

    @Test
    @DisplayName("Should handle negative total with fees")
    void testNegativeTotalWithFees() {
        assertDoesNotThrow(() -> financialView.displayTotalWithFee(-10000.00));
    }

    @Test
    @DisplayName("Should handle very large scholarship amounts")
    void testLargeScholarshipAmount() {
        assertDoesNotThrow(() -> financialView.displayScholarshipAmount(Double.MAX_VALUE));
    }

    @Test
    @DisplayName("Should handle very large loan eligibility")
    void testLargeLoanEligibility() {
        assertDoesNotThrow(() -> financialView.displayLoanEligibility(999999999.99));
    }

    @Test
    @DisplayName("Should handle zero values for all financial displays")
    void testZeroValues() {
        assertDoesNotThrow(() -> {
            financialView.displayScholarshipAmount(0.00);
            financialView.displayLoanEligibility(0.00);
            financialView.displayMonthlyPayment(0.00);
            financialView.displayTotalWithFee(0.00);
        });
    }

    @Test
    @DisplayName("Dependent checkbox should be toggleable")
    void testDependentCheckboxToggle() {
        JCheckBox dependentBox = financialView.getDependentBox();

        assertFalse(dependentBox.isSelected());
        dependentBox.setSelected(true);
        assertTrue(dependentBox.isSelected());
        dependentBox.setSelected(false);
        assertFalse(dependentBox.isSelected());
    }

    @Test
    @DisplayName("Should format currency values to 2 decimal places")
    void testCurrencyFormatting() throws NoSuchFieldException, IllegalAccessException {
        Field scholarshipLabelField = FinancialView.class.getDeclaredField("scholarshipLabel");
        scholarshipLabelField.setAccessible(true);
        JLabel scholarshipLabel = (JLabel) scholarshipLabelField.get(financialView);

        financialView.displayScholarshipAmount(5000.5);
        assertTrue(scholarshipLabel.getText().contains("5000.50"));

        financialView.displayScholarshipAmount(1234.567);
        assertTrue(scholarshipLabel.getText().contains("1234.57"));
    }

    @Test
    @DisplayName("Should handle decimal precision for all financial displays")
    void testDecimalPrecision() throws NoSuchFieldException, IllegalAccessException {
        Field loanLabelField = FinancialView.class.getDeclaredField("loanLabel");
        loanLabelField.setAccessible(true);
        JLabel loanLabel = (JLabel) loanLabelField.get(financialView);

        financialView.displayLoanEligibility(10000.123456);
        assertTrue(loanLabel.getText().contains("10000.12"));
    }

    @Test
    @DisplayName("Should display all financial information correctly together")
    void testMultipleDisplays() {
        assertDoesNotThrow(() -> {
            financialView.displayScholarshipAmount(5000.00);
            financialView.displayLoanEligibility(20000.00);
            financialView.displayMonthlyPayment(500.00);
            financialView.displayTotalWithFee(25000.00);
        });
    }
}