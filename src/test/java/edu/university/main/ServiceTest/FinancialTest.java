package edu.university.main.ServiceTest;

import edu.university.main.service.FinancialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FinancialTest {

    private FinancialService service;

    @BeforeEach
    void setUp() {
        service = new FinancialService();
    }

    @Test
    void testCalculateScholarshipAmountHighestTier() {
        double amount = service.calculateScholarshipAmount(3.95, 16, 10000.0);

        assertEquals(10000.0, amount);
    }

    @Test
    void testCalculateScholarshipAmountSecondTier() {
        double amount = service.calculateScholarshipAmount(3.75, 15, 10000.0);

        assertEquals(7500.0, amount);
    }

    @Test
    void testCalculateScholarshipAmountThirdTier() {
        double amount = service.calculateScholarshipAmount(3.6, 12, 10000.0);

        assertEquals(5000.0, amount);
    }

    @Test
    void testCalculateScholarshipAmountFourthTier() {
        double amount = service.calculateScholarshipAmount(3.2, 12, 10000.0);

        assertEquals(2500.0, amount);
    }

    @Test
    void testCalculateScholarshipAmountNoScholarship() {
        double amount = service.calculateScholarshipAmount(2.8, 12, 10000.0);

        assertEquals(0.0, amount);
    }

    @Test
    void testCalculateScholarshipAmountInsufficientCredits() {
        double amount = service.calculateScholarshipAmount(3.95, 10, 10000.0);

        assertEquals(0.0, amount);
    }

    @Test
    void testCalculateScholarshipAmountInvalidGPA() {
        double amount = service.calculateScholarshipAmount(5.0, 15, 10000.0);

        assertEquals(-1.0, amount);
    }

    @Test
    void testCalculateLoanEligibilityDependentLowIncome() {
        double loan = service.calculateLoanEligibility(25000.0, 12, true);

        assertEquals(5500.0, loan);
    }

    @Test
    void testCalculateLoanEligibilityDependentMidIncome() {
        double loan = service.calculateLoanEligibility(50000.0, 12, true);

        assertEquals(4500.0, loan);
    }

    @Test
    void testCalculateLoanEligibilityDependentHighIncome() {
        double loan = service.calculateLoanEligibility(80000.0, 6, true);

        assertEquals(3500.0, loan);
    }

    @Test
    void testCalculateLoanEligibilityIndependentLowIncome() {
        double loan = service.calculateLoanEligibility(40000.0, 12, false);

        assertEquals(12500.0, loan);
    }

    @Test
    void testCalculateLoanEligibilityIndependentMidIncome() {
        double loan = service.calculateLoanEligibility(80000.0, 12, false);

        assertEquals(9500.0, loan);
    }

    @Test
    void testCalculateLoanEligibilityIndependentHighIncome() {
        double loan = service.calculateLoanEligibility(120000.0, 6, false);

        assertEquals(6500.0, loan);
    }

    @Test
    void testCalculateLoanEligibilityInsufficientCredits() {
        double loan = service.calculateLoanEligibility(25000.0, 5, true);

        assertEquals(0.0, loan);
    }

    @Test
    void testCalculateLoanEligibilityNegativeIncome() {
        double loan = service.calculateLoanEligibility(-1000.0, 12, true);

        assertEquals(-1.0, loan);
    }

    @Test
    void testCalculatePaymentPlanWithInterest() {
        double payment = service.calculatePaymentPlan(10000.0, 12, 5.0);

        assertEquals(856.07, payment);
    }

    @Test
    void testCalculatePaymentPlanZeroInterest() {
        double payment = service.calculatePaymentPlan(12000.0, 12, 0.0);

        assertEquals(1000.0, payment);
    }

    @Test
    void testCalculatePaymentPlanLongTerm() {
        double payment = service.calculatePaymentPlan(50000.0, 120, 6.0);

        assertEquals(555.1, payment);
    }

    @Test
    void testCalculatePaymentPlanNegativeAmount() {
        double payment = service.calculatePaymentPlan(-1000.0, 12, 5.0);

        assertEquals(-1.0, payment);
    }

    @Test
    void testCalculatePaymentPlanZeroMonths() {
        double payment = service.calculatePaymentPlan(10000.0, 0, 5.0);

        assertEquals(-1.0, payment);
    }

    @Test
    void testCalculatePaymentPlanInvalidRate() {
        double payment = service.calculatePaymentPlan(10000.0, 12, 150.0);

        assertEquals(-1.0, payment);
    }

    @Test
    void testApplyLateFeeOver30Days() {
        double total = service.applyLateFee(1000.0, 35);

        assertEquals(1100.0, total);
    }

    @Test
    void testApplyLateFee16To30Days() {
        double total = service.applyLateFee(1000.0, 20);

        assertEquals(1050.0, total);
    }

    @Test
    void testApplyLateFee8To15Days() {
        double total = service.applyLateFee(1000.0, 10);

        assertEquals(1020.0, total);
    }

    @Test
    void testApplyLateFee1To7Days() {
        double total = service.applyLateFee(1000.0, 5);

        assertEquals(1025.0, total);
    }

    @Test
    void testApplyLateFeeNoDays() {
        double total = service.applyLateFee(1000.0, 0);

        assertEquals(1000.0, total);
    }

    @Test
    void testApplyLateFeeNegativeAmount() {
        double total = service.applyLateFee(-100.0, 10);

        assertEquals(-1.0, total);
    }

    @Test
    void testQualifiesForWaiverHighGPALowIncome() {
        boolean qualifies = service.qualifiesForWaiver(3.85, 35000.0, 12);

        assertTrue(qualifies);
    }

    @Test
    void testQualifiesForWaiverMidGPAVeryLowIncome() {
        boolean qualifies = service.qualifiesForWaiver(3.6, 20000.0, 15);

        assertTrue(qualifies);
    }

    @Test
    void testQualifiesForWaiverLowGPA() {
        boolean qualifies = service.qualifiesForWaiver(3.2, 35000.0, 12);

        assertFalse(qualifies);
    }

    @Test
    void testQualifiesForWaiverHighIncome() {
        boolean qualifies = service.qualifiesForWaiver(3.85, 50000.0, 12);

        assertFalse(qualifies);
    }

    @Test
    void testQualifiesForWaiverInsufficientCredits() {
        boolean qualifies = service.qualifiesForWaiver(3.85, 35000.0, 10);

        assertFalse(qualifies);
    }

    @Test
    void testQualifiesForWaiverInvalidGPA() {
        boolean qualifies = service.qualifiesForWaiver(5.0, 35000.0, 12);

        assertFalse(qualifies);
    }

    @Test
    void testCalculateWorkStudyAmount() {
        double amount = service.calculateWorkStudyAmount(15, 12.0, 16);

        assertEquals(2880.0, amount);
    }

    @Test
    void testCalculateWorkStudyAmountFullYear() {
        double amount = service.calculateWorkStudyAmount(20, 15.0, 52);

        assertEquals(15600.0, amount);
    }

    @Test
    void testCalculateWorkStudyAmountZeroHours() {
        double amount = service.calculateWorkStudyAmount(0, 12.0, 16);

        assertEquals(0.0, amount);
    }

    @Test
    void testCalculateWorkStudyAmountExcessiveHours() {
        double amount = service.calculateWorkStudyAmount(25, 12.0, 16);

        assertEquals(-1.0, amount);
    }

    @Test
    void testCalculateWorkStudyAmountNegativeRate() {
        double amount = service.calculateWorkStudyAmount(15, -5.0, 16);

        assertEquals(-1.0, amount);
    }

    @Test
    void testCalculateWorkStudyAmountExcessiveWeeks() {
        double amount = service.calculateWorkStudyAmount(15, 12.0, 60);

        assertEquals(-1.0, amount);
    }

    @Test
    void testCalculateFinancialAidPackageFullCoverage() {
        double outOfPocket = service.calculateFinancialAidPackage(20000.0, 10000.0, 5000.0, 3000.0, 2000.0);

        assertEquals(0.0, outOfPocket);
    }

    @Test
    void testCalculateFinancialAidPackagePartialCoverage() {
        double outOfPocket = service.calculateFinancialAidPackage(20000.0, 5000.0, 3000.0, 2000.0, 1000.0);

        assertEquals(9000.0, outOfPocket);
    }

    @Test
    void testCalculateFinancialAidPackageNoCoverage() {
        double outOfPocket = service.calculateFinancialAidPackage(20000.0, 0.0, 0.0, 0.0, 0.0);

        assertEquals(20000.0, outOfPocket);
    }

    @Test
    void testCalculateFinancialAidPackageNegativeTuition() {
        double outOfPocket = service.calculateFinancialAidPackage(-1000.0, 5000.0, 3000.0, 2000.0, 1000.0);

        assertEquals(-1.0, outOfPocket);
    }

    @Test
    void testCalculateRefundAmountWithin10Percent() {
        double refund = service.calculateRefundAmount(10000.0, 1, 16);

        assertEquals(9000.0, refund);
    }

    @Test
    void testCalculateRefundAmountWithin25Percent() {
        double refund = service.calculateRefundAmount(10000.0, 3, 16);

        assertEquals(7500.0, refund);
    }

    @Test
    void testCalculateRefundAmountWithin50Percent() {
        double refund = service.calculateRefundAmount(10000.0, 6, 16);

        assertEquals(5000.0, refund);
    }

    @Test
    void testCalculateRefundAmountWithin75Percent() {
        double refund = service.calculateRefundAmount(10000.0, 10, 16);

        assertEquals(2500.0, refund);
    }

    @Test
    void testCalculateRefundAmountOver75Percent() {
        double refund = service.calculateRefundAmount(10000.0, 14, 16);

        assertEquals(0.0, refund);
    }

    @Test
    void testCalculateRefundAmountNegativeTuition() {
        double refund = service.calculateRefundAmount(-1000.0, 3, 16);

        assertEquals(-1.0, refund);
    }

    @Test
    void testCalculateRefundAmountExceedsTotal() {
        double refund = service.calculateRefundAmount(10000.0, 20, 16);

        assertEquals(-1.0, refund);
    }
}