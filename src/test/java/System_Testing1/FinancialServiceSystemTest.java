package System_Testing1;


import edu.university.main.service.FinancialService;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FR-FS: Financial Service System Tests")
public class FinancialServiceSystemTest {

    private FinancialService financialService;

    @BeforeEach
    void setUp() {
        financialService = new FinancialService();
    }

    @Test
    @DisplayName("FR-FS-001: Scholarship - 100% (GPA 3.9+, 15+ credits)")
    void testScholarship_100Percent() {
        double scholarship = financialService.calculateScholarshipAmount(3.95, 16, 10000.0);
        assertEquals(10000.0, scholarship, 0.01, "100% scholarship");
    }

    @Test
    @DisplayName("FR-FS-001: Scholarship - 75% (GPA 3.7+, 15+ credits)")
    void testScholarship_75Percent() {
        double scholarship = financialService.calculateScholarshipAmount(3.75, 15, 10000.0);
        assertEquals(7500.0, scholarship, 0.01, "75% scholarship");
    }

    @Test
    @DisplayName("FR-FS-001: Scholarship - 50% (GPA 3.5+, 12+ credits)")
    void testScholarship_50Percent() {
        double scholarship = financialService.calculateScholarshipAmount(3.6, 13, 10000.0);
        assertEquals(5000.0, scholarship, 0.01, "50% scholarship");
    }

    @Test
    @DisplayName("FR-FS-001: Scholarship - 25% (GPA 3.0+, 12+ credits)")
    void testScholarship_25Percent() {
        double scholarship = financialService.calculateScholarshipAmount(3.2, 12, 10000.0);
        assertEquals(2500.0, scholarship, 0.01, "25% scholarship");
    }

    @Test
    @DisplayName("FR-FS-001: Scholarship - 0% (Below threshold)")
    void testScholarship_0Percent() {
        double scholarship = financialService.calculateScholarshipAmount(2.8, 12, 10000.0);
        assertEquals(0.0, scholarship, 0.01, "No scholarship");
    }

    @Test
    @DisplayName("FR-FS-002: Loan Eligibility - Dependent Low Income")
    void testLoanEligibility_DependentLowIncome() {
        double loan = financialService.calculateLoanEligibility(25000.0, 12, true);
        assertEquals(5500.0, loan, 0.01, "Dependent, <$30k, 12+ credits");
    }

    @Test
    @DisplayName("FR-FS-002: Loan Eligibility - Independent Low Income")
    void testLoanEligibility_IndependentLowIncome() {
        double loan = financialService.calculateLoanEligibility(40000.0, 13, false);
        assertEquals(12500.0, loan, 0.01, "Independent, <$50k, 12+ credits");
    }

    @Test
    @DisplayName("FR-FS-002: Loan Eligibility - Independent High Income")
    void testLoanEligibility_IndependentHighIncome() {
        double loan = financialService.calculateLoanEligibility(120000.0, 8, false);
        assertEquals(6500.0, loan, 0.01, "Independent, high income, 6+ credits");
    }

    @Test
    @DisplayName("FR-FS-003: Payment Plan - No Interest")
    void testPaymentPlan_NoInterest() {
        double monthly = financialService.calculatePaymentPlan(12000.0, 12, 0.0);
        assertEquals(1000.0, monthly, 0.01, "12000/12 = 1000");
    }

    @Test
    @DisplayName("FR-FS-003: Payment Plan - With Interest")
    void testPaymentPlan_WithInterest() {
        double monthly = financialService.calculatePaymentPlan(10000.0, 12, 6.0);
        assertTrue(monthly > 850 && monthly < 870, "Monthly payment with 6% APR");
    }

    @Test
    @DisplayName("FR-FS-004: Late Fee - 1-7 Days")
    void testLateFee_1To7Days() {
        double total = financialService.applyLateFee(1000.0, 5);
        assertEquals(1025.0, total, 0.01, "$25 flat fee");
    }

    @Test
    @DisplayName("FR-FS-004: Late Fee - 8-15 Days")
    void testLateFee_8To15Days() {
        double total = financialService.applyLateFee(1000.0, 10);
        assertEquals(1020.0, total, 0.01, "2% fee");
    }

    @Test
    @DisplayName("FR-FS-004: Late Fee - 16-30 Days")
    void testLateFee_16To30Days() {
        double total = financialService.applyLateFee(1000.0, 20);
        assertEquals(1050.0, total, 0.01, "5% fee");
    }

    @Test
    @DisplayName("FR-FS-004: Late Fee - Over 30 Days")
    void testLateFee_Over30Days() {
        double total = financialService.applyLateFee(1000.0, 35);
        assertEquals(1100.0, total, 0.01, "10% fee");
    }

    @Test
    @DisplayName("FR-FS-005: Fee Waiver - Qualifies (High GPA, Low Income)")
    void testFeeWaiver_Qualifies() {
        boolean qualifies = financialService.qualifiesForWaiver(3.85, 35000.0, 14);
        assertTrue(qualifies, "GPA 3.8+, income <$40k, 12+ credits");
    }

    @Test
    @DisplayName("FR-FS-005: Fee Waiver - Qualifies (Alternative)")
    void testFeeWaiver_QualifiesAlternative() {
        boolean qualifies = financialService.qualifiesForWaiver(3.6, 20000.0, 15);
        assertTrue(qualifies, "GPA 3.5+, income <$25k, 15+ credits");
    }

    @Test
    @DisplayName("FR-FS-005: Fee Waiver - Does Not Qualify")
    void testFeeWaiver_DoesNotQualify() {
        boolean qualifies = financialService.qualifiesForWaiver(3.4, 30000.0, 12);
        assertFalse(qualifies, "Does not meet criteria");
    }

    @Test
    @DisplayName("FR-FS-006: Work Study Amount")
    void testWorkStudyAmount() {
        double amount = financialService.calculateWorkStudyAmount(15, 12.50, 16);
        assertEquals(3000.0, amount, 0.01, "15 * 12.50 * 16 = 3000");
    }

    @Test
    @DisplayName("FR-FS-007: Financial Aid Package")
    void testFinancialAidPackage() {
        double outOfPocket = financialService.calculateFinancialAidPackage(
                20000.0, 5000.0, 3000.0, 8000.0, 2000.0
        );
        assertEquals(2000.0, outOfPocket, 0.01, "20000 - 18000 = 2000");
    }

    @Test
    @DisplayName("FR-FS-007: Financial Aid Package - Fully Covered")
    void testFinancialAidPackage_FullyCovered() {
        double outOfPocket = financialService.calculateFinancialAidPackage(
                15000.0, 8000.0, 5000.0, 3000.0, 1000.0
        );
        assertEquals(0.0, outOfPocket, 0.01, "Aid exceeds tuition");
    }

    @Test
    @DisplayName("FR-FS-008: Refund - 10% Complete")
    void testRefund_10PercentComplete() {
        double refund = financialService.calculateRefundAmount(10000.0, 1, 16);
        assertEquals(9000.0, refund, 0.01, "90% refund");
    }

    @Test
    @DisplayName("FR-FS-008: Refund - 25% Complete")
    void testRefund_25PercentComplete() {
        double refund = financialService.calculateRefundAmount(10000.0, 4, 16);
        assertEquals(7500.0, refund, 0.01, "75% refund");
    }

    @Test
    @DisplayName("FR-FS-008: Refund - 50% Complete")
    void testRefund_50PercentComplete() {
        double refund = financialService.calculateRefundAmount(10000.0, 8, 16);
        assertEquals(5000.0, refund, 0.01, "50% refund");
    }

    @Test
    @DisplayName("FR-FS-008: Refund - 75% Complete")
    void testRefund_75PercentComplete() {
        double refund = financialService.calculateRefundAmount(10000.0, 12, 16);
        assertEquals(2500.0, refund, 0.01, "25% refund");
    }

    @Test
    @DisplayName("FR-FS-008: Refund - Over 75% Complete")
    void testRefund_Over75PercentComplete() {
        double refund = financialService.calculateRefundAmount(10000.0, 13, 16);
        assertEquals(0.0, refund, 0.01, "No refund");
    }
}
