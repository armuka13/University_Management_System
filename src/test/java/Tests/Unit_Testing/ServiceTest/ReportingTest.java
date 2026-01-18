package Tests.Unit_Testing.ServiceTest;

import edu.university.main.service.ReportingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReportingTest {

    private ReportingService service;

    @BeforeEach
    void setUp() {
        service = new ReportingService();
    }

    @Test
    void testCalculateGraduationRate() {
        double rate = service.calculateGraduationRate(80, 100);

        assertEquals(80.0, rate);
    }

    @Test
    void testCalculateGraduationRatePerfect() {
        double rate = service.calculateGraduationRate(100, 100);

        assertEquals(100.0, rate);
    }

    @Test
    void testCalculateGraduationRateLow() {
        double rate = service.calculateGraduationRate(45, 100);

        assertEquals(45.0, rate);
    }

    @Test
    void testCalculateGraduationRateNegativeGraduated() {
        double rate = service.calculateGraduationRate(-10, 100);

        assertEquals(-1.0, rate);
    }

    @Test
    void testCalculateGraduationRateZeroTotal() {
        double rate = service.calculateGraduationRate(50, 0);

        assertEquals(-1.0, rate);
    }

    @Test
    void testCalculateGraduationRateExceedsTotal() {
        double rate = service.calculateGraduationRate(110, 100);

        assertEquals(-1.0, rate);
    }

    @Test
    void testCalculateAverageTimeToGraduation() {
        List<Integer> semesters = Arrays.asList(8, 10, 9, 8);

        double average = service.calculateAverageTimeToGraduation(semesters);

        assertEquals(8.75, average);
    }

    @Test
    void testCalculateAverageTimeToGraduationWithInvalid() {
        List<Integer> semesters = Arrays.asList(8, 25, 9, -5, 10);

        double average = service.calculateAverageTimeToGraduation(semesters);

        assertEquals(9.0, average);
    }

    @Test
    void testCalculateAverageTimeToGraduationAllInvalid() {
        List<Integer> semesters = Arrays.asList(0, -5, 25, 30);

        double average = service.calculateAverageTimeToGraduation(semesters);

        assertEquals(-1.0, average);
    }

    @Test
    void testCalculateAverageTimeToGraduationNull() {
        double average = service.calculateAverageTimeToGraduation(null);

        assertEquals(-1.0, average);
    }

    @Test
    void testCalculateAverageTimeToGraduationEmpty() {
        List<Integer> semesters = Arrays.asList();

        double average = service.calculateAverageTimeToGraduation(semesters);

        assertEquals(-1.0, average);
    }

    @Test
    void testCalculateDepartmentRevenue() {
        double revenue = service.calculateDepartmentRevenue(500, 10000.0, 250000.0);

        assertEquals(5250000.0, revenue);
    }

    @Test
    void testCalculateDepartmentRevenueNoGrants() {
        double revenue = service.calculateDepartmentRevenue(500, 10000.0, 0.0);

        assertEquals(5000000.0, revenue);
    }

    @Test
    void testCalculateDepartmentRevenueNoStudents() {
        double revenue = service.calculateDepartmentRevenue(0, 10000.0, 250000.0);

        assertEquals(250000.0, revenue);
    }

    @Test
    void testCalculateDepartmentRevenueNegativeStudents() {
        double revenue = service.calculateDepartmentRevenue(-10, 10000.0, 250000.0);

        assertEquals(-1.0, revenue);
    }

    @Test
    void testCalculateDepartmentRevenueNegativeTuition() {
        double revenue = service.calculateDepartmentRevenue(500, -100.0, 250000.0);

        assertEquals(-1.0, revenue);
    }

    @Test
    void testCalculateDepartmentRevenueNegativeGrants() {
        double revenue = service.calculateDepartmentRevenue(500, 10000.0, -1000.0);

        assertEquals(-1.0, revenue);
    }

    @Test
    void testEvaluateInstitutionalHealthExcellent() {
        String health = service.evaluateInstitutionalHealth(90.0, 88.0, 92.0);

        assertEquals("EXCELLENT", health);
    }

    @Test
    void testEvaluateInstitutionalHealthGood() {
        String health = service.evaluateInstitutionalHealth(75.0, 72.0, 78.0);

        assertEquals("GOOD", health);
    }

    @Test
    void testEvaluateInstitutionalHealthFair() {
        String health = service.evaluateInstitutionalHealth(60.0, 58.0, 62.0);

        assertEquals("FAIR", health);
    }

    @Test
    void testEvaluateInstitutionalHealthPoor() {
        String health = service.evaluateInstitutionalHealth(45.0, 48.0, 50.0);

        assertEquals("POOR", health);
    }

    @Test
    void testEvaluateInstitutionalHealthInvalidLow() {
        String health = service.evaluateInstitutionalHealth(-5.0, 88.0, 92.0);

        assertEquals("INVALID", health);
    }

    @Test
    void testEvaluateInstitutionalHealthInvalidHigh() {
        String health = service.evaluateInstitutionalHealth(90.0, 110.0, 92.0);

        assertEquals("INVALID", health);
    }

    @Test
    void testCalculateCostPerGraduate() {
        double cost = service.calculateCostPerGraduate(5000000.0, 500);

        assertEquals(10000.0, cost);
    }

    @Test
    void testCalculateCostPerGraduateHighCost() {
        double cost = service.calculateCostPerGraduate(10000000.0, 250);

        assertEquals(40000.0, cost);
    }

    @Test
    void testCalculateCostPerGraduateLowCost() {
        double cost = service.calculateCostPerGraduate(2000000.0, 1000);

        assertEquals(2000.0, cost);
    }

    @Test
    void testCalculateCostPerGraduateNegativeCost() {
        double cost = service.calculateCostPerGraduate(-100000.0, 500);

        assertEquals(-1.0, cost);
    }

    @Test
    void testCalculateCostPerGraduateZeroGraduates() {
        double cost = service.calculateCostPerGraduate(5000000.0, 0);

        assertEquals(-1.0, cost);
    }

    @Test
    void testCalculateCostPerGraduateNegativeGraduates() {
        double cost = service.calculateCostPerGraduate(5000000.0, -50);

        assertEquals(-1.0, cost);
    }
}