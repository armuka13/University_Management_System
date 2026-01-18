package System_Testing;


import edu.university.main.service.ReportingService;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

@DisplayName("FR-RS: Reporting Service System Tests")
public class ReportingServiceSystemTest {

    private ReportingService reportingService;

    @BeforeEach
    void setUp() {
        reportingService = new ReportingService();
    }

    @Test
    @DisplayName("FR-RS-001: Graduation Rate")
    void testGraduationRate() {
        double rate = reportingService.calculateGraduationRate(425, 500);
        assertEquals(85.0, rate, 0.01, "425/500 = 85%");
    }

    @Test
    @DisplayName("FR-RS-002: Average Time to Graduation")
    void testAverageTimeToGraduation() {
        List<Integer> semesters = Arrays.asList(8, 9, 10, 7, 8);
        double average = reportingService.calculateAverageTimeToGraduation(semesters);
        assertEquals(8.4, average, 0.01, "Average of semesters");
    }

    @Test
    @DisplayName("FR-RS-002: Average Time to Graduation - With Invalid Values")
    void testAverageTimeToGraduation_WithInvalidValues() {
        List<Integer> semesters = Arrays.asList(8, 25, 10, -2, 9);
        double average = reportingService.calculateAverageTimeToGraduation(semesters);
        assertEquals(9.0, average, 0.01, "Should skip invalid values (>20 or <1)");
    }

    @Test
    @DisplayName("FR-RS-003: Department Revenue")
    void testDepartmentRevenue() {
        double revenue = reportingService.calculateDepartmentRevenue(200, 15000.0, 500000.0);
        assertEquals(3500000.0, revenue, 0.01, "(200*15000) + 500000");
    }

    @Test
    @DisplayName("FR-RS-004: Institutional Health - Excellent")
    void testInstitutionalHealth_Excellent() {
        String health = reportingService.evaluateInstitutionalHealth(90.0, 88.0, 92.0);
        assertEquals("EXCELLENT", health, "Weighted score >= 85");
    }

    @Test
    @DisplayName("FR-RS-004: Institutional Health - Good")
    void testInstitutionalHealth_Good() {
        String health = reportingService.evaluateInstitutionalHealth(75.0, 72.0, 78.0);
        assertEquals("GOOD", health, "Weighted score 70-84.9");
    }

    @Test
    @DisplayName("FR-RS-004: Institutional Health - Fair")
    void testInstitutionalHealth_Fair() {
        String health = reportingService.evaluateInstitutionalHealth(60.0, 58.0, 62.0);
        assertEquals("FAIR", health, "Weighted score 55-69.9");
    }

    @Test
    @DisplayName("FR-RS-004: Institutional Health - Poor")
    void testInstitutionalHealth_Poor() {
        String health = reportingService.evaluateInstitutionalHealth(45.0, 50.0, 48.0);
        assertEquals("POOR", health, "Weighted score < 55");
    }

    @Test
    @DisplayName("FR-RS-005: Cost per Graduate")
    void testCostPerGraduate() {
        double cost = reportingService.calculateCostPerGraduate(5000000.0, 250);
        assertEquals(20000.0, cost, 0.01, "5000000/250 = 20000");
    }
}