package edu.university.main.ControllerTest;

import edu.university.main.controller.FinancialController;
import edu.university.main.service.FinancialService;
import edu.university.main.view.FinancialView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FinancialControllerTest {

    private FinancialController controller;
    private StubFinancialService financialService;
    private SpyFinancialView view;

    @BeforeEach
    void setUp() {
        financialService = new StubFinancialService();
        view = new SpyFinancialView();
        controller = new FinancialController(financialService, view);
    }

    @Test
    void testCalculateScholarshipHighGPA() {
        financialService.setScholarshipAmountToReturn(5000.0);

        controller.calculateScholarship(3.8, 15, 10000.0);

        assertEquals(3.8, financialService.getGpaReceived());
        assertEquals(15, financialService.getCreditsReceived());
        assertEquals(10000.0, financialService.getBaseTuitionReceived());
        assertEquals(5000.0, view.getScholarshipAmountDisplayed());
    }

    @Test
    void testCalculateScholarshipLowGPA() {
        financialService.setScholarshipAmountToReturn(0.0);

        controller.calculateScholarship(2.5, 12, 10000.0);

        assertEquals(2.5, financialService.getGpaReceived());
        assertEquals(0.0, view.getScholarshipAmountDisplayed());
    }

    @Test
    void testCalculateScholarshipMediumCredits() {
        financialService.setScholarshipAmountToReturn(2000.0);

        controller.calculateScholarship(3.5, 9, 8000.0);

        assertEquals(3.5, financialService.getGpaReceived());
        assertEquals(9, financialService.getCreditsReceived());
        assertEquals(2000.0, view.getScholarshipAmountDisplayed());
    }

    @Test
    void testCalculateLoanDependentHighIncome() {
        financialService.setLoanEligibilityToReturn(5000.0);

        controller.calculateLoan(80000.0, 15, true);

        assertEquals(80000.0, financialService.getIncomeReceived());
        assertEquals(15, financialService.getCreditsForLoan());
        assertTrue(financialService.getDependentReceived());
        assertEquals(5000.0, view.getLoanEligibilityDisplayed());
    }

    @Test
    void testCalculateLoanIndependentLowIncome() {
        financialService.setLoanEligibilityToReturn(15000.0);

        controller.calculateLoan(20000.0, 12, false);

        assertEquals(20000.0, financialService.getIncomeReceived());
        assertEquals(12, financialService.getCreditsForLoan());
        assertFalse(financialService.getDependentReceived());
        assertEquals(15000.0, view.getLoanEligibilityDisplayed());
    }

    @Test
    void testCalculatePaymentShortTerm() {
        financialService.setMonthlyPaymentToReturn(856.07);

        controller.calculatePayment(10000.0, 12, 0.05);

        assertEquals(10000.0, financialService.getAmountReceived());
        assertEquals(12, financialService.getMonthsReceived());
        assertEquals(0.05, financialService.getRateReceived());
        assertEquals(856.07, view.getMonthlyPaymentDisplayed());
    }

    @Test
    void testCalculatePaymentLongTerm() {
        financialService.setMonthlyPaymentToReturn(555.10);

        controller.calculatePayment(50000.0, 120, 0.06);

        assertEquals(50000.0, financialService.getAmountReceived());
        assertEquals(120, financialService.getMonthsReceived());
        assertEquals(0.06, financialService.getRateReceived());
        assertEquals(555.10, view.getMonthlyPaymentDisplayed());
    }

    @Test
    void testCalculatePaymentZeroInterest() {
        financialService.setMonthlyPaymentToReturn(500.0);

        controller.calculatePayment(5000.0, 10, 0.0);

        assertEquals(0.0, financialService.getRateReceived());
        assertEquals(500.0, view.getMonthlyPaymentDisplayed());
    }

    @Test
    void testApplyLateFeeFewDays() {
        financialService.setTotalWithFeeToReturn(1050.0);

        controller.applyLate(1000.0, 5);

        assertEquals(1000.0, financialService.getBaseReceived());
        assertEquals(5, financialService.getDaysReceived());
        assertEquals(1050.0, view.getTotalWithFeeDisplayed());
    }

    @Test
    void testApplyLateFeeManyDays() {
        financialService.setTotalWithFeeToReturn(2300.0);

        controller.applyLate(2000.0, 30);

        assertEquals(2000.0, financialService.getBaseReceived());
        assertEquals(30, financialService.getDaysReceived());
        assertEquals(2300.0, view.getTotalWithFeeDisplayed());
    }

    @Test
    void testApplyLateFeeNoDays() {
        financialService.setTotalWithFeeToReturn(1500.0);

        controller.applyLate(1500.0, 0);

        assertEquals(0, financialService.getDaysReceived());
        assertEquals(1500.0, view.getTotalWithFeeDisplayed());
    }

    // Test Doubles
    private static class StubFinancialService extends FinancialService {
        private double scholarshipAmountToReturn;
        private double loanEligibilityToReturn;
        private double monthlyPaymentToReturn;
        private double totalWithFeeToReturn;

        private double gpaReceived;
        private int creditsReceived;
        private double baseTuitionReceived;
        private double incomeReceived;
        private int creditsForLoan;
        private boolean dependentReceived;
        private double amountReceived;
        private int monthsReceived;
        private double rateReceived;
        private double baseReceived;
        private int daysReceived;

        public void setScholarshipAmountToReturn(double amount) {
            this.scholarshipAmountToReturn = amount;
        }

        public void setLoanEligibilityToReturn(double eligibility) {
            this.loanEligibilityToReturn = eligibility;
        }

        public void setMonthlyPaymentToReturn(double payment) {
            this.monthlyPaymentToReturn = payment;
        }

        public void setTotalWithFeeToReturn(double total) {
            this.totalWithFeeToReturn = total;
        }

        @Override
        public double calculateScholarshipAmount(double gpa, int credits, double baseTuition) {
            this.gpaReceived = gpa;
            this.creditsReceived = credits;
            this.baseTuitionReceived = baseTuition;
            return scholarshipAmountToReturn;
        }

        @Override
        public double calculateLoanEligibility(double income, int credits, boolean dependent) {
            this.incomeReceived = income;
            this.creditsForLoan = credits;
            this.dependentReceived = dependent;
            return loanEligibilityToReturn;
        }

        @Override
        public double calculatePaymentPlan(double amount, int months, double rate) {
            this.amountReceived = amount;
            this.monthsReceived = months;
            this.rateReceived = rate;
            return monthlyPaymentToReturn;
        }

        @Override
        public double applyLateFee(double base, int days) {
            this.baseReceived = base;
            this.daysReceived = days;
            return totalWithFeeToReturn;
        }

        public double getGpaReceived() { return gpaReceived; }
        public int getCreditsReceived() { return creditsReceived; }
        public double getBaseTuitionReceived() { return baseTuitionReceived; }
        public double getIncomeReceived() { return incomeReceived; }
        public int getCreditsForLoan() { return creditsForLoan; }
        public boolean getDependentReceived() { return dependentReceived; }
        public double getAmountReceived() { return amountReceived; }
        public int getMonthsReceived() { return monthsReceived; }
        public double getRateReceived() { return rateReceived; }
        public double getBaseReceived() { return baseReceived; }
        public int getDaysReceived() { return daysReceived; }
    }

    private static class SpyFinancialView extends FinancialView {
        private double scholarshipAmountDisplayed;
        private double loanEligibilityDisplayed;
        private double monthlyPaymentDisplayed;
        private double totalWithFeeDisplayed;

        @Override
        public void displayScholarshipAmount(double amount) {
            this.scholarshipAmountDisplayed = amount;
        }

        @Override
        public void displayLoanEligibility(double eligibility) {
            this.loanEligibilityDisplayed = eligibility;
        }

        @Override
        public void displayMonthlyPayment(double monthly) {
            this.monthlyPaymentDisplayed = monthly;
        }

        @Override
        public void displayTotalWithFee(double total) {
            this.totalWithFeeDisplayed = total;
        }

        public double getScholarshipAmountDisplayed() { return scholarshipAmountDisplayed; }
        public double getLoanEligibilityDisplayed() { return loanEligibilityDisplayed; }
        public double getMonthlyPaymentDisplayed() { return monthlyPaymentDisplayed; }
        public double getTotalWithFeeDisplayed() { return totalWithFeeDisplayed; }
    }
}