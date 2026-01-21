package Tests.ECT;

import edu.university.main.service.FinancialService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FinancialServiceECTTest {
    FinancialService financialService=new FinancialService();

    @Test
    public void testInvalidGPA_LessThanZero() {
        assertEquals(-1.0, financialService.calculateScholarshipAmount(-0.5, 12, 10000));
    }

    @Test
    public void testInvalidGPA_GreaterThanFour() {
        assertEquals(-1.0, financialService.calculateScholarshipAmount(4.5, 12, 10000));
    }

    @Test
    public void testInvalidCreditHours() {
        assertEquals(-1.0, financialService.calculateScholarshipAmount(3.5, -5, 10000));
    }

    @Test
    public void testInvalidBaseTuition() {
        assertEquals(-1.0, financialService.calculateScholarshipAmount(3.5, 12, -1000));
    }

    @Test
    public void testFullScholarship() {
        assertEquals(20000.0, financialService.calculateScholarshipAmount(4.0, 16, 20000));
    }

    @Test
    public void testHighScholarship() {
        assertEquals(15000.0, financialService.calculateScholarshipAmount(3.8, 15, 20000));
    }

    @Test
    public void testMediumScholarship() {
        assertEquals(10000.0, financialService.calculateScholarshipAmount(3.6, 12, 20000));
    }

    @Test
    public void testLowScholarship() {
        assertEquals(5000.0, financialService.calculateScholarshipAmount(3.2, 12, 20000));
    }

    @Test
    public void testNoScholarship_LowGPA() {
        assertEquals(0.0, financialService.calculateScholarshipAmount(2.8, 12, 20000));
    }

    @Test
    public void testNoScholarship_LowCreditHours() {
        assertEquals(0.0, financialService.calculateScholarshipAmount(3.8, 10, 20000));
    }

}