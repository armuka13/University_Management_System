package Tests.BVT;

import edu.university.main.service.FinancialService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FinancialServiceLateFeeTest {

    FinancialService service = new FinancialService();

    @Test
    public void invalidNegativeDays() {
        assertEquals(-1.0, service.applyLateFee(100.0, -1), 0.01);
    }

    @Test
    public void invalidNegativeBaseAmount() {
        assertEquals(-1.0, service.applyLateFee(-50.0, 5), 0.01);
    }

    @Test
    public void noLateFeeWhenDaysZero() {
        assertEquals(100.0, service.applyLateFee(100.0, 0), 0.01);
    }

    @Test
    public void LateOneDay() {
        assertEquals(125.0, service.applyLateFee(100.0, 1), 0.01);
    }

    @Test
    public void FeeSevenDays() {
        assertEquals(125.0, service.applyLateFee(100.0, 7), 0.01);
    }

    @Test
    public void TwoPercentFeeEightDays() {
        assertEquals(102.0, service.applyLateFee(100.0, 8), 0.01);
    }

    @Test
    public void TwoPercentFeeFifteenDays() {
        assertEquals(102.0, service.applyLateFee(100.0, 15), 0.01);
    }

    @Test
    public void FivePercentFeeSixteenDays() {
        assertEquals(105.0, service.applyLateFee(100.0, 16), 0.01);
    }

    @Test
    public void FivePercentFeeThirtyDays() {
        assertEquals(105.0, service.applyLateFee(100.0, 30), 0.01);
    }

    @Test
    public void TenPercentFeeThirtyOneDays() {
        assertEquals(110.0, service.applyLateFee(100.0, 31), 0.01);
    }

}