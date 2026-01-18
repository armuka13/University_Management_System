package Tests.Unit_Testing.ModelTest;

import edu.university.main.model.Scholarship;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScholarshipTest {

    private Scholarship scholarship;

    @BeforeEach
    void setUp() {
        scholarship = new Scholarship("SCH001", "Merit Scholarship", 5000.0, "MERIT");
    }

    @Test
    void testCalculateAwardAmountHighestTier() {
        double award = scholarship.calculateAwardAmount(10000.0, 3.9, 15);

        assertEquals(10000.0, award);
    }

    @Test
    void testCalculateAwardAmountSecondTier() {
        double award = scholarship.calculateAwardAmount(10000.0, 3.7, 15);

        assertEquals(7500.0, award);
    }

    @Test
    void testCalculateAwardAmountThirdTier() {
        double award = scholarship.calculateAwardAmount(10000.0, 3.5, 12);

        assertEquals(5000.0, award);
    }

    @Test
    void testCalculateAwardAmountFourthTier() {
        double award = scholarship.calculateAwardAmount(10000.0, 3.2, 12);

        assertEquals(2500.0, award);
    }

    @Test
    void testCalculateAwardAmountNotEligible() {
        double award = scholarship.calculateAwardAmount(10000.0, 2.8, 12);

        assertEquals(0.0, award);
    }

    @Test
    void testCalculateAwardAmountInsufficientCredits() {
        double award = scholarship.calculateAwardAmount(10000.0, 3.9, 10);

        assertEquals(0.0, award);
    }

    @Test
    void testCalculateAwardAmountInvalidGPA() {
        double award = scholarship.calculateAwardAmount(10000.0, 5.0, 15);

        assertEquals(-1.0, award);
    }

    @Test
    void testCalculateAwardAmountNegativeBase() {
        double award = scholarship.calculateAwardAmount(-1000.0, 3.9, 15);

        assertEquals(-1.0, award);
    }

    @Test
    void testIsEligibleMeetsAllRequirements() {
        scholarship.setMinGPA(3.0);
        scholarship.setMinCredits(12);

        boolean eligible = scholarship.isEligible(3.5, 15, 45000.0, "Computer Science");

        assertTrue(eligible);
    }

    @Test
    void testIsEligibleBelowMinGPA() {
        scholarship.setMinGPA(3.5);
        scholarship.setMinCredits(12);

        boolean eligible = scholarship.isEligible(3.2, 15, 45000.0, "Computer Science");

        assertFalse(eligible);
    }

    @Test
    void testIsEligibleBelowMinCredits() {
        scholarship.setMinGPA(3.0);
        scholarship.setMinCredits(15);

        boolean eligible = scholarship.isEligible(3.5, 12, 45000.0, "Computer Science");

        assertFalse(eligible);
    }

    @Test
    void testIsEligibleNeedsBasedExceedsIncome() {
        scholarship.setMinGPA(3.0);
        scholarship.setMinCredits(12);
        scholarship.setNeedsBased(true);

        boolean eligible = scholarship.isEligible(3.5, 15, 70000.0, "Computer Science");

        assertFalse(eligible);
    }

    @Test
    void testIsEligibleNeedsBasedWithinIncome() {
        scholarship.setMinGPA(3.0);
        scholarship.setMinCredits(12);
        scholarship.setNeedsBased(true);

        boolean eligible = scholarship.isEligible(3.5, 15, 50000.0, "Computer Science");

        assertTrue(eligible);
    }

    @Test
    void testIsEligibleDepartmentSpecificMatches() {
        scholarship.setMinGPA(3.0);
        scholarship.setMinCredits(12);
        scholarship.setDepartment("Computer Science");

        boolean eligible = scholarship.isEligible(3.5, 15, 45000.0, "Computer Science");

        assertTrue(eligible);
    }

    @Test
    void testIsEligibleDepartmentSpecificNoMatch() {
        scholarship.setMinGPA(3.0);
        scholarship.setMinCredits(12);
        scholarship.setDepartment("Computer Science");

        boolean eligible = scholarship.isEligible(3.5, 15, 45000.0, "Mathematics");

        assertFalse(eligible);
    }

    @Test
    void testIsEligibleGeneralDepartment() {
        scholarship.setMinGPA(3.0);
        scholarship.setMinCredits(12);
        scholarship.setDepartment("GENERAL");

        boolean eligible = scholarship.isEligible(3.5, 15, 45000.0, "Mathematics");

        assertTrue(eligible);
    }

    @Test
    void testIsEligibleInvalidGPA() {
        scholarship.setMinGPA(3.0);

        boolean eligible = scholarship.isEligible(-1.0, 15, 45000.0, "Computer Science");

        assertFalse(eligible);
    }

    @Test
    void testIsEligibleNegativeIncome() {
        scholarship.setMinGPA(3.0);

        boolean eligible = scholarship.isEligible(3.5, 15, -1000.0, "Computer Science");

        assertFalse(eligible);
    }

    @Test
    void testCalculateRenewalAmountMaintainedGPA() {
        scholarship.setMinGPA(3.0);

        double renewal = scholarship.calculateRenewalAmount(5000.0, 3.5, 3.4);

        assertEquals(5000.0, renewal);
    }

    @Test
    void testCalculateRenewalAmountSlightDrop() {
        scholarship.setMinGPA(3.0);

        double renewal = scholarship.calculateRenewalAmount(5000.0, 3.3, 3.4);

        assertEquals(3750.0, renewal);
    }

    @Test
    void testCalculateRenewalAmountModerateDropAboveMin() {
        scholarship.setMinGPA(3.0);

        double renewal = scholarship.calculateRenewalAmount(5000.0, 2.8, 3.4);

        assertEquals(2500.0, renewal);
    }

    @Test
    void testCalculateRenewalAmountBelowThreshold() {
        scholarship.setMinGPA(3.0);

        double renewal = scholarship.calculateRenewalAmount(5000.0, 2.5, 3.4);

        assertEquals(0.0, renewal);
    }

    @Test
    void testCalculateRenewalAmountInvalidCurrentGPA() {
        scholarship.setMinGPA(3.0);

        double renewal = scholarship.calculateRenewalAmount(5000.0, 5.0, 3.4);

        assertEquals(-1.0, renewal);
    }

    @Test
    void testCalculateRenewalAmountInvalidPreviousGPA() {
        scholarship.setMinGPA(3.0);

        double renewal = scholarship.calculateRenewalAmount(5000.0, 3.5, -1.0);

        assertEquals(-1.0, renewal);
    }

    @Test
    void testCalculateRenewalAmountNegativeOriginalAmount() {
        scholarship.setMinGPA(3.0);

        double renewal = scholarship.calculateRenewalAmount(-1000.0, 3.5, 3.4);

        assertEquals(-1.0, renewal);
    }

    @Test
    void testGettersAndSetters() {
        scholarship.setName("Presidential Scholarship");
        scholarship.setAmount(10000.0);
        scholarship.setType("MERIT_BASED");
        scholarship.setMinGPA(3.8);
        scholarship.setMinCredits(15);
        scholarship.setNeedsBased(true);
        scholarship.setDepartment("Engineering");

        assertEquals("SCH001", scholarship.getScholarshipId());
        assertEquals("Presidential Scholarship", scholarship.getName());
        assertEquals(10000.0, scholarship.getAmount());
        assertEquals("MERIT_BASED", scholarship.getType());
        assertEquals(3.8, scholarship.getMinGPA());
        assertEquals(15, scholarship.getMinCredits());
        assertTrue(scholarship.isNeedsBased());
        assertEquals("Engineering", scholarship.getDepartment());
    }
}